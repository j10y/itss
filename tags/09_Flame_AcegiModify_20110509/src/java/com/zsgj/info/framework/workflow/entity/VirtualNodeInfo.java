package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class VirtualNodeInfo extends BaseObject{
	private Long id;
	private String virtualNodeName;
	private String virtualNodeDesc;
	private VirtualDefinitionInfo virtualDefinitionInfo;
	private Long nodeId;//真实流程的nodeId
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVirtualNodeName() {
		return virtualNodeName;
	}
	public void setVirtualNodeName(String virtualNodeName) {
		this.virtualNodeName = virtualNodeName;
	}
	public String getVirtualNodeDesc() {
		return virtualNodeDesc;
	}
	public void setVirtualNodeDesc(String virtualNodeDesc) {
		this.virtualNodeDesc = virtualNodeDesc;
	}
	
	public VirtualDefinitionInfo getVirtualDefinitionInfo() {
		return virtualDefinitionInfo;
	}
	public void setVirtualDefinitionInfo(VirtualDefinitionInfo virtualDefinitionInfo) {
		this.virtualDefinitionInfo = virtualDefinitionInfo;
	}
	
}
