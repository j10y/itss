package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class WorkflowRecordTaskInfo extends BaseObject{
	
	private Long id;
	private Long dataId;//唯一标示
	private Long processInstanceId;//这个是流程实例ID
	private Long taskId;
	private Long nodeId;
	private Long virtualProcessId;
	private String virtualProcessName;
	private String nodeDesc;
	private String nodeName;
	private String auditUserInfos;
	private String processCreator;
	public String getAuditUserInfos() {
		return auditUserInfos;
	}
	public void setAuditUserInfos(String auditUserInfos) {
		this.auditUserInfos = auditUserInfos;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Long getVirtualProcessId() {
		return virtualProcessId;
	}
	public void setVirtualProcessId(Long virtualProcessId) {
		this.virtualProcessId = virtualProcessId;
	}
	public String getVirtualProcessName() {
		return virtualProcessName;
	}
	public void setVirtualProcessName(String virtualProcessName) {
		this.virtualProcessName = virtualProcessName;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessCreator() {
		return processCreator;
	}
	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((auditUserInfos == null) ? 0 : auditUserInfos.hashCode());
		result = prime * result + ((dataId == null) ? 0 : dataId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nodeDesc == null) ? 0 : nodeDesc.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result
				+ ((processCreator == null) ? 0 : processCreator.hashCode());
		result = prime
				* result
				+ ((processInstanceId == null) ? 0 : processInstanceId
						.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime
				* result
				+ ((virtualProcessId == null) ? 0 : virtualProcessId.hashCode());
		result = prime
				* result
				+ ((virtualProcessName == null) ? 0 : virtualProcessName
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
		final WorkflowRecordTaskInfo other = (WorkflowRecordTaskInfo) obj;
		if (auditUserInfos == null) {
			if (other.auditUserInfos != null)
				return false;
		} else if (!auditUserInfos.equals(other.auditUserInfos))
			return false;
		if (dataId == null) {
			if (other.dataId != null)
				return false;
		} else if (!dataId.equals(other.dataId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (processCreator == null) {
			if (other.processCreator != null)
				return false;
		} else if (!processCreator.equals(other.processCreator))
			return false;
		if (processInstanceId == null) {
			if (other.processInstanceId != null)
				return false;
		} else if (!processInstanceId.equals(other.processInstanceId))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (virtualProcessId == null) {
			if (other.virtualProcessId != null)
				return false;
		} else if (!virtualProcessId.equals(other.virtualProcessId))
			return false;
		if (virtualProcessName == null) {
			if (other.virtualProcessName != null)
				return false;
		} else if (!virtualProcessName.equals(other.virtualProcessName))
			return false;
		return true;
	}
	

}
