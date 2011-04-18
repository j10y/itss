package com.digitalchina.info.framework.workflow.action;

import java.util.Map;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;


public abstract class BaseAction{
	//执行具体任务
	public abstract void execute(ExecutionContext ec) throws Exception;
	//定义的名称	
	public abstract String getDefinitionName();
	//节点的名称
	public abstract String getNodeName();
	//键值
	public abstract String getKey();
	
	public String getBizParam(ExecutionContext ec,String key) {
		Map params = (Map)ec.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		if(params==null) {
			return null;
		}
		String value = (String)params.get(key);
		return value;
	}
}
