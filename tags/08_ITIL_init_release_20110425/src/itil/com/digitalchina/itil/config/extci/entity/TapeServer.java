package com.digitalchina.itil.config.extci.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class TapeServer extends BaseObject {
	private java.lang.Long id;
	private java.lang.String brand;
	private java.lang.String model;
	private com.digitalchina.itil.config.extlist.entity.TapeServerType type;
	private java.lang.String cisn;
	private java.lang.String ipAddress;
	private java.lang.String sn;
	private Integer standardHigh;
	private com.digitalchina.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setBrand(java.lang.String brand) {
		this.brand = brand;
	}

	public void setModel(java.lang.String model) {
		this.model = model;
	}

	public void setType(
			com.digitalchina.itil.config.extlist.entity.TapeServerType type) {
		this.type = type;
	}

	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}

	public void setIpAddress(java.lang.String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setSn(java.lang.String sn) {
		this.sn = sn;
	}

	public void setCreateUser(
			com.digitalchina.info.framework.security.entity.UserInfo createUser) {
		this.createUser = createUser;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyUser(
			com.digitalchina.info.framework.security.entity.UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getBrand() {
		return this.brand;
	}

	public java.lang.String getModel() {
		return this.model;
	}

	public com.digitalchina.itil.config.extlist.entity.TapeServerType getType() {
		return this.type;
	}

	public java.lang.String getCisn() {
		return this.cisn;
	}

	public java.lang.String getIpAddress() {
		return this.ipAddress;
	}

	public java.lang.String getSn() {
		return this.sn;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getCreateUser() {
		return this.createUser;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getModifyUser() {
		return this.modifyUser;
	}

	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}

	public Integer getStandardHigh() {
		return standardHigh;
	}

	public void setStandardHigh(Integer standardHigh) {
		this.standardHigh = standardHigh;
	}

}
