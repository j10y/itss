package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandChannelDao;
import com.xpsoft.oa.model.bandpoor.BandChannel;
import com.xpsoft.oa.service.bandpoor.BandChannelService;

public class BandChannelServiceImpl extends BaseServiceImpl<BandChannel> implements BandChannelService{
	private BandChannelDao dao;
	
	public BandChannelServiceImpl(BandChannelDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<BandChannel> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
}
