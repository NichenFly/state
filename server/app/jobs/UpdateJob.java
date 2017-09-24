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
import util.MySqlDBUtil;

@OnApplicationStart
@Every("10s")
public class UpdateJob extends Job {

	public static final String BASES_KEY = "bases";
	public static final String REPLICATION_KEY = "replications";
	public static final String PROBELMS = "problems";
	public static final String BASE_TYPE = "存活状态";
	public static final String REPLICATION_TYPE = "复制状态";

	@Override
	public void doJob() {

		Logger.info("Update Job Start ....");

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
				con = MySqlDBUtil.getMysqlConnection(mysqlHost, port, user, passwd);
				
				Map<String, String> basesMap = null;
				if (con != null) {
					Logger.info("Get %s's Bases info ...", mysqlHost);
					basesMap = MySqlDBUtil.getBases(con);

					Logger.info("Get %s's Replications info ...", mysqlHost);
					List<Map<String, Object>> replicationMap = MySqlDBUtil.getReplications(con, "slave");
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
		
		// 没想好怎么发送邮件, 好像比我预想中的要复杂
		List<Map<String, String>> prepareNotifyData = checkErrors(hosts);

		Logger.info("Update Job End ....");
	}
	
	/**
	 * @param host
	 * @param basesMap
	 * @return
	 */
	private Map<String, Object> resolveBasesData(String host, Map<String, String> basesMap) {
		Map<String, Object> data = new HashMap<String, Object>();
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
		Map<String, Object> data = new HashMap<String, Object>();
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
					Map<String, String> baseInfo = new HashMap<String, String>();
					baseInfo.put("host", host);
					baseInfo.put("type", BASE_TYPE);
					baseInfo.put("problem", "无法连接到服务器");
					data.add(baseInfo);
				}
			}
			
			Map<String, Object> replicationMap = (Map<String, Object>) Cache.get(REPLICATION_KEY + "_" + host);
			if (baseMap != null && baseMap.containsKey("hasError")) {
				if ((boolean) baseMap.get("hasError")) {
					Map<String, String> replicationInfo = new HashMap<String, String>();
					replicationInfo.put("host", host);
					replicationInfo.put("type", BASE_TYPE);
					replicationInfo.put("problem", "无法连接到服务器");
					data.add(replicationInfo);
				}
			}
		}
		Collections.sort(data,new Comparator<Map<String, String>>(){
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                return m1.get("type").charAt(0) - m2.get("type").charAt(0);
            }
        });
		return data;
	}
}
