package com.zsgj.info.framework.workflow.dao;
/**
 * �������jbpm��
 * @author Administrator
 *
 */
public interface WorkFlowGoBackDao {
	
	public void workFlowGoBack(Long processInstanceId, Long nodeId,String taskName); 
}
