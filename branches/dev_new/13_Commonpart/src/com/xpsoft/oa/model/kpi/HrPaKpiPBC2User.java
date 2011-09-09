package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiPBC2User extends BaseModel {
	protected Long id;
	protected String pbcName;
	protected String fromPBC;//此处存放来源PBC的ID数组，以“,”分割。
	protected Long belongUser;
	protected HrPaDatadictionary frequency;
	protected Long createPerson;
	protected Date createDate;
	protected int publishStatus;
	protected float totalScore;
	protected Long modifyPerson;
	protected Date modifyDate;
	protected Double coefficient;
	
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}
	
	public HrPaKpiPBC2User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPbcName() {
		return pbcName;
	}

	public void setPbcName(String pbcName) {
		this.pbcName = pbcName;
	}

	public String getFromPBC() {
		return fromPBC;
	}

	public void setFromPBC(String fromPBC) {
		this.fromPBC = fromPBC;
	}

	public Long getBelongUser() {
		return belongUser;
	}

	public void setBelongUser(Long belongUser) {
		this.belongUser = belongUser;
	}

	public HrPaDatadictionary getFrequency() {
		return frequency;
	}

	public void setFrequency(HrPaDatadictionary frequency) {
		this.frequency = frequency;
	}

	public Long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}

	public Long getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(Long modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	
}
