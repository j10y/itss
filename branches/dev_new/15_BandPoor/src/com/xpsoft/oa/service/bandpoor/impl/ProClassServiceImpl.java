package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.ProClassDao;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.service.bandpoor.ProClassService;

public class ProClassServiceImpl extends BaseServiceImpl<ProClass> implements ProClassService{
	private ProClassDao dao;
	
	public ProClassServiceImpl(ProClassDao dao) {
		super(dao);
		this.dao = dao;
	}
}
