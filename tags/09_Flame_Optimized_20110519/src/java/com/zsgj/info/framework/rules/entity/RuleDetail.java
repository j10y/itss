package com.zsgj.info.framework.rules.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * RuleDetail是实体，是EntityRule和WorkFlowRule的共同基类
 * @Class Name RoleDetail
 * @Author peixf
 * @Create In 2008-4-14
 */
public class RuleDetail extends BaseObject{
	private static final Long serialVersionUID = -7094151506604177790L;
	
	public static Integer RULE_DETAIL_TYPE_ENTITY = new Integer(0);
	public static Integer RULE_DETAIL_TYPE_WORKFLOW = new Integer(1);
	
	private Long id;
	
	private Rule rule;
	
	private SystemMainTable entity; 
	private SystemMainTableColumn entityProperty;
	
	private RuleCondition condition;
	private String conditionValue;
	
	private Integer ruleDetailType; //用于此继承映射的类型，以区分是实体还是工作流的rule
	
	public Integer getRuleDetailType() {
		return ruleDetailType;
	}
	public void setRuleDetailType(Integer ruleDetailType) {
		this.ruleDetailType = ruleDetailType;
	}
	public RuleCondition getCondition() {
		return condition;
	}
	public void setCondition(RuleCondition condition) {
		this.condition = condition;
	}
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	public SystemMainTable getEntity() {
		return entity;
	}
	public void setEntity(SystemMainTable entity) {
		this.entity = entity;
	}
	public SystemMainTableColumn getEntityProperty() {
		return entityProperty;
	}
	public void setEntityProperty(SystemMainTableColumn entityProperty) {
		this.entityProperty = entityProperty;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((condition == null) ? 0 : condition.hashCode());
		result = PRIME * result + ((conditionValue == null) ? 0 : conditionValue.hashCode());
		result = PRIME * result + ((entity == null) ? 0 : entity.hashCode());
		result = PRIME * result + ((entityProperty == null) ? 0 : entityProperty.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
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
		final RuleDetail other = (RuleDetail) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (conditionValue == null) {
			if (other.conditionValue != null)
				return false;
		} else if (!conditionValue.equals(other.conditionValue))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (entityProperty == null) {
			if (other.entityProperty != null)
				return false;
		} else if (!entityProperty.equals(other.entityProperty))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}

}
