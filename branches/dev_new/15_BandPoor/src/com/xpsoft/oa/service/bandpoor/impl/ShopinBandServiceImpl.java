package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.ShopinBandDao;
import com.xpsoft.oa.model.bandpoor.ShopinBand;
import com.xpsoft.oa.service.bandpoor.ShopinBandService;

public class ShopinBandServiceImpl extends BaseServiceImpl<ShopinBand> implements ShopinBandService{
	private ShopinBandDao dao;
	
	public ShopinBandServiceImpl(ShopinBandDao dao) {
		super(dao);
		this.dao = dao;
	}
}
