package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.BudgetItemDao;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;

public class BudgetItemDaoImpl extends BaseDaoImpl<BudgetItem> implements
		BudgetItemDao {
	public BudgetItemDaoImpl() {
		/* 13 */super(BudgetItem.class);
	}
}
