package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAcreachedDao;
import com.xpsoft.oa.model.kpi.HrPaAcreached;
import com.xpsoft.oa.service.kpi.HrPaAcreachedService;

public class HrPaAcreachedServiceImpl extends BaseServiceImpl<HrPaAcreached>
	implements HrPaAcreachedService{
	private HrPaAcreachedDao dao;
	
	public HrPaAcreachedServiceImpl(HrPaAcreachedDao dao){
		super(dao);
		this.dao = dao;
	}
}
