package com.xpsoft.oa.service.bandpoor.impl;

import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandDao;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.service.bandpoor.BandService;

public class BandServiceImpl extends BaseServiceImpl<Band> implements BandService{
	private BandDao dao;
	
	public BandServiceImpl(BandDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		// TODO Auto-generated method stub
		return false;
	}
}
