package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.SaleStoreDao;
import com.xpsoft.oa.model.bandpoor.SaleStore;

public class SaleStoreDaoImpl extends BaseDaoImpl<SaleStore> implements SaleStoreDao {
	public SaleStoreDaoImpl() {
		super(SaleStore.class);
	}
}
