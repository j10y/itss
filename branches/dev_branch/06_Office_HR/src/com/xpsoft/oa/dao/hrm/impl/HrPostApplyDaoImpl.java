package com.xpsoft.oa.dao.hrm.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.HrPostApplyDao;
import com.xpsoft.oa.model.hrm.HrPostApply;

public class HrPostApplyDaoImpl extends BaseDaoImpl<HrPostApply>
		implements HrPostApplyDao{
	public HrPostApplyDaoImpl() {
		super(HrPostApply.class);
	}
}
