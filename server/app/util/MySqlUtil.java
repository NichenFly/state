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
public class MySqlUtil {
	static final int TIMEOUT = Integer.parseInt(Play.configuration.getProperty("db.timeout", "10"));

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
			// 10s连接不上即认为连接失败
			DriverManager.setLoginTimeout(10); 
			con = DriverManager.getConnection(url, user, passwd);
		} catch (Exception e) {
			Logger.error("无法连接服务器: %s", host);
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
		Map<String, String> infoMap = new HashMap<String, String>(16);
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
	public static List<Map<String, Object>> getReplications(Connection con, String type) {
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
		String slaveSql = "show slave status";
		String masterSql = "show master status";
		boolean isSlaveTask = "slave".equals(type);
		String sql = isSlaveTask ? slaveSql : masterSql;
		try {
			PreparedStatement prep = con.prepareStatement(sql);
			ResultSet resultSet = prep.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> infoMap = new HashMap<String, Object>(16);
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					infoMap.put(columnName, resultSet.getString(columnName));
				}
				if (isSlaveTask) {
					infoMap.put("host", "Master:" + infoMap.get("Master_Host"));
					String ioRunning = infoMap.get("Slave_IO_Running").toString();
					String sqlRunning = infoMap.get("Slave_SQL_Running").toString();
					if ("Yes".equals(ioRunning) && "Yes".equals(sqlRunning)) {
						infoMap.put("hasError", false);
					} else {
						infoMap.put("hasError", true);
					}
				} else {
					infoMap.put("host", "Self");
				}
				
				infoList.add(infoMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}
}
