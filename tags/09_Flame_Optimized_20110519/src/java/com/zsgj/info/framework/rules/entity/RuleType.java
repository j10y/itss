package com.zsgj.info.framework.rules.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 规则类型实体
 * @Class Name RuleType
 * @Author peixf
 * @Create In 2008-4-14
 */
public class RuleType extends BaseObject{
	private static final Long serialVersionUID = -3210336051711665518L;
	
	private Long id;
	private String roleTypeName;
	private RuleType parentRuleType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RuleType getParentRuleType() {
		return parentRuleType;
	}
	public void setParentRuleType(RuleType parentRuleType) {
		this.parentRuleType = parentRuleType;
	}
	public String getRoleTypeName() {
		return roleTypeName;
	}
	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((parentRuleType == null) ? 0 : parentRuleType.hashCode());
		result = PRIME * result + ((roleTypeName == null) ? 0 : roleTypeName.hashCode());
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
		final RuleType other = (RuleType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parentRuleType == null) {
			if (other.parentRuleType != null)
				return false;
		} else if (!parentRuleType.equals(other.parentRuleType))
			return false;
		if (roleTypeName == null) {
			if (other.roleTypeName != null)
				return false;
		} else if (!roleTypeName.equals(other.roleTypeName))
			return false;
		return true;
	}
	

}
