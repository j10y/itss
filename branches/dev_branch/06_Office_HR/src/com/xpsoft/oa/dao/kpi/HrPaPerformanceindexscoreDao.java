package com.xpsoft.oa.dao.kpi;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;

public interface HrPaPerformanceindexscoreDao extends BaseDao<HrPaPerformanceindexscore>{
	public abstract void removeByPiId(long piId);
}
