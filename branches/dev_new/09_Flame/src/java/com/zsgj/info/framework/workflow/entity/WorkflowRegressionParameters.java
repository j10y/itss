package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;
/**
 * 我们要确定一个节点的参数；那我们就需要找“哪个虚拟流程的”，“哪个流程实例的”，“哪个节点的”，“的参数”
 * @author Administrator
 *
 */
public class WorkflowRegressionParameters extends BaseObject{
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2072451785389998563L;
	private Long id;
	private Long virtualProcessId;
	private Long processInstanceId;
	private Long nodeId;//同一个流程定义的多个虚拟流程的节点ID是一样的，需要结合虚拟节点Id来唯一标示
	private String nodeName;
	private String nodeDesc;
	private String regressionParams;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getVirtualProcessId() {
		return virtualProcessId;
	}
	public void setVirtualProcessId(Long virtualProcessId) {
		this.virtualProcessId = virtualProcessId;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public String getRegressionParams() {
		return regressionParams;
	}
	public void setRegressionParams(String regressionParams) {
		this.regressionParams = regressionParams;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nodeDesc == null) ? 0 : nodeDesc.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime
				* result
				+ ((processInstanceId == null) ? 0 : processInstanceId
						.hashCode());
		result = prime
				* result
				+ ((regressionParams == null) ? 0 : regressionParams.hashCode());
		result = prime
				* result
				+ ((virtualProcessId == null) ? 0 : virtualProcessId.hashCode());
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
		final WorkflowRegressionParameters other = (WorkflowRegressionParameters) obj;
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
		if (processInstanceId == null) {
			if (other.processInstanceId != null)
				return false;
		} else if (!processInstanceId.equals(other.processInstanceId))
			return false;
		if (regressionParams == null) {
			if (other.regressionParams != null)
				return false;
		} else if (!regressionParams.equals(other.regressionParams))
			return false;
		if (virtualProcessId == null) {
			if (other.virtualProcessId != null)
				return false;
		} else if (!virtualProcessId.equals(other.virtualProcessId))
			return false;
		return true;
	}
	public Long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	

}
