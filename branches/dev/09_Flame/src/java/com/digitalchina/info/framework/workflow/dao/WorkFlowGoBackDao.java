package com.digitalchina.info.framework.workflow.dao;
/**
 * 具体操作jbpm表
 * @author Administrator
 *
 */
public interface WorkFlowGoBackDao {
	
	public void workFlowGoBack(Long processInstanceId, Long nodeId,String taskName); 
}
