package com.xpsoft.oa.model.hrm;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class Budget extends BaseModel{
	
	@Expose
	protected Long budgetId;
	@Expose
	protected String  name;
	@Expose
	protected Department belongDept;
	@Expose
	protected Date beginDate;
	@Expose
	protected Date endDate;
	@Expose
	protected String remark;
	@Expose
	protected Date createDate;
	@Expose
	protected AppUser createPerson;
	@Expose
	protected Date modifyDate;
	@Expose
	protected AppUser modifyPerson;
	@Expose
	protected Integer publishStatus;// 0：草稿  1：审核中 2：退回 3：审核完毕，发布 4：删除标记'
	
	
	public static final String ALARM_GREEN="0";
	public static final String ALARM_YELLOW="1";
	public static final String ALARM_RED="2";
	
	@Expose
	protected Budget belongBudget;//所属（年度）预算
	@Expose
	protected Integer budgetType;//年度1 季度2 月度3（待议）
	
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
	public Department getBelongDept() {
		return belongDept;
	}
	public void setBelongDept(Department belongDept) {
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
	public AppUser getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(AppUser createPerson) {
		this.createPerson = createPerson;
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
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Budget getBelongBudget() {
		return belongBudget;
	}
	public void setBelongBudget(Budget belongBudget) {
		this.belongBudget = belongBudget;
	}
	public Integer getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(Integer budgetType) {
		this.budgetType = budgetType;
	}
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
	       .append(this.budgetId)
	       .append(this.name)
	       .append(this.getBelongDept())
	       .append(this.beginDate)
	       .append(this.endDate)
	       .append(this.createDate)
	       .append(this.createPerson)
	       .append(this.modifyDate)
	       .append(this.modifyPerson)
	       .append(this.remark)
	       .append(this.publishStatus)
	       .append(this.belongBudget)
	       .toHashCode();
	}
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Budget)) {
			return false;
		}
		Budget rhs = (Budget)object;
		return new EqualsBuilder()
			.append(this.budgetId, rhs.budgetId)
			.append(this.name, rhs.name)
			.append(this.getBelongDept(), rhs.getBelongDept())
			.append(this.beginDate, rhs.beginDate)
			.append(this.endDate, rhs.endDate)
			.append(this.createDate, rhs.createDate)
			.append(this.createPerson, rhs.createPerson)
			.append(this.modifyDate, rhs.modifyDate)
			.append(this.modifyPerson, rhs.modifyPerson)
			.append(this.remark, rhs.remark)
			.append(this.publishStatus, rhs.publishStatus)
			.append(this.belongBudget, rhs.belongBudget)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("budgetId", this.budgetId)
			.append("name", this.name)
			.append("department", this.getBelongDept().getDepName())
			.append("beginDate", this.beginDate)
			.append("endDate", this.endDate)
			.append("createDate", this.createDate)
			.append("createPerson", this.createPerson)
			.append("modifyDate", this.modifyDate)
			.append("modifyPerson", this.modifyPerson)
			.append("remark", this.remark)
			.append("publishStatus", this.publishStatus)
			.append("budgetType", this.budgetType)
			.toString();
	}

	
}
