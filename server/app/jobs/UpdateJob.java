package jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

import play.jobs.Job;
import util.CommonUtil;
import util.MySqlDBUtil;

public class UpdateJob extends Job{
	@Override
	public void doJob(){
		List<Map<String, String>> hosts = CommonUtil.getDBs();
		
		for(Map<String, String> host : hosts){
			
			String mysqlHost = host.get("ip");
			String port = host.get("port");
			String user = host.get("user");
			String passwd = host.get("passwd");
			
			Connection con = MySqlDBUtil.getMysqlConnection(mysqlHost, port, user, passwd);
			
			MySqlDBUtil.getBases(mysqlHost, port, user, passwd);
			
			MySqlDBUtil.getCaches(con);
			
			MySqlDBUtil.getReplications(mysqlHost, port, user, passwd);
		
		}
	}
}
