package com.zsgj.info.framework.workflow;

import java.util.Map;

import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;

public interface ContextService {
	/**
	 * 通过流程获得上下文变量列表
	 * @Methods Name listVariablesByProcessId
	 * @Create In 2008-4-3 By yang
	 * @param processInstanceId 流程标识
	 * @ReturnType Map
	 */
	public Map listVariablesByProcessId(long processInstanceId);
	/**
	 * 通过流程获得某个上下文的值
	 * @Methods Name getVariableByProcessId
	 * @Create In 2008-4-3 By yang
	 * @param instanceId 流程标识
	 * @param name 变量名称
	 * @ReturnType Object
	 */
	public Object getVariableByProcessId(long instanceId, String name);
	/**
	 * 通过流程设置上下文变量值
	 * @Methods Name setVariableByProcessId
	 * @Create In 2008-4-3 By yang
	 * @param instanceId 流程标识
	 * @param name 变量名称
	 * @param value 变量的值
	 * @ReturnType void
	 */
	public void setVariableByProcessId(long instanceId, String name,Object value);

	/**
	 * 通过流程删除上下文变量
	 * @Methods Name removeVariableByProcessId
	 * @Create In 2008-4-10 By y
	 * @param instanceId 流程标识
	 * @param name 变量名称
	 * @ReturnType void
	 */
	public void removeVariableByProcessId(long instanceId, String name);

	/**
	 * 通过任务获得上下文变量列表
	 * @Methods Name listVariablesByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId 任务标识
	 * @ReturnType Map
	 */
	public Map listVariablesByTaskId(long taskId);
	
	
	/**
	 * 通过任务得到业务参数Map
	 * @Methods Name listBizVariablesByTaskId
	 * @Create In Mar 22, 2009 By Administrator
	 * @param taskId
	 * @return Map
	 */
	public Map listBizVariablesByTaskId(long taskId);
	/**
	 * 通过任务获得某个上下文的值
	 * @Methods Name getVariableByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId 任务标识
	 * @param name 变量名称
	 * @ReturnType Object
	 */
	public Object getVariableByTaskId(long taskId, String name);
	/**
	 * 通过任务设置上下文变量值
	 * @Methods Name setVariableByTaskId
	 * @Create In 2008-4-3 By yang
	 * @param taskId 任务标识
	 * @param name 变量名称
	 * @param value 变量的值
	 * @ReturnType void
	 */
	public void setVariableByTaskId(long taskId, String name,Object value);
	
	
	/**
	 * 在流程实例上下文中的map中设置一下业务参数
	 * @param taskId
	 * @param name
	 * @param value
	 */
	public void setVariableToBizParam(long taskId, String name, String value);
	/**
	 * 通过任务删除上下文变量
	 * @Methods Name removeVariableByTaskId
	 * @Create In 2008-4-10 By y
	 * @param taskId
	 * @param name 
	 * @ReturnType void
	 */
	public void removeVariableByTaskId(long taskId, String name);
	/**
	 * 由于页面审批的需要，我们需要更新数据库RecordTaskInfo中信息
	 * @Methods Name removeVariableByTaskId
	 * @Create In 2008-4-10 By y
	 * @param virtualProcessId
	 * @param nodeId
	 * @ReturnType void
	 */
	public WorkflowRecordTaskInfo findWorkflowRecordTaskInfoById(Long processId, Long nodeId);

}
