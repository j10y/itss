package com.xpsoft.oa.dao.hrm.impl;

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
		String hql = "SELECT re.budgetItem, SUM(re.realValue) FROM RealExecution re where re.budget.budgetId=? and re.budgetItem.deleteFlag=0 GROUP BY re.budgetItem ORDER BY re.budgetItem.budgetItemId ASC";
		Object [] objs = new Object[]{paramLong};
		List list  = super.findByHql(hql, objs);
		return list;
	}

	
}
