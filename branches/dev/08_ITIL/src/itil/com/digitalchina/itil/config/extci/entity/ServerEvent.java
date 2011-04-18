package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ServerEvent extends BaseObject {
	private java.lang.Long id;
	private java.lang.String brand;
	private java.lang.String model;
	private java.lang.String cpuType;
	private java.lang.String cpuNumber;
	private java.lang.String ram;
	private java.lang.String internalDriver;
	private java.lang.String powerConsumption;
	private java.lang.String nicNumber;
	private java.lang.String fcPortNumber;
	private java.lang.String cisn;
	private java.lang.String sn;
	private com.digitalchina.itil.config.extlist.entity.ServerType serverType;
	private java.lang.String ipAddress1;
	private java.lang.String ipAddress2;
	private java.lang.String ipAddress3;
	private java.lang.String mac1;
	private java.lang.String mac2;
	private java.lang.String mac3;
	private com.digitalchina.itil.config.extlist.entity.DiskRaidtype diskRaidType;
	private java.lang.String serverName;

	private com.digitalchina.itil.config.extlist.entity.OStype osType;

	private java.lang.String osBrand;

	private java.lang.String osModel;

	private java.lang.String osPackages;

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
	public java.lang.String getCpuType() {
		return cpuType;
	}
	public void setCpuType(java.lang.String cpuType) {
		this.cpuType = cpuType;
	}
	public java.lang.String getCpuNumber() {
		return cpuNumber;
	}
	public void setCpuNumber(java.lang.String cpuNumber) {
		this.cpuNumber = cpuNumber;
	}
	public java.lang.String getRam() {
		return ram;
	}
	public void setRam(java.lang.String ram) {
		this.ram = ram;
	}
	public java.lang.String getInternalDriver() {
		return internalDriver;
	}
	public void setInternalDriver(java.lang.String internalDriver) {
		this.internalDriver = internalDriver;
	}
	public java.lang.String getPowerConsumption() {
		return powerConsumption;
	}
	public void setPowerConsumption(java.lang.String powerConsumption) {
		this.powerConsumption = powerConsumption;
	}
	public java.lang.String getNicNumber() {
		return nicNumber;
	}
	public void setNicNumber(java.lang.String nicNumber) {
		this.nicNumber = nicNumber;
	}
	public java.lang.String getFcPortNumber() {
		return fcPortNumber;
	}
	public void setFcPortNumber(java.lang.String fcPortNumber) {
		this.fcPortNumber = fcPortNumber;
	}
	public java.lang.String getCisn() {
		return cisn;
	}
	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}
	public java.lang.String getSn() {
		return sn;
	}
	public void setSn(java.lang.String sn) {
		this.sn = sn;
	}
	public com.digitalchina.itil.config.extlist.entity.ServerType getServerType() {
		return serverType;
	}
	public void setServerType(
			com.digitalchina.itil.config.extlist.entity.ServerType serverType) {
		this.serverType = serverType;
	}
	public java.lang.String getIpAddress1() {
		return ipAddress1;
	}
	public void setIpAddress1(java.lang.String ipAddress1) {
		this.ipAddress1 = ipAddress1;
	}
	public java.lang.String getIpAddress2() {
		return ipAddress2;
	}
	public void setIpAddress2(java.lang.String ipAddress2) {
		this.ipAddress2 = ipAddress2;
	}
	public java.lang.String getIpAddress3() {
		return ipAddress3;
	}
	public void setIpAddress3(java.lang.String ipAddress3) {
		this.ipAddress3 = ipAddress3;
	}
	public java.lang.String getMac1() {
		return mac1;
	}
	public void setMac1(java.lang.String mac1) {
		this.mac1 = mac1;
	}
	public java.lang.String getMac2() {
		return mac2;
	}
	public void setMac2(java.lang.String mac2) {
		this.mac2 = mac2;
	}
	public java.lang.String getMac3() {
		return mac3;
	}
	public void setMac3(java.lang.String mac3) {
		this.mac3 = mac3;
	}
	public com.digitalchina.itil.config.extlist.entity.DiskRaidtype getDiskRaidType() {
		return diskRaidType;
	}
	public void setDiskRaidType(
			com.digitalchina.itil.config.extlist.entity.DiskRaidtype diskRaidType) {
		this.diskRaidType = diskRaidType;
	}
	public java.lang.String getServerName() {
		return serverName;
	}
	public void setServerName(java.lang.String serverName) {
		this.serverName = serverName;
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
	public com.digitalchina.info.framework.security.entity.UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(
			com.digitalchina.info.framework.security.entity.UserInfo createUser) {
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
	public void setModifyUser(
			com.digitalchina.info.framework.security.entity.UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}
	public java.util.Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	
}
