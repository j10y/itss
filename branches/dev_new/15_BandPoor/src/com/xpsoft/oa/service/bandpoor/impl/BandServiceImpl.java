package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.BandDao;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.service.bandpoor.BandService;

public class BandServiceImpl extends BaseServiceImpl<Band> implements BandService{
	private BandDao dao;
	
	public BandServiceImpl(BandDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<Band> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
	
	public boolean multiSave(List<Band> list) {
		try {
			for(Band band : list) {
				this.save(band);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
