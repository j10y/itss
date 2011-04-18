package com.digitalchina.info.framework.workflow.action;

import org.jbpm.graph.exe.ExecutionContext;

import com.digitalchina.info.framework.workflow.WorkflowConstants;
/**
 * ��ͨ�ڵ��Action���ࣨ��Ҫ��Ӧ������ڵ㣩
 * @Class Name NodeAction
 * @Author yang
 * @Create In Jun 19, 2008
 */


public  abstract class NodeAction extends BaseAction{
	/**
	 * ִ�о���������Ҫָ����һ��ת�ƣ���ec.leaveNode("Y")
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
	 * ������Ӧ���¼�����
	 */
	public abstract String getEventType();
	
	//�ϳɼ�ֵ
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_";
		key += getEventType().trim();
		return key;
		
	}

}
