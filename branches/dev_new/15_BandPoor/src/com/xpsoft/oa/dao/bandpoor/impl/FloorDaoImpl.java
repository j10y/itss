package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.FloorDao;
import com.xpsoft.oa.model.bandpoor.Floor;

public class FloorDaoImpl extends BaseDaoImpl<Floor> implements FloorDao {
	public FloorDaoImpl() {
		super(Floor.class);
	}
}
