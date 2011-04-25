package com.digitalchina.info.framework.workflow.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jbpm.graph.def.ProcessDefinition;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.workflow.WorkFlowGoBackService;
import com.digitalchina.info.framework.workflow.dao.WorkFlowGoBackDao;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.WorkflowRegressionParameters;

public class WorkFlowGoBackServiceImp extends BaseDao implements WorkFlowGoBackService {
	private WorkFlowGoBackDao workflowGoBackDao=null;
	
	public void setWorkflowGoBackDao(WorkFlowGoBackDao workflowGoBackDao) {
		this.workflowGoBackDao = workflowGoBackDao;
	}

	/**
	 * 1.先把当前任务终止掉
	 * 2.然后开启上一个任务
	 * 3.把上一个节点id放入到相应的token当中
	 * @Methods Name saveWorkFlowGoBack
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public void saveWorkFlowGoBack(Long processInstanceId, Long nodeId,String taskName) {
		
		workflowGoBackDao.workFlowGoBack(processInstanceId, nodeId, taskName);
	}
	
	
	/**
	 * 保存工作流节点的参数 
	 * @Methods Name saveWorkflowRegressionParams
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public WorkflowRegressionParameters saveWorkflowRegressionParams(Long virtualProcessId,Long processInstanceId, Long nodeId, String nodeName,String nodeDesc ,
			Map bizParams) {
		
		//先找到所需的流程节点
		//参数格式：{key+value;key+value;key+value;+key+value}
		String regressionParams = "";
		WorkflowRegressionParameters regParam = null;
		//开始寻找相应的流程实例节点的参数实体
		Criteria regParams = super.getCriteria(WorkflowRegressionParameters.class);
		regParams.add(Restrictions.eq("virtualProcessId", virtualProcessId));
		regParams.add(Restrictions.eq("processInstanceId", processInstanceId));
		regParams.add(Restrictions.eq("nodeId", nodeId));
		WorkflowRegressionParameters paramEntity = (WorkflowRegressionParameters)regParams.uniqueResult();
		if(paramEntity==null||"".equals(paramEntity)){
			//需要把参数转换成string类型
			Set params = bizParams.keySet();
			Iterator iterator = params.iterator();
			while(iterator.hasNext()){
				String key = (String)iterator.next();
				String value = (String)bizParams.get(key);
				if(value==null||"".equals(value)){
					value = "nonValue";
				}
				regressionParams+=key+"+"+value;
				regressionParams+=";";
			}
			if(regressionParams.endsWith(";")){
				regressionParams = regressionParams.substring(0,regressionParams.length()-1);
			}
			//参数整理完毕之后需要保存相应的实体
			WorkflowRegressionParameters workflowRegressionParameters = new WorkflowRegressionParameters();
			workflowRegressionParameters.setVirtualProcessId(virtualProcessId);
			workflowRegressionParameters.setProcessInstanceId(processInstanceId);
			workflowRegressionParameters.setNodeId(nodeId);
			workflowRegressionParameters.setNodeName(nodeName);
			workflowRegressionParameters.setNodeDesc(nodeDesc);
			workflowRegressionParameters.setRegressionParams(regressionParams);
			
			try{
				regParam = (WorkflowRegressionParameters)super.save(workflowRegressionParameters);						
			}catch(Exception e){
				throw new RuntimeException("保存 "+nodeName+"(节点) 业务参数的时候发生异常,请找管理员核查!");
			}
		}
		return regParam;
		
	}
	
	/**
	 * 根据虚拟流程，流程实例，节点id来查找节点参数
	 * @Methods Name findWorkflowRegressionParametersByMuitlyId
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public WorkflowRegressionParameters findWorkflowRegressionParametersByMuitlyId(
			Long virtualProcessId, Long processInstanceId, Long nodeId) {
		
		Criteria criteria = super.getCriteria(WorkflowRegressionParameters.class);
		criteria.add(Restrictions.eq("virtualProcessId", virtualProcessId));
		criteria.add(Restrictions.eq("processInstanceId", processInstanceId));
		criteria.add(Restrictions.eq("nodeId", nodeId));
		WorkflowRegressionParameters regParam = (WorkflowRegressionParameters)criteria.uniqueResult();
		
		return regParam;
	}

	/**
	 * 根据虚拟流程，流程实例来删除某流程实例的所有节点参数
	 * @Methods Name findWorkflowRegressionParametersByMuitlyId
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @throws Exception 
	 * @throws Exception String
	 */
	public void removeWorkflowRegressionParametersByProcessId(
			Long virtualProcessId, Long processInstanceId) throws Exception {
		// TODO Auto-generated method stub
		
		VirtualDefinitionInfo vd = (VirtualDefinitionInfo)super.findUniqueBy(VirtualDefinitionInfo.class, "id", virtualProcessId);
		String processDesc = "";
		if(vd!=null&&!"".equals(vd)){
			processDesc = vd.getVirtualDefinitionDesc();
		}
		Criteria criteria = super.getCriteria(WorkflowRegressionParameters.class);
		criteria.add(Restrictions.eq("virtualProcessId", virtualProcessId));
		criteria.add(Restrictions.eq("processInstanceId", processInstanceId));
		List<WorkflowRegressionParameters> allNodeParams = criteria.list();
		
		for(WorkflowRegressionParameters nodeParam : allNodeParams){
			try{
//				System.out.println(nodeParam.getId());
				super.remove(nodeParam);
				super.flush();
			}catch(Exception e){
				throw new Exception(processDesc+"流程的"+processInstanceId+"流程实例在流程结束删除节点参数过程中发生异常");
			}
			
		}
		
	}
	/**
	 * 根据虚拟流程，流程实例来删除某流程实例的所有节点参数
	 * @Methods Name findWorkflowRegressionParametersByMuitlyId
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @param vprocessId 
	 */
	public long findRealDefinitonByVprocessId(String vprocessId) {
		Criteria criteria = super.getCriteria(VirtualDefinitionInfo.class);
		criteria.add(Restrictions.eq("id", Long.valueOf(vprocessId)));
		List<VirtualDefinitionInfo> list = criteria.list();
		Long processId = list.get(0).getProcessDefinitionId();
		
		return processId;
	}
	/**
	 * 根据虚拟流程，流程实例来删除某流程实例的相应节点的参数
	 * @Methods Name findWorkflowRegressionParametersByMuitlyId
	 * @Create In Mar 4, 2009 By guangsa
	 * @return
	 * @param vprocessId 
	 */
	public void removeNodeWorkflowRegressionParameters(Long virtualProcessId,
			Long processInstanceId, Long nodeId) {
		
		Criteria criteria = super.getCriteria(WorkflowRegressionParameters.class);
		criteria.add(Restrictions.eq("virtualProcessId", virtualProcessId));
		criteria.add(Restrictions.eq("processInstanceId", processInstanceId));
		criteria.add(Restrictions.eq("nodeId", nodeId));
		WorkflowRegressionParameters regressParam = (WorkflowRegressionParameters)criteria.uniqueResult();
		
		if(regressParam!=null&&!"".equals(regressParam)){
			super.remove(regressParam);
		}
	}

}
