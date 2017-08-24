package controllers;

import play.*;
import play.mvc.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.*;

import org.apache.commons.io.IOUtils;

import models.*;

public class Application extends Controller {

	static List<String> ipList = new ArrayList<String>();
	static String user = Play.configuration.getProperty("mysql.user", "root");
	static String passwd = Play.configuration.getProperty("mysql.passwd", "Root1234@");

	static {
		String ip = Play.configuration.getProperty("mysql.host1");
		for (int i = 2; ip != null; i++) {
			ipList.add(ip);
			ip = Play.configuration.getProperty("mysql.host" + i);
		}
	}

	public static void index() {
		renderTemplate("/public/index.html");
	}

	public static void mysql() {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		try {
			for (String ip : ipList) {

				Logger.info("IP地址: %s", ip);

				Map<String, String> infoMap = new HashMap<String, String>();
				infoMap.put("IP", ip);

				String cmd = "bash shell/mysql-slave.sh " + ip + " " + user + " " + passwd;
				Process process = Runtime.getRuntime().exec(cmd, null, Play.applicationPath);
				int statusCode = process.waitFor();
				if (statusCode == 0) {
					InputStream inputStream = process.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					String line = null;
					StringBuilder stringBuilder = new StringBuilder();
					while ((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line + "\n");
						int colonIndex = line.indexOf(":");
						String key = line.substring(0, colonIndex).trim();
						String value = line.substring(colonIndex + 1).trim();
						if (!"".equals(value)) {
							infoMap.put(key, value);
						}
					}
					
					if(infoMap.get("Slave_SQL_Running").trim().equals("No") || infoMap.get("Slave_IO_Running").trim().equals("No")){
						infoMap.put("hasError", "true");
					} else {
						infoMap.put("hasError", "false");
					}
					
					inputStream.close();
					bufferedReader.close();
					Logger.info("%s", stringBuilder.toString());
					result.put("code", 200);
					result.put("msg", "ok");
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
					result.put("code", 500);
					result.put("msg", "服务器出现错误");
				}
				if(!infoMap.containsKey("Slave_IO_State")){
					infoMap.put("hasError", "false");
				}
				data.add(infoMap);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		result.put("data", data);

		renderJSON(result);
	}

}