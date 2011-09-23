package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpiPBC2UserAuditHisDao;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2UserAuditHis;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserAuditHisService;

public class HrPaKpiPBC2UserAuditHisServiceImpl extends BaseServiceImpl<HrPaKpiPBC2UserAuditHis>
		implements HrPaKpiPBC2UserAuditHisService {
	private HrPaKpiPBC2UserAuditHisDao dao;
	
	public HrPaKpiPBC2UserAuditHisServiceImpl(HrPaKpiPBC2UserAuditHisDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	
}
