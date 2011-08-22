package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;

public interface HrPaPerformanceindexscoreService extends BaseService<HrPaPerformanceindexscore>{
	/*
	 * 通过外键piId批量删除考核指标关联的得分
	 * */
	public abstract void removeByPiId(long piId);
}
