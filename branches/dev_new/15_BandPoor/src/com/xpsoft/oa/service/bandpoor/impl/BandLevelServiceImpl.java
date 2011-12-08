package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandLevelDao;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.service.bandpoor.BandLevelService;

public class BandLevelServiceImpl extends BaseServiceImpl<BandLevel> implements BandLevelService{
	private BandLevelDao dao;
	
	public BandLevelServiceImpl(BandLevelDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<BandLevel> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
}
