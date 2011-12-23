package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.MrbsScheduleDao;
import com.xpsoft.oa.model.system.MrbsSchedule;
import com.xpsoft.oa.service.system.MrbsScheduleService;

public class MrbsScheduleServiceImpl extends BaseServiceImpl<MrbsSchedule> implements MrbsScheduleService{
	private MrbsScheduleDao dao;
	
	public MrbsScheduleServiceImpl(MrbsScheduleDao dao) {
		super(dao);
		this.dao = dao;
	}
}
