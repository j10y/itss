package com.xpsoft.oa.service.hrm.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.RealExecutionDao;
import com.xpsoft.oa.model.hrm.RealExecution;
import com.xpsoft.oa.service.hrm.RealExecutionService;

public class RealExecutionServiceImpl extends BaseServiceImpl<RealExecution> implements
		RealExecutionService {
	private RealExecutionDao dao;

	public RealExecutionServiceImpl(RealExecutionDao dao) {
		/* 15 */super(dao);
		/* 16 */this.dao = dao;
	}
}