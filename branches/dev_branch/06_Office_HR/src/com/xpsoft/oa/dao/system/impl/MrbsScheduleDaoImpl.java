package com.xpsoft.oa.dao.system.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.MrbsScheduleDao;
import com.xpsoft.oa.model.system.MrbsSchedule;

public class MrbsScheduleDaoImpl extends BaseDaoImpl<MrbsSchedule> implements MrbsScheduleDao{
	public MrbsScheduleDaoImpl() {
		super(MrbsSchedule.class);
	}
}
