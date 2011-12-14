package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
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

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<SaleStore> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
	
	public boolean multiSave(List<SaleStore> list) {
		try {
			for(SaleStore bs : list) {
				this.save(bs);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
