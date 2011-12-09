package net.shopin.alipay.util;

/**
 * 封装返回结果
 * 如果success为false,则会设置errorCode和msg 
 * @author wchao
 *
 * @param <E>
 */
public class Result<E> {
	private boolean success;//是否成功
	private String errorCode;//错误编码
	private String msg;//返回信息
	private E data;//结果集
	private Throwable exception;

	public Result() {
		super();
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
