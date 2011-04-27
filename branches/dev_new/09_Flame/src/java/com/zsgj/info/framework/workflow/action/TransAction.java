package com.zsgj.info.framework.workflow.action;

import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.WorkflowConstants;
/**
 * 转移节点的Action基类
 * @Class Name TransAction
 * @Author yang
 * @Create In Jun 19, 2008
 */

public  abstract class TransAction extends BaseAction{
	
	/**
	 * 执行具体任务
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
	 * 给出转移名称
	 */
	public abstract String getTransName();

	//合成键值
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_";
		key += getTransName().trim();
		return key;
	}
}
