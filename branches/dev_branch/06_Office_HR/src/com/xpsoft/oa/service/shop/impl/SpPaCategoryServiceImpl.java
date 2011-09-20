package com.xpsoft.oa.service.shop.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.shop.SpPaCategoryDao;
import com.xpsoft.oa.model.shop.SpPaCategory;
import com.xpsoft.oa.service.shop.SpPaCategoryService;

public class SpPaCategoryServiceImpl extends BaseServiceImpl<SpPaCategory>
	implements SpPaCategoryService{
	private SpPaCategoryDao dao;
	
	public SpPaCategoryServiceImpl(SpPaCategoryDao dao){
		super(dao);
		this.dao = dao;
	}
}
