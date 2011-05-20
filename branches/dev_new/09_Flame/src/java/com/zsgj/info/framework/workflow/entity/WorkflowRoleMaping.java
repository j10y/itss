package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Role;

/**
 * 系统的角色与工作流的角色的映射
 * @Class Name WorkflowRoleMaping
 * @Author sa
 * @Create In 2008-12-12
 */
public class WorkflowRoleMaping extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 375806058938445053L;
	private Long id;
	private Role role;
	private WorkflowRole workflowRole;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public WorkflowRole getWorkflowRole() {
		return workflowRole;
	}
	public void setWorkflowRole(WorkflowRole workflowRole) {
		this.workflowRole = workflowRole;
	}
}
