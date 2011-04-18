package com.digitalchina.info.framework.workflow.action;

import java.util.Map;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;


public abstract class BaseAction{
	//ִ�о�������
	public abstract void execute(ExecutionContext ec) throws Exception;
	//���������	
	public abstract String getDefinitionName();
	//�ڵ������
	public abstract String getNodeName();
	//��ֵ
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
