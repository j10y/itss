package com.digitalchina.info.framework.workflow.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 工作流任务预指派
 * 实际是指代理关系的信息
 * 保存用户预指派信息
 * @Class Name TaskPreAssign
 * @Author peixf
 * @Create In 2008-8-28
 */
public class TaskPreAssign extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1122237860796064184L;

	private Long id;
		
	private String departmentCode;//实际对应的部门	
	private String departmentName;
	private String definitionName;
	private String definitionDesc;
	private String taskName;
	private String actorId;
	private String actorName;
	private String proxyId;
	private String proxyName;
	private Date proxyBegin;
	private Date proxyEnd;
	
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getProxyBegin() {
		return proxyBegin;
	}
	public void setProxyBegin(Date proxyBegin) {
		this.proxyBegin = proxyBegin;
	}
	public Date getProxyEnd() {
		return proxyEnd;
	}
	public void setProxyEnd(Date proxyEnd) {
		this.proxyEnd = proxyEnd;
	}
	public String getProxyId() {
		return proxyId;
	}
	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((definitionName == null) ? 0 : definitionName.hashCode());
		result = PRIME * result + ((proxyId == null) ? 0 : proxyId.hashCode());
		result = PRIME * result + ((taskName == null) ? 0 : taskName.hashCode());
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
		final TaskPreAssign other = (TaskPreAssign) obj;
		if (definitionName == null) {
			if (other.definitionName != null)
				return false;
		} else if (!definitionName.equals(other.definitionName))
			return false;
		if (proxyId == null) {
			if (other.proxyId != null)
				return false;
		} else if (!proxyId.equals(other.proxyId))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}
	public String getProxyName() {
		return proxyName;
	}
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	public String getDefinitionDesc() {
		return definitionDesc;
	}
	public void setDefinitionDesc(String definitionDesc) {
		this.definitionDesc = definitionDesc;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
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
}
