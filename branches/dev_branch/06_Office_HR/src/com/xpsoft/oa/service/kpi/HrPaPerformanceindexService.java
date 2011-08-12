package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;

public interface HrPaPerformanceindexService extends BaseService<HrPaPerformanceindex> {
	public abstract void saveToPublish(long piId);
}
