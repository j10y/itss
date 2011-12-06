package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandStyleDao;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.service.bandpoor.BandStyleService;

public class BandStyleServiceImpl extends BaseServiceImpl<BandStyle> implements BandStyleService{
	private BandStyleDao dao;
	
	public BandStyleServiceImpl(BandStyleDao dao) {
		super(dao);
		this.dao = dao;
	}
}
