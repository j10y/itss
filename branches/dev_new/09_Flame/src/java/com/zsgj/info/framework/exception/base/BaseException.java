// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BaseException.java

package com.zsgj.info.framework.exception.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ExceptionMessageFactory;

/**
 * 基础错误信息类
 * @Class Name BaseException
 * @Author zhangpeng
 * @Create In Mar 6, 2008
 */
@SuppressWarnings("serial")
public class BaseException extends RuntimeException {

	protected long errorCode;
	
	protected static ContextHolder cx = ContextHolder.getInstance();
	
	protected final Log logger = LogFactory.getLog("servicelog");
	
	
	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(long errorCode) {
		super(getMessage(errorCode));
		this.errorCode = errorCode;
	}

	public BaseException(String message, long errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessageAndErrorCode() {
		if(errorCode==0){
			return super.getMessage();
		}
		return super.getMessage() + "<br/> 错误号:" + errorCode;
	}
	
	/**
	 * 获取错误信息
	 * @Methods Name getMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param errorCode 错误号
	 * @return String
	 */
	@SuppressWarnings("static-access")
	private static String getMessage(long errorCode){
		String message ;
		String isDefault = cx.getApplicationContext().getMessage("system.isDefault", new Object[0], cx.getLocal());
		String path = cx.getApplicationContext().getMessage("system.custPath", new Object[0], cx.getLocal());
		
		if(Boolean.valueOf(isDefault).booleanValue() == true){
			message = ExceptionMessageFactory.getInstance(true, "").getExceptionMessage(String.valueOf(errorCode));
		}else{
			message = ExceptionMessageFactory.getInstance(false, path).getExceptionMessage(String.valueOf(errorCode));
		}
		
		return message;
	}
}
