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
public class NotifyUtil {

	public static long lastSentTime = 0L;
	public static String sendPeriod = Play.configuration.getProperty("notify.sendPeriod", "1h");

	/**
	 * 检查是否可以发送提醒
	 * @param lastSent
	 * @return
	 */
	public static boolean couldSend() {
		long period = 0;
		if (sendPeriod.contains("mn") && sendPeriod.length() > 2) {
			if (!checkPeriod(sendPeriod, "mn")) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30mn, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf("mn")));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSentTime >= period * 60 * 1000) {
				return true;
			}
			return false;
		} else if (sendPeriod.contains("h") && sendPeriod.length() >= 2) {
			if (!checkPeriod(sendPeriod, "h")) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf("h")));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSentTime >= period * 60 * 60 * 1000) {
				return true;
			}
		} else if (sendPeriod.contains("d") && sendPeriod.length() >= 2) {
			if (!checkPeriod(sendPeriod, "d")) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30m, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf("d")));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSentTime >= period * 24 * 60 * 60 * 1000) {
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
	public static boolean checkPeriod(String period, String pattern) {
		if (period == null || period.trim().length() < 2) {
			return false;
		}
		boolean isOk = true;
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
