package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BeElectedBandPoorDao;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;

public class BeElectedBandPoorServiceImpl extends BaseServiceImpl<BeElectedBandPoor>implements BeElectedBandPoorService {
	private BeElectedBandPoorDao dao;
	
	public BeElectedBandPoorServiceImpl(BeElectedBandPoorDao dao) {
		super(dao);
		this.dao = dao;
	}	
}
