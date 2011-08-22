package com.xpsoft.oa.dao.kpi;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;

public interface HrPaPerformanceindexscoreDao extends BaseDao<HrPaPerformanceindexscore>{
	/*
	 * 通过外键piId批量删除考核指标关联的得分
	 * */
	public abstract void removeByPiId(long piId);
}
