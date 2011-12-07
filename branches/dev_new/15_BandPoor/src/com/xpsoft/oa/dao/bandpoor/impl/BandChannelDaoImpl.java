package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.BandChannelDao;
import com.xpsoft.oa.model.bandpoor.BandChannel;

public class BandChannelDaoImpl extends BaseDaoImpl<BandChannel> implements BandChannelDao{
	public BandChannelDaoImpl() {
		super(BandChannel.class);
	}
}
