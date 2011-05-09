package com.zsgj.info.framework.workflow.action;

import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.WorkflowConstants;
/**
 * ת�ƽڵ��Action����
 * @Class Name TransAction
 * @Author yang
 * @Create In Jun 19, 2008
 */

public  abstract class TransAction extends BaseAction{
	
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
	/**
	 * ����ת������
	 */
	public abstract String getTransName();

	//�ϳɼ�ֵ
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_";
		key += getTransName().trim();
		return key;
	}
}
