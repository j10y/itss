package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaKpipbcHistDao;
import com.xpsoft.oa.model.shop.SpPaKpipbcHist;
import com.xpsoft.oa.service.shop.SpPaKpipbcHistService;

public class SpPaKpipbcHistServiceImpl extends BaseServiceImpl<SpPaKpipbcHist>
	implements SpPaKpipbcHistService{
	private SpPaKpipbcHistDao dao;
	
	public SpPaKpipbcHistServiceImpl(SpPaKpipbcHistDao dao){
		super(dao);
		this.dao = dao;
	}
}
