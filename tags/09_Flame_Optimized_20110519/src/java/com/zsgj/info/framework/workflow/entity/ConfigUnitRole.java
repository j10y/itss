package com.zsgj.info.framework.workflow.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;
/**
 * 这个实体的意思就是表示“在哪个流程的哪个节点属于哪个部门中的哪个角色”
 * @Class Name ConfigUnitRole
 * @Author guangsa
 * @Create In Feb 23, 2009
 */
public class ConfigUnitRole extends BaseObject{
	private Long id;
	private String definitionName;
	private String nodeDesc;
	private String nodeName;
	private String depName;
	private Set roles = new HashSet(0);
	private Long processId;//虚拟流程的id
	private Long nodeId;
	private Integer nodeType;
	private Integer isGiveCreate;
	private Integer isNotSendMail;  
	
	
	public Integer getIsNotSendMail() {
		return isNotSendMail;
	}
	public void setIsNotSendMail(Integer isNotSendMail) {
		this.isNotSendMail = isNotSendMail;
	}
	public Integer getIsGiveCreate() {
		return isGiveCreate;
	}
	public void setIsGiveCreate(Integer isGiveCreate) {
		this.isGiveCreate = isGiveCreate;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}	
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((definitionName == null) ? 0 : definitionName.hashCode());
		result = prime * result + ((depName == null) ? 0 : depName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isGiveCreate == null) ? 0 : isGiveCreate.hashCode());
		result = prime * result
				+ ((isNotSendMail == null) ? 0 : isNotSendMail.hashCode());
		result = prime * result
				+ ((nodeDesc == null) ? 0 : nodeDesc.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result
				+ ((nodeType == null) ? 0 : nodeType.hashCode());
		result = prime * result
				+ ((processId == null) ? 0 : processId.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		final ConfigUnitRole other = (ConfigUnitRole) obj;
		if (definitionName == null) {
			if (other.definitionName != null)
				return false;
		} else if (!definitionName.equals(other.definitionName))
			return false;
		if (depName == null) {
			if (other.depName != null)
				return false;
		} else if (!depName.equals(other.depName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isGiveCreate == null) {
			if (other.isGiveCreate != null)
				return false;
		} else if (!isGiveCreate.equals(other.isGiveCreate))
			return false;
		if (isNotSendMail == null) {
			if (other.isNotSendMail != null)
				return false;
		} else if (!isNotSendMail.equals(other.isNotSendMail))
			return false;
		if (nodeDesc == null) {
			if (other.nodeDesc != null)
				return false;
		} else if (!nodeDesc.equals(other.nodeDesc))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (nodeType == null) {
			if (other.nodeType != null)
				return false;
		} else if (!nodeType.equals(other.nodeType))
			return false;
		if (processId == null) {
			if (other.processId != null)
				return false;
		} else if (!processId.equals(other.processId))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
	
}
