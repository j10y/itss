package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.BudgetDao;
import com.xpsoft.oa.model.hrm.Budget;

public class BudgetDaoImpl extends BaseDaoImpl<Budget> implements
		BudgetDao {
	public BudgetDaoImpl() {
		/* 13 */super(Budget.class);
	}
}
