package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Parameter;
import javax.persistence.Query;

import jobs.ApplicationStartJob;
import jobs.UpdateJob;
import models.Result;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.db.jpa.JPA;
import play.mvc.*;
import util.CommonUtil;
import util.MySqlDBUtil;

/**
 * MySQL相关信息获取
 * @author nichen
 * date: 2017-09-18
 */
public class MySQLController extends Controller {
    
	/**
	 * 获取基本信息的接口
	 */
    public static void getBases() {
    	Map<String, Map<String, String>> hostsMap = CommonUtil.getDBs();
    	Result result = new Result();
    	
    	List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
    	Set<String> hosts = hostsMap.keySet();
    	for (String host : hosts) {
			Map<String, Object> infos = null;
			
	    	try {
	    		infos = (Map<String, Object>) Cache.get(UpdateJob.BASES_KEY +"_" +host);
	    	}catch (Exception e) {
	    		Logger.error(e, "Get bases from cache error", ""); 
	    	}
	    	
	    	if(infos != null){
	    		infoList.add(infos);
	    	}
    	}
    	
    	result.setCode(Result.OK);
    	result.setMsg(Result.OK_MSG);
    	result.data = infoList;
    	renderJSON(result);
    }
    
    /**
     * 通过主机地址获取当前的状态信息
     * @param host
     */
    public static void getBasesByHost(String host) {
    	Map<String, Map<String, String>> hostsMap = CommonUtil.getDBs();
    	Result result = new Result();
    	Map<String, String> hostMap = hostsMap.get(host);
    	if (hostMap == null) {
    		result.setCode(Result.ERROR);
    		result.setMsg("目标主机不存在");
    		renderJSON(result);
    	}
    	
    	List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();
    	List<Map<String, String>> columnList = new ArrayList<Map<String, String>>();
    	
    	Connection con = MySqlDBUtil.getMysqlConnection(host, hostMap.get("port"), hostMap.get("user"), hostMap.get("passwd"));
    	
    	if (con == null) {
    		Map<String, String> info = new HashMap<String, String>();
    		info.put("category", "State");
    		info.put("status", "无法连接到数据库");
    		info.put("desc", "在连接到数据库时出现问题, 请检查数据库主机, 用户名, 密码, 端口号是否配置正确,目标机器是否授予本监控程序远程访问权限");
    		infoList.add(info);
    	} else {
    		Map<String, String> infoMap = MySqlDBUtil.getBases(con);
        	Map<String, String> descMap = (Map<String, String>) Cache.get(ApplicationStartJob.MYSQL_DESC_KEY);
        	Set<String> keySet = infoMap.keySet();
        	for (String key : keySet) {
        		Map<String, String> info = new HashMap<String, String>();
        		info.put("category", key);
        		info.put("status", infoMap.get(key));
        		info.put("desc",descMap.get(key));
        		infoList.add(info);
        	}
    	}
    	
    	Map<String, String> column = new HashMap<String, String>();
    	column.put("title", "变量");
    	column.put("key", "category");
    	columnList.add(column);
    	
    	column = new HashMap<String, String>();
    	column.put("title", "值");
    	column.put("key", "status");
    	columnList.add(column);
    	
    	Map<String, List<Map<String, String>>> data = new HashMap<String, List<Map<String, String>>>();
    	data.put("data", infoList);
    	data.put("columns", columnList);
    	
    	result.setCode(Result.OK);
    	result.setMsg(Result.OK_MSG);
    	result.setData(data);
    	renderJSON(result);
    }
    
    /**
     * 获取复制基本状态的接口
     */
    public static void getReplications() {
    	Map<String, Map<String, String>> hostsMap = CommonUtil.getDBs();
    	Result result = new Result();
    	
    	List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
    	Set<String> hosts = hostsMap.keySet();
    	for (String host : hosts) {
			Map<String, Object> infos = null;
			
	    	try {
	    		infos = (Map<String, Object>) Cache.get(UpdateJob.REPLICATION_KEY +"_" +host);
	    	}catch (Exception e) {
	    		Logger.error(e, "Get replication status from cache error", ""); 
	    	}
	    	
	    	if(infos != null){
	    		infoList.add(infos);
	    	}
    	}
    	
    	result.setCode(Result.OK);
    	result.setMsg(Result.OK_MSG);
    	result.data = infoList;
    	renderJSON(result);
    }
    
    /**
     * 通过主机地址获取当前复制信息
     * @param host
     */
    public static void getReplicationsByHost(String host) {
    	Map<String, Map<String, String>> hostsMap = CommonUtil.getDBs();
    	Result result = new Result();
    	Map<String, String> hostMap = hostsMap.get(host);
    	if (hostMap == null) {
    		result.setCode(Result.ERROR);
    		result.setMsg("目标主机不存在");
    		renderJSON(result);
    	} 
    	List<Map<String, Object>> infoMap = new ArrayList<Map<String, Object>>();
    	Connection con = MySqlDBUtil.getMysqlConnection(host, hostMap.get("port"), hostMap.get("user"), hostMap.get("passwd"));
    	if (con == null) {
    		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
    		Map<String, Object> info = new HashMap<String, Object>();
    		info.put("host", "Self");
    		info.put("hasError", true);
    		info.put("state", "无法连接到数据库");
    		infoList.add(info);
    		infoMap.addAll(infoList);
    	} else {
    		List<Map<String, Object>> masterInfoMap = MySqlDBUtil.getReplications(con, "master");
        	List<Map<String, Object>> slaveInfoMap = MySqlDBUtil.getReplications(con, "slave");
        	
        	infoMap.addAll(masterInfoMap);
        	infoMap.addAll(slaveInfoMap);
    	}
    	
    	result.setCode(Result.OK);
    	result.setMsg(Result.OK_MSG);
    	result.setData(infoMap);
    	renderJSON(result);
    }

}
