package com.digitalchina.info.framework.security.entity;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 部门
 */

public class Department extends BaseObject {
	

	private static final long serialVersionUID = -8973915980510250335L;
	
	private Long id;
	private Long departCode;
	private String departName;
	private String fullDepartName;
	private Department parentDepartment;
	private Set childDepartments = new HashSet();
	private boolean looped; //是否遍历过
	private Integer childSum; 
	private Integer level;
	private Date startDate;
	private Date endDate;
	// Constructors

	public Integer getChildSum() {
		return childSum;
	}

	public void setChildSum(Integer childSum) {
		this.childSum = childSum;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isLooped() {
		return looped;
	}

	public void setLooped(boolean looped) {
		this.looped = looped;
	}

	public Set getChildDepartments() {
		return childDepartments;
	}

	public void setChildDepartments(Set childDepartments) {
		this.childDepartments = childDepartments;
	}

	/** default constructor */
	public Department() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepartCode() {
		return this.departCode;
	}

	public void setDepartCode(Long departCode) {
		this.departCode = departCode;
	}

	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((departCode == null) ? 0 : departCode.hashCode());
		result = PRIME * result + ((departName == null) ? 0 : departName.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((parentDepartment == null) ? 0 : parentDepartment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Department other = (Department) obj;
		if (departCode == null) {
			if (other.departCode != null)
				return false;
		} else if (!departCode.equals(other.departCode))
			return false;
		if (departName == null) {
			if (other.departName != null)
				return false;
		} else if (!departName.equals(other.departName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parentDepartment == null) {
			if (other.parentDepartment != null)
				return false;
		} else if (!parentDepartment.equals(other.parentDepartment))
			return false;
		return true;
	}

	public String getFullDepartName() {
		return fullDepartName;
	}

	public void setFullDepartName(String fullDepartName) {
		this.fullDepartName = fullDepartName;
	}
	@Override
	public String toString() {
		return this.getDepartName();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}