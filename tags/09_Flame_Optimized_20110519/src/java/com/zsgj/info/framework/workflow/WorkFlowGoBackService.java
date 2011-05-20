package com.zsgj.info.framework.workflow;

import java.util.Map;

import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.framework.workflow.entity.WorkflowRegressionParameters;

/**
 * 流程回退的一些业务方法
 * @author Administrator
 *
 */
public interface WorkFlowGoBackService {
	/**
	 * 工作流回退分三步(此方法已过时)
	 * 1.先把当前任务终止掉
	 * 2.然后开启上一个任务
	 * 3.把上一个节点id放入到相应的token当中
	 * @param processInstanceId
	 * @param nodeId
	 * @param taskName
	 */
	public void saveWorkFlowGoBack(Long processInstanceId, Long nodeId, String taskName);
	
	/**
	 * 工作流回退中保存每一节点的参数
	 * @param virtualProcessId
	 * @param nodeId
	 * @param params
	 */
	public WorkflowRegressionParameters saveWorkflowRegressionParams(Long virtualProcessId, Long processInstanceId ,Long nodeId , String nodeName,String nodeDesc ,Map bizParams);
	
	/**
	 * 根据虚拟流程，流程实例，节点id来查找节点参数
	 * @param virtualProcessId
	 * @param processInstanceId
	 * @param nodeId
	 * @return
	 */
	public WorkflowRegressionParameters findWorkflowRegressionParametersByMuitlyId(Long virtualProcessId, Long processInstanceId ,Long nodeId );
	
	/**
	 * 当一个流程实例结束的时候需要把记录当前流程实例的节点参数的表记录删除掉
	 * @param virtualProcessId
	 * @param processInstanceId
	 */
	public void removeWorkflowRegressionParametersByProcessId(Long virtualProcessId, Long processInstanceId)throws Exception;
	/**
	 * 当流程在一个节点发生异常以后，会回退;当再次跑到这个节点的时候，此时库中已经有了这个节点的信息；
	 * 此时会走另外一段代码，这是就不会发生异常，不符合逻辑
	 * @param virtualProcessId
	 * @param processInstanceId
	 * @param nodeId
	 */
	public void removeNodeWorkflowRegressionParameters(Long virtualProcessId, Long processInstanceId,Long nodeId);
	
	/**
	 * 通过虚拟流程ID找到相应的真是流程 
	 * @param vProcessId
	 * @return
	 */
	public long findRealDefinitonByVprocessId(String vProcessId);
}
