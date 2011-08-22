package com.xpsoft.oa.dao.kpi;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;

public interface HrPaPerformanceindexDao extends BaseDao<HrPaPerformanceindex> {
	/*
	 * 发布考核指标
	 * */
	public abstract void saveToPublish(long piId);
}
