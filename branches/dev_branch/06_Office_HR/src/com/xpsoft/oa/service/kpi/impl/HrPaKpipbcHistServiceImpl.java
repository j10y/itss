package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaKpipbcHistDao;
import com.xpsoft.oa.model.kpi.HrPaKpipbcHist;
import com.xpsoft.oa.service.kpi.HrPaKpipbcHistService;

public class HrPaKpipbcHistServiceImpl extends BaseServiceImpl<HrPaKpipbcHist>
	implements HrPaKpipbcHistService{
	private HrPaKpipbcHistDao dao;
	
	public HrPaKpipbcHistServiceImpl(HrPaKpipbcHistDao dao){
		super(dao);
		this.dao = dao;
	}
}
