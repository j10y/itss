package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.BudgetDao;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.service.hrm.BudgetService;

public class BudgetServiceImpl extends BaseServiceImpl<Budget> implements
		BudgetService {
	private BudgetDao dao;

	public BudgetServiceImpl(BudgetDao dao) {
		/* 15 */super(dao);
		/* 16 */this.dao = dao;
	}
}