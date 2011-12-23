package com.xpsoft.oa.service.system.impl;

import com.xpsoft.oa.dao.system.MrbsAreaDao;
import com.xpsoft.oa.model.system.MrbsArea;
import com.xpsoft.oa.service.system.MrbsAreaService;

import com.xpsoft.core.service.impl.BaseServiceImpl;

public class MrbsAreaServiceImpl extends BaseServiceImpl<MrbsArea> implements MrbsAreaService{
	private MrbsAreaDao dao;
	
	public MrbsAreaServiceImpl(MrbsAreaDao dao) {
		super(dao);
		this.dao = dao;
	}
}
