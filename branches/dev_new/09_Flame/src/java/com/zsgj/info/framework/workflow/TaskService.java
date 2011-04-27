package com.zsgj.info.framework.workflow;

import java.util.List;
import java.util.Map;

import org.jbpm.graph.def.Node;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.workflow.info.ProcessInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
/**
 * �û��������ӿ�
 * @Class Name TaskService
 * @Author yang
 * @Create In 2008-3-10
 */
public interface TaskService {
	
	/**
	 * �б����д򿪵��������ڹ���������
	 * @Methods Name listAllTasks
	 * @Create In Aug 11, 2008 By yang
	 * @return 
	 * @ReturnType List<TaskInfo>
	 */
	public List<TaskInfo> listAllTasks();
	/**
	 * �����û���ʶ�������б�
	 * @Methods Name listTasks
	 * @Create In 2008-3-10 By yang
	 * @param actorId �û���ʶ
	 * @return List<TaskInfo> �����б�,�ڲ�Ԫ��ΪTaskInfo
	 */	
	public List<TaskInfo> listTasks(String actorId);
	/**
	 * ���������ʶ��ȡһ���������Ϣ
	 * @Methods Name getTaskById
	 * @Create In 2008-3-12 By yang
	 * @param id �����ʶ
	 * @return TaskInstance 
	 */
	public TaskInstance getTaskById(long id);
//	/**
//	 * ȡ��һ���������ڹ���������
//	 * @Methods Name cancel
//	 * @Create In Aug 11, 2008 By yang
//	 * @param taskId 
//	 * @ReturnType void
//	 */
//	public void cancel(long taskId); 
	
	/**
	 * ִ��һ������
	 * @Methods Name execute
	 * @Create In 2008-3-12 By yang
	 * @param taskId �����ʶ
	 * @ReturnType void
	 */
	
	public void execute(long taskId); 
	
	/**
	 * ���ض�����˽��ִ��������ʽ�ر���result��comment
	 * @Methods Name execute
	 * @Create In 2008-4-10 By y
	 * @param taskId �����ʶ
	 * @param result ���������涨Ϊ����"Y,N,R"֮һ����ʾͨ�����ܾ��ͱ�����ʵ���϶�Ӧ�뿪·�������ơ�
	 * @param comment ������Ϣ
	 * @ReturnType void
	 */
	
//	public void execute(long taskId,String result,String comment);

	/**
	 * ���ض��ĸ�����Ϣִ������
	 * @Methods Name execute
	 * @Create In 2008-4-10 By y
	 * @param taskId �����ʶ
	 * @param attachments ������Ϣ��Ӧ�ð���result��comment
	 * @ReturnType void
	 */
	
//	public void execute(long taskId,Map attachments);

	
	/**
	 * Ϊ�ض���������ָ����Ա
	 * @Methods Name assign
	 * @Create In 2008-3-12 By yang
	 * @param taskId  �����ʶ
	 * @param actorId  �û���ʶ,������û���ѡʱ���м��á�|������
	 * @ReturnType void
	 */
	public void reAssign(long taskId, String actorId);
	
	/**
	 * Ϊ��ǩ����ڵ�����ָ����Ա
	 * @Methods Name assign
	 * @Create In 2009-12-2 By gaowen
	 * @param taskId  �����ʶ
	 * @param actorId  �û���ʶ,������û���ѡʱ���м��á�|������
	 * @ReturnType void
	 */
	public Long addSignReAssign(long taskId, String actorId);
	
	/**
	 * ����ָ��
	 * @Methods Name proxyAssign
	 * @Create In Aug 27, 2008 By yang
	 * @param taskId
	 * @param proxy ������Ϣ 
	 * @ReturnType void
	 */
	//@Deprecated
	//public void proxyAssign(long taskId, ActorInfo proxy);

	/**
	 * �����������������ʵ��
	 * @Methods Name getProcessInfo
	 * @Create In 2008-4-2 By yang
	 * @param taskId �����ʶ
	 * @ReturnType ProcessInfo
	 */
	public ProcessInfo getProcessInfo(long taskId);
	
	/**
	 * ������������Ϣ
	 * @Methods Name getTaskInfo
	 * @Create In 2008-4-17 By yang
	 * @param taskId �����ʶ
	 * @return 
	 * @ReturnType TaskInfo
	 */
	public TaskInfo getTaskInfo(long taskId);
	
	/**
	 * ��������id�õ����нڵ�
	 * @Methods Name getAllNodeByTaskId
	 * @Create In Mar 26, 2009 By Administrator
	 * @param taskId
	 * @return TaskInfo
	 */
	public String getAllNodeByTaskId(long taskId);
	
	/**
	 * �õ��ڵ�����
	 * @Methods Name getNodeDesc
	 * @Create In Mar 26, 2009 By Administrator
	 * @param taskId
	 * @param nodeId
	 * @return String
	 */
	public String getNodeDesc(Long taskId,Long nodeId);
	
	/**
	 * �õ���һ���ڵ���Ϣ
	 * @Methods Name getNextNodeInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param taskId
	 * @return Map
	 */
	public Map getNextNodeInfo(Long taskId);
	/**
	 * ������Ӧ�Ĳ����õ���Ӧ�Ĺ���Ԫ
	 * @Methods Name getNextNodeInfo
	 * @param nodeId
	 * @param ProcessId
	 * @return
	 */
	public String findRuleConfigUnitByParam(Long nodeId ,Long processId);
	/**
	 * �õ���ǰ�����������������������Ϣ
	 * @Methods Name getAllActiveTaskNodeMessage
	 * @param actorId
	 * @return
	 */
	public List<TaskInfo> getAllActiveTaskNodeMessage(String actorId);
}
