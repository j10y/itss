package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.MrbsScheduleUserDao;
import com.xpsoft.oa.model.system.MrbsScheduleUser;
import com.xpsoft.oa.service.system.MrbsScheduleUserService;

public class MrbsScheduleUserServiceImpl extends BaseServiceImpl<MrbsScheduleUser> implements MrbsScheduleUserService{
	private MrbsScheduleUserDao dao;
	
	public MrbsScheduleUserServiceImpl(MrbsScheduleUserDao dao) {
		super(dao);
		this.dao = dao;
	}

	public void removeByScheduleId(Long scheduleId) {
		this.dao.removeDatabySql("Delete from mrbs_schedule_user where scheduleid="+scheduleId);
	}
}
