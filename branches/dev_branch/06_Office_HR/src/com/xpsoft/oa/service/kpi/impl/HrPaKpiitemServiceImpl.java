package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitemDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;
import com.xpsoft.oa.service.kpi.HrPaKpiitemService;

public class HrPaKpiitemServiceImpl extends BaseServiceImpl<HrPaKpiitem>
	implements HrPaKpiitemService{
	private HrPaKpiitemDao dao;
	
	public HrPaKpiitemServiceImpl(HrPaKpiitemDao dao){
		super(dao);
		this.dao = dao;
	}
	
	public boolean findByPiIdAndPbcId(long piId, String[] pbcIds) {
		return this.dao.findByPiIdAndPbcId(piId, pbcIds);
	}
}
