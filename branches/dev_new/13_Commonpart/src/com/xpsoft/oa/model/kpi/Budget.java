package com.xpsoft.oa.model.kpi;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


import com.xpsoft.core.model.BaseModel;

public class Budget extends BaseModel{
	
	
	protected Long budgetId;
	
	protected String  name;
	
	protected Long belongDept;
	
	protected Date beginDate;
	
	protected Date endDate;
	
	protected String remark;
	
	protected Date createDate;
	
	protected Long createPerson;
	
	protected Date modifyDate;
	
	protected Long modifyPerson;
	
	protected Integer publishStatus;// 0：草稿  1：审核中 2：退回 3：审核完毕，发布 4：删除标记'
	
	
	public static final String ALARM_GREEN="0";
	public static final String ALARM_YELLOW="1";
	public static final String ALARM_RED="2";
	public Long getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getBelongDept() {
		return belongDept;
	}
	public void setBelongDept(Long belongDept) {
		this.belongDept = belongDept;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Long getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
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
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	public static String getALARM_GREEN() {
		return ALARM_GREEN;
	}
	public static String getALARM_YELLOW() {
		return ALARM_YELLOW;
	}
	public static String getALARM_RED() {
		return ALARM_RED;
	}
	
}
