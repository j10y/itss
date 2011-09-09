package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpipbc extends BaseModel {
	protected Long id;
	protected String pbcName;
	protected Long belongDept;
	protected Long belongPost;
	protected HrPaDatadictionary frequency;
	protected Date createDate;
	protected Long createPerson;
	protected Integer publishStatus;
	protected Integer totalScore;
	protected Date modifyDate;
	protected Long modifyPerson;	
	protected Double coefficient;
	
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}
	
	public HrPaKpipbc(){}

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

	public Long getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Long belongDept) {
		this.belongDept = belongDept;
	}

	public Long getBelongPost() {
		return belongPost;
	}

	public void setBelongPost(Long belongPost) {
		this.belongPost = belongPost;
	}

	public HrPaDatadictionary getFrequency() {
		return frequency;
	}

	public void setFrequency(HrPaDatadictionary frequency) {
		this.frequency = frequency;
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

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
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
