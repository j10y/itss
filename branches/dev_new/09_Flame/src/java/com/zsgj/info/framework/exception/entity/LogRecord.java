package com.zsgj.info.framework.exception.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class LogRecord extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -3260170929282775635L;
	private Long id;
	private String message;
	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Return the String message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @Param String message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @Return the String entityClass
	 */
	public String getEntityClass() {
		return entityClass;
	}
	/**
	 * @Param String entityClass to set
	 */
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	/**
	 * @Return the String userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @Param String userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @Return the String createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @Param String createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @Return the String modifyDate
	 */
	public String getModifyDate() {
		return modifyDate;
	}
	/**
	 * @Param String modifyDate to set
	 */
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	private String entityClass;
	private String userId;
	private String createDate;
	private String modifyDate;
}
