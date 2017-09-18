package util;

import java.util.List;

import play.Logger;
import play.Play;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import models.Result;

/**
 * 提醒
 * 
 * @author nichen
 *
 */
public class Remind {

	static long lastSentMessageTime = System.currentTimeMillis();
	static long lastSentMailTime = System.currentTimeMillis();
	static String sendPeriod = Play.configuration.getProperty("remind.sendPeriod", "1h");

	/**
	 * 发送短信信息
	 */
	public static void sendMessage(String telephones, String hosts) {
		if (!couldSend(lastSentMessageTime)) {
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
				lastSentMessageTime = System.currentTimeMillis();
				Logger.info("短信发送到 %s 成功", telephones);
			} else {
				Logger.error("短信发送到 %s 失败, 失败相关信息: %s", telephones, ress);
			}
		} catch (ApiException e) {
			Logger.error("短信发送到 %s 失败", telephones);
			e.printStackTrace();
		}

	}

	/**
	 * 发送邮件
	 */
	public static void sendEmail() {
		if (!couldSend(lastSentMailTime)) {
			return;
		}

		// lastSentMailTime = System.currentTimeMillis();
	}

	private static boolean couldSend(long lastSent) {
		long period = 0;
		if (sendPeriod.contains("mn") && sendPeriod.length() > 2) {
			if (!checkPeriod(sendPeriod, "mn")) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30mn, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf("mn")));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSent >= period) {
				return true;
			}
			return false;
		} else if (sendPeriod.contains("h") && sendPeriod.length() > 2) {
			if (!checkPeriod(sendPeriod, "h")) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf("h")));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSent >= period) {
				return true;
			}
		} else if (sendPeriod.contains("d") && sendPeriod.length() > 2) {
			if (!checkPeriod(sendPeriod, "d")) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30m, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf("d")));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSent >= period) {
				return true;
			}
		} else {
			Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30m, 1h, 1d等");
		}

		return false;
	}

	/**
	 * 检测mn字符串是否符合要求
	 * 
	 * @param period
	 * @param pattern
	 * @return
	 */
	private static boolean checkPeriod(String period, String pattern) {
		if (period == null || period.trim().length() < 2) {
			return false;
		}
		boolean isOk = false;
		period = period.toLowerCase().substring(0, period.indexOf(pattern));
		for (int i = 0, length = period.length(); i < length; i++) {
			char c = period.charAt(i);
			if ('0' > c || c > '9') {
				isOk = false;
				break;
			}
		}
		return isOk;
	}
}
