package com.zsgj.info.framework.exception.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class LogRecord extends BaseObject{
	private Long id;
	private String message;
	private String entityClass;
	private String userId;
	private String createDate;
	private String modifyDate;
}
