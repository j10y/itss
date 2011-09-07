package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.ExportSalaryDao;
import com.xpsoft.oa.dao.hrm.ExportSalaryItemDao;
import com.xpsoft.oa.model.hrm.ExportSalary;
import com.xpsoft.oa.model.hrm.ExportSalaryItemOrder;
import com.xpsoft.oa.service.hrm.ExportSalaryItemService;
import com.xpsoft.oa.service.hrm.ExportSalaryService;


public class ExportSalaryItemServiceImpl extends BaseServiceImpl<ExportSalaryItemOrder>
		implements ExportSalaryItemService {
	private ExportSalaryItemDao dao;

	public ExportSalaryItemServiceImpl(ExportSalaryItemDao dao) {
		super(dao);
		this.dao = dao;
	}
}
