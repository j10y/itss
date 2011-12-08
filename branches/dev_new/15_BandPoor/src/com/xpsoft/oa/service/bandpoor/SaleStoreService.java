package com.xpsoft.oa.service.bandpoor;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.bandpoor.SaleStore;

public abstract interface SaleStoreService extends BaseService<SaleStore>{
	public boolean validateUnique(Map params);}
