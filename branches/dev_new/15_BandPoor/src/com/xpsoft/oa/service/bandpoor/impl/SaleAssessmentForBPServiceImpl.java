package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.SaleAssessmentForBPDao;
import com.xpsoft.oa.model.bandpoor.SaleAssessmentForBP;
import com.xpsoft.oa.service.bandpoor.SaleAssessmentForBPService;

public class SaleAssessmentForBPServiceImpl extends BaseServiceImpl<SaleAssessmentForBP> implements SaleAssessmentForBPService{
	private SaleAssessmentForBPDao dao;
	
	public SaleAssessmentForBPServiceImpl(SaleAssessmentForBPDao dao) {
		super(dao);
		this.dao = dao;
	}
}
