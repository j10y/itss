package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaKpipbcDao;
import com.xpsoft.oa.model.shop.SpPaKpipbc;
import com.xpsoft.oa.service.shop.SpPaKpipbcService;

public class SpPaKpipbcServiceImpl extends BaseServiceImpl<SpPaKpipbc>
	implements SpPaKpipbcService{
	private SpPaKpipbcDao dao;
	
	public SpPaKpipbcServiceImpl(SpPaKpipbcDao dao){
		super(dao);
		this.dao = dao;
	}
}
