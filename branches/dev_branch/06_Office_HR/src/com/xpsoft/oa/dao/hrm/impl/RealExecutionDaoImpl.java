package com.xpsoft.oa.dao.hrm.impl;

import java.util.Date;
import java.util.List;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.RealExecutionDao;
import com.xpsoft.oa.model.hrm.RealExecution;

public class RealExecutionDaoImpl extends BaseDaoImpl<RealExecution> implements
	RealExecutionDao {
	public RealExecutionDaoImpl() {
		/* 13 */super(RealExecution.class);
	}

	public List treeStatics(Long paramLong) {
		//String hql = "SELECT re.budgetItem, SUM(re.realValue) FROM RealExecution re where re.budget.budgetId=? and re.budgetItem.deleteFlag=0 GROUP BY re.budgetItem ORDER BY re.budgetItem.budgetItemId ASC";
		String hql = "SELECT re.budgetItem, SUM(re.realValue) FROM RealExecution re where re.budget.budgetId=? " +
		"and re.budgetItem.deleteFlag=0 " + 
		//"and re.inputDate>re.budget.beginDate AND re.inputDate<re.budget.endDate " +
		"GROUP BY re.budgetItem ORDER BY re.budgetItem.budgetItemId ASC";
		Object [] objs = new Object[]{paramLong};
		List list  = super.findByHql(hql, objs);
		return list;
	}
	
	/**
	 * 季度预算统计
	 * @param paramLong 季度所属年度预算
	 * @param startDate 季度预算开始时间
	 * @param endDate 季度预算结束时间
	 * @return
	 */
	public List treeQuarterStatics(Long paramLong, Date startDate, Date endDate) {
		String hql = "SELECT re.budgetItem, SUM(re.realValue) FROM RealExecution re where re.budget.budgetId=? " +
			"and re.budgetItem.deleteFlag=0 " +
			"and re.inputDate>? AND re.inputDate<? " +
			"GROUP BY re.budgetItem ORDER BY re.budgetItem.budgetItemId ASC";
		Object [] objs = new Object[]{paramLong, startDate, endDate};
		List list  = super.findByHql(hql, objs);
		return list;
	}

	
}
