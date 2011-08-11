package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.BudgetItemDao;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.service.hrm.BudgetItemService;

public class BudgetItemServiceImpl extends BaseServiceImpl<BudgetItem> implements
		BudgetItemService {
	private BudgetItemDao dao;

	public BudgetItemServiceImpl(BudgetItemDao dao) {
		/* 15 */super(dao);
		/* 16 */this.dao = dao;
	}
}