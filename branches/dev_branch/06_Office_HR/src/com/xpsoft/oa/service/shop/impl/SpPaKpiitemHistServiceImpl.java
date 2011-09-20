package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaKpiitemHistDao;
import com.xpsoft.oa.model.shop.SpPaKpiitemHist;
import com.xpsoft.oa.service.shop.SpPaKpiitemHistService;

public class SpPaKpiitemHistServiceImpl extends BaseServiceImpl<SpPaKpiitemHist>
	implements SpPaKpiitemHistService{
	private SpPaKpiitemHistDao dao;
	
	public SpPaKpiitemHistServiceImpl(SpPaKpiitemHistDao dao){
		super(dao);
		this.dao = dao;
	}
}
