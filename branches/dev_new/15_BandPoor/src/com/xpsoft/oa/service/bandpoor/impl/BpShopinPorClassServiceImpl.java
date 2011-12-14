package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BpShopinPorClassDao;
import com.xpsoft.oa.model.bandpoor.BpShopinPorClass;
import com.xpsoft.oa.service.bandpoor.BpShopinPorClassService;

public class BpShopinPorClassServiceImpl extends BaseServiceImpl<BpShopinPorClass> implements BpShopinPorClassService{
	private BpShopinPorClassDao dao;
	
	public BpShopinPorClassServiceImpl(BpShopinPorClassDao dao) {
		super(dao);
		this.dao = dao;
	}
}
