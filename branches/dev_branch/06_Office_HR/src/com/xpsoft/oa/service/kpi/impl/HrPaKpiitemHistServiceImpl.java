package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitemHistDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitemHist;
import com.xpsoft.oa.service.kpi.HrPaKpiitemHistService;

public class HrPaKpiitemHistServiceImpl extends BaseServiceImpl<HrPaKpiitemHist>
	implements HrPaKpiitemHistService{
	private HrPaKpiitemHistDao dao;
	
	public HrPaKpiitemHistServiceImpl(HrPaKpiitemHistDao dao){
		super(dao);
		this.dao = dao;
	}
}
