package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
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
 * @author nichen
 * date: 2017-08-27
 */
public class MySqlDBUtil {
	
	/**
	 * 获取数据库连接
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
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 获取数据库的基本信息
	 * @param con
	 * @return
	 */
	public static List<String> getBases(String host, String port, String user, String passwd){
		String cmd = "mysql " + "-h" + host + " -u" + user + " -p" + passwd + " -e status";
		System.out.println(cmd);
		List<String> infos = new ArrayList<String>();
		try {
//			Process process = Runtime.getRuntime().exec(cmd, null, Play.applicationPath);
			Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
			int statusCode = process.waitFor();
			if (statusCode == 0) {
				InputStream inputStream = process.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				StringBuilder stringBuilder = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line + "\n");
					if(line.startsWith("msyql") || line.startsWith("Uptime")){
						infos.add(line);
					} else if (line.startsWith("Threads")) {
						String[] spliteds = line.split("  ");
						for(String s : spliteds){
							infos.add(s);
						}
					}
				}
				inputStream.close();
				bufferedReader.close();
				Logger.info("%s", stringBuilder.toString());
			} else {
				// 非正确退出,输出错误信息
				InputStream errorInputStream = process.getErrorStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorInputStream));
				String line = null;
				StringBuilder stringBuilder = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line + "\n");
					Logger.error("%s", line);
				}
				errorInputStream.close();
				bufferedReader.close();
				Logger.error("错误信息: %s", stringBuilder.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
		return infos;
	}
	
	/**
	 * 获取数据库的缓存信息
	 * @param con
	 * @return
	 */
	public static List<Map<String, String>> getCaches(Connection con) {
		String sql = "show variables like '%cache%'";
		try {
			PreparedStatement prep = con.prepareStatement(sql);
			ResultSet set =  prep.executeQuery();
			ResultSetMetaData metaData = set.getMetaData();
			int columns = metaData.getColumnCount();
			System.out.println(columns);
			for(int i = 1; i <= columns; i++){
				System.out.println(metaData.getColumnLabel(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取数据库的复制信息
	 * @param con
	 * @return
	 */
	public static List<Map<String, String>> getReplications(Connection con) {
		String sql = "show slave status";
		try {
			PreparedStatement prep = con.prepareStatement(sql);
			ResultSet set =  prep.executeQuery();
			ResultSetMetaData metaData = set.getMetaData();
			int columns = metaData.getColumnCount();
			System.out.println(columns);
			for(int i = 1; i <= columns; i++){
				System.out.println(metaData.getColumnLabel(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
