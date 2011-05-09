package com.zsgj.info.framework.exception;

import com.zsgj.info.framework.exception.base.BaseException;

public class RuleFileException extends BaseException{
	
	private static final long serialVersionUID = 1L;
	
	public RuleFileException(){
		super();
	}
	
	public RuleFileException(String msg){
		super(msg);
	}

	public RuleFileException(String msg, Throwable cause){
		super(msg,cause);
	}
	
	public RuleFileException(Throwable cause){
		super(cause);
	}
}
