package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.SaleStoreDao;
import com.xpsoft.oa.model.bandpoor.SaleStore;
import com.xpsoft.oa.service.bandpoor.SaleStoreService;

public class SaleStoreServiceImpl extends BaseServiceImpl<SaleStore> implements SaleStoreService{
	private SaleStoreDao dao;
	
	public SaleStoreServiceImpl(SaleStoreDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique() {
		// TODO Auto-generated method stub
		return false;
	}
}
