package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;

public interface HrPaPerformanceindexscoreService extends BaseService<HrPaPerformanceindexscore>{
	public abstract void removeByPiId(long piId);
}
