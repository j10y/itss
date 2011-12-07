package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.BandDao;
import com.xpsoft.oa.model.bandpoor.Band;

public class BusinessAreaDaoImpl extends BaseDaoImpl<Band> implements BandDao{
	public BusinessAreaDaoImpl() {
		super(Band.class);
	}
}
