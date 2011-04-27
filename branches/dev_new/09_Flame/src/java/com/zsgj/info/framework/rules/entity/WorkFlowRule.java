package com.zsgj.info.framework.rules.entity;


/**
 * 工作流相关规则描述
 * @Class Name WorkFlowRule
 * @Author peixf
 * @Create In 2008-4-14
 */
public class WorkFlowRule extends RuleDetail{
	private static final long serialVersionUID = -3584382574851162405L;

	private Long id;
	private String workFlowName;
	private String workFlowItemName;

	//用于此继承映射的类型，以区分是实体还是工作流的rule
	private Integer ruleDetailType = RuleDetail.RULE_DETAIL_TYPE_WORKFLOW; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRuleDetailType() {
		return ruleDetailType;
	}

	public void setRuleDetailType(Integer ruleDetailType) {
		this.ruleDetailType = ruleDetailType;
	}

	public String getWorkFlowItemName() {
		return workFlowItemName;
	}

	public void setWorkFlowItemName(String workFlowItemName) {
		this.workFlowItemName = workFlowItemName;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((ruleDetailType == null) ? 0 : ruleDetailType.hashCode());
		result = PRIME * result + ((workFlowItemName == null) ? 0 : workFlowItemName.hashCode());
		result = PRIME * result + ((workFlowName == null) ? 0 : workFlowName.hashCode());
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
		final WorkFlowRule other = (WorkFlowRule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ruleDetailType == null) {
			if (other.ruleDetailType != null)
				return false;
		} else if (!ruleDetailType.equals(other.ruleDetailType))
			return false;
		if (workFlowItemName == null) {
			if (other.workFlowItemName != null)
				return false;
		} else if (!workFlowItemName.equals(other.workFlowItemName))
			return false;
		if (workFlowName == null) {
			if (other.workFlowName != null)
				return false;
		} else if (!workFlowName.equals(other.workFlowName))
			return false;
		return true;
	}
	
	
}
