package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandLevelDao;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.service.bandpoor.BandLevelService;

public class BandLevelServiceImpl extends BaseServiceImpl<BandLevel> implements BandLevelService{
	private BandLevelDao dao;
	
	public BandLevelServiceImpl(BandLevelDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique() {
		// TODO Auto-generated method stub
		return false;
	}
}
