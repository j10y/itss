package com.xpsoft.oa.dao.shop;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;

public interface SpPaPerformanceindexDao extends BaseDao<SpPaPerformanceindex> {
	/*
	 * 发布考核指标
	 * */
	public abstract void saveToPublish(long piId);
}
