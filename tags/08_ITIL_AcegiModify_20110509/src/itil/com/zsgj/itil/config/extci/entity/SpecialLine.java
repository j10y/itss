package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SpecialLine extends BaseObject {
	private java.lang.Long id;
	private com.zsgj.itil.config.extlist.entity.LineCostRecoverType lineCostRecoverType;
	private java.lang.String cisn;
	private java.lang.String serviceProviderATel;
	private java.lang.String serviceProviderBTel;
	private java.lang.String lineNumberA;
	private java.lang.String lineNumberB;
	private com.zsgj.itil.config.extlist.entity.SpecialLineType type;
	private Operators operators;
	private com.zsgj.info.framework.security.entity.UserInfo createUser;
	private java.util.Date createDate;
	private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
	private java.util.Date modifyDate;
	private java.lang.String costCenterNum; // 分摊成本中心
	private String aclientContacts; // A端联系人
	private String bclientContacts; // B端联系人
	
	public Operators getOperators() {
		return operators;
	}

	public void setOperators(Operators operators) {
		this.operators = operators;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public com.zsgj.itil.config.extlist.entity.LineCostRecoverType getLineCostRecoverType() {
		return lineCostRecoverType;
	}

	public void setLineCostRecoverType(
			com.zsgj.itil.config.extlist.entity.LineCostRecoverType lineCostRecoverType) {
		this.lineCostRecoverType = lineCostRecoverType;
	}

	public java.lang.String getCisn() {
		return cisn;
	}

	public void setCisn(java.lang.String cisn) {
		this.cisn = cisn;
	}

	public java.lang.String getServiceProviderATel() {
		return serviceProviderATel;
	}

	public void setServiceProviderATel(java.lang.String serviceProviderATel) {
		this.serviceProviderATel = serviceProviderATel;
	}

	public java.lang.String getServiceProviderBTel() {
		return serviceProviderBTel;
	}

	public void setServiceProviderBTel(java.lang.String serviceProviderBTel) {
		this.serviceProviderBTel = serviceProviderBTel;
	}

	public java.lang.String getLineNumberA() {
		return lineNumberA;
	}

	public void setLineNumberA(java.lang.String lineNumberA) {
		this.lineNumberA = lineNumberA;
	}

	public java.lang.String getLineNumberB() {
		return lineNumberB;
	}

	public void setLineNumberB(java.lang.String lineNumberB) {
		this.lineNumberB = lineNumberB;
	}

	public com.zsgj.itil.config.extlist.entity.SpecialLineType getType() {
		return type;
	}

	public void setType(
			com.zsgj.itil.config.extlist.entity.SpecialLineType type) {
		this.type = type;
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

	public java.lang.String getCostCenterNum() {
		return costCenterNum;
	}

	public void setCostCenterNum(java.lang.String costCenterNum) {
		this.costCenterNum = costCenterNum;
	}

	public String getAclientContacts() {
		return aclientContacts;
	}

	public void setAclientContacts(String aclientContacts) {
		this.aclientContacts = aclientContacts;
	}

	public String getBclientContacts() {
		return bclientContacts;
	}

	public void setBclientContacts(String bclientContacts) {
		this.bclientContacts = bclientContacts;
	}

}
