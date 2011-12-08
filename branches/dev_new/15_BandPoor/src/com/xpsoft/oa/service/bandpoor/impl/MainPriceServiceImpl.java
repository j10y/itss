package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.MainPriceDao;
import com.xpsoft.oa.model.bandpoor.MainPrice;
import com.xpsoft.oa.service.bandpoor.MainPriceService;

public class MainPriceServiceImpl extends BaseServiceImpl<MainPrice> implements MainPriceService{
	private MainPriceDao dao;
	
	public MainPriceServiceImpl(MainPriceDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<MainPrice> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
}
