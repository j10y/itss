package com.xpsoft.oa.dao.shop.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.shop.SpPaCategoryDao;
import com.xpsoft.oa.model.shop.SpPaCategory;

public class SpPaCategoryDaoImpl extends BaseDaoImpl<SpPaCategory>
	implements SpPaCategoryDao{
	public SpPaCategoryDaoImpl(){
		super(SpPaCategory.class);
	}
}
