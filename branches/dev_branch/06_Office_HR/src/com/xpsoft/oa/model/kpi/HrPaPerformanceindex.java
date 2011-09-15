package com.xpsoft.oa.model.kpi;

import java.util.*;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class HrPaPerformanceindex extends BaseModel{
	protected Long id;
	protected String paName;
	protected HrPaDatadictionary type;
	protected HrPaDatadictionary frequency;
	protected HrPaDatadictionary mode;
	protected Integer paIsOnlyNegative;
	protected String paDesc;
	protected String remark;
	protected Date createDate;
	protected AppUser createPerson;
	protected Integer publishStatus;
	protected Date modifyDate;
	protected AppUser modifyPerson;
	protected Double baseScore;
	protected Double finalScore;
	protected HrPaPerformanceindex parentPa;//父级考核指标
	protected Long fromPi;
	protected Double finalCoefficient;//最终否决值的系数
	protected Department belongDept;
	
	public HrPaPerformanceindex(){}

	public Double getFinalCoefficient() {
		return finalCoefficient;
	}

	public void setFinalCoefficient(Double finalCoefficient) {
		this.finalCoefficient = finalCoefficient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaName() {
		return paName;
	}

	public void setPaName(String paName) {
		this.paName = paName;
	}

	public HrPaDatadictionary getType() {
		return type;
	}

	public void setType(HrPaDatadictionary type) {
		this.type = type;
	}

	public HrPaDatadictionary getFrequency() {
		return frequency;
	}

	public void setFrequency(HrPaDatadictionary frequency) {
		this.frequency = frequency;
	}

	public HrPaDatadictionary getMode() {
		return mode;
	}

	public void setMode(HrPaDatadictionary mode) {
		this.mode = mode;
	}

	public Integer getPaIsOnlyNegative() {
		return paIsOnlyNegative;
	}

	public void setPaIsOnlyNegative(Integer paIsOnlyNegative) {
		this.paIsOnlyNegative = paIsOnlyNegative;
	}

	public String getPaDesc() {
		return paDesc;
	}

	public void setPaDesc(String paDesc) {
		this.paDesc = paDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public AppUser getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(AppUser modifyPerson) {
		this.modifyPerson = modifyPerson;
	}

	public Double getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(Double baseScore) {
		this.baseScore = baseScore;
	}

	public Double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Double finalScore) {
		this.finalScore = finalScore;
	}

	public HrPaPerformanceindex getParentPa() {
		return parentPa;
	}

	public void setParentPa(HrPaPerformanceindex parentPa) {
		this.parentPa = parentPa;
	}

	public Long getFromPi() {
		return fromPi;
	}

	public void setFromPi(Long fromPi) {
		this.fromPi = fromPi;
	}

	public Department getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Department belongDept) {
		this.belongDept = belongDept;
	}

}
