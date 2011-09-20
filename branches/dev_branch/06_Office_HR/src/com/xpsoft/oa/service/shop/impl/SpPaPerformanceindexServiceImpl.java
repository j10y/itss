package com.xpsoft.oa.service.shop.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.shop.SpPaPerformanceindexDao;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;
import com.xpsoft.oa.model.shop.SpPaPerformanceindexscore;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexService;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexscoreService;

public class SpPaPerformanceindexServiceImpl extends BaseServiceImpl<SpPaPerformanceindex> 
	implements SpPaPerformanceindexService {
	private SpPaPerformanceindexDao dao;
	
	public SpPaPerformanceindexServiceImpl(SpPaPerformanceindexDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 发布考核指标
	 * */
	public boolean saveToPublish(long piId) {
		SpPaPerformanceindexscoreService spPaPerformanceindexscoreService = 
				(SpPaPerformanceindexscoreService)AppUtil.getBean("spPaPerformanceindexscoreService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_piId_L_EQ", String.valueOf(piId));
		QueryFilter filter = new QueryFilter(map);
		List<SpPaPerformanceindexscore> list = spPaPerformanceindexscoreService.getAll(filter);
		//考核指标没有得分
		if(list.size() == 0 || list.size() > 5) {
			return false;
		}
		HashSet<BigDecimal> set = new HashSet<BigDecimal>();
		for(int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getPisScore());
		}
		//考核指标有重复得分
		if(set.size() < list.size()) {
			return false;
		}
		this.dao.saveToPublish(piId);
		return true;
	}
}
