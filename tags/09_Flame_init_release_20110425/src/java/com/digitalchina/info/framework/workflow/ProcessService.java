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
 * 关于流程的接口
 * @Class Name ProcessService
 * @Author yang
 * @Create In 2008-3-11
 */
public interface ProcessService {
	
	/**
	 * 用户启动一个流程定义，创建流程，建议使用带业务数据的同名函数
	 * @Methods Name createProcess
	 * @Create In 2008-3-10 By yang
	 * @param ename 流程定义的英文名称，此名称对应一个流程定义文件，启动时装载其已经发布的最新版本。
	 * @param preAssignMapping 预指派的人员映射，key：任务节点名称node_id；value:指派人员标识（为ActorInfo）；
	 * 其中还要流程创建人信息
	 * @ReturnType long 创建的流程标识
	 */

	public long createProcess(String defname,String creator);

	
	/**
	 * 用户启动一个流程定义并把业务数据首先传入，可在随后的ACTION中使用，创建流程
	 * @Methods Name createProcess
	 * @Create In 2008-3-10 By yang
	 * @param ename 流程定义的英文名称，此名称对应一个流程定义文件，启动时装载其已经发布的最新版本。
	 * @param preAssignMapping 预指派的人员映射，key：任务节点名称node_id；value:指派人员标识（为ActorInfo）；
	 * 其中还要流程创建人信息
	 * @param bizParam 业务参数
	 * @ReturnType long 创建的流程标识
	 */
	public long createProcess(String defname,String creator,Map bizParam);

	/**
	 * 强行结束一个流程,不论执行到哪一步。
	 * @Methods Name endProcess
	 * @Create In 2008-3-14 By yang
	 * @param id 流程实例标识
	 * @ReturnType void
	 */	 
	public void endProcess(long id);
	
	/**
	 * 流程进行历史状态列表。
	 * @Methods Name getHistory
	 * @Create In 2008-3-10 By yang
	 * @param id 流程实例标识
	 * @return List<HistoryInfo> 元素为前面已经执行完的任务的顺序列表。
	 */
	public List<HistoryInfo> getHistory(long id);
	
	/**
	 * 根据标识取得流程定义信息
	 * @Methods Name getDefinitionName
	 * @Create In 2008-4-2 By yang
	 * @param id 流程标识
	 * @ReturnType DefinitionInfo 流程定义信息
	 */
	public DefinitionInfo getDefinitionInfo(long id);
	
	/**
	 * 设置当前流程发起用户
	 * @Methods Name setCreator
	 * @Create In 2008-4-3 By yang
	 * @param actorId 用户标识
	 * @param processId 流程标识
	 * @ReturnType void
	 */
//	public void setCreator(long processId,String actorId);
	/**
	 * 获得流程发起者标识
	 * @Methods Name getCreatorId
	 * @Create In 2008-4-3 By yang
	 * @param id 流程标识
	 * @ReturnType String
	 */
//	public String getCreator(long id);
	
	/**
	 * 根据标志获得流程实例
	 * @Methods Name getProcessById
	 * @Create In 2008-4-10 By yang
	 * @param id 流程实例
	 * @ReturnType ProcessInstance
	 */
	public ProcessInstance getProcessById(long id);
	/**
	 * 活动任务
	 * @Methods Name getActiveTasks
	 * @Create In 2008-4-17 By yang
	 * @param processId 流程标识
	 * @return 活动任务列表
	 * @ReturnType List<TaskInfo>
	 */
	public List<TaskInfo> getActiveTasks(long processId);
	
	/**
	 * 活动任务
	 * @Methods Name getAllTasks
	 * @Create In 2009-12-3 By gaowen
	 * @param processId 流程标识
	 * @return 活动任务列表
	 * @ReturnType List<TaskInfo>
	 */
	public List<TaskInfo> getAllTasks(long processId);
	
	/**
	 * 取流程信息
	 * @Methods Name getProcessInfo
	 * @Create In 2008-4-18 By yang
	 * @param processId 流程标识
	 * @return 
	 * @ReturnType ProcessInfo
	 */
	public ProcessInfo getProcessInfo(long processId);
	
	/**
	 * 得到流程定义之后给流程挂接相应的action
	 * @Methods Name addActions
	 * @Create In Apr 13, 2009 By guangsa
	 * @param pd void
	 */
	public void addActions(ProcessDefinition pd);
	
	/**
	 * 流程格式化
	 * @Methods Name formatDefinition
	 * @Create In Apr 13, 2009 By guangsa
	 * @param pd
	 * @return ProcessDefinition
	 */
	public ProcessDefinition formatDefinition(ProcessDefinition pd) ;
}
