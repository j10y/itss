package com.xpsoft.oa.model.hrm;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class RealExecution extends BaseModel{
	
	protected Long realExecutionId;
	protected Budget  budget;
	protected BudgetItem  budgetItem;
	protected Double realValue;
	protected Integer month;
	protected Date inputDate;
	protected String remark;
	protected Integer deleteFlag;
	
	public Long getRealExecutionId() {
		return realExecutionId;
	}
	public void setRealExecutionId(Long realExecutionId) {
		this.realExecutionId = realExecutionId;
	}
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	public BudgetItem getBudgetItem() {
		return budgetItem;
	}
	public void setBudgetItem(BudgetItem budgetItem) {
		this.budgetItem = budgetItem;
	}
	public Double getRealValue() {
		return realValue;
	}
	public void setRealValue(Double realValue) {
		this.realValue = realValue;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
	       .append(this.realExecutionId)
	       .append(this.budget)
	       .append(this.budgetItem)
	       .append(this.realValue)
	       .append(this.month)
	       .append(this.inputDate)
	       .append(this.remark)
	       .append(this.deleteFlag)
	       .toHashCode();
	}
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof RealExecution)) {
			return false;
		}
		RealExecution rhs = (RealExecution)object;
		return new EqualsBuilder()
			.append(this.realExecutionId, rhs.realExecutionId)
			.append(this.budget, rhs.budget)
			.append(this.budgetItem, rhs.budgetItem)
			.append(this.realValue, rhs.realValue)
			.append(this.month, rhs.month)
			.append(this.inputDate, rhs.inputDate)
			.append(this.remark, rhs.remark)
			.append(this.deleteFlag, rhs.deleteFlag)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("realExecutionId", this.realExecutionId)
			.append("budget", this.budget)
			.append("budgetItem", this.budgetItem)
			.append("realValue", this.realValue)
			.append("month", this.month)
			.append("inputDate", this.inputDate)
			.append("remark", this.remark)
			.append("deleteFlag", this.deleteFlag)
			.toString();
	}
}
