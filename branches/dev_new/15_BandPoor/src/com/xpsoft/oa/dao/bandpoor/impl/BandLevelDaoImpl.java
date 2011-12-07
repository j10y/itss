package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.BandLevelDao;
import com.xpsoft.oa.model.bandpoor.BandLevel;

public class BandLevelDaoImpl extends BaseDaoImpl<BandLevel> implements BandLevelDao{
	public BandLevelDaoImpl() {
		super(BandLevel.class);
	}
}
