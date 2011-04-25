package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class PBX extends BaseObject {
	private java.lang.Long id;
	private java.lang.String brand;
	private java.lang.String model;
	private java.lang.String capacity;
	private java.lang.String sn;
	private com.digitalchina.itil.config.extlist.entity.PBXtype type;
	private java.lang.String analTelLicenseNumber;
	private java.lang.String digiTelLicenseNumber;
	private java.lang.String ipTelLicenseNumber;
	private java.lang.String analTelLicenseUsed;
	private java.lang.String digiTelLicenseUsed;
	private java.lang.String ipTelLicenseUsed;
	private java.lang.String cisn;
	private com.digitalchina.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getBrand() {
		return brand;
	}

	public void setBrand(java.lang.String brand) {
		this.brand = brand;
	}

	public java.lang.String getModel() {
		return model;
	}

	public void setModel(java.lang.String model) {
		this.model = model;
	}

	public java.lang.String getCapacity() {
		return capacity;
	}

	public void setCapacity(java.lang.String capacity) {
		this.capacity = capacity;
	}

	public java.lang.String getSn() {
		return sn;
	}

	public void setSn(java.lang.String sn) {
		this.sn = sn;
	}

	public com.digitalchina.itil.config.extlist.entity.PBXtype getType() {
		return type;
	}

	public void setType(com.digitalchina.itil.config.extlist.entity.PBXtype type) {
		this.type = type;
	}

	public java.lang.String getAnalTelLicenseNumber() {
		return analTelLicenseNumber;
	}

	public void setAnalTelLicenseNumber(java.lang.String analTelLicenseNumber) {
		this.analTelLicenseNumber = analTelLicenseNumber;
	}

	public java.lang.String getDigiTelLicenseNumber() {
		return digiTelLicenseNumber;
	}

	public void setDigiTelLicenseNumber(java.lang.String digiTelLicenseNumber) {
		this.digiTelLicenseNumber = digiTelLicenseNumber;
	}

	public java.lang.String getIpTelLicenseNumber() {
		return ipTelLicenseNumber;
	}

	public void setIpTelLicenseNumber(java.lang.String ipTelLicenseNumber) {
		this.ipTelLicenseNumber = ipTelLicenseNumber;
	}

	public java.lang.String getAnalTelLicenseUsed() {
		return analTelLicenseUsed;
	}

	public void setAnalTelLicenseUsed(java.lang.String analTelLicenseUsed) {
		this.analTelLicenseUsed = analTelLicenseUsed;
	}

	public java.lang.String getDigiTelLicenseUsed() {
		return digiTelLicenseUsed;
	}

	public void setDigiTelLicenseUsed(java.lang.String digiTelLicenseUsed) {
		this.digiTelLicenseUsed = digiTelLicenseUsed;
	}

	public java.lang.String getIpTelLicenseUsed() {
		return ipTelLicenseUsed;
	}

	public void setIpTelLicenseUsed(java.lang.String ipTelLicenseUsed) {
		this.ipTelLicenseUsed = ipTelLicenseUsed;
	}

	public java.lang.String getCisn() {
		return cisn;
	}

	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(com.digitalchina.info.framework.security.entity.UserInfo createUser) {
		this.createUser = createUser;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(com.digitalchina.info.framework.security.entity.UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public java.util.Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
