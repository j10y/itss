package com.digitalchina.info.framework.workflow.info;

import org.jbpm.graph.def.Node;

import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.entity.NodeType;
/**
 * 包装节点
 * @Class Name NodeInfo
 * @Author yang
 * @Create In 2008-6-10
 */
public class NodeInfo extends BaseInfo{
	String definitionName;//定义名称	
	String nodeName;//节点名称
	String type;
	String desc;

	public NodeInfo() {
	}
	
	public NodeInfo(Node node) {
		nodeName = node.getName();
		definitionName = node.getProcessDefinition().getName();
		desc = node.getDescription();
		setId(node.getId());
		setName(WorkflowConstants.NODE_KEY+node.getId());
		type=node.toString();
	}

	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
