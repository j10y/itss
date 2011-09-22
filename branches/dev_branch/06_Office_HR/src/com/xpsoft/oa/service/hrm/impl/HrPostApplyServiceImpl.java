package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.HrPostApplyDao;
import com.xpsoft.oa.model.hrm.HrPostApply;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.service.hrm.HrPostApplyService;

public class HrPostApplyServiceImpl extends BaseServiceImpl<HrPostApply>
		implements HrPostApplyService{
	private HrPostApplyDao dao;
	
	public HrPostApplyServiceImpl(HrPostApplyDao dao) {
		super(dao);
		this.dao = dao;
	}
}
