package com.xpsoft.oa.service.kpi.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.kpi.HrPaCategoryDao;
import com.xpsoft.oa.model.kpi.HrPaCategory;
import com.xpsoft.oa.service.kpi.HrPaCategoryService;

public class HrPaCategoryServiceImpl extends BaseServiceImpl<HrPaCategory>
	implements HrPaCategoryService{
	private HrPaCategoryDao dao;
	
	public HrPaCategoryServiceImpl(HrPaCategoryDao dao){
		super(dao);
		this.dao = dao;
	}
}
