package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpipbcDao;
import com.xpsoft.oa.model.kpi.HrPaKpipbc;
import com.xpsoft.oa.service.kpi.HrPaKpipbcService;

public class HrPaKpipbcServiceImpl extends BaseServiceImpl<HrPaKpipbc>
	implements HrPaKpipbcService{
	private HrPaKpipbcDao dao;
	
	public HrPaKpipbcServiceImpl(HrPaKpipbcDao dao){
		super(dao);
		this.dao = dao;
	}
}
