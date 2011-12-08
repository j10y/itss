package com.xpsoft.oa.service.bandpoor.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.FloorDao;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.service.bandpoor.FloorService;

public class FloorServiceImpl extends BaseServiceImpl<Floor> implements FloorService{
	private FloorDao dao;
	
	public FloorServiceImpl(FloorDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique() {
		// TODO Auto-generated method stub
		return false;
	}
}
