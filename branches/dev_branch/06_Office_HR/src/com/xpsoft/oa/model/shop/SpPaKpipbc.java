package com.xpsoft.oa.model.shop;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class SpPaKpipbc extends BaseModel {
	protected long id;
	protected String pbcName;
	protected Department belongDept;
	protected Job belongPost;
	protected SpPaDatadictionary frequency;
	protected Date createDate;
	protected AppUser createPerson;
	protected int publishStatus;
	protected int totalScore;
	protected Date modifyDate;
	protected AppUser modifyPerson;
	protected long fromPbc;
	protected Double coefficient;
	protected Long fromPi;
	protected SpPaDatadictionary pbcType;
	
	public SpPaKpipbc(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPbcName() {
		return pbcName;
	}

	public void setPbcName(String pbcName) {
		this.pbcName = pbcName;
	}

	public Department getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Department belongDept) {
		this.belongDept = belongDept;
	}

	public Job getBelongPost() {
		return belongPost;
	}

	public void setBelongPost(Job belongPost) {
		this.belongPost = belongPost;
	}

	public SpPaDatadictionary getFrequency() {
		return frequency;
	}

	public void setFrequency(SpPaDatadictionary frequency) {
		this.frequency = frequency;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public AppUser getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(AppUser createPerson) {
		this.createPerson = createPerson;
	}

	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public AppUser getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(AppUser modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public long getFromPbc() {
		return fromPbc;
	}

	public void setFromPbc(long fromPbc) {
		this.fromPbc = fromPbc;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public SpPaDatadictionary getPbcType() {
		return pbcType;
	}

	public void setPbcType(SpPaDatadictionary pbcType) {
		this.pbcType = pbcType;
	}

	public Long getFromPi() {
		return fromPi;
	}

	public void setFromPi(Long fromPi) {
		this.fromPi = fromPi;
	}

}
