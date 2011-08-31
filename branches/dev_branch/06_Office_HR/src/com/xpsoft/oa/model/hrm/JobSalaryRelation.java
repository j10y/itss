package com.xpsoft.oa.model.hrm;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;

public class JobSalaryRelation extends BaseModel {
	public static Integer DELFLAG_NOT = 0;
	public static Integer DELFLAG_HAD = 1;
	protected Long relationId;
	protected Department department;
	protected Job job;//岗位
	protected StandSalary standSalary;//薪资标准
	protected Integer empCount;//人数
	protected BigDecimal totalMoney;
	protected Integer deleteFlag;
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public StandSalary getStandSalary() {
		return standSalary;
	}
	public void setStandSalary(StandSalary standSalary) {
		this.standSalary = standSalary;
	}
	public Integer getEmpCount() {
		return empCount;
	}
	public void setEmpCount(Integer empCount) {
		this.empCount = empCount;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.relationId)
		.append(this.department)
		.append(this.job)
		.append(this.standSalary)
		.append(this.empCount)
		.append(this.totalMoney)
		.append(this.deleteFlag)
		.toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Job)) {
			return false;
		}
		JobSalaryRelation rhs = (JobSalaryRelation) obj;
		return new EqualsBuilder()
			.append(this.relationId, rhs.relationId)
			.append(this.department, rhs.department)
			.append(this.job, rhs.job)
			.append(this.standSalary, rhs.standSalary)
			.append(this.empCount, rhs.empCount)
			.append(this.totalMoney, rhs.totalMoney)
			.append(this.deleteFlag, rhs.deleteFlag)
			.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("jobId", this.relationId)
			.append("department", this.department)
			.append("job", this.job)
			.append("standSalary", this.standSalary)
			.append("empCount", this.empCount)
			.append("totalMoney", this.totalMoney)
			.append("deleteFlag", this.deleteFlag)
			.toString();
	}
	
}
