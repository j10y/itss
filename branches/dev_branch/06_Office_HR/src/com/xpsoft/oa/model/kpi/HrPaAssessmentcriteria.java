package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAssessmentcriteria extends BaseModel {
	protected long id;
	protected String acName;
	protected String acKey;
	protected String acDesc;
	protected int isSalesAC;
	protected Date createDate;
	protected long createPerson;
	protected int publishStatus;
	protected Date modifyDate;
	protected long modifyPerson;
	protected Long fromAc;
	
	public HrPaAssessmentcriteria(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getAcKey() {
		return acKey;
	}

	public void setAcKey(String acKey) {
		this.acKey = acKey;
	}

	public String getAcDesc() {
		return acDesc;
	}

	public void setAcDesc(String acDesc) {
		this.acDesc = acDesc;
	}

	public int getIsSalesAC() {
		return isSalesAC;
	}

	public void setIsSalesAC(int isSalesAC) {
		this.isSalesAC = isSalesAC;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(long createPerson) {
		this.createPerson = createPerson;
	}

	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public long getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(long modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Long getFromAc() {
		return fromAc;
	}

	public void setFromAc(Long fromAc) {
		this.fromAc = fromAc;
	}
}
