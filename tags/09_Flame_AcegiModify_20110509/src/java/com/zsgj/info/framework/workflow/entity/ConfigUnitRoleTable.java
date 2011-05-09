package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Role;

/**
 * ConfigUnit与角色的对应关联表
 * @Class Name ConfigUnitRoleTable
 * @Author guangsa
 * @Create In Feb 24, 2009
 */
public class ConfigUnitRoleTable extends BaseObject{
	private Long id;
	private ConfigUnitRole configUnitRole;
	private Role role;//配置单元中角色
	private Integer flag;
	private String workflowBrowsePerson;//工作流查看人
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ConfigUnitRole getConfigUnitRole() {
		return configUnitRole;
	}
	public void setConfigUnitRole(ConfigUnitRole configUnitRole) {
		this.configUnitRole = configUnitRole;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getWorkflowBrowsePerson() {
		return workflowBrowsePerson;
	}
	public void setWorkflowBrowsePerson(String workflowBrowsePerson) {
		this.workflowBrowsePerson = workflowBrowsePerson;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((configUnitRole == null) ? 0 : configUnitRole.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime
				* result
				+ ((workflowBrowsePerson == null) ? 0 : workflowBrowsePerson
						.hashCode());
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
		final ConfigUnitRoleTable other = (ConfigUnitRoleTable) obj;
		if (configUnitRole == null) {
			if (other.configUnitRole != null)
				return false;
		} else if (!configUnitRole.equals(other.configUnitRole))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (workflowBrowsePerson == null) {
			if (other.workflowBrowsePerson != null)
				return false;
		} else if (!workflowBrowsePerson.equals(other.workflowBrowsePerson))
			return false;
		return true;
	}
}
