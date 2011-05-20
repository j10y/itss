package com.zsgj.info.framework.workflow;

import java.util.Map;
/**
 * ����������ҵ���ϵĲ���,��ֹ��ϵͳ���ò�������
 * @Class Name ParameterService
 * @Author yang
 * @Create In Jul 1, 2008
 */
public interface ParameterService {
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
	 * ��������ҵ�����
	 * @Methods Name addVariablesByProcessId
	 * @Create In Jul 2, 2008 By yang
	 * @param instanceId
	 * @param params 
	 * @ReturnType void
	 */
	public void addVariablesByProcessId(long instanceId, Map params);

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
	
//	/**
//	 * ɾ������ҵ�����
//	 * @Methods Name removeAllVariablesByProcessId
//	 * @Create In Jul 2, 2008 By yang
//	 * @param instanceId 
//	 * @ReturnType void
//	 */
//	public void removeAllVariablesByProcessId(long instanceId);

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
	 * ͨ������ɾ�������ı���
	 * @Methods Name removeVariableByTaskId
	 * @Create In 2008-4-10 By y
	 * @param taskId
	 * @param name 
	 * @ReturnType void
	 */
	public void removeVariableByTaskId(long taskId, String name);

}
