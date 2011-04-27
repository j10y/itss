package com.zsgj.info.framework.rules.entity;

import com.zsgj.info.framework.security.entity.Module;

/**
 * 实体规则
 * @Class Name EntityRule
 * @Author peixf
 * @Create In 2008-4-14
 */
public class EntityRule extends RuleDetail{
	private static final long serialVersionUID = -1833457797434047121L;
	
	private Long id;
	private Module module;
	private RuleResultType ruleResultType;
	private String ruleResultValue;
	
//	用于此继承映射的类型，以区分是实体还是工作流的rule
	private Integer ruleDetailType = RuleDetail.RULE_DETAIL_TYPE_ENTITY; 
	

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((module == null) ? 0 : module.hashCode());
		result = PRIME * result + ((ruleDetailType == null) ? 0 : ruleDetailType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EntityRule other = (EntityRule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (ruleDetailType == null) {
			if (other.ruleDetailType != null)
				return false;
		} else if (!ruleDetailType.equals(other.ruleDetailType))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Integer getRuleDetailType() {
		return ruleDetailType;
	}

	public void setRuleDetailType(Integer ruleDetailType) {
		this.ruleDetailType = ruleDetailType;
	}

	public RuleResultType getRuleResultType() {
		return ruleResultType;
	}

	public void setRuleResultType(RuleResultType ruleResultType) {
		this.ruleResultType = ruleResultType;
	}

	public String getRuleResultValue() {
		return ruleResultValue;
	}

	public void setRuleResultValue(String ruleResultValue) {
		this.ruleResultValue = ruleResultValue;
	}
	
	
}
