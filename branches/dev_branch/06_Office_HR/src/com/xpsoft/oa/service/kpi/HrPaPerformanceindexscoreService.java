package com.xpsoft.oa.service.kpi;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.kpi.HrPaPisrule;

public interface HrPaPerformanceindexscoreService extends BaseService<HrPaPerformanceindexscore>{
	/*
	 * 通过外键piId批量删除考核指标关联的得分
	 * */
	public abstract void removeByPiId(long piId);
	/*
	 * 批量添加考核项目关联的分数
	 * */
	public void multiSave(List<HrPaPerformanceindexscore> scoreList, List<HrPaPisrule> ruleList, long paMode);
}
