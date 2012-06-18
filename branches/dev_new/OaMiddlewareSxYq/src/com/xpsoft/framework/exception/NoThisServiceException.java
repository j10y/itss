package com.xpsoft.framework.exception;
/**
 * 用于getBean时，不存在抛出该异常
 * @Class Name ServiceException
 * @Author likang
 * @Create In Jul 30, 2010
 */
public class NoThisServiceException extends BaseException{

	public NoThisServiceException() {
		super("00000018");
	}
	
	
}
