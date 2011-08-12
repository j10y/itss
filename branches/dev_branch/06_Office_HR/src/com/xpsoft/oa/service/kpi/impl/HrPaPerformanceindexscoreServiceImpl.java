package com.xpsoft.oa.service.kpi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.kpi.HrPaPerformanceindexscoreDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindexscore;
import com.xpsoft.oa.model.kpi.HrPaPisrule;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexscoreService;
import com.xpsoft.oa.service.kpi.HrPaPisruleService;

public class HrPaPerformanceindexscoreServiceImpl extends BaseServiceImpl<HrPaPerformanceindexscore>
	implements HrPaPerformanceindexscoreService{
	private HrPaPerformanceindexscoreDao dao;
	
	public HrPaPerformanceindexscoreServiceImpl(HrPaPerformanceindexscoreDao dao){
		super(dao);
		this.dao = dao;
	}
	
	public void removeByPiId(long piId) {
		HrPaPisruleService hrPaPisruleService = (HrPaPisruleService)AppUtil.getBean("hrPaPisruleService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_piId_L_EQ", String.valueOf(piId));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaPerformanceindexscore> list = this.dao.getAll(filter);
		for(int i = 0; i < list.size(); i++) {
			hrPaPisruleService.removeByPisId(list.get(i).getId());
		}
		this.dao.removeByPiId(piId);
	}
}
