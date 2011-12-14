package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.ShopinProClassDao;
import com.xpsoft.oa.model.bandpoor.ShopinProClass;
import com.xpsoft.oa.service.bandpoor.ShopinProClassService;

public class ShopinProClassServiceImpl extends BaseServiceImpl<ShopinProClass> implements ShopinProClassService{
	private ShopinProClassDao dao;
	
	public ShopinProClassServiceImpl(ShopinProClassDao dao) {
		super(dao);
		this.dao = dao;
	}
}
