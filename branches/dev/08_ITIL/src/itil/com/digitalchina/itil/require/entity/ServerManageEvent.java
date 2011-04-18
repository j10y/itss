package com.digitalchina.itil.require.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ServerManageEvent extends BaseObject {
	private java.lang.Long id;
	private java.lang.Long rootId;
	private java.lang.String name;
	private com.digitalchina.itil.require.entity.ServerManage oldApply;
	private java.lang.Integer processType;
	private java.lang.Integer status;
	private java.lang.Integer deleteFlag;
	private java.lang.Long serviceItem;
	private com.digitalchina.info.framework.security.entity.UserInfo applyUser;
	private java.lang.String userNum;
	private java.lang.String costConter;
	private com.digitalchina.info.framework.security.entity.Department applyDept;
	private java.lang.String tel;
	private java.util.Date applyDate;
	private com.digitalchina.info.framework.security.entity.UserInfo appOffer;
	private java.lang.String offerTel;
	private com.digitalchina.info.framework.security.entity.Department belongDept;
	private com.digitalchina.info.framework.security.entity.Department useDept;
	private com.digitalchina.itil.config.extlist.entity.ServerUseType serverType;
	private java.lang.String serverName;
	private java.lang.String serverModel;
	private java.lang.String serverCpu;
	private java.lang.String serverMomery;
	private java.lang.String serverDisk;
	private java.lang.String serverOs;
	private java.lang.String serverDatebase;
	private java.lang.String serverApp;
	private java.lang.String serverDescn;
	private com.digitalchina.itil.config.entity.ConfigItem configItem;
	private java.util.Date outDate;		//Ǩ��ʱ��
	private com.digitalchina.info.framework.security.entity.UserInfo confirmUser;
	private java.lang.String applyNum;
	private com.digitalchina.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;
	private java.lang.String serverPort;		//�������˿�

	public java.lang.String getServerPort() {
		return serverPort;
	}
	public void setServerPort(java.lang.String serverPort) {
		this.serverPort = serverPort;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setRootId(java.lang.Long rootId) {
		this.rootId = rootId;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setOldApply(
			com.digitalchina.itil.require.entity.ServerManage oldApply) {
		this.oldApply = oldApply;
	}

	public void setProcessType(java.lang.Integer processType) {
		this.processType = processType;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public void setDeleteFlag(java.lang.Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setServiceItem(java.lang.Long serviceItem) {
		this.serviceItem = serviceItem;
	}

	public void setApplyUser(
			com.digitalchina.info.framework.security.entity.UserInfo applyUser) {
		this.applyUser = applyUser;
	}

	public void setUserNum(java.lang.String userNum) {
		this.userNum = userNum;
	}

	public void setCostConter(java.lang.String costConter) {
		this.costConter = costConter;
	}

	public void setApplyDept(
			com.digitalchina.info.framework.security.entity.Department applyDept) {
		this.applyDept = applyDept;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setAppOffer(
			com.digitalchina.info.framework.security.entity.UserInfo appOffer) {
		this.appOffer = appOffer;
	}

	public void setOfferTel(java.lang.String offerTel) {
		this.offerTel = offerTel;
	}

	public void setBelongDept(
			com.digitalchina.info.framework.security.entity.Department belongDept) {
		this.belongDept = belongDept;
	}

	public void setUseDept(
			com.digitalchina.info.framework.security.entity.Department useDept) {
		this.useDept = useDept;
	}

	public void setServerType(
			com.digitalchina.itil.config.extlist.entity.ServerUseType serverType) {
		this.serverType = serverType;
	}

	public void setServerName(java.lang.String serverName) {
		this.serverName = serverName;
	}

	public void setServerModel(java.lang.String serverModel) {
		this.serverModel = serverModel;
	}

	public void setServerCpu(java.lang.String serverCpu) {
		this.serverCpu = serverCpu;
	}

	public void setServerMomery(java.lang.String serverMomery) {
		this.serverMomery = serverMomery;
	}

	public void setServerDisk(java.lang.String serverDisk) {
		this.serverDisk = serverDisk;
	}

	public void setServerOs(java.lang.String serverOs) {
		this.serverOs = serverOs;
	}

	public void setServerDatebase(java.lang.String serverDatebase) {
		this.serverDatebase = serverDatebase;
	}

	public void setServerApp(java.lang.String serverApp) {
		this.serverApp = serverApp;
	}

	public void setServerDescn(java.lang.String serverDescn) {
		this.serverDescn = serverDescn;
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

	public java.lang.Long getRootId() {
		return this.rootId;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public com.digitalchina.itil.require.entity.ServerManage getOldApply() {
		return this.oldApply;
	}

	public java.lang.Integer getProcessType() {
		return this.processType;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public java.lang.Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public java.lang.Long getServiceItem() {
		return this.serviceItem;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getApplyUser() {
		return this.applyUser;
	}

	public java.lang.String getUserNum() {
		return this.userNum;
	}

	public java.lang.String getCostConter() {
		return this.costConter;
	}

	public com.digitalchina.info.framework.security.entity.Department getApplyDept() {
		return this.applyDept;
	}

	public java.lang.String getTel() {
		return this.tel;
	}

	public java.util.Date getApplyDate() {
		return this.applyDate;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getAppOffer() {
		return this.appOffer;
	}

	public java.lang.String getOfferTel() {
		return this.offerTel;
	}

	public com.digitalchina.info.framework.security.entity.Department getBelongDept() {
		return this.belongDept;
	}

	public com.digitalchina.info.framework.security.entity.Department getUseDept() {
		return this.useDept;
	}

	public com.digitalchina.itil.config.extlist.entity.ServerUseType getServerType() {
		return this.serverType;
	}

	public java.lang.String getServerName() {
		return this.serverName;
	}

	public java.lang.String getServerModel() {
		return this.serverModel;
	}

	public java.lang.String getServerCpu() {
		return this.serverCpu;
	}

	public java.lang.String getServerMomery() {
		return this.serverMomery;
	}

	public java.lang.String getServerDisk() {
		return this.serverDisk;
	}

	public java.lang.String getServerOs() {
		return this.serverOs;
	}

	public java.lang.String getServerDatebase() {
		return this.serverDatebase;
	}

	public java.lang.String getServerApp() {
		return this.serverApp;
	}

	public java.lang.String getServerDescn() {
		return this.serverDescn;
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

	public com.digitalchina.info.framework.security.entity.UserInfo getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(
			com.digitalchina.info.framework.security.entity.UserInfo confirmUser) {
		this.confirmUser = confirmUser;
	}

	public java.lang.String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(java.lang.String applyNum) {
		this.applyNum = applyNum;
	}

	public com.digitalchina.itil.config.entity.ConfigItem getConfigItem() {
		return configItem;
	}

	public void setConfigItem(
			com.digitalchina.itil.config.entity.ConfigItem configItem) {
		this.configItem = configItem;
	}

	public java.util.Date getOutDate() {
		return outDate;
	}

	public void setOutDate(java.util.Date outDate) {
		this.outDate = outDate;
	}
}
