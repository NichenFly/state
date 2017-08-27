package models;

public class Result {
	
	public static final int OK = 200;
	public static final int ERROR = 500;
	
	public static final String OK_MSG = "成功";
	public static final String ERR_MSG = "失败";
	
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
