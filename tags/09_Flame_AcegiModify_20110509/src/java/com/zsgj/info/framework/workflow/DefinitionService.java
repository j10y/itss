package com.zsgj.info.framework.workflow;
/**
 * �������ǰ�˶��ƹ������ƣ��˴������̶����еĽڵ����ͽ���������ڵ����ͨ�ڵ㡣
 * ÿ���ڵ��ڲ���һ��"node-leave"�¼���NodeLeaveActionHandler,����ִ�и����뿪�ڵ�ʱ������
 * ͬʱ��һ��"node-enter"�¼���NodeEnterActionHandler,����ִ�и��ֽ���ڵ�ʱ������
 * ÿ��ת��(Transation)�ڲ���һ��TransActionHandler,����ִ�и���ת��ʱ������
 */

import java.util.List;
import java.util.Map;

import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.workflow.entity.DefinitionInfo;
import com.zsgj.info.framework.workflow.info.NodeInfo;
import com.zsgj.info.framework.workflow.info.ProcessInfo;
/**
 * �������̶���Ľӿ�
 * @Class Name DefinitionService
 * @Author yang
 * @Create In 2008-3-11
 */
public interface DefinitionService {
	
	/**
	 * ���һ�����̶�������������ڵ�
	 * @Methods Name getTaskNodes
	 * @Create In 2008-3-18 By yang
	 * @param name ���̶����Ӣ������
	 * @return ���̶����е�����ڵ����ƣ�����Ԥָ�ɹ��ܡ�
	 * @ReturnType List<NodeInfo>
	 */
	public List<NodeInfo> getTaskNodes(String name);
	
	/**
	 * ���һ�����̶��������нڵ�
	 * @Methods Name getAllNodes
	 * @Create In Feb 12, 2009 By yang
	 * @param name
	 * @return 
	 * @ReturnType List<NodeInfo>
	 */
	public List<NodeInfo> getAllNodes(String name);
	
	/**
	 * ��ȡ���е����̶�����Ϣ
	 * @Methods Name getAllDefinitions
	 * @Create In 2008-3-27 By yang
	 * @return DefinitionInfo���б�
	 * @ReturnType List<DefinitionInfo>
	 */
	public List<DefinitionInfo> getAllDefinitions();
	/**
	 * ��ȡ���е����̶������汾��Ϣ
	 * @Methods Name getLatestDefinitions
	 * @Create In Nov 24, 2008 By yang
	 * @return 
	 * @ReturnType List<DefinitionInfo>
	 */
	public List<DefinitionInfo> getLatestDefinitions();
	/**
	 * �����µ����̶���
	 * @Methods Name deployDefinition
	 * @Create In 2008-4-1 By yang
	 * @param name ������
	 * @ReturnType void
	 */
	public void deployDefinition(String name);
	
	/**
	 * ���������ƺ�����ģ����ѯ���̶���
	 * ��ѯ����Ϊ�ջ�Ϊnull������Ϊ�������ѯ
	 * @Methods Name searchDefinition
	 * @Create In Feb 12, 2009 By yang
	 * @param descLike 
	 * @ReturnType void
	 */	
	public List<DefinitionInfo> searchDefinition(String nameLike, String descLike);
	
	/**
	 * ɾ�����е����̶���
	 * @Methods Name deleteDefinition
	 * @Create In 2008-4-1 By yang
	 * @param processDefinitionId ����ID
	 * @ReturnType void
	 */
	public void deleteDefinition(long processDefinitionId);
	
	/**
	 * �������̶�������ȡ�����а汾�ĵ�ǰ���л������ʵ����Ϣ
	 * @Methods Name getActiveProcess
	 * @Create In 2008-4-16 By yang
	 * @param processDefinitionId ���̶���ID
	 * @return ProcessInfo�б�
	 * @ReturnType List
	 */
	public List<ProcessInfo> getActiveProcess(long processDefinitionId); 
	/**
	 * 2010-05-12 abate by ��˳�� for ����ʧЧԵ��
	 * ɾ���÷���
	 * ���µķ������
	 * public List<ProcessInfo> getAllActiveProcessInstance()
	 * ȡ�������а汾���̶���Ļ������ʵ����Ϣ
	 * @Methods Name getAllActiveProcess
	 * @Create In 2008-4-16 By yang
	 * @return ProcessInfo�б�
	 * @ReturnType List
	 */
	public List<ProcessInfo> getAllActiveProcess(); 
	/**
	 * ȡ�������а汾���̶���Ļ������ʵ����Ϣ
	 * @Methods Name getAllActiveProcess
	 * @Create In 2010-5-12 By guangsa
	 * @return ProcessInfo�б�
	 * @ReturnType List
	 */
	public List<ProcessInfo> getAllActiveProcessInstance();
	/**
	 * �������̶����ʶ������̶���
	 * @Methods Name getDefinitionById
	 * @Create In 2008-4-17 By yang
	 * @param id ���̶����ʶ
	 * @return ���̶���
	 * @ReturnType ProcessDefinition
	 */
	public ProcessDefinition getDefinitionById(long id);
	
	
	/**
	 * ���������̺ͽڵ�id�õ��ýڵ������
	 * @Methods Name getNodeByNodeId
	 * @Create In Mar 25, 2009 By Administrator
	 * @param nodeId
	 * @param p
	 * @return String
	 */
	public String getNodeByNodeId(String nodeId,Long p) ;
	
	/**
	 * �õ����°汾�����̶���
	 * @Methods Name getAllLatestProcess
	 * @Create In Mar 26, 2009 By Administrator
	 * @return List<ProcessDefinition>
	 */
	public List<ProcessDefinition> getAllLatestProcess();
	/**
	 * ����������������ȡ���ݣ�ģ����ѯ��
	 * @Methods Name getProcessDefinition
	 * @Create In Jun 19, 2009 By lee
	 * @param processName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getProcessDefinition(String processName, int pageNo, int pageSize);
}
