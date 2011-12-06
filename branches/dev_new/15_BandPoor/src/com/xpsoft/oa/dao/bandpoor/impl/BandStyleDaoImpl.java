package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.BandStyleDao;
import com.xpsoft.oa.model.bandpoor.BandStyle;

public class BandStyleDaoImpl extends BaseDaoImpl<BandStyle> implements BandStyleDao{
	public BandStyleDaoImpl() {
		super(BandStyle.class);
	}
}
