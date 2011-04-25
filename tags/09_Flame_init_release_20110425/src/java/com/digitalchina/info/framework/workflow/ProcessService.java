package com.digitalchina.info.framework.workflow;

import java.util.List;
import java.util.Map;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.info.HistoryInfo;
import com.digitalchina.info.framework.workflow.info.ProcessInfo;
import com.digitalchina.info.framework.workflow.info.TaskInfo;
/**
 * �������̵Ľӿ�
 * @Class Name ProcessService
 * @Author yang
 * @Create In 2008-3-11
 */
public interface ProcessService {
	
	/**
	 * �û�����һ�����̶��壬�������̣�����ʹ�ô�ҵ�����ݵ�ͬ������
	 * @Methods Name createProcess
	 * @Create In 2008-3-10 By yang
	 * @param ename ���̶����Ӣ�����ƣ������ƶ�Ӧһ�����̶����ļ�������ʱװ�����Ѿ����������°汾��
	 * @param preAssignMapping Ԥָ�ɵ���Աӳ�䣬key������ڵ�����node_id��value:ָ����Ա��ʶ��ΪActorInfo����
	 * ���л�Ҫ���̴�������Ϣ
	 * @ReturnType long ���������̱�ʶ
	 */

	public long createProcess(String defname,String creator);

	
	/**
	 * �û�����һ�����̶��岢��ҵ���������ȴ��룬��������ACTION��ʹ�ã���������
	 * @Methods Name createProcess
	 * @Create In 2008-3-10 By yang
	 * @param ename ���̶����Ӣ�����ƣ������ƶ�Ӧһ�����̶����ļ�������ʱװ�����Ѿ����������°汾��
	 * @param preAssignMapping Ԥָ�ɵ���Աӳ�䣬key������ڵ�����node_id��value:ָ����Ա��ʶ��ΪActorInfo����
	 * ���л�Ҫ���̴�������Ϣ
	 * @param bizParam ҵ�����
	 * @ReturnType long ���������̱�ʶ
	 */
	public long createProcess(String defname,String creator,Map bizParam);

	/**
	 * ǿ�н���һ������,����ִ�е���һ����
	 * @Methods Name endProcess
	 * @Create In 2008-3-14 By yang
	 * @param id ����ʵ����ʶ
	 * @ReturnType void
	 */	 
	public void endProcess(long id);
	
	/**
	 * ���̽�����ʷ״̬�б�
	 * @Methods Name getHistory
	 * @Create In 2008-3-10 By yang
	 * @param id ����ʵ����ʶ
	 * @return List<HistoryInfo> Ԫ��Ϊǰ���Ѿ�ִ����������˳���б�
	 */
	public List<HistoryInfo> getHistory(long id);
	
	/**
	 * ���ݱ�ʶȡ�����̶�����Ϣ
	 * @Methods Name getDefinitionName
	 * @Create In 2008-4-2 By yang
	 * @param id ���̱�ʶ
	 * @ReturnType DefinitionInfo ���̶�����Ϣ
	 */
	public DefinitionInfo getDefinitionInfo(long id);
	
	/**
	 * ���õ�ǰ���̷����û�
	 * @Methods Name setCreator
	 * @Create In 2008-4-3 By yang
	 * @param actorId �û���ʶ
	 * @param processId ���̱�ʶ
	 * @ReturnType void
	 */
//	public void setCreator(long processId,String actorId);
	/**
	 * ������̷����߱�ʶ
	 * @Methods Name getCreatorId
	 * @Create In 2008-4-3 By yang
	 * @param id ���̱�ʶ
	 * @ReturnType String
	 */
//	public String getCreator(long id);
	
	/**
	 * ���ݱ�־�������ʵ��
	 * @Methods Name getProcessById
	 * @Create In 2008-4-10 By yang
	 * @param id ����ʵ��
	 * @ReturnType ProcessInstance
	 */
	public ProcessInstance getProcessById(long id);
	/**
	 * �����
	 * @Methods Name getActiveTasks
	 * @Create In 2008-4-17 By yang
	 * @param processId ���̱�ʶ
	 * @return ������б�
	 * @ReturnType List<TaskInfo>
	 */
	public List<TaskInfo> getActiveTasks(long processId);
	
	/**
	 * �����
	 * @Methods Name getAllTasks
	 * @Create In 2009-12-3 By gaowen
	 * @param processId ���̱�ʶ
	 * @return ������б�
	 * @ReturnType List<TaskInfo>
	 */
	public List<TaskInfo> getAllTasks(long processId);
	
	/**
	 * ȡ������Ϣ
	 * @Methods Name getProcessInfo
	 * @Create In 2008-4-18 By yang
	 * @param processId ���̱�ʶ
	 * @return 
	 * @ReturnType ProcessInfo
	 */
	public ProcessInfo getProcessInfo(long processId);
	
	/**
	 * �õ����̶���֮������̹ҽ���Ӧ��action
	 * @Methods Name addActions
	 * @Create In Apr 13, 2009 By guangsa
	 * @param pd void
	 */
	public void addActions(ProcessDefinition pd);
	
	/**
	 * ���̸�ʽ��
	 * @Methods Name formatDefinition
	 * @Create In Apr 13, 2009 By guangsa
	 * @param pd
	 * @return ProcessDefinition
	 */
	public ProcessDefinition formatDefinition(ProcessDefinition pd) ;
}
