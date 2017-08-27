package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

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
	static Connection getMysqlConnection(String host, String port, String user, String passwd) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/test";
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
	public static List<Map<String, String>> getBases(Connection con){
		return null;
	}
	
	/**
	 * 获取数据库的缓存信息
	 * @param con
	 * @return
	 */
	public static List<Map<String, String>> getCaches(Connection con) {
		return null;
	}
	
	/**
	 * 获取数据库的复制信息
	 * @param con
	 * @return
	 */
	public static List<Map<String, String>> getReplications(Connection con) {
		return null;
	}
}
