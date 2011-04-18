package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.security.entity.Role;
/**
 * NodeConfig这个实体其实是提取了相应的配置模型（ConfigModel）的信息？
 * @Class Name NodeConfig
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class NodeConfig {
	private Long id;
	private String definitionName;
	private String nodeName;
	private NodeType nodeType;
	private Integer deleteFlag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public NodeType getNodeType() {
		return nodeType;
	}
	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
