package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class StorageServer extends BaseObject {
	private java.lang.Long id;
	private java.lang.String brand;
	private java.lang.String storageSize;
	private java.lang.String model;
	private java.lang.String cisn;
	private com.digitalchina.itil.config.extlist.entity.DiskRaidtype raidMode1;
	private java.lang.String ipAddress;
	private com.digitalchina.itil.config.extlist.entity.DiskRaidtype raidMode2;
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

	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}

	public void setRaidMode1(
			com.digitalchina.itil.config.extlist.entity.DiskRaidtype raidMode1) {
		this.raidMode1 = raidMode1;
	}

	public void setIpAddress(java.lang.String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setRaidMode2(
			com.digitalchina.itil.config.extlist.entity.DiskRaidtype raidMode2) {
		this.raidMode2 = raidMode2;
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

	public java.lang.String getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(java.lang.String storageSize) {
		this.storageSize = storageSize;
	}

	public java.lang.String getModel() {
		return this.model;
	}

	public java.lang.String getCisn() {
		return this.cisn;
	}

	public com.digitalchina.itil.config.extlist.entity.DiskRaidtype getRaidMode1() {
		return this.raidMode1;
	}

	public java.lang.String getIpAddress() {
		return this.ipAddress;
	}

	public com.digitalchina.itil.config.extlist.entity.DiskRaidtype getRaidMode2() {
		return this.raidMode2;
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
