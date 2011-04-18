package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.framework.dao.BaseObject;



/**
 * 流程定义和部门之间的关系，只有定义了这个关系才能进行下一步配置,
 * 相当于流程定义的注册
 * 保存管理员配置的流程预指派信息
 * 一个节点只对应一个角色
 * @Class Name DepartmentDefinition
 * @Author yang
 * @Create In Dec 8, 2008
 */
public class DefinitionPreAssign extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1133347597290598277L;

	Long id;
	
	String departmentCode;//key，定义对应的部门
	String departmentName;//name
	String definitionName;//key
	String definitionDesc;//name
	String nodeName;
	WorkflowRole workflowRole;
	
	
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public String getDefinitionDesc() {
		return definitionDesc;
	}
	public void setDefinitionDesc(String definitionDesc) {
		this.definitionDesc = definitionDesc;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public WorkflowRole getWorkflowRole() {
		return workflowRole;
	}
	public void setWorkflowRole(WorkflowRole workflowRole) {
		this.workflowRole = workflowRole;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((definitionName == null) ? 0 : definitionName.hashCode());
		result = prime * result
				+ ((departmentCode == null) ? 0 : departmentCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((workflowRole == null) ? 0 : workflowRole.hashCode());
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
		final DefinitionPreAssign other = (DefinitionPreAssign) obj;
		if (definitionName == null) {
			if (other.definitionName != null)
				return false;
		} else if (!definitionName.equals(other.definitionName))
			return false;
		if (departmentCode == null) {
			if (other.departmentCode != null)
				return false;
		} else if (!departmentCode.equals(other.departmentCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (workflowRole == null) {
			if (other.workflowRole != null)
				return false;
		} else if (!workflowRole.equals(other.workflowRole))
			return false;
		return true;
	}
}
