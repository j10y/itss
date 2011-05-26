package com.zsgj.itil.config.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;

public class ModleToProcess extends BaseObject{
	private Long id;
	private String modleType;
	private Integer processStatusType;
	private String definitionName;
	private VirtualDefinitionInfo processInfo; // 关联流程
	public VirtualDefinitionInfo getProcessInfo() {
		return processInfo;
	}
	public void setProcessInfo(VirtualDefinitionInfo processInfo) {
		this.processInfo = processInfo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModleType() {
		return modleType;
	}
	public void setModleType(String modleType) {
		this.modleType = modleType;
	}
	public Integer getProcessStatusType() {
		return processStatusType;
	}
	public void setProcessStatusType(Integer processStatusType) {
		this.processStatusType = processStatusType;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((definitionName == null) ? 0 : definitionName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((modleType == null) ? 0 : modleType.hashCode());
		result = prime
				* result
				+ ((processStatusType == null) ? 0 : processStatusType
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
		ModleToProcess other = (ModleToProcess) obj;
		if (definitionName == null) {
			if (other.definitionName != null)
				return false;
		} else if (!definitionName.equals(other.definitionName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modleType == null) {
			if (other.modleType != null)
				return false;
		} else if (!modleType.equals(other.modleType))
			return false;
		if (processStatusType == null) {
			if (other.processStatusType != null)
				return false;
		} else if (!processStatusType.equals(other.processStatusType))
			return false;
		return true;
	}
	
	
}
