package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.MrbsRepeatDao;
import com.xpsoft.oa.model.system.MrbsRepeat;
import com.xpsoft.oa.service.system.MrbsRepeatService;

public class MrbsRepeatServiceImpl extends BaseServiceImpl<MrbsRepeat> implements MrbsRepeatService{
	private MrbsRepeatDao dao;
	
	public MrbsRepeatServiceImpl(MrbsRepeatDao dao) {
		super(dao);
		this.dao = dao;
	}
}
