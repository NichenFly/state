package notifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import play.Play;
import play.mvc.Mailer;

public class MailNotifier extends Mailer{
	
	public static final String TYPE_BASES = "BASES";
	public static final String TYPE_REPLICATIONS = "REPLICATIONS";
	
	static final String emailFrom = Play.configuration.getProperty("mail.from", "system");
	
	public static void mkNotify(List<String> receivers, Map data) {
		if (receivers.size() == 0 || data.isEmpty()) {
			return;
		}
		String regex = "/^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$/";
		
		List<String> checkedReceivers = new ArrayList<String>();
		for (String receiver : receivers) {
			if (Pattern.matches(regex, receiver)) {
				checkedReceivers.add(receiver);
			}
		}
		// 主题
		String subject = "";
		
		// 内容
		Map<String, Object> content = new HashMap<String, Object>();
		
		// 类型的提醒
//		if (TYPE_BASES.equals(type)) {
//			
//		} else if (TYPE_REPLICATIONS.equals(type)) {
//			
//		}
		
		setFrom(emailFrom);
		setSubject(subject);
		addRecipient(checkedReceivers.toArray());
		
		send(content);
	}
}
