package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaKpiitem2userCmpDao;
import com.xpsoft.oa.model.shop.SpPaKpiitem2userCmp;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userCmpService;

public class SpPaKpiitem2userCmpServiceImpl extends BaseServiceImpl<SpPaKpiitem2userCmp>
		implements SpPaKpiitem2userCmpService {
	private SpPaKpiitem2userCmpDao dao;
	
	public SpPaKpiitem2userCmpServiceImpl(SpPaKpiitem2userCmpDao dao) {
		super(dao);
		this.dao = dao;
	}
}
