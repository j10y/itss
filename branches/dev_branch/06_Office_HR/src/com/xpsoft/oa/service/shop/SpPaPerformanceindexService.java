package com.xpsoft.oa.service.shop;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;

public interface SpPaPerformanceindexService extends BaseService<SpPaPerformanceindex> {
	/*
	 * 发布考核指标
	 * */
	public abstract boolean saveToPublish(long piId);
}
