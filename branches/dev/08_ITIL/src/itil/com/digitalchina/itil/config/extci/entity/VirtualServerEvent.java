package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class VirtualServerEvent extends BaseObject {
	private java.lang.Long id;
	private java.lang.String cpu;
	private java.lang.String ram;
	private java.lang.String diskSpace;
	private java.lang.String cisn;
	private java.lang.String ipAddress1;
	private java.lang.String ipAddress2;
	private java.lang.String ipAddress3;
	private java.lang.String serverName;
	
	private com.digitalchina.itil.config.extlist.entity.OStype osType;

	private java.lang.String osBrand;

	private java.lang.String osModel;

	private java.lang.String osPackages;
	
	private com.digitalchina.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setCpu(java.lang.String cpu) {
		this.cpu = cpu;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}


	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}

	public void setIpAddress1(java.lang.String ipAddress1) {
		this.ipAddress1 = ipAddress1;
	}

	public void setIpAddress2(java.lang.String ipAddress2) {
		this.ipAddress2 = ipAddress2;
	}

	public void setIpAddress3(java.lang.String ipAddress3) {
		this.ipAddress3 = ipAddress3;
	}

	public void setServerName(java.lang.String serverName) {
		this.serverName = serverName;
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

	public java.lang.String getCpu() {
		return this.cpu;
	}

	public String getRam() {
		return this.ram;
	}

	
	public java.lang.String getDiskSpace() {
		return diskSpace;
	}

	public void setDiskSpace(java.lang.String diskSpace) {
		this.diskSpace = diskSpace;
	}

	public java.lang.String getCisn() {
		return this.cisn;
	}

	public java.lang.String getIpAddress1() {
		return this.ipAddress1;
	}

	public java.lang.String getIpAddress2() {
		return this.ipAddress2;
	}

	public java.lang.String getIpAddress3() {
		return this.ipAddress3;
	}

	public java.lang.String getServerName() {
		return this.serverName;
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

	public com.digitalchina.itil.config.extlist.entity.OStype getOsType() {
		return osType;
	}

	public void setOsType(com.digitalchina.itil.config.extlist.entity.OStype osType) {
		this.osType = osType;
	}

	public java.lang.String getOsBrand() {
		return osBrand;
	}

	public void setOsBrand(java.lang.String osBrand) {
		this.osBrand = osBrand;
	}

	public java.lang.String getOsModel() {
		return osModel;
	}

	public void setOsModel(java.lang.String osModel) {
		this.osModel = osModel;
	}

	public java.lang.String getOsPackages() {
		return osPackages;
	}

	public void setOsPackages(java.lang.String osPackages) {
		this.osPackages = osPackages;
	}
	
}
