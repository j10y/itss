package com.xpsoft.oa.service.hrm.impl;

import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.hrm.IncomeTaxDao;
import com.xpsoft.oa.model.hrm.IncomeTax;
import com.xpsoft.oa.service.hrm.IncomeTaxService;

public class IncomeTaxServiceImpl extends BaseServiceImpl<IncomeTax> implements
	IncomeTaxService {	
	private IncomeTaxDao incomeTaxDao;

	public IncomeTaxServiceImpl(IncomeTaxDao incomeTaxDao) {
		/* 21 */super(incomeTaxDao);
		/* 22 */this.incomeTaxDao = incomeTaxDao;
	}

	public List<IncomeTax> findIncomeTax() {
		/* 28 */return this.incomeTaxDao.findIncomeTax();
	}

	public List<IncomeTax> findByHql(String hql) {
		/* 34 */return null;
	}
}
