package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

public class MailForwardApply extends BaseObject {
	private java.lang.Long id;
	private java.lang.String accountName;
	private java.lang.String applyReason;
	private java.lang.String targetMail;
	private java.lang.String companionDCMail;
	
	private java.lang.String accountState;
	private java.lang.String mailType;
	private com.digitalchina.info.framework.security.entity.UserInfo accountOwner;
	private AccountApplyMainTable applyId;
	private MailForwardApply oldApply;
	private java.util.Date createDate; // 生效时间
	private java.util.Date stopDate;//停止转发时间


	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}

	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}

	public void setTargetMail(java.lang.String targetMail) {
		this.targetMail = targetMail;
	}

	public void setAccountState(java.lang.String accountState) {
		this.accountState = accountState;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getAccountName() {
		return this.accountName;
	}

	public java.lang.String getApplyReason() {
		return this.applyReason;
	}

	public java.lang.String getTargetMail() {
		return this.targetMail;
	}

	public java.lang.String getAccountState() {
		return this.accountState;
	}

	public AccountApplyMainTable getApplyId() {
		return applyId;
	}

	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(
			com.digitalchina.info.framework.security.entity.UserInfo accountOwner) {
		this.accountOwner = accountOwner;
	}

	public MailForwardApply getOldApply() {
		return oldApply;
	}

	public void setOldApply(MailForwardApply oldApply) {
		this.oldApply = oldApply;
	}
	

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	

	public java.lang.String getMailType() {
		return mailType;
	}

	public void setMailType(java.lang.String mailType) {
		this.mailType = mailType;
	}
	
	
	

	public java.lang.String getCompanionDCMail() {
		return companionDCMail;
	}

	public void setCompanionDCMail(java.lang.String companionDCMail) {
		this.companionDCMail = companionDCMail;
	}

	public java.util.Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(java.util.Date stopDate) {
		this.stopDate = stopDate;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.digitalchina.itil.config.extlist.entity.MailForwardApply other = (com.digitalchina.itil.config.extlist.entity.MailForwardApply) obj;
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
