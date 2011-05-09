package com.zsgj.info.framework.rules.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class RuleResultType extends BaseObject{
	
	private Long id;
	private String ruleResultTypeName;
	private RuleResultType parentRuleResultType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RuleResultType getParentRuleResultType() {
		return parentRuleResultType;
	}
	public void setParentRuleResultType(RuleResultType parentRuleResultType) {
		this.parentRuleResultType = parentRuleResultType;
	}
	public String getRuleResultTypeName() {
		return ruleResultTypeName;
	}
	public void setRuleResultTypeName(String ruleResultTypeName) {
		this.ruleResultTypeName = ruleResultTypeName;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((ruleResultTypeName == null) ? 0 : ruleResultTypeName.hashCode());
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
		final RuleResultType other = (RuleResultType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ruleResultTypeName == null) {
			if (other.ruleResultTypeName != null)
				return false;
		} else if (!ruleResultTypeName.equals(other.ruleResultTypeName))
			return false;
		return true;
	}
}
