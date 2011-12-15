package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.SaleAssessmentDao;
import com.xpsoft.oa.model.bandpoor.SaleAssessment;
import com.xpsoft.oa.service.bandpoor.SaleAssessmentService;

public class SaleAssessmentServiceImpl extends BaseServiceImpl<SaleAssessment> implements SaleAssessmentService{
	private SaleAssessmentDao dao;
	
	public SaleAssessmentServiceImpl(SaleAssessmentDao dao) {
		super(dao);
		this.dao = dao;
	}
}
