package com.zsgj.itil.require.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class ERP_NormalNeedEvent extends BaseObject {
	private java.lang.Long id;
	private java.lang.Long rootId;
	private java.lang.String name;
	private com.zsgj.itil.require.entity.ERP_NormalNeed oldApply;
	private java.lang.Integer processType;
	private java.lang.Integer status;
	private java.lang.Integer deleteFlag;
	private java.lang.Long serviceItem;
	private java.lang.String applyNum;
	private java.util.Date applyDate;
	private com.zsgj.info.framework.security.entity.Department applyDept;
	private java.lang.String costConter;
	private com.zsgj.info.framework.security.entity.UserInfo applyUser;
	private java.lang.String tel;
	private com.zsgj.itil.config.extlist.entity.RequirementLevel requireLevel;
	private java.lang.String reason;
	private java.lang.String oldSystemLink;
	private java.lang.String actualStatus;
	private java.lang.String useStation;
	private java.lang.String includeAndExpend;
	private java.lang.String otherInfo;
	private java.util.Set scope = new java.util.HashSet();
	private java.lang.String attachment;
	private com.zsgj.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;
	private java.lang.Integer isWM;
	private java.lang.Integer isStore;
	private com.zsgj.itil.require.entity.RequireApplyDefaultAudit flat;

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
			com.zsgj.itil.require.entity.ERP_NormalNeed oldApply) {
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

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.Long getRootId() {
		return this.rootId;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public com.zsgj.itil.require.entity.ERP_NormalNeed getOldApply() {
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

	public java.lang.String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(java.lang.String applyNum) {
		this.applyNum = applyNum;
	}

	public java.util.Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	public com.zsgj.info.framework.security.entity.Department getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(
			com.zsgj.info.framework.security.entity.Department applyDept) {
		this.applyDept = applyDept;
	}

	public java.lang.String getCostConter() {
		return costConter;
	}

	public void setCostConter(java.lang.String costConter) {
		this.costConter = costConter;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(
			com.zsgj.info.framework.security.entity.UserInfo applyUser) {
		this.applyUser = applyUser;
	}

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public com.zsgj.itil.config.extlist.entity.RequirementLevel getRequireLevel() {
		return requireLevel;
	}

	public void setRequireLevel(
			com.zsgj.itil.config.extlist.entity.RequirementLevel requireLevel) {
		this.requireLevel = requireLevel;
	}

	public java.lang.String getReason() {
		return reason;
	}

	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}

	public java.lang.String getOldSystemLink() {
		return oldSystemLink;
	}

	public void setOldSystemLink(java.lang.String oldSystemLink) {
		this.oldSystemLink = oldSystemLink;
	}

	public java.lang.String getActualStatus() {
		return actualStatus;
	}

	public void setActualStatus(java.lang.String actualStatus) {
		this.actualStatus = actualStatus;
	}

	public java.lang.String getUseStation() {
		return useStation;
	}

	public void setUseStation(java.lang.String useStation) {
		this.useStation = useStation;
	}

	public java.lang.String getIncludeAndExpend() {
		return includeAndExpend;
	}

	public void setIncludeAndExpend(java.lang.String includeAndExpend) {
		this.includeAndExpend = includeAndExpend;
	}

	public java.lang.String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(java.lang.String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public java.util.Set getScope() {
		return scope;
	}

	public void setScope(java.util.Set scope) {
		this.scope = scope;
	}

	public java.lang.String getAttachment() {
		return attachment;
	}

	public void setAttachment(java.lang.String attachment) {
		this.attachment = attachment;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(
			com.zsgj.info.framework.security.entity.UserInfo createUser) {
		this.createUser = createUser;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public com.zsgj.info.framework.security.entity.UserInfo getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(
			com.zsgj.info.framework.security.entity.UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}

	public java.util.Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public java.lang.Integer getIsWM() {
		return isWM;
	}

	public void setIsWM(java.lang.Integer isWM) {
		this.isWM = isWM;
	}

	public java.lang.Integer getIsStore() {
		return isStore;
	}

	public void setIsStore(java.lang.Integer isStore) {
		this.isStore = isStore;
	}

	public com.zsgj.itil.require.entity.RequireApplyDefaultAudit getFlat() {
		return flat;
	}

	public void setFlat(
			com.zsgj.itil.require.entity.RequireApplyDefaultAudit flat) {
		this.flat = flat;
	}
}
