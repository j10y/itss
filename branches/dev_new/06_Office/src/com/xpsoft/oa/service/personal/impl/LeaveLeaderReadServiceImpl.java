package com.xpsoft.oa.service.personal.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.personal.LeaveLeaderReadDao;
import com.xpsoft.oa.model.personal.LeaveLeaderRead;
import com.xpsoft.oa.service.personal.LeaveLeaderReadService;

public class LeaveLeaderReadServiceImpl extends BaseServiceImpl<LeaveLeaderRead>
		implements LeaveLeaderReadService {
	private LeaveLeaderReadDao dao;

	public LeaveLeaderReadServiceImpl(LeaveLeaderReadDao dao) {
		super(dao);
		this.dao = dao;
	}
}
