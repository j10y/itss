package com.digitalchina.itil.actor.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class RAndBUserList extends BaseObject{
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
