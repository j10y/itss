package com.zsgj.info.framework.rules.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 规则条件
 * @Class Name RuleCondition
 * @Author peixf
 * @Create In 2008-4-14
 */
public class RuleCondition extends BaseObject{
	private static final long serialVersionUID = -3220341709863429490L;
	
	private Long id;
	private String conditionName;
	private String conditionValue;
	private RuleCondition parentRuleCondition;
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((conditionName == null) ? 0 : conditionName.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((parentRuleCondition == null) ? 0 : parentRuleCondition.hashCode());
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
		final RuleCondition other = (RuleCondition) obj;
		if (conditionName == null) {
			if (other.conditionName != null)
				return false;
		} else if (!conditionName.equals(other.conditionName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parentRuleCondition == null) {
			if (other.parentRuleCondition != null)
				return false;
		} else if (!parentRuleCondition.equals(other.parentRuleCondition))
			return false;
		return true;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RuleCondition getParentRuleCondition() {
		return parentRuleCondition;
	}
	public void setParentRuleCondition(RuleCondition parentRuleCondition) {
		this.parentRuleCondition = parentRuleCondition;
	}

}
