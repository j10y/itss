package com.xpsoft.oa.dao.hrm.impl;

import java.util.List;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.hrm.IncomeTaxDao;
import com.xpsoft.oa.model.hrm.IncomeTax;

public class IncomeTaxDaoImpl extends BaseDaoImpl<IncomeTax> implements
	IncomeTaxDao {
	public IncomeTaxDaoImpl() {
		super(IncomeTax.class);
	}

	public List<IncomeTax> findIncomeTax() {
		String hql = "from IncomeTax c";
		return findByHql(hql);
	}
}
