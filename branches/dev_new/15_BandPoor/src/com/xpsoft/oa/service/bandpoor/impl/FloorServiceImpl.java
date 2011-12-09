package com.xpsoft.oa.service.bandpoor.impl;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.FloorDao;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.service.bandpoor.FloorService;

public class FloorServiceImpl extends BaseServiceImpl<Floor> implements FloorService{
	private FloorDao dao;
	
	public FloorServiceImpl(FloorDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		QueryFilter filter = new QueryFilter(params);
		List<Floor> list = this.getAll(filter);
		if(list.size() > 0) {
			return false;
		}
		return true;
	}
	
	public boolean multiSave(List<Floor> list) {
		try {
			for(Floor floor : list) {
				this.save(floor);
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
