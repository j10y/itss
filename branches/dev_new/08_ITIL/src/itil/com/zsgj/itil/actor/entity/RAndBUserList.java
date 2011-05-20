package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class RAndBUserList extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -7361052613398779953L;
	private java.lang.Long id;
	private java.lang.String userName;
	private java.lang.String type;//0表示红 1表示黑
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	

}
