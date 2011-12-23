package com.xpsoft.oa.dao.system.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.MrbsRoomDao;
import com.xpsoft.oa.model.system.MrbsRoom;

public class MrbsRoomDaoImpl extends BaseDaoImpl<MrbsRoom> implements MrbsRoomDao{
	public MrbsRoomDaoImpl() {
		super(MrbsRoom.class);
	}
}
