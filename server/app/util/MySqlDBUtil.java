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
	public static Map<String, String> getBases(Connection con) {
		Map<String, String> infoMap = new HashMap<String, String>();
		String sql = "show status";
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
	public static List<Map<String, Object>> getReplications(Connection con) {
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
		String sql = "show slave status";
		try {
			PreparedStatement prep = con.prepareStatement(sql);
			ResultSet resultSet = prep.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> infoMap = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					infoMap.put(columnName, resultSet.getString(columnName));
				}
				String ioRunning = infoMap.get("Slave_IO_Running").toString();
				String sqlRunning = infoMap.get("Slave_SQL_Running").toString();
				if ("No".equals(ioRunning) || "No".equals(sqlRunning)) {
					infoMap.put("hasError", true);
				}
				infoList.add(infoMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}
}
