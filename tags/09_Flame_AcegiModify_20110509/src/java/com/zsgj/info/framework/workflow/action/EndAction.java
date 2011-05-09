package com.zsgj.info.framework.workflow.action;

import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.WorkflowConstants;
/**
 * ��ֹ�ڵ��Action����
 * @Class Name EndAction
 * @Author yang
 * @Create In Jun 19, 2008
 */

public abstract class EndAction extends BaseAction{
	/**
	 * ִ�о�������
	 */
	public abstract void execute(ExecutionContext ec) throws Exception;
	/**
	 * �������̶�������
	 */
	public abstract String getDefinitionName();
	/**
	 * �����ڵ�����
	 */
	public abstract String getNodeName();
	
	//�ϳɼ�ֵ
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_end";
		return key;
	}
}
