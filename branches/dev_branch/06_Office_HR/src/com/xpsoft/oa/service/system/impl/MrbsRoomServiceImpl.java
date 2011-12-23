package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.MrbsRoomDao;
import com.xpsoft.oa.model.system.MrbsRoom;
import com.xpsoft.oa.service.system.MrbsRoomService;

public class MrbsRoomServiceImpl extends BaseServiceImpl<MrbsRoom> implements MrbsRoomService{
	private MrbsRoomDao dao;
	
	public MrbsRoomServiceImpl(MrbsRoomDao dao)	 {
		super(dao);
		this.dao = dao;
	}
}
