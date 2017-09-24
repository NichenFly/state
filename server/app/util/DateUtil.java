package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public static String date2String(Date date, String format) {
		if (date == null) {
			date = new Date();
		}
		if (format == null || format.trim().equals("")) {
			format = dateFormat;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
}
