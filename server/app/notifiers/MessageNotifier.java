package notifiers;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import play.Logger;
import play.Play;
import util.NotifyUtil;

/**
 * 发送短信
 * @author nichen
 */
public class MessageNotifier {

	public static void sendMessage(String telephones, String hosts) {
		if (!NotifyUtil.couldSend(NotifyUtil.lastSentMessageTime)) {
			return;
		}
		String phoneList = Play.configuration.getProperty("sms.phone.numbers");
		String appKey = Play.configuration.getProperty("sms.app.key");
		String appSecret = Play.configuration.getProperty("sms.app.secret");
		String signName = Play.configuration.getProperty("sms.signname");
		String templateCode = Play.configuration.getProperty("sms.templateCode");
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", appKey, appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName(signName);
		req.setSmsParamString("监控的机器: " + hosts + "出现异常, 请及时处理");
		req.setRecNum(phoneList);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			String ress = rsp.getBody();
			Logger.info(ress);
			if (ress.contains("true")) {
				NotifyUtil.lastSentMessageTime = System.currentTimeMillis();
				Logger.info("短信发送到 %s 成功", telephones);
			} else {
				Logger.error("短信发送到 %s 失败, 失败相关信息: %s", telephones, ress);
			}
		} catch (ApiException e) {
			Logger.error("短信发送到 %s 失败", telephones);
			e.printStackTrace();
		}

	}
}