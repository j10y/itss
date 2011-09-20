package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaAuthorizepbcDao;
import com.xpsoft.oa.model.shop.SpPaAuthorizepbc;
import com.xpsoft.oa.service.shop.SpPaAuthorizepbcService;

public class SpPaAuthorizepbcServiceImpl extends BaseServiceImpl<SpPaAuthorizepbc>
	implements SpPaAuthorizepbcService{
	private SpPaAuthorizepbcDao dao;
	
	public SpPaAuthorizepbcServiceImpl(SpPaAuthorizepbcDao dao){
		super(dao);
		this.dao = dao;
	}
}
