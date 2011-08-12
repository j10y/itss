package com.xpsoft.oa.service.kpi.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.kpi.HrPaPerformanceindexDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;

public class HrPaPerformanceindexServiceImpl extends BaseServiceImpl<HrPaPerformanceindex> 
	implements HrPaPerformanceindexService {
	private HrPaPerformanceindexDao dao;
	
	public HrPaPerformanceindexServiceImpl(HrPaPerformanceindexDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 发布考核项目
	 * */
	public boolean saveToPublish(long piId) {
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = 
				(HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_piId_L_EQ", String.valueOf(piId));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaPerformanceindexscore> list = hrPaPerformanceindexscoreService.getAll(filter);
		//考核项目没有得分
		if(list.size() == 0 || list.size() > 5) {
			return false;
		}
		HashSet<BigDecimal> set = new HashSet<BigDecimal>();
		for(int i = 0; i < list.size(); i++) {
			set.add(list.get(i).getPisScore());
		}
		//考核项目有重复得分
		if(set.size() < list.size()) {
			return false;
		}
		this.dao.saveToPublish(piId);
		return true;
	}
}
