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
	static final String SEND_STATE_OFF = "off";
	static final String SNED_EMAIL_SWITCH = Play.configuration.getProperty("notify.send", SEND_STATE_OFF);
	public static String sendPeriod = Play.configuration.getProperty("notify.sendPeriod", "1h");
	static final String MN = "mn";
	static final String HOUR = "h";
	static final String DAY = "d";
	static final int SEND_PERIOD = 2;
	static final int MILLS_OF_SECONDS = 1000;
	static final int SECONDS_OF_MN = 60;
	static final int MNS_OF_HOUR = 60;
	static final int HOURS_OF_DAY = 24;

	/**
	 * 检查是否可以发送提醒
	 * @param lastSent
	 * @return
	 */
	public static boolean couldSend() {
		if (SEND_STATE_OFF.equals(SNED_EMAIL_SWITCH)) {
			Logger.warn("没有开启出错通知, 开启方法: 在conf/application.conf添加或修改notify.send=on");
			return false;
		}
		long period = 0;
		if (sendPeriod.contains(MN) && sendPeriod.length() > SEND_PERIOD) {
			if (!checkPeriod(sendPeriod, MN)) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30mn, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf(MN)));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSentTime >= period * SECONDS_OF_MN * MILLS_OF_SECONDS) {
				return true;
			}
			return false;
		} else if (sendPeriod.contains(HOUR) && sendPeriod.length() >= SEND_PERIOD) {
			if (!checkPeriod(sendPeriod, HOUR)) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf(HOUR)));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSentTime >= period * MNS_OF_HOUR * SECONDS_OF_MN * MILLS_OF_SECONDS) {
				return true;
			}
		} else if (sendPeriod.contains(DAY) && sendPeriod.length() >= SEND_PERIOD) {
			if (!checkPeriod(sendPeriod, DAY)) {
				Logger.error("提醒发送信息的周期配置不正确, 正确的配置如 30m, 1h, 1d等");
				return false;
			}
			period = Long.parseLong(sendPeriod.substring(0, sendPeriod.indexOf(DAY)));
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSentTime >= period * HOURS_OF_DAY * MNS_OF_HOUR * SECONDS_OF_MN * MILLS_OF_SECONDS) {
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
		if (period == null || period.trim().length() < SEND_PERIOD) {
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
