package com.xpsoft.oa.model.kpi;


import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAssessmentcriteria extends BaseModel {
	protected Long id;
	protected String acName;
	protected String acKey;
	protected String acDesc;
	protected Integer isSalesAC;
	protected Date createDate;
	protected Long createPerson;
	protected Integer publishStatus;
	protected Date modifyDate;
	protected Long modifyPerson;
	
	public HrPaAssessmentcriteria(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getIsSalesAC() {
		return isSalesAC;
	}

	public void setIsSalesAC(Integer isSalesAC) {
		this.isSalesAC = isSalesAC;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(Long modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

}
