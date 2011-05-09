package com.zsgj.info.framework.exception;

import org.springframework.dao.DataAccessException;




public class DaoException extends DataAccessException {

	public DaoException(String msg) {
		super(msg);
	}
	
	public DaoException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	

  
}
