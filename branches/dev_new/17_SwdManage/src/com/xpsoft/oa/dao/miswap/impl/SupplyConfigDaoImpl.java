package com.xpsoft.oa.dao.miswap.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.miswap.SupplyConfigDao;
import com.xpsoft.oa.model.miswap.SupplyConfig;

public class SupplyConfigDaoImpl extends BaseDaoImpl<SupplyConfig> implements SupplyConfigDao{
	public SupplyConfigDaoImpl() {
		super(SupplyConfig.class);
	}
}
