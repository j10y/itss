package com.xpsoft.oa.dao.shop;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;

public interface SpPaPerformanceindexscoreDao extends BaseDao<SpPaPerformanceindexscore>{
	/*
	 * 通过外键piId批量删除考核指标关联的得分
	 * */
	public abstract void removeByPiId(long piId);
}
