package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.BusinessAreaDao;
import com.xpsoft.oa.model.bandpoor.BusinessArea;

public class BusinessAreaDaoImpl extends BaseDaoImpl<BusinessArea> implements BusinessAreaDao{
	public BusinessAreaDaoImpl() {
		super(BusinessArea.class);
	}
}
