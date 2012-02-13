package com.xpsoft.oa.service.miswap.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.miswap.TmTemplateDao;
import com.xpsoft.oa.model.miswap.TmTemplate;
import com.xpsoft.oa.service.miswap.TmTemplateService;

public class TmTemplateServiceImpl extends BaseServiceImpl<TmTemplate> implements TmTemplateService{
	private TmTemplateDao dao;
	
	public TmTemplateServiceImpl(TmTemplateDao dao) {
		super(dao);
		this.dao = dao;
	}
}
