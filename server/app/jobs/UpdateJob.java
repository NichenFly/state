package jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import notifiers.MailNotifier;
import play.Logger;
import play.cache.Cache;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import util.CommonUtil;
import util.MySqlUtil;
import util.NotifyUtil;

/**
 * 更新数据的任务
 * @author nichen
 *
 */
@OnApplicationStart
@Every("20s")
public class UpdateJob extends Job {

	public static final String BASES_KEY = "bases";
	public static final String REPLICATION_KEY = "replications";
	public static final String PROBELMS = "problems";
	public static final String BASE_TYPE = "存活状态";
	public static final String REPLICATION_TYPE = "复制状态";
	public static final int RETRY_TIMES = 3;
	
	public static Set<String> justErrorReceiversSet = null;

	@Override
	public void doJob() {

		Logger.info("Update Job Start ....");
		
		// 初始化为空
		justErrorReceiversSet = new HashSet<String>();

		Map<String, Map<String, String>> hostsMap = CommonUtil.getDBs();
		Set<String> hosts = hostsMap.keySet();
		for (String host : hosts) {
			Connection con = null;
			try {
				Map<String, String> hostMap = hostsMap.get(host);
				String mysqlHost = hostMap.get("ip");
				String port = hostMap.get("port");
				String user = hostMap.get("user");
				String passwd = hostMap.get("passwd");
				con = MySqlUtil.getMysqlConnection(mysqlHost, port, user, passwd);
				
				Map<String, String> basesMap = null;
				if (con != null) {
					Logger.info("Get %s's Bases info ...", mysqlHost);
					basesMap = MySqlUtil.getBases(con);

					Logger.info("Get %s's Replications info ...", mysqlHost);
					List<Map<String, Object>> replicationMap = MySqlUtil.getReplications(con, "slave");
					Cache.set(REPLICATION_KEY + "_" + mysqlHost, resolveReplicationsData(mysqlHost, replicationMap));
				}
				
				Cache.set(BASES_KEY + "_" + mysqlHost, resolveBasesData(mysqlHost, basesMap));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		Logger.info("Update Job End ....");
		
		// 邮件提醒
		List<Map<String, String>> errorHostMapList = checkErrors(hosts);
		if (errorHostMapList.size() == 0) {
			return;
		}
		
		if (!NotifyUtil.couldSend()) {
			return;
		}
		
		Logger.info("出现错误, 准备发送邮件提醒...");
		
		Map<String, List<Map<String, String>>> receiverInfoMap = new HashMap<String, List<Map<String, String>>>(16);
		for (Map<String, String> errorHostMap : errorHostMapList) {
			String host = errorHostMap.get("host");
			String emailReceivers = hostsMap.get(host).get("email");
			if (!"".equals(emailReceivers)) {
				String[] receivers = emailReceivers.split(",");
				for (String receiver : receivers) {
					if (receiverInfoMap.containsKey(receiver)) {
						receiverInfoMap.get(receiver).add(errorHostMap);
					} else {
						List<Map<String, String>> preparedErrorHostMapList = new ArrayList<Map<String, String>>();
						preparedErrorHostMapList.add(errorHostMap);
						receiverInfoMap.put(receiver, preparedErrorHostMapList);
					}
				}
			}
		}
		Set<String> receiverSet = receiverInfoMap.keySet();
		for (String receiver : receiverSet) {
			MailNotifier.mkNotify(receiver, receiverInfoMap.get(receiver));
		}
		NotifyUtil.lastSentTime = System.currentTimeMillis();
		
		// 出错的重试TRY_TIMES(3)次
		if (!justErrorReceiversSet.isEmpty()) {
			for (int i = 0; i < RETRY_TIMES; i++) {
				Logger.info("正在进行第 %s 次重试...", i + 1);
				Iterator<String> it = justErrorReceiversSet.iterator();
				while (it.hasNext()) {
					String receiver = it.next();
					it.remove();
					MailNotifier.mkNotify(receiver, receiverInfoMap.get(receiver));
				}
				if (justErrorReceiversSet.isEmpty()) {
					break;
				}
			}
		}
		
		// 重试3次仍出错的, 加入到计划队列中, 过段时间后再次重试
		if (!justErrorReceiversSet.isEmpty()) {
			Logger.info("%s 次重试仍有失败, 加入到重试计划中, 稍后进行再次重试", RETRY_TIMES);
			Iterator<String> it = justErrorReceiversSet.iterator();
			while (it.hasNext()) {
				String receiver = it.next();
				ErrorRetryJob.errorReceiversMap.put(receiver, receiverInfoMap.get(receiver));
				it.remove();
			}
		}
	}
	
	/**
	 * @param host
	 * @param basesMap
	 * @return
	 */
	private Map<String, Object> resolveBasesData(String host, Map<String, String> basesMap) {
		Map<String, Object> data = new HashMap<String, Object>(16);
		data.put("title", host);
		if(host == null || host.trim().equals("") || basesMap == null || basesMap.keySet().isEmpty()){
			data.put("hasError", true);
		} else {
			data.put("hasError", false);
		}
		
		return data;
	}
	
	/**
	 * @param host
	 * @param replicationsMap
	 * @return
	 */
	private Map<String, Object> resolveReplicationsData(String host, List<Map<String, Object>> replicationsMap) {
		Map<String, Object> data = new HashMap<String, Object>(16);
		if(host == null || host.trim().equals("")){
			return data;
		}
		boolean hasError = false;
		List<String> masters = new ArrayList<String>();
		for (Map<String, Object> replication : replicationsMap) {
			masters.add(replication.get("Master_Host").toString());
			if ((boolean) replication.get("hasError")) {
				hasError = true;
			}
		}
		
		data.put("name", host);
		data.put("hasError", hasError);
		data.put("masters", masters);
		
		return data;
	}
	
	private static List<Map<String, String>> checkErrors(Set<String> hosts) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (String host : hosts) {
			Map<String, Object> baseMap = (Map<String, Object>) Cache.get(BASES_KEY + "_" + host);
			if (baseMap != null && baseMap.containsKey("hasError")) {
				if ((boolean) baseMap.get("hasError")) {
					Map<String, String> baseInfo = new HashMap<String, String>(16);
					baseInfo.put("host", host);
					baseInfo.put("type", BASE_TYPE);
					baseInfo.put("problem", "无法连接到服务器");
					data.add(baseInfo);
				}
			}
			
			Map<String, Object> replicationMap = (Map<String, Object>) Cache.get(REPLICATION_KEY + "_" + host);
			if (replicationMap != null && replicationMap.containsKey("hasError")) {
				if ((boolean) replicationMap.get("hasError")) {
					Map<String, String> replicationInfo = new HashMap<String, String>(16);
					replicationInfo.put("host", host);
					replicationInfo.put("type", REPLICATION_TYPE);
					replicationInfo.put("problem", "复制出现错误");
					data.add(replicationInfo);
				}
			}
		}
		Collections.sort(data,new Comparator<Map<String, String>>(){
			@Override
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                return m1.get("type").charAt(0) - m2.get("type").charAt(0);
            }
        });
		return data;
	}
}
