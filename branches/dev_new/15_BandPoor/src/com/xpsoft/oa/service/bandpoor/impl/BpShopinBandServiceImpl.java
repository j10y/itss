package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BpShopinBandDao;
import com.xpsoft.oa.model.bandpoor.BpShopinBand;
import com.xpsoft.oa.service.bandpoor.BpShopinBandService;

public class BpShopinBandServiceImpl extends BaseServiceImpl<BpShopinBand> implements BpShopinBandService{
	private BpShopinBandDao dao;
	
	public BpShopinBandServiceImpl(BpShopinBandDao dao) {
		super(dao);
		this.dao = dao;
	}
}
