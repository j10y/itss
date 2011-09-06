package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.HrPromApplyDao;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.service.hrm.HrPromApplyService;

public class HrPromApplyServiceImpl extends BaseServiceImpl<HrPromApply> implements
		HrPromApplyService{
	private HrPromApplyDao dao;
	public HrPromApplyServiceImpl(HrPromApplyDao dao) {
		super(dao);
		this.dao = dao;
	}
}
