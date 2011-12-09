package net.shopin.alipay.util;

/**
 * ��װ���ؽ��
 * ���successΪfalse,�������errorCode��msg 
 * @author wchao
 *
 * @param <E>
 */
public class Result<E> {
	private boolean success;//�Ƿ�ɹ�
	private String errorCode;//�������
	private String msg;//������Ϣ
	private E data;//�����
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
