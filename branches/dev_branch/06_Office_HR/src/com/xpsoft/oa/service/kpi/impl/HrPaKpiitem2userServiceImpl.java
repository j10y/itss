package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiitem2userDao;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

public class HrPaKpiitem2userServiceImpl extends BaseServiceImpl<HrPaKpiitem2user>
		implements HrPaKpiitem2userService {
	private HrPaKpiitem2userDao dao;
	
	public HrPaKpiitem2userServiceImpl(HrPaKpiitem2userDao dao) {
		super(dao);
		this.dao = dao;
	}
}
