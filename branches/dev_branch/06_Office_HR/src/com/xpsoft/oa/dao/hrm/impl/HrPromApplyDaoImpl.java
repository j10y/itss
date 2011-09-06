package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.HrPromApplyDao;
import com.xpsoft.oa.model.hrm.HrPromApply;

public class HrPromApplyDaoImpl extends BaseDaoImpl<HrPromApply> implements
		HrPromApplyDao{
	public HrPromApplyDaoImpl() {
		super(HrPromApply.class);
	}
}
