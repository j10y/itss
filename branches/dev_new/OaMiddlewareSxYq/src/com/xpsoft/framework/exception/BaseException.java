package com.xpsoft.framework.exception;

import com.xpsoft.framework.context.ContextHolder;
import com.xpsoft.framework.util.PropertiesUtil;
/**
 * 异常处理基类 所有其他自定义异常均要继承此类 
 * 只需要例如：
 * public MyException(String number) {
		super(number);
	}
 * @Class Name BaseException
 * @Author likang
 * @Create In Jul 22, 2010
 */
public class BaseException extends RuntimeException {

	public String errorCode;
	
	protected static ContextHolder cx = ContextHolder.getInstance();
	
	
	public BaseException() {}


	public BaseException(String errorCode) {
		this.errorCode = errorCode;
	}

	public BaseException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessageAndErrorCode() {
		if(errorCode.equals("0")){
			return super.getMessage();
		}
		return super.getMessage() + "<br/> 错误号:" + errorCode;
	}
	
	/**
	 * 获取错误信息
	 * @Methods Name getMessage
	 * @Create In Jul 22, 2010 By likang
	 * @param errorCode
	 * @return String
	 */
	private static String getMessage(String errorCode){
		String message = "" ;
		message = PropertiesUtil.getProperties(errorCode, "You didn't config the exception for this Exception!");
		return message;
	}
	
	/**
	 * 获取错误信息
	 * @Methods Name getMessage
	 * @Create In Jul 22, 2010 By likang
	 * @param errorCode
	 * @return String
	 */
	public String getMessage(){
		String message = "" ;
		message = PropertiesUtil.getProperties(this.errorCode, "You didn't config the exception for this Exception!");
		return message;
	}
}
