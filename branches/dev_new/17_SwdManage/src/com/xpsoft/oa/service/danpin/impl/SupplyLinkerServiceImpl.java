package com.xpsoft.oa.service.danpin.impl;

import com.xpsoft.core.service.impl.DanpinBaseServiceImpl;
import com.xpsoft.oa.dao.danpin.SupplyLinkerDao;
import com.xpsoft.oa.model.danpin.SupplyLinker;
import com.xpsoft.oa.service.danpin.SupplyLinkerService;

public class SupplyLinkerServiceImpl extends DanpinBaseServiceImpl<SupplyLinker> implements SupplyLinkerService{
	private SupplyLinkerDao dao;
	
	public SupplyLinkerServiceImpl(SupplyLinkerDao dao) {
		super(dao);
		this.dao = dao;
	}
}
