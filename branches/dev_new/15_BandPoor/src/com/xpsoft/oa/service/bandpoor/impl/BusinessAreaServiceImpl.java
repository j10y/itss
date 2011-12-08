package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BusinessAreaDao;
import com.xpsoft.oa.model.bandpoor.BusinessArea;
import com.xpsoft.oa.service.bandpoor.BusinessAreaService;

public class BusinessAreaServiceImpl extends BaseServiceImpl<BusinessArea> implements BusinessAreaService{
	private BusinessAreaDao dao;
	
	public BusinessAreaServiceImpl(BusinessAreaDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique() {
		// TODO Auto-generated method stub
		return false;
	}
}
