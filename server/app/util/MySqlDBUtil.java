package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.Play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author nichen date: 2017-08-27
 */
public class MySqlDBUtil {

	/**
	 * 获取数据库连接
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static Connection getMysqlConnection(String host, String port, String user, String passwd) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/sys";
			con = DriverManager.getConnection(url, user, passwd);
		} catch (Exception e) {
			Logger.error("无法连接连接服务器: %s", host);
//			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 获取数据库的基本信息
	 * 
	 * @param con
	 * @return
	 */
	public static Map<String, String> getBases(String host, String port, String user, String passwd) {
		String cmd = "mysql " + "-h" + host + " -u" + user + " -p" + passwd + " -e status";
		Map<String, String> infoMap = new HashMap<String, String>();
		try {
			Process process = Runtime.getRuntime().exec(cmd, null, Play.applicationPath);
			int statusCode = process.waitFor();
			if (statusCode == 0) {
				InputStream inputStream = process.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					if (line.startsWith("msyql") || line.startsWith("Uptime")) {
						String[] spliteds = line.split(":");
						infoMap.put(spliteds[0].trim(), spliteds[1].trim());
					} else if (line.startsWith("Threads")) {
						String[] categories = line.split("  ");
						for (String s : categories) {
							String[] spliteds = s.split(":");
							infoMap.put(spliteds[0].trim(), spliteds[1].trim());
						}
					}
				}
				inputStream.close();
				bufferedReader.close();
			} else {
				// 非正确退出,输出错误信息
				
				infoMap.put("state", "连接服务器出错");
				
				InputStream errorInputStream = process.getErrorStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorInputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					Logger.error("%s", line);
				}
				errorInputStream.close();
				bufferedReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return infoMap;
	}

	/**
	 * 获取数据库的缓存信息
	 * 
	 * @param con
	 * @return
	 */
	public static Map<String, String> getCaches(Connection con) {
		Map<String, String> infoMap = new HashMap<String, String>();
		String sql = "show variables like '%cache%'";
		try {
			PreparedStatement prep = con.prepareStatement(sql);
			ResultSet resultSet = prep.executeQuery();
			while (resultSet.next()) {
				infoMap.put(resultSet.getString("Variable_name"), resultSet.getString("Value"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoMap;
	}

	/**
	 * 获取数据库的复制信息
	 * 
	 * @param con
	 * @return
	 */
	public static Map<String, String> getReplications(String host, String port, String user, String passwd) {
		String cmd = "mysql " + "-h" + host + " -P" + port + " -u" + user + " -p" + passwd + " -e\"show slave status\\G\"";
		Map<String, String> infoMap = new HashMap<String, String>();
		try {
			Process process = Runtime.getRuntime().exec(cmd, null, Play.applicationPath);
			int statusCode = process.waitFor();
			if (statusCode == 0) {
				InputStream inputStream = process.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					String[] spliteds = line.split(":");
					if(spliteds.length == 2){
						infoMap.put(spliteds[0].trim(), spliteds[1].trim());
					}
					
				}
				inputStream.close();
				bufferedReader.close();
			} else {
				// 非正确退出,输出错误信息
				
				infoMap.put("state", "连接服务器出错");
				
				InputStream errorInputStream = process.getErrorStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorInputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					Logger.error("%s", line);
				}
				errorInputStream.close();
				bufferedReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return infoMap;
	}
}
