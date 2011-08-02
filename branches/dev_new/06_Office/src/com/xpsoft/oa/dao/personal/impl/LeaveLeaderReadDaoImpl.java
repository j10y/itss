package com.xpsoft.oa.dao.personal.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.personal.LeaveLeaderReadDao;
import com.xpsoft.oa.model.archive.LeaderRead;
import com.xpsoft.oa.model.personal.LeaveLeaderRead;

public class LeaveLeaderReadDaoImpl extends BaseDaoImpl<LeaveLeaderRead> implements
		LeaveLeaderReadDao {
	public LeaveLeaderReadDaoImpl() {
		super(LeaderRead.class);
	}
}
