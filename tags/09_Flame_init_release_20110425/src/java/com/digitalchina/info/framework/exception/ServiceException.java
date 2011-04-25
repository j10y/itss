// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ServiceException.java

package com.digitalchina.info.framework.exception;

import com.digitalchina.info.framework.exception.base.BaseException;

/**
 * ·þÎñÒì³£
 * @Class Name ServiceException
 * @author xiaofeng
 * @Create In 2007-10-30

 */
public class ServiceException extends BaseException {
	
	
	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, long errorCode) {
		super(message);
		super.setErrorCode(errorCode);
		logger.error("service exception, errorCode: " + errorCode + "\nMessage:" + message);
		logger.error("--------------------end exception log----------------------------------------");
	}

	public ServiceException(long errorCode) {

		super(errorCode);
		super.setErrorCode(errorCode);
		logger.error("service exception, errorCode: " + errorCode);
		logger.error("--------------------end exception log----------------------------------------");
	}

	public long getErrorCode() {
		return super.getErrorCode();
	}

	public String getMessageAndErrorCode() {
		return super.getMessageAndErrorCode();
	}

	public void setErrorCode(long errorCode) {
		super.setErrorCode(errorCode);
	}
}
