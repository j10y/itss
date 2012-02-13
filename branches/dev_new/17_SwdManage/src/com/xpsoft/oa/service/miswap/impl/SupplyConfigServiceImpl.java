package com.xpsoft.oa.service.miswap.impl;

import com.xpsoft.oa.dao.miswap.SupplyConfigDao;
import com.xpsoft.oa.model.miswap.SupplyConfig;
import com.xpsoft.oa.service.miswap.SupplyConfigService;

import com.xpsoft.core.service.impl.BaseServiceImpl;

public class SupplyConfigServiceImpl extends BaseServiceImpl<SupplyConfig> implements SupplyConfigService{
	private SupplyConfigDao dao;
	
	public SupplyConfigServiceImpl(SupplyConfigDao dao) {
		super(dao);
		this.dao = dao;
	}
}
