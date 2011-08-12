package com.xpsoft.oa.dao.kpi.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.kpi.HrPaCategoryDao;
import com.xpsoft.oa.model.kpi.HrPaCategory;

public class HrPaCategoryDaoImpl extends BaseDaoImpl<HrPaCategory>
	implements HrPaCategoryDao{
	public HrPaCategoryDaoImpl(){
		super(HrPaCategory.class);
	}
}
