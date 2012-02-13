package com.xpsoft.oa.dao.danpin.impl;

import com.xpsoft.core.dao.impl.DanpinBaseDaoImpl;
import com.xpsoft.oa.dao.danpin.SupplyInfoDao;
import com.xpsoft.oa.model.danpin.SupplyInfo;

public class SupplyInfoDaoImpl extends DanpinBaseDaoImpl<SupplyInfo> implements SupplyInfoDao{
	public SupplyInfoDaoImpl() {
		super(SupplyInfo.class);
	}
}
