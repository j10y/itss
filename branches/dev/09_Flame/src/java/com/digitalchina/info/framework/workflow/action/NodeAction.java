package com.digitalchina.info.framework.workflow.action;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;
/**
 * 普通节点的Action基类（主要对应非任务节点）
 * @Class Name NodeAction
 * @Author yang
 * @Create In Jun 19, 2008
 */


public  abstract class NodeAction extends BaseAction{
	/**
	 * 执行具体任务，需要指出下一个转移：如ec.leaveNode("Y")
	 */
	public abstract void execute(ExecutionContext ec) throws Exception;
	/**
	 * 给出流程定义名称
	 */
	public abstract String getDefinitionName();
	/**
	 * 给出节点名称
	 */
	public abstract String getNodeName();
	/**
	 * 给出对应的事件类型
	 */
	public abstract String getEventType();
	
	//合成键值
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_";
		key += getEventType().trim();
		return key;
		
	}

}
