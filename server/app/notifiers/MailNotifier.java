package notifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import play.Play;
import play.mvc.Mailer;
import util.DateUtil;
import util.NotifyUtil;

public class MailNotifier extends Mailer{
	
	static final String emailFrom = Play.configuration.getProperty("mail.from", "notify@system.com");
	
	public static void mkNotify(List<String> receivers, List<Map<String, String>> data) {
		if (!NotifyUtil.couldSend(NotifyUtil.lastSentMessageTime)) {
			return;
		}
		
		if (receivers == null || receivers.size() == 0 || data == null || data.size() == 0) {
			return;
		}
		
		NotifyUtil.lastSentMailTime = System.currentTimeMillis();
		
		String regex = "/^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$/";
		
		List<String> checkedReceivers = new ArrayList<String>();
		for (String receiver : receivers) {
			if (Pattern.matches(regex, receiver)) {
				checkedReceivers.add(receiver);
			}
		}
		
		String subject = "监控的主机出现问题";
		
		// 发件人
		setFrom(emailFrom);
		
		// 收件人
		System.out.println(checkedReceivers.toArray());
		addRecipient(checkedReceivers.toArray());
		
		// 主题
		setSubject(subject);
		
		String time = DateUtil.date2String(null, null);
		
		// 使用 app/views/MailNotify/mkNotify 模板
		send(time, data);
	}
}
