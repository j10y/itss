package com.digitalchina.info.framework.rules.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 规则实体
 * @Class Name RuleType
 * @Author peixf
 * @Create In 2008-4-14
 */
public class Rule extends BaseObject{
	private static final long serialVersionUID = -3210336051711665518L;
	
	private Long id;
	//private Long roleId;
	private String roleName;
	private RuleType roleType;
	
	private Set ruleDetails = new HashSet();
	//private RuleDetail ruleDetail;
	
	private String descn;

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

	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public RuleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RuleType roleType) {
		this.roleType = roleType;
	}

	public Set getRuleDetails() {
		return ruleDetails;
	}

	public void setRuleDetails(Set ruleDetails) {
		this.ruleDetails = ruleDetails;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((descn == null) ? 0 : descn.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = PRIME * result + ((roleType == null) ? 0 : roleType.hashCode());
		result = PRIME * result + ((ruleDetails == null) ? 0 : ruleDetails.hashCode());
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
		final Rule other = (Rule) obj;
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
		
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (roleType == null) {
			if (other.roleType != null)
				return false;
		} else if (!roleType.equals(other.roleType))
			return false;
		if (ruleDetails == null) {
			if (other.ruleDetails != null)
				return false;
		} else if (!ruleDetails.equals(other.ruleDetails))
			return false;
		return true;
	}

}
