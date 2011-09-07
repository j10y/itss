package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.ExportSalaryItemDao;
import com.xpsoft.oa.model.hrm.ExportSalaryItemOrder;

public class ExportSalaryItemDaoImpl extends BaseDaoImpl<ExportSalaryItemOrder> implements
	ExportSalaryItemDao {
	public ExportSalaryItemDaoImpl() {
		super(ExportSalaryItemOrder.class);
	}
}
