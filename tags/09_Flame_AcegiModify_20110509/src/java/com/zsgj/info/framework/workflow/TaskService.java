package com.zsgj.info.framework.workflow;

import java.util.List;
import java.util.Map;

import org.jbpm.graph.def.Node;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.workflow.info.ProcessInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
/**
 * 用户任务管理接口
 * @Class Name TaskService
 * @Author yang
 * @Create In 2008-3-10
 */
public interface TaskService {
	
	/**
	 * 列表所有打开的任务，用于工作流管理
	 * @Methods Name listAllTasks
	 * @Create In Aug 11, 2008 By yang
	 * @return 
	 * @ReturnType List<TaskInfo>
	 */
	public List<TaskInfo> listAllTasks();
	/**
	 * 根据用户标识对任务列表
	 * @Methods Name listTasks
	 * @Create In 2008-3-10 By yang
	 * @param actorId 用户标识
	 * @return List<TaskInfo> 任务列表,内部元素为TaskInfo
	 */	
	public List<TaskInfo> listTasks(String actorId);
	/**
	 * 根据任务标识获取一个任务的信息
	 * @Methods Name getTaskById
	 * @Create In 2008-3-12 By yang
	 * @param id 任务标识
	 * @return TaskInstance 
	 */
	public TaskInstance getTaskById(long id);
//	/**
//	 * 取消一项任务，用于工作流管理
//	 * @Methods Name cancel
//	 * @Create In Aug 11, 2008 By yang
//	 * @param taskId 
//	 * @ReturnType void
//	 */
//	public void cancel(long taskId); 
	
	/**
	 * 执行一个任务
	 * @Methods Name execute
	 * @Create In 2008-3-12 By yang
	 * @param taskId 任务标识
	 * @ReturnType void
	 */
	
	public void execute(long taskId); 
	
	/**
	 * 以特定的审核结果执行任务，显式地保存result、comment
	 * @Methods Name execute
	 * @Create In 2008-4-10 By y
	 * @param taskId 任务标识
	 * @param result 任务结果，规定为常量"Y,N,R"之一，表示通过，拒绝和保留，实际上对应离开路径的名称。
	 * @param comment 审批信息
	 * @ReturnType void
	 */
	
//	public void execute(long taskId,String result,String comment);

	/**
	 * 以特定的附件信息执行任务，
	 * @Methods Name execute
	 * @Create In 2008-4-10 By y
	 * @param taskId 任务标识
	 * @param attachments 附件信息，应该包括result和comment
	 * @ReturnType void
	 */
	
//	public void execute(long taskId,Map attachments);

	
	/**
	 * 为特定任务重新指派人员
	 * @Methods Name assign
	 * @Create In 2008-3-12 By yang
	 * @param taskId  任务标识
	 * @param actorId  用户标识,当多个用户任选时，中间用“|”隔开
	 * @ReturnType void
	 */
	public void reAssign(long taskId, String actorId);
	
	/**
	 * 为加签任务节点重新指派人员
	 * @Methods Name assign
	 * @Create In 2009-12-2 By gaowen
	 * @param taskId  任务标识
	 * @param actorId  用户标识,当多个用户任选时，中间用“|”隔开
	 * @ReturnType void
	 */
	public Long addSignReAssign(long taskId, String actorId);
	
	/**
	 * 代理指派
	 * @Methods Name proxyAssign
	 * @Create In Aug 27, 2008 By yang
	 * @param taskId
	 * @param proxy 代理信息 
	 * @ReturnType void
	 */
	//@Deprecated
	//public void proxyAssign(long taskId, ActorInfo proxy);

	/**
	 * 获得任务所属的流程实例
	 * @Methods Name getProcessInfo
	 * @Create In 2008-4-2 By yang
	 * @param taskId 任务标识
	 * @ReturnType ProcessInfo
	 */
	public ProcessInfo getProcessInfo(long taskId);
	
	/**
	 * 获得任务基本信息
	 * @Methods Name getTaskInfo
	 * @Create In 2008-4-17 By yang
	 * @param taskId 任务标识
	 * @return 
	 * @ReturnType TaskInfo
	 */
	public TaskInfo getTaskInfo(long taskId);
	
	/**
	 * 根据任务id得到所有节点
	 * @Methods Name getAllNodeByTaskId
	 * @Create In Mar 26, 2009 By Administrator
	 * @param taskId
	 * @return TaskInfo
	 */
	public String getAllNodeByTaskId(long taskId);
	
	/**
	 * 得到节点描述
	 * @Methods Name getNodeDesc
	 * @Create In Mar 26, 2009 By Administrator
	 * @param taskId
	 * @param nodeId
	 * @return String
	 */
	public String getNodeDesc(Long taskId,Long nodeId);
	
	/**
	 * 得到下一个节点信息
	 * @Methods Name getNextNodeInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param taskId
	 * @return Map
	 */
	public Map getNextNodeInfo(Long taskId);
	/**
	 * 根据相应的参数得到相应的规则单元
	 * @Methods Name getNextNodeInfo
	 * @param nodeId
	 * @param ProcessId
	 * @return
	 */
	public String findRuleConfigUnitByParam(Long nodeId ,Long processId);
	/**
	 * 得到当前登入人所申请的所有流程信息
	 * @Methods Name getAllActiveTaskNodeMessage
	 * @param actorId
	 * @return
	 */
	public List<TaskInfo> getAllActiveTaskNodeMessage(String actorId);
}
