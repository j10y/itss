package com.zsgj.info.framework.workflow;

import java.util.Map;
/**
 * 管理流程中业务上的参数,防止与系统所用参数混淆
 * @Class Name ParameterService
 * @Author yang
 * @Create In Jul 1, 2008
 */
public interface ParameterService {
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
	 * 批量增加业务参数
	 * @Methods Name addVariablesByProcessId
	 * @Create In Jul 2, 2008 By yang
	 * @param instanceId
	 * @param params 
	 * @ReturnType void
	 */
	public void addVariablesByProcessId(long instanceId, Map params);

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
	
//	/**
//	 * 删除所有业务参数
//	 * @Methods Name removeAllVariablesByProcessId
//	 * @Create In Jul 2, 2008 By yang
//	 * @param instanceId 
//	 * @ReturnType void
//	 */
//	public void removeAllVariablesByProcessId(long instanceId);

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
	 * 通过任务删除上下文变量
	 * @Methods Name removeVariableByTaskId
	 * @Create In 2008-4-10 By y
	 * @param taskId
	 * @param name 
	 * @ReturnType void
	 */
	public void removeVariableByTaskId(long taskId, String name);

}
