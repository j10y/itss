package com.zsgj.info.framework.workflow.action;

import org.jbpm.graph.exe.ExecutionContext;
/**
 * ��ʼ�ڵ��Action����
 * @Class Name InitAction
 * @Author yang
 * @Create In Jun 19, 2008
 */

public abstract class InitAction extends BaseAction{
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
	
	public String getKey() {
		String key = getDefinitionName().trim()+"_";
		key += getNodeName().trim()+"_init";
		return key;
	}
}
