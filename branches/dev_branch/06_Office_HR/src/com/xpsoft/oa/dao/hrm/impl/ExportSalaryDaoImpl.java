package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.ExportSalaryDao;
import com.xpsoft.oa.model.hrm.ExportSalary;

public class ExportSalaryDaoImpl extends BaseDaoImpl<ExportSalary> implements
		ExportSalaryDao {
	public ExportSalaryDaoImpl() {
		super(ExportSalary.class);
	}
}
