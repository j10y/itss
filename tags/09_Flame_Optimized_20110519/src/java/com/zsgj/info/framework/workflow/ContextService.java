package com.zsgj.info.framework.workflow;

import java.util.Map;

import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;

public interface ContextService {
	/**
	 * ͨ�����̻�������ı����б�
	 * @Methods Name listVariablesByProcessId
	 * @Create In 2008-4-3 By yang
	 * @param processInstanceId ���̱�ʶ
	 * @ReturnType Map
	 */
	public Map listVariablesByProcessId(long processInstanceId);
	/**
	 * ͨ�����̻��ĳ�������ĵ�ֵ
	 * @Methods Name getVariableByProcessId
	 * @Create In 2008-4-3 By yang
	 * @param instanceId ���̱�ʶ
	 * @param name ��������
	 * @ReturnType Object
	 */
	public Object getVariableByProcessId(long instanceId, String name);
	/**
	 * ͨ���������������ı���ֵ
	 * @Methods Name setVariableByProcessId
	 * @Create In 2008-4-3 By yang
	 * @param instanceId ���̱�ʶ
	 * @param name ��������
	 * @param value ������ֵ
	 * @ReturnType void
	 */
	public void setVariableByProcessId(long instanceId, String name,Object value);

	/**
	 * ͨ������ɾ�������ı���
	 * @Methods Name removeVariableByProcessId
	 * @Create In 2008-4-10 By y
	 * @param instanceId ���̱�ʶ
	 * @param name ��������
	 * @ReturnType void
	 */
	public void removeVariableByProcessId(long instanceId, String name);

	/**
	 * ͨ�������������ı����б�
	 * @Methods Name listVariablesByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId �����ʶ
	 * @ReturnType Map
	 */
	public Map listVariablesByTaskId(long taskId);
	
	
	/**
	 * ͨ������õ�ҵ�����Map
	 * @Methods Name listBizVariablesByTaskId
	 * @Create In Mar 22, 2009 By Administrator
	 * @param taskId
	 * @return Map
	 */
	public Map listBizVariablesByTaskId(long taskId);
	/**
	 * ͨ��������ĳ�������ĵ�ֵ
	 * @Methods Name getVariableByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId �����ʶ
	 * @param name ��������
	 * @ReturnType Object
	 */
	public Object getVariableByTaskId(long taskId, String name);
	/**
	 * ͨ���������������ı���ֵ
	 * @Methods Name setVariableByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId �����ʶ
	 * @param name ��������
	 * @param value ������ֵ
	 * @ReturnType void
	 */
	public void setVariableByTaskId(long taskId, String name,Object value);
	
	
	/**
	 * ������ʵ���������е�map������һ��ҵ�����
	 * @param taskId
	 * @param name
	 * @param value
	 */
	public void setVariableToBizParam(long taskId, String name, String value);
	/**
	 * ͨ������ɾ�������ı���
	 * @Methods Name removeVariableByTaskId
	 * @Create In 2008-4-10 By y
	 * @param taskId
	 * @param name 
	 * @ReturnType void
	 */
	public void removeVariableByTaskId(long taskId, String name);
	/**
	 * ����ҳ����������Ҫ��������Ҫ�������ݿ�RecordTaskInfo����Ϣ
	 * @Methods Name removeVariableByTaskId
	 * @Create In 2008-4-10 By y
	 * @param virtualProcessId
	 * @param nodeId
	 * @ReturnType void
	 */
	public WorkflowRecordTaskInfo findWorkflowRecordTaskInfoById(Long processId, Long nodeId);

}
