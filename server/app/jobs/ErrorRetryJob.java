package jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import notifiers.MailNotifier;
import play.jobs.Job;
import play.jobs.On;
import play.jobs.OnApplicationStart;

/**
 * 出错重试的任务
 * @author nichen
 *
 */
@OnApplicationStart
@On("0 0/5 * * * ?")
public class ErrorRetryJob extends Job{
	public static Map<String, List<Map<String, String>>> errorReceiversMap = new HashMap<String, List<Map<String, String>>>();
	@Override
	public void doJob() {
		Set<String> receiverSet = errorReceiversMap.keySet();
		for (String receiver : receiverSet) {
			List<Map<String, String>> data = errorReceiversMap.get(receiver);
			errorReceiversMap.remove(receiver);
			MailNotifier.mkNotify(receiver, data);
		}
	}
}
