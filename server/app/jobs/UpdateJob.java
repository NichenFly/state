package jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public static final String CACHE_KEY = "caches";
	public static final String REPLICATION_KEY = "replications";

	@Override
	public void doJob() {

		Logger.info("Update Job Start ....");

		List<Map<String, String>> hosts = CommonUtil.getDBs();

		for (Map<String, String> host : hosts) {

			String mysqlHost = host.get("ip");
			String port = host.get("port");
			String user = host.get("user");
			String passwd = host.get("passwd");

			

			Logger.info("Get %s's Bases info ...", mysqlHost);
			Map<String, String> basesMap = MySqlDBUtil.getBases(mysqlHost, port, user, passwd);
			Cache.safeSet(BASES_KEY + "_" + mysqlHost, resolveBasesData(mysqlHost, basesMap), "1mn");

			Logger.info("Get %s's Caches info ...", mysqlHost);
			Map<String, String> cacheMap = null;
			Connection con = MySqlDBUtil.getMysqlConnection(mysqlHost, port, user, passwd);
			if(con != null){
				cacheMap = MySqlDBUtil.getCaches(con);
			} else {
				cacheMap = new HashMap<String, String>();
				cacheMap.put("state", "无法连接");
			}
			
			Cache.safeSet(CACHE_KEY + "_" + mysqlHost, resolveCachesData(mysqlHost, cacheMap), "1mn");

			Logger.info("Get %s's Replications info ...", mysqlHost);
			Map<String, String> replicationMap = MySqlDBUtil.getReplications(mysqlHost, port, user, passwd);
			Cache.safeSet(REPLICATION_KEY + "_" + mysqlHost, resolveReplicationsData(mysqlHost, replicationMap), "1mn");
		}

		Logger.info("Update Job End ....");
	}
	
	private Map<String, Object> resolveBasesData(String host, Map<String, String> basesMap) {
		Map<String, Object> basesData = new HashMap<String, Object>();
		
		if(host == null || host.trim().equals("")){
			return basesData;
		}
		
		List<Map<String, Object>> columnsList = new ArrayList<Map<String, Object>>();
		
		basesData.put("title", host);
		
		Map<String, Object> categoryKeyMap = new HashMap<String, Object>();
		categoryKeyMap.put("title", "类别");
		categoryKeyMap.put("key", "category");
		categoryKeyMap.put("width", 170);
		columnsList.add(categoryKeyMap);
		
		Map<String, Object> statusKeyMap = new HashMap<String, Object>();
		statusKeyMap.put("title", "状态");
		statusKeyMap.put("key", "status");
		columnsList.add(statusKeyMap);
		basesData.put("columns", columnsList);
		
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Set<String> keySet = basesMap.keySet();
		for(String key : keySet){
			Map<String, String> dataItemMap = new HashMap<String, String>();
			dataItemMap.put("category", key);
			dataItemMap.put("status", basesMap.get(key));
			dataList.add(dataItemMap);
		}
		
		basesData.put("data", dataList);
		
		return basesData;
	}
	
	private Map<String, Object> resolveCachesData(String host, Map<String, String> cachesMap) {
		Map<String, Object> cachesData = new HashMap<String, Object>();
		
		if(host == null || host.trim().equals("")){
			return cachesData;
		}
		
		List<Map<String, Object>> columnsList = new ArrayList<Map<String, Object>>();
		
		cachesData.put("title", host);
		
		Map<String, Object> variableKeyMap = new HashMap<String, Object>();
		variableKeyMap.put("title", "变量名");
		variableKeyMap.put("key", "variable");
		columnsList.add(variableKeyMap);
		
		Map<String, Object> valueKeyMap = new HashMap<String, Object>();
		valueKeyMap.put("title", "变量值");
		valueKeyMap.put("key", "value");
		columnsList.add(valueKeyMap);
		cachesData.put("columns", columnsList);
		
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Set<String> keySet = cachesMap.keySet();
		for(String key : keySet){
			Map<String, String> dataItemMap = new HashMap<String, String>();
			dataItemMap.put("variable", key);
			dataItemMap.put("value", cachesMap.get(key));
			dataItemMap.put("desc", "This is a desc");
			dataList.add(dataItemMap);
		}
		
		cachesData.put("data", dataList);
		
		return cachesData;
	}
	
	/**
	 * has problems
	 * @param host
	 * @param replicationsMap
	 * @return
	 */
	private Map<String, String> resolveReplicationsData(String host, Map<String, String> replicationsMap) {
		if(host == null || host.trim().equals("")){
			return replicationsMap;
		}
		replicationsMap.put("host", host);
		
		if(replicationsMap.get("state") != null) {
			replicationsMap.put("masterSlave", "-");
			replicationsMap.put("hasError", "yes");
			return replicationsMap;
		}
		
		boolean slave = replicationsMap.containsKey("Slave_IO_State");
		if(slave){
			replicationsMap.put("masterSlave", "slave");
		} else {
			replicationsMap.put("state", "正常");
			replicationsMap.put("hasError", "no");
			replicationsMap.put("masterSlave", "master");
			return replicationsMap;
		}
		
		String slaveIOState = replicationsMap.get("Slave_IO_State");
		
		if(slaveIOState == null || slaveIOState.trim().equals("")){
			replicationsMap.put("state", "未启动slave服务");
			replicationsMap.put("hasError", "no");
		} else if (replicationsMap.get("Slave_SQL_Running").trim().equals("Yes") && replicationsMap.get("Slave_IO_Running").trim().equals("Yes")) {
			replicationsMap.put("state", "正常");
			replicationsMap.put("hasError", "no");
		} else {
			replicationsMap.put("state", "出错");
			replicationsMap.put("hasError", "yes");
		}
		
		return replicationsMap;
	}
}
