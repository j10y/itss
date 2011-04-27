package com.zsgj.itil.require.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.ServiceItemProcess;

public class AccountApplyMainTable extends BaseObject {
	private java.lang.Long id;
	private java.lang.String name;
	private com.zsgj.itil.require.entity.AccountApplyMainTable oldApply;
	private java.lang.Integer processType;
	private java.lang.Integer status;
	private java.lang.Integer deleteFlag;
	private java.lang.Long serviceItem;
	private java.util.Date applyDate;
	private com.zsgj.info.framework.security.entity.UserInfo applyUser;
	private com.zsgj.info.framework.security.entity.UserInfo delegateApplyUser;
	private java.lang.String applyUserTel;
	private java.lang.String delegateApplyTel;
	private java.lang.String attachment;
	private com.zsgj.info.framework.security.entity.UserInfo confirmUser;
	private com.zsgj.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;
	private ServiceItemProcess serviceItemProcess;
	private java.lang.String mail;
	private com.zsgj.info.framework.security.entity.UserInfo signAuditUser;
	private com.zsgj.itil.config.extlist.entity.PlatFormHRCountSign platFormHRCountSign;
	private com.zsgj.itil.config.extlist.entity.TelephoneCountSign telephoneSignUser;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setOldApply(
			com.zsgj.itil.require.entity.AccountApplyMainTable oldApply) {
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

	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	public void setApplyUser(
			com.zsgj.info.framework.security.entity.UserInfo applyUser) {
		this.applyUser = applyUser;
	}

	public void setDelegateApplyUser(
			com.zsgj.info.framework.security.entity.UserInfo delegateApplyUser) {
		this.delegateApplyUser = delegateApplyUser;
	}

	public void setApplyUserTel(java.lang.String applyUserTel) {
		this.applyUserTel = applyUserTel;
	}

	public void setDelegateApplyTel(java.lang.String delegateApplyTel) {
		this.delegateApplyTel = delegateApplyTel;
	}

	public void setAttachment(java.lang.String attachment) {
		this.attachment = attachment;
	}

	public void setConfirmUser(
			com.zsgj.info.framework.security.entity.UserInfo confirmUser) {
		this.confirmUser = confirmUser;
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

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public com.zsgj.itil.require.entity.AccountApplyMainTable getOldApply() {
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

	public java.util.Date getApplyDate() {
		return this.applyDate;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getApplyUser() {
		return this.applyUser;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getDelegateApplyUser() {
		return this.delegateApplyUser;
	}

	public java.lang.String getApplyUserTel() {
		return this.applyUserTel;
	}

	public java.lang.String getDelegateApplyTel() {
		return this.delegateApplyTel;
	}

	public java.lang.String getAttachment() {
		return this.attachment;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getConfirmUser() {
		return this.confirmUser;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getCreateUser() {
		return this.createUser;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getSignAuditUser() {
		return signAuditUser;
	}

	public void setSignAuditUser(
			com.zsgj.info.framework.security.entity.UserInfo signAuditUser) {
		this.signAuditUser = signAuditUser;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getModifyUser() {
		return this.modifyUser;
	}

	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public ServiceItemProcess getServiceItemProcess() {
		return serviceItemProcess;
	}

	public void setServiceItemProcess(ServiceItemProcess serviceItemProcess) {
		this.serviceItemProcess = serviceItemProcess;
	}
	
	

	public com.zsgj.itil.config.extlist.entity.PlatFormHRCountSign getPlatFormHRCountSign() {
		return platFormHRCountSign;
	}

	public void setPlatFormHRCountSign(
			com.zsgj.itil.config.extlist.entity.PlatFormHRCountSign platFormHRCountSign) {
		this.platFormHRCountSign = platFormHRCountSign;
	}
	
	

	public com.zsgj.itil.config.extlist.entity.TelephoneCountSign getTelephoneSignUser() {
		return telephoneSignUser;
	}

	public void setTelephoneSignUser(
			com.zsgj.itil.config.extlist.entity.TelephoneCountSign telephoneSignUser) {
		this.telephoneSignUser = telephoneSignUser;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.zsgj.itil.require.entity.AccountApplyMainTable other = (com.zsgj.itil.require.entity.AccountApplyMainTable) obj;
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
