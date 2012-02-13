package com.xpsoft.oa.dao.danpin.impl;

import com.xpsoft.core.dao.impl.DanpinBaseDaoImpl;
import com.xpsoft.oa.dao.danpin.SupplyLinkerDao;
import com.xpsoft.oa.model.danpin.SupplyLinker;

public class SupplyLinkerDaoImpl extends DanpinBaseDaoImpl<SupplyLinker> implements SupplyLinkerDao{
	public SupplyLinkerDaoImpl() {
		super(SupplyLinker.class);
	}
}
