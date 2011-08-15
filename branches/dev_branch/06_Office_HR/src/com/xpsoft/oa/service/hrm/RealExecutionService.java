package com.xpsoft.oa.service.hrm;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.RealExecution;

public abstract interface RealExecutionService extends BaseService<RealExecution> {
	
	/**
	 * 
	 * 根据预算ID统计各预算项目的告警情况
	 * @param paramLong budgetId
	 * @return
	 */
	public abstract List<Map> treeStatics(Long paramLong);
}
