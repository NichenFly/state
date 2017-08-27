package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.Play;

/**
 * 
 * @author nichen date: 2017-08-27
 */
public class CommonUtil {
	static final String resourcesPath = Play.applicationPath + "/conf/resources/";
	static final String mysqlConf = resourcesPath + "mysql.conf";
	static final String redisConf = resourcesPath + "redis.conf";

	/**
	 * 获取数据库连接信息
	 */
	public static List<Map<String, String>> getDBs() {

		List<Map<String, String>> hostList = new ArrayList<Map<String, String>>();

		Play.configuration.clear();
		try {
			Play.configuration.load(new FileInputStream(new File(mysqlConf)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String commonUser = Play.configuration.getProperty("mysql.user");
		String commonPasswd = Play.configuration.getProperty("mysql.passwd");
		String commonPort = Play.configuration.getProperty("mysql.port");

		for (int i = 1; true; i++) {
			String ip = Play.configuration.getProperty("mysql.host" + i);
			if (ip == null) {
				break;
			}
			Map<String, String> hostInfo = new HashMap<String, String>();
			String user = Play.configuration.getProperty("mysql.host" + i + ".user", commonUser);
			String passwd = Play.configuration.getProperty("mysql.host" + i + ".passwd", commonPasswd);
			String port = Play.configuration.getProperty("mysql.host" + i + ".port", commonPort);

			if (user == null || passwd == null || port == null) {
				Logger.error("%s 配置信息不正确,当前配置: port:%s, user:%s, passwd:%s", ip, port, user, passwd);
				continue;
			}

			hostInfo.put("ip", ip);
			hostInfo.put("port", port);
			hostInfo.put("user", user);
			hostInfo.put("passwd", passwd);
			hostList.add(hostInfo);
		}
		return hostList;
	}

	public static List<Map<String, String>> getRedis() {
		List<Map<String, String>> hostList = new ArrayList<Map<String, String>>();

		Play.configuration.clear();
		try {
			Play.configuration.load(new FileInputStream(new File(redisConf)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String commonPasswd = Play.configuration.getProperty("redis.passwd");
		String commonPort = Play.configuration.getProperty("redis.port", "6379");

		for (int i = 1; true; i++) {
			String ip = Play.configuration.getProperty("redis.host" + i);
			if (ip == null) {
				break;
			}
			Map<String, String> hostInfo = new HashMap<String, String>();
			String passwd = Play.configuration.getProperty("redis.host" + i + ".passwd", commonPasswd);
			String port = Play.configuration.getProperty("redis.host" + i + ".port", commonPort);

			hostInfo.put("ip", ip);
			hostInfo.put("port", port);
			
			if(passwd != null){
				hostInfo.put("passwd", passwd);
			}
			
			hostList.add(hostInfo);
		}
		
		return hostList;
	}

}
