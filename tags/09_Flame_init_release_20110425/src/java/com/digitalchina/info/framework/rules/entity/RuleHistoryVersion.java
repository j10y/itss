package com.digitalchina.info.framework.rules.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 规则历史版本实体
 * @Class Name RuleHistoryVersion
 * @Author peixf
 * @Create In 2008-4-16
 */
public class RuleHistoryVersion extends BaseObject{
	private static final long serialVersionUID = 5366237919493263383L;
	
	private Long id;
	private Rule rule;
	private String ruleName;
	private RuleType ruleType;
	private RuleDetail ruleDetail;
	private String descn;
	private Integer version;
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((descn == null) ? 0 : descn.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((rule == null) ? 0 : rule.hashCode());
		result = PRIME * result + ((ruleName == null) ? 0 : ruleName.hashCode());
		result = PRIME * result + ((version == null) ? 0 : version.hashCode());
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
		final RuleHistoryVersion other = (RuleHistoryVersion) obj;
		if (descn == null) {
			if (other.descn != null)
				return false;
		} else if (!descn.equals(other.descn))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (ruleName == null) {
			if (other.ruleName != null)
				return false;
		} else if (!ruleName.equals(other.ruleName))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	public RuleDetail getRuleDetail() {
		return ruleDetail;
	}
	public void setRuleDetail(RuleDetail ruleDetail) {
		this.ruleDetail = ruleDetail;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public RuleType getRuleType() {
		return ruleType;
	}
	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	
}
