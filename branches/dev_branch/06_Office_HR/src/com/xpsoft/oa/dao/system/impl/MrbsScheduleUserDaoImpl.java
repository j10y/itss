package com.xpsoft.oa.dao.system.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.MrbsScheduleUserDao;
import com.xpsoft.oa.model.system.MrbsScheduleUser;

public class MrbsScheduleUserDaoImpl extends BaseDaoImpl<MrbsScheduleUser> implements MrbsScheduleUserDao{
	public MrbsScheduleUserDaoImpl() {
		super(MrbsScheduleUser.class);
	}
}
