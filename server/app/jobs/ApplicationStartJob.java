package jobs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import play.cache.Cache;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * 应用程序正式启动前需要处理的任务
 * @author nichen
 * 
 */
@OnApplicationStart
public class ApplicationStartJob extends Job{
	public static final String MYSQL_DESC_KEY = "mysql_desc";
	
	@Override
	public void doJob(){
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(
					new InputStreamReader(new FileInputStream("conf/resources/mysql-bases-desc"), "UTF-8"));
			Map<String, String> descMap = new HashMap<String, String>(16);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				int index = line.indexOf(":");
				String key = line.substring(0, index);
				String value = line.substring(index + 1, line.length());
				if (key == null || key.trim().equals("") || value == null || value.trim().equals("")) {
					continue;
				}
				descMap.put(key, value);
			}
			Cache.set(MYSQL_DESC_KEY, descMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
