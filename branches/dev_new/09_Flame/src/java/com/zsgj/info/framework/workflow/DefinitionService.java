package com.zsgj.info.framework.workflow;
/**
 * 受设计中前端定制工具限制，此处的流程定义中的节点类型仅限于任务节点和普通节点。
 * 每个节点内部绑定一个"node-leave"事件的NodeLeaveActionHandler,用以执行各种离开节点时动作。
 * 同时绑定一个"node-enter"事件的NodeEnterActionHandler,用以执行各种进入节点时动作。
 * 每个转移(Transation)内部绑定一个TransActionHandler,用以执行各种转移时动作。
 */

import java.util.List;
import java.util.Map;

import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.workflow.entity.DefinitionInfo;
import com.zsgj.info.framework.workflow.info.NodeInfo;
import com.zsgj.info.framework.workflow.info.ProcessInfo;
/**
 * 关于流程定义的接口
 * @Class Name DefinitionService
 * @Author yang
 * @Create In 2008-3-11
 */
public interface DefinitionService {
	
	/**
	 * 获得一个流程定义中所有任务节点
	 * @Methods Name getTaskNodes
	 * @Create In 2008-3-18 By yang
	 * @param name 流程定义的英文名称
	 * @return 流程定义中的任务节点名称，用于预指派功能。
	 * @ReturnType List<NodeInfo>
	 */
	public List<NodeInfo> getTaskNodes(String name);
	
	/**
	 * 获得一个流程定义中所有节点
	 * @Methods Name getAllNodes
	 * @Create In Feb 12, 2009 By yang
	 * @param name
	 * @return 
	 * @ReturnType List<NodeInfo>
	 */
	public List<NodeInfo> getAllNodes(String name);
	
	/**
	 * 获取所有的流程定义信息
	 * @Methods Name getAllDefinitions
	 * @Create In 2008-3-27 By yang
	 * @return DefinitionInfo的列表
	 * @ReturnType List<DefinitionInfo>
	 */
	public List<DefinitionInfo> getAllDefinitions();
	/**
	 * 获取所有的流程定义最后版本信息
	 * @Methods Name getLatestDefinitions
	 * @Create In Nov 24, 2008 By yang
	 * @return 
	 * @ReturnType List<DefinitionInfo>
	 */
	public List<DefinitionInfo> getLatestDefinitions();
	/**
	 * 发布新的流程定义
	 * @Methods Name deployDefinition
	 * @Create In 2008-4-1 By yang
	 * @param name 流程名
	 * @ReturnType void
	 */
	public void deployDefinition(String name);
	
	/**
	 * 用流程名称和描述模糊查询流程定义
	 * 查询条件为空或为null是意义为不参与查询
	 * @Methods Name searchDefinition
	 * @Create In Feb 12, 2009 By yang
	 * @param descLike 
	 * @ReturnType void
	 */	
	public List<DefinitionInfo> searchDefinition(String nameLike, String descLike);
	
	/**
	 * 删除已有的流程定义
	 * @Methods Name deleteDefinition
	 * @Create In 2008-4-1 By yang
	 * @param processDefinitionId 定义ID
	 * @ReturnType void
	 */
	public void deleteDefinition(long processDefinitionId);
	
	/**
	 * 根据流程定义名称取得所有版本的当前所有活动的流程实例信息
	 * @Methods Name getActiveProcess
	 * @Create In 2008-4-16 By yang
	 * @param processDefinitionId 流程定义ID
	 * @return ProcessInfo列表
	 * @ReturnType List
	 */
	public List<ProcessInfo> getActiveProcess(long processDefinitionId); 
	/**
	 * 2010-05-12 abate by 光顺安 for 具体失效缘由
	 * 删除该方法
	 * 用新的方法替代
	 * public List<ProcessInfo> getAllActiveProcessInstance()
	 * 取所有所有版本流程定义的活动的流程实例信息
	 * @Methods Name getAllActiveProcess
	 * @Create In 2008-4-16 By yang
	 * @return ProcessInfo列表
	 * @ReturnType List
	 */
	public List<ProcessInfo> getAllActiveProcess(); 
	/**
	 * 取所有所有版本流程定义的活动的流程实例信息
	 * @Methods Name getAllActiveProcess
	 * @Create In 2010-5-12 By guangsa
	 * @return ProcessInfo列表
	 * @ReturnType List
	 */
	public List<ProcessInfo> getAllActiveProcessInstance();
	/**
	 * 根据流程定义标识获得流程定义
	 * @Methods Name getDefinitionById
	 * @Create In 2008-4-17 By yang
	 * @param id 流程定义标识
	 * @return 流程定义
	 * @ReturnType ProcessDefinition
	 */
	public ProcessDefinition getDefinitionById(long id);
	
	
	/**
	 * 根据是流程和节点id得到该节点的类型
	 * @Methods Name getNodeByNodeId
	 * @Create In Mar 25, 2009 By Administrator
	 * @param nodeId
	 * @param p
	 * @return String
	 */
	public String getNodeByNodeId(String nodeId,Long p) ;
	
	/**
	 * 得到最新版本的流程定义
	 * @Methods Name getAllLatestProcess
	 * @Create In Mar 26, 2009 By Administrator
	 * @return List<ProcessDefinition>
	 */
	public List<ProcessDefinition> getAllLatestProcess();
	/**
	 * 根据虚拟流程名获取数据（模糊查询）
	 * @Methods Name getProcessDefinition
	 * @Create In Jun 19, 2009 By lee
	 * @param processName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getProcessDefinition(String processName, int pageNo, int pageSize);
}
