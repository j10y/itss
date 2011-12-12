package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandPoorDao;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.service.bandpoor.BandPoorService;

public class BandPoorServiceImpl extends BaseServiceImpl<BandPoor> implements BandPoorService {
	private BandPoorDao dao;
	
	public BandPoorServiceImpl(BandPoorDao dao) {
		super(dao);
		this.dao = dao;
	}	

}
