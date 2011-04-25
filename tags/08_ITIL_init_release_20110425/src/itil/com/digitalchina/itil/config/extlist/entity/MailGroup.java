package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

public class MailGroup extends BaseObject {
	private java.lang.Long id;
	private java.lang.String groupName;
	private java.lang.String groupManger;
	private java.lang.String changeGroupManger;
	private java.lang.String groupNewName;
	private java.lang.String applyReason;
	private com.digitalchina.itil.config.extlist.entity.MailGroup oldApply;
	private java.lang.String accountState; // ’À∫≈◊¥Ã¨
	private AccountApplyMainTable applyId;
	private com.digitalchina.info.framework.security.entity.UserInfo accountowner; //  π”√»À

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setGroupName(java.lang.String groupName) {
		this.groupName = groupName;
	}

	public void setGroupManger(java.lang.String groupManger) {
		this.groupManger = groupManger;
	}

	public void setChangeGroupManger(java.lang.String changeGroupManger) {
		this.changeGroupManger = changeGroupManger;
	}

	public void setGroupNewName(java.lang.String groupNewName) {
		this.groupNewName = groupNewName;
	}

	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}

	public void setOldApply(
			com.digitalchina.itil.config.extlist.entity.MailGroup oldApply) {
		this.oldApply = oldApply;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getGroupName() {
		return this.groupName;
	}

	public java.lang.String getGroupManger() {
		return this.groupManger;
	}

	public java.lang.String getChangeGroupManger() {
		return this.changeGroupManger;
	}

	public java.lang.String getGroupNewName() {
		return this.groupNewName;
	}

	public java.lang.String getApplyReason() {
		return this.applyReason;
	}

	public com.digitalchina.itil.config.extlist.entity.MailGroup getOldApply() {
		return this.oldApply;
	}

	public java.lang.String getAccountState() {
		return accountState;
	}

	public void setAccountState(java.lang.String accountState) {
		this.accountState = accountState;
	}

	public AccountApplyMainTable getApplyId() {
		return applyId;
	}

	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}

	public com.digitalchina.info.framework.security.entity.UserInfo getAccountowner() {
		return accountowner;
	}

	public void setAccountowner(
			com.digitalchina.info.framework.security.entity.UserInfo accountowner) {
		this.accountowner = accountowner;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.digitalchina.itil.config.extlist.entity.MailGroup other = (com.digitalchina.itil.config.extlist.entity.MailGroup) obj;
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
