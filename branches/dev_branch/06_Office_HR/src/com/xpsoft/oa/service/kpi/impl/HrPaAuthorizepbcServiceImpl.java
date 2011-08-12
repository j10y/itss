package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaAuthorizepbcDao;
import com.xpsoft.oa.model.kpi.HrPaAuthorizepbc;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;

public class HrPaAuthorizepbcServiceImpl extends BaseServiceImpl<HrPaAuthorizepbc>
	implements HrPaAuthorizepbcService{
	private HrPaAuthorizepbcDao dao;
	
	public HrPaAuthorizepbcServiceImpl(HrPaAuthorizepbcDao dao){
		super(dao);
		this.dao = dao;
	}
}
