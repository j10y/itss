package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitem2userCmpDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2userCmp;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userCmpService;

public class HrPaKpiitem2userCmpServiceImpl extends BaseServiceImpl<HrPaKpiitem2userCmp>
		implements HrPaKpiitem2userCmpService {
	private HrPaKpiitem2userCmpDao dao;
	
	public HrPaKpiitem2userCmpServiceImpl(HrPaKpiitem2userCmpDao dao) {
		super(dao);
		this.dao = dao;
	}
}
