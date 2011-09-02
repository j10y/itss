package com.xpsoft.oa.service.hrm;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.SalaryPayoff;

public abstract interface SalaryPayoffService extends BaseService<SalaryPayoff>
{
	public String exportData(String fileRootPath, String sheetName,
			String filePrefix, String[] titles, String[] proNames,
			List dataList, String exportType);
	
	public boolean saveRealexecution(Long userId,Double amount,Integer month);
}

