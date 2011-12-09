package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.PictureOrdocDao;
import com.xpsoft.oa.model.bandpoor.PictureOrdoc;
import com.xpsoft.oa.service.bandpoor.PictureOrdocService;

public class PictureOrdocServiceImpl extends BaseServiceImpl<PictureOrdoc> implements PictureOrdocService {
	private PictureOrdocDao dao;
	
	public PictureOrdocServiceImpl(PictureOrdocDao dao) {
		super(dao);
		this.dao = dao;
	}
	
}
