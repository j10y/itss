package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.HrPromAssessmentDao;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;

public class HrPromAssessmentServiceImpl extends BaseServiceImpl<HrPromAssessment> implements
		HrPromAssessmentService{
	private HrPromAssessmentDao dao;
	public HrPromAssessmentServiceImpl(HrPromAssessmentDao dao) {
		super(dao);
		this.dao = dao;
	}
}
