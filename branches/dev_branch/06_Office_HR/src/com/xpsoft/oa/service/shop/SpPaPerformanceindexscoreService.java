package com.xpsoft.oa.service.shop;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;
import com.xpsoft.oa.model.shop.SpPaPisrule;

public interface SpPaPerformanceindexscoreService extends BaseService<SpPaPerformanceindexscore>{
	/*
	 * 通过外键piId批量删除考核指标关联的得分
	 * */
	public abstract void removeByPiId(long piId);
	/*
	 * 批量添加考核项目关联的分数
	 * */
	public void multiSave(List<SpPaPerformanceindexscore> scoreList, List<SpPaPisrule> ruleList, long paMode);
}
