package com.xpsoft.oa.service.danpin.impl;

import com.xpsoft.core.service.impl.DanpinBaseServiceImpl;
import com.xpsoft.oa.dao.danpin.SupplyInfoDao;
import com.xpsoft.oa.model.danpin.SupplyInfo;
import com.xpsoft.oa.service.danpin.SupplyInfoService;

public class SupplyInfoServiceImpl extends DanpinBaseServiceImpl<SupplyInfo> implements SupplyInfoService{
	private SupplyInfoDao dao;
	
	public SupplyInfoServiceImpl(SupplyInfoDao dao) {
		super(dao);
		this.dao = dao;
	}
}
