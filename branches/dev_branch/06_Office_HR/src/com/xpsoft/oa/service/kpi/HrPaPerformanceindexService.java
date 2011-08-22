package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;

public interface HrPaPerformanceindexService extends BaseService<HrPaPerformanceindex> {
	/*
	 * 发布考核指标
	 * */
	public abstract boolean saveToPublish(long piId);
}
