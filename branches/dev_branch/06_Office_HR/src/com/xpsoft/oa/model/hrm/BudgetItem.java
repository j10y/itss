package com.xpsoft.oa.model.hrm;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class BudgetItem extends BaseModel{
	
	protected Long budgetItemId;
	protected Budget budget;
	
	protected String code;//编号
	protected String  name;//名称
	protected String key;//缩写 key为mysql的关键字
	protected Double value;//预算金额 value为mysql的关键字
	protected Double threshold;//控制阀值
	protected BudgetItem parent;//父
	protected Integer isDefault;//是否默认
	protected Integer deleteFlag;//删除标记 0未删除 1删除
	
	public Long getBudgetItemId() {
		return budgetItemId;
	}
	public void setBudgetItemId(Long budgetItemId) {
		this.budgetItemId = budgetItemId;
	}	
	
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getThreshold() {
		return threshold;
	}
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	public BudgetItem getParent() {
		return parent;
	}
	public void setParent(BudgetItem parent) {
		this.parent = parent;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
	       .append(this.budgetItemId)
	       .append(this.budget)
	       .append(this.code)
	       .append(this.name)
	       .append(this.key)
	       .append(this.value)
	       .append(this.threshold)
	       .append(this.parent)
	       .append(this.deleteFlag)
	       .append(this.isDefault)
	       .toHashCode();
	}
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BudgetItem)) {
			return false;
		}
		BudgetItem rhs = (BudgetItem)object;
		return new EqualsBuilder()
			.append(this.budgetItemId, rhs.budgetItemId)
			.append(this.budget, rhs.budget)
			.append(this.name, rhs.name)
			.append(this.code, rhs.code)
			.append(this.key, rhs.key)
			.append(this.value, rhs.value)
			.append(this.threshold, rhs.threshold)
			.append(this.parent, rhs.parent)
			.append(this.isDefault, rhs.isDefault)
			.append(this.deleteFlag, rhs.deleteFlag)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("budgetItemId", this.budgetItemId)
			.append("budget", this.budget)
			.append("name", this.name)
			.append("code", this.code)
			.append("key", this.key)
			.append("value", this.value)
			.append("threshold", this.threshold)
			.append("parent", this.parent)
			.append("isDefault", this.isDefault)
			.append("deleteFlag", this.deleteFlag)
			.toString();
	}
}
