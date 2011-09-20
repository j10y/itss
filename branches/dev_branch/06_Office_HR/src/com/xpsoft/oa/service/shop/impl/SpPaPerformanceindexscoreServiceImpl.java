package com.xpsoft.oa.service.shop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.shop.SpPaPerformanceindexscoreDao;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;
import com.xpsoft.oa.model.shop.SpPaPisrule;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexscoreService;
import com.xpsoft.oa.service.shop.SpPaPisruleService;

public class SpPaPerformanceindexscoreServiceImpl extends BaseServiceImpl<SpPaPerformanceindexscore>
	implements SpPaPerformanceindexscoreService{
	private SpPaPerformanceindexscoreDao dao;
	
	public SpPaPerformanceindexscoreServiceImpl(SpPaPerformanceindexscoreDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 通过外键piId批量删除考核指标关联的得分
	 * */
	public void removeByPiId(long piId) {
		SpPaPisruleService spPaPisruleService = (SpPaPisruleService)AppUtil.getBean("spPaPisruleService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_piId_L_EQ", String.valueOf(piId));
		QueryFilter filter = new QueryFilter(map);
		List<SpPaPerformanceindexscore> list = this.dao.getAll(filter);
		for(int i = 0; i < list.size(); i++) {
			spPaPisruleService.removeByPisId(list.get(i).getId());
		}
		this.dao.removeByPiId(piId);
	}
	/*
	 * 批量添加考核项目关联的分数
	 * */
	public void multiSave(List<SpPaPerformanceindexscore> scoreList, List<SpPaPisrule> ruleList, long paMode) {
		if(paMode == SpPaDatadictionary.PA_QUALITATIVE_ASSESSMENT) {
			for(int i = 0; i < scoreList.size(); i++) {
				this.save(scoreList.get(i));
			}
		} else {
			SpPaPisruleService spPaPisruleService = (SpPaPisruleService)AppUtil.getBean("spPaPisruleService");
			for(int i = 0; i < scoreList.size(); i++) {
				SpPaPerformanceindexscore scoreNew = this.save(scoreList.get(i));
				ruleList.get(i).setPis(scoreNew);
				ruleList.get(i).setPisAC(new Long(0));
				spPaPisruleService.save(ruleList.get(i));
			}
		}
	}
}
