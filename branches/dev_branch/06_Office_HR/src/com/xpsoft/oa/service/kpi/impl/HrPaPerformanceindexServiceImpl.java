package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaPerformanceindexDao;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;

public class HrPaPerformanceindexServiceImpl extends BaseServiceImpl<HrPaPerformanceindex> 
	implements HrPaPerformanceindexService {
	private HrPaPerformanceindexDao dao;
	
	public HrPaPerformanceindexServiceImpl(HrPaPerformanceindexDao dao){
		super(dao);
		this.dao = dao;
	}
	
	public void saveToPublish(long piId) {
		this.dao.saveToPublish(piId);
	}
}
