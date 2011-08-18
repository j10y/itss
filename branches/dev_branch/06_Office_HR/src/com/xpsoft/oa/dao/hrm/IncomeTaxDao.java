package com.xpsoft.oa.dao.hrm;

import java.util.List;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.hrm.IncomeTax;

public abstract interface IncomeTaxDao extends BaseDao<IncomeTax> {
	public abstract List<IncomeTax> findByHql(String paramString);

	public abstract List<IncomeTax> findIncomeTax();
}
