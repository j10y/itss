package com.zsgj.info.framework.workflow.daoImp;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zsgj.info.framework.workflow.dao.WorkFlowGoBackDao;

public class WorkFlowGoBackDaoImp extends HibernateDaoSupport implements WorkFlowGoBackDao {
	
	
	public void workFlowGoBack(Long processInstanceId, Long nodeId,
			String taskName) {
		int updateRow = 0;
		//END=new Date();
		String endCurrentTaskHql = "update org.jbpm.taskmgmt.exe.TaskInstance ti set ti.isOpen=false ,ti.isSignalling=false, ti.end=? where ti.processInstance.id=? and ti.end is null";
//		String startOldTaskHql = "update org.jbpm.taskmgmt.exe.TaskInstance ti set ti.isOpen=true ,ti.isSignalling=true ,ti.end=null where ti.processInstance.id=? and ti.name=?";
//		String completeTokenHql = "update org.jbpm.graph.exe.Token t set t.node.id=? where t.processInstance.id=?";
		
		try {
			//先终止当前任务
			Date nowDate = new Date();
			Object[] nParams = new Object[]{nowDate,processInstanceId};
			updateRow = this.getHibernateTemplate().bulkUpdate(endCurrentTaskHql, nParams);
//			//然后开启新任务
//			Object[] sParams = new Object[]{processInstanceId,taskName};
//			updateRow = this.getHibernateTemplate().bulkUpdate(startOldTaskHql, sParams);
			//放token节点Id
//			Object[] tParams = new Object[]{nodeId,processInstanceId};
//			updateRow = this.getHibernateTemplate().bulkUpdate(completeTokenHql, tParams);
			System.out.println(updateRow);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
