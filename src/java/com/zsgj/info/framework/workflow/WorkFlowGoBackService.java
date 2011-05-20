package com.zsgj.info.framework.workflow;

import java.util.Map;

import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.framework.workflow.entity.WorkflowRegressionParameters;

/**
 * ���̻��˵�һЩҵ�񷽷�
 * @author Administrator
 *
 */
public interface WorkFlowGoBackService {
	/**
	 * ���������˷�����(�˷����ѹ�ʱ)
	 * 1.�Ȱѵ�ǰ������ֹ��
	 * 2.Ȼ������һ������
	 * 3.����һ���ڵ�id���뵽��Ӧ��token����
	 * @param processInstanceId
	 * @param nodeId
	 * @param taskName
	 */
	public void saveWorkFlowGoBack(Long processInstanceId, Long nodeId, String taskName);
	
	/**
	 * �����������б���ÿһ�ڵ�Ĳ���
	 * @param virtualProcessId
	 * @param nodeId
	 * @param params
	 */
	public WorkflowRegressionParameters saveWorkflowRegressionParams(Long virtualProcessId, Long processInstanceId ,Long nodeId , String nodeName,String nodeDesc ,Map bizParams);
	
	/**
	 * �����������̣�����ʵ�����ڵ�id�����ҽڵ����
	 * @param virtualProcessId
	 * @param processInstanceId
	 * @param nodeId
	 * @return
	 */
	public WorkflowRegressionParameters findWorkflowRegressionParametersByMuitlyId(Long virtualProcessId, Long processInstanceId ,Long nodeId );
	
	/**
	 * ��һ������ʵ��������ʱ����Ҫ�Ѽ�¼��ǰ����ʵ���Ľڵ�����ı��¼ɾ����
	 * @param virtualProcessId
	 * @param processInstanceId
	 */
	public void removeWorkflowRegressionParametersByProcessId(Long virtualProcessId, Long processInstanceId)throws Exception;
	/**
	 * ��������һ���ڵ㷢���쳣�Ժ󣬻����;���ٴ��ܵ�����ڵ��ʱ�򣬴�ʱ�����Ѿ���������ڵ����Ϣ��
	 * ��ʱ��������һ�δ��룬���ǾͲ��ᷢ���쳣���������߼�
	 * @param virtualProcessId
	 * @param processInstanceId
	 * @param nodeId
	 */
	public void removeNodeWorkflowRegressionParameters(Long virtualProcessId, Long processInstanceId,Long nodeId);
	
	/**
	 * ͨ����������ID�ҵ���Ӧ���������� 
	 * @param vProcessId
	 * @return
	 */
	public long findRealDefinitonByVprocessId(String vProcessId);
}
