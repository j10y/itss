package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandStyleDao;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.service.bandpoor.BandStyleService;

public class BandStyleServiceImpl extends BaseServiceImpl<BandStyle> implements BandStyleService{
	private BandStyleDao dao;
	
	public BandStyleServiceImpl(BandStyleDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<BandStyle> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
	
	public boolean multiSave(List<BandStyle> list) {
		try {
			for(BandStyle bs : list) {
				this.save(bs);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
