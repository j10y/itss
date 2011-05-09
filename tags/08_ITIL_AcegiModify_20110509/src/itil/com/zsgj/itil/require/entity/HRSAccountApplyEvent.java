package com.zsgj.itil.require.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.ServiceItemProcess;

public class HRSAccountApplyEvent extends BaseObject {
	private java.lang.Long id;
	private java.lang.Long rootId;
	private java.lang.String name;
	private com.zsgj.itil.require.entity.HRSAccountApply oldApply;
	private java.lang.Integer processType;
	private java.lang.Integer status;
	private java.lang.Integer deleteFlag;
	private java.lang.Long serviceItem;
	private java.lang.String accountName;
	private java.lang.String accountState;
	private java.lang.Integer isChangeRight;
	private java.lang.String applyReason;
	private java.lang.Integer isReferMoney;
	private com.zsgj.itil.config.extlist.entity.HRSUserRight userRight;
	private com.zsgj.itil.config.extlist.entity.HRSOperationScope operationScope;
	private java.lang.String userOwner;
	private java.lang.String workDuty;
	private com.zsgj.info.framework.security.entity.UserInfo applyUser;
	private java.lang.String applyTel;
	private com.zsgj.itil.config.extlist.entity.HRSAccountManger accountManger;
	private com.zsgj.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;
	private com.zsgj.info.framework.security.entity.UserInfo confirmUser;
	private java.lang.String delegateApplyTel;
	private java.util.Date applyDate;
	private com.zsgj.info.framework.security.entity.UserInfo delegateApplyUser;
	private ServiceItemProcess serviceItemProcess;
	public java.lang.String getDelegateApplyTel() {
		return delegateApplyTel;
	}

	public void setDelegateApplyTel(java.lang.String delegateApplyTel) {
		this.delegateApplyTel = delegateApplyTel;
	}

	public java.lang.Integer getIsChangeRight() {
		return isChangeRight;
	}

	public void setIsChangeRight(java.lang.Integer isChangeRight) {
		this.isChangeRight = isChangeRight;
	}

	public java.util.Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getDelegateApplyUser() {
		return delegateApplyUser;
	}

	public void setDelegateApplyUser(
			com.zsgj.info.framework.security.entity.UserInfo delegateApplyUser) {
		this.delegateApplyUser = delegateApplyUser;
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
			com.zsgj.itil.require.entity.HRSAccountApply oldApply) {
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

	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}

	public void setAccountState(java.lang.String accountState) {
		this.accountState = accountState;
	}

	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}
	

	public ServiceItemProcess getServiceItemProcess() {
		return serviceItemProcess;
	}

	public void setServiceItemProcess(ServiceItemProcess serviceItemProcess) {
		this.serviceItemProcess = serviceItemProcess;
	}

	public void setIsReferMoney(java.lang.Integer isReferMoney) {
		this.isReferMoney = isReferMoney;
	}

	public void setUserRight(
			com.zsgj.itil.config.extlist.entity.HRSUserRight userRight) {
		this.userRight = userRight;
	}

	public void setOperationScope(
			com.zsgj.itil.config.extlist.entity.HRSOperationScope operationScope) {
		this.operationScope = operationScope;
	}

	public void setWorkDuty(java.lang.String workDuty) {
		this.workDuty = workDuty;
	}

	public void setApplyUser(
			com.zsgj.info.framework.security.entity.UserInfo applyUser) {
		this.applyUser = applyUser;
	}

	public void setApplyTel(java.lang.String applyTel) {
		this.applyTel = applyTel;
	}

	public void setAccountManger(
			com.zsgj.itil.config.extlist.entity.HRSAccountManger accountManger) {
		this.accountManger = accountManger;
	}

	public void setCreateUser(
			com.zsgj.info.framework.security.entity.UserInfo createUser) {
		this.createUser = createUser;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyUser(
			com.zsgj.info.framework.security.entity.UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(
			com.zsgj.info.framework.security.entity.UserInfo confirmUser) {
		this.confirmUser = confirmUser;
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

	public com.zsgj.itil.require.entity.HRSAccountApply getOldApply() {
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

	public java.lang.String getAccountName() {
		return this.accountName;
	}

	public java.lang.String getAccountState() {
		return this.accountState;
	}

	public java.lang.String getApplyReason() {
		return this.applyReason;
	}

	public java.lang.Integer getIsReferMoney() {
		return this.isReferMoney;
	}

	public com.zsgj.itil.config.extlist.entity.HRSUserRight getUserRight() {
		return this.userRight;
	}

	public com.zsgj.itil.config.extlist.entity.HRSOperationScope getOperationScope() {
		return this.operationScope;
	}

	public java.lang.String getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(java.lang.String userOwner) {
		this.userOwner = userOwner;
	}

	public java.lang.String getWorkDuty() {
		return this.workDuty;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getApplyUser() {
		return this.applyUser;
	}

	public java.lang.String getApplyTel() {
		return this.applyTel;
	}

	public com.zsgj.itil.config.extlist.entity.HRSAccountManger getAccountManger() {
		return this.accountManger;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getCreateUser() {
		return this.createUser;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getModifyUser() {
		return this.modifyUser;
	}

	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.zsgj.itil.require.entity.HRSAccountApplyEvent other = (com.zsgj.itil.require.entity.HRSAccountApplyEvent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
