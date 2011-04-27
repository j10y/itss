package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Module;

public class VirtualDefinitionInfo extends BaseObject{
	private Long id;
	private Long processDefinitionId;
	private String realDefinitionName;
	private String virtualDefinitionName;
	private String realDefinitionDesc;
	private String virtualDefinitionDesc;
	private String ruleFileName;
	private Module type;
	private Department dept;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRealDefinitionName() {
		return realDefinitionName;
	}
	public void setRealDefinitionName(String realDefinitionName) {
		this.realDefinitionName = realDefinitionName;
	}
	public String getVirtualDefinitionName() {
		return virtualDefinitionName;
	}
	public void setVirtualDefinitionName(String virtualDefinitionName) {
		this.virtualDefinitionName = virtualDefinitionName;
	}
	public String getRealDefinitionDesc() {
		return realDefinitionDesc;
	}
	public void setRealDefinitionDesc(String realDefinitionDesc) {
		this.realDefinitionDesc = realDefinitionDesc;
	}
	public String getVirtualDefinitionDesc() {
		return virtualDefinitionDesc;
	}
	public void setVirtualDefinitionDesc(String virtualDefinitionDesc) {
		this.virtualDefinitionDesc = virtualDefinitionDesc;
	}
	public String getRuleFileName() {
		return ruleFileName;
	}
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((processDefinitionId == null) ? 0 : processDefinitionId
						.hashCode());
		result = prime
				* result
				+ ((realDefinitionDesc == null) ? 0 : realDefinitionDesc
						.hashCode());
		result = prime
				* result
				+ ((realDefinitionName == null) ? 0 : realDefinitionName
						.hashCode());
		result = prime * result
				+ ((ruleFileName == null) ? 0 : ruleFileName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime
				* result
				+ ((virtualDefinitionDesc == null) ? 0 : virtualDefinitionDesc
						.hashCode());
		result = prime
				* result
				+ ((virtualDefinitionName == null) ? 0 : virtualDefinitionName
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
		final VirtualDefinitionInfo other = (VirtualDefinitionInfo) obj;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (processDefinitionId == null) {
			if (other.processDefinitionId != null)
				return false;
		} else if (!processDefinitionId.equals(other.processDefinitionId))
			return false;
		if (realDefinitionDesc == null) {
			if (other.realDefinitionDesc != null)
				return false;
		} else if (!realDefinitionDesc.equals(other.realDefinitionDesc))
			return false;
		if (realDefinitionName == null) {
			if (other.realDefinitionName != null)
				return false;
		} else if (!realDefinitionName.equals(other.realDefinitionName))
			return false;
		if (ruleFileName == null) {
			if (other.ruleFileName != null)
				return false;
		} else if (!ruleFileName.equals(other.ruleFileName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (virtualDefinitionDesc == null) {
			if (other.virtualDefinitionDesc != null)
				return false;
		} else if (!virtualDefinitionDesc.equals(other.virtualDefinitionDesc))
			return false;
		if (virtualDefinitionName == null) {
			if (other.virtualDefinitionName != null)
				return false;
		} else if (!virtualDefinitionName.equals(other.virtualDefinitionName))
			return false;
		return true;
	}
	public Module getType() {
		return type;
	}
	public void setType(Module type) {
		this.type = type;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	

}
