package notifiers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import jobs.UpdateJob;
import play.Logger;
import play.Play;
import play.mvc.Mailer;
import util.DateUtil;
import util.NotifyUtil;

public class MailNotifier extends Mailer{
	
	static final String emailFrom = Play.configuration.getProperty("mail.from", "notify@system.com");
	static final String applicationConf = "conf/application.conf";
	
	public static void mkNotify(String receiver, List<Map<String, String>> data) {
		if (receiver == null || receiver.trim().equals("") || data == null || data.size() == 0) {
			return;
		}
		
		try {
			Play.configuration.load(new FileInputStream(new File(applicationConf)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
		
		if (!Pattern.matches(regex, receiver)) {
			return;
		}
		
		String subject = "监控的主机出现问题";
		
		// 发件人
		setFrom(emailFrom);
		
		// 收件人
		addRecipient(receiver);
		
		// 主题
		setSubject(subject);
		
		String time = DateUtil.date2String(null, null);
		
		// 使用 app/views/MailNotify/mkNotify 模板
		try {
			if (send(time, data).get()) {
				Logger.info("send email to %s success ", receiver);
			} else {
				UpdateJob.justErrorReceiversSet.add(receiver);
				Logger.error("send email to %s faild", receiver);
			}
		}catch (Exception e) {
			UpdateJob.justErrorReceiversSet.add(receiver);
			Logger.error(e, "send email to %s error", receiver);
		}
	}
}
