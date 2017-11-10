package models;

/**
 * 请求结果
 * @author nichen
 *
 */
public class Result {
	
	public static final int OK = 200;
	public static final int ERROR = 500;
	public static final int AUTH_FAILD = 403;
	public static final int NOT_FOUND = 400;
	
	public static final String OK_MSG = "成功";
	public static final String ERR_MSG = "失败";
	public static final String AUTH_FAILD_MSG = "用户名或密码错误";
	public static final String NOT_FOUND_MSG = "目标主机不在监控配置中";
	
	public int code;
	public String msg;
	public Object data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
