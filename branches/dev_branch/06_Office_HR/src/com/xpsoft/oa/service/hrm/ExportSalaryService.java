package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.ExportSalary;

public abstract interface ExportSalaryService extends BaseService<ExportSalary>
{
	public String exportSalary(String fileRootPath, String sheetName,String filePrefix,String sql,String exportSaId );
}

