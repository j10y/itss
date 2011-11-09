package com.xpsoft.oa.service.kpi;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;

public interface HrPaPerformanceindexService extends BaseService<HrPaPerformanceindex> {
	/*
	 * 发布考核指标
	 * */
	public abstract boolean saveToPublish(long piId);
	/*
	 * 批量导入考核指标
	 * */
	public abstract boolean uploadPi(List<Map<HrPaPerformanceindex, List<HrPaPerformanceindexscore>>> list);
}
