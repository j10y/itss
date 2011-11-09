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
	 * 发布考核指标
	 * */
	public boolean saveToPublish(long piId) {
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = 
				(HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_piId_L_EQ", String.valueOf(piId));
		QueryFilter filter = new QueryFilter(map);
		List<HrPaPerformanceindexscore> list = hrPaPerformanceindexscoreService.getAll(filter);
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
	/*
	 * 批量导入考核指标
	 * */
	public boolean uploadPi(List<Map<HrPaPerformanceindex, List<HrPaPerformanceindexscore>>> list) {
		HrPaPerformanceindexscoreService hrPaPerformanceindexscoreService = 
				(HrPaPerformanceindexscoreService)AppUtil.getBean("hrPaPerformanceindexscoreService");
		try {
			for(Map<HrPaPerformanceindex, List<HrPaPerformanceindexscore>> item : list) {
				for(Map.Entry<HrPaPerformanceindex, List<HrPaPerformanceindexscore>> entry : item.entrySet()) {
					HrPaPerformanceindex pi = entry.getKey();
					List<HrPaPerformanceindexscore> pisList = entry.getValue();
					pi = this.save(pi);
					for(HrPaPerformanceindexscore pis : pisList) {
						pis.setPi(pi);
						hrPaPerformanceindexscoreService.save(pis);
					}
				}
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
