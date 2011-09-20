package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaKpiitemDao;
import com.xpsoft.oa.model.shop.SpPaKpiitem;
import com.xpsoft.oa.service.shop.SpPaKpiitemService;

public class SpPaKpiitemServiceImpl extends BaseServiceImpl<SpPaKpiitem>
	implements SpPaKpiitemService{
	private SpPaKpiitemDao dao;
	
	public SpPaKpiitemServiceImpl(SpPaKpiitemDao dao){
		super(dao);
		this.dao = dao;
	}
	
	public boolean findByPiIdAndPbcId(long piId, String pbcIds) {
		return this.dao.findByPiIdAndPbcId(piId, pbcIds);
	}
}
