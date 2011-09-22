package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.HrPostAssessmentDao;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;

public class HrPostAssessmentServiceImpl extends BaseServiceImpl<HrPostAssessment>
		implements HrPostAssessmentService{
	private HrPostAssessmentDao dao;
	
	public HrPostAssessmentServiceImpl(HrPostAssessmentDao dao) {
		super(dao);
		this.dao = dao;
	}
}
