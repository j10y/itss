package com.xpsoft.oa.service.personal.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.personal.LeaveTypeDao;
import com.xpsoft.oa.model.personal.LeaveType;
import com.xpsoft.oa.service.personal.LeaveTypeService;

public class LeaveTypeServiceImpl extends BaseServiceImpl<LeaveType>
		implements LeaveTypeService {
	private LeaveTypeDao dao;

	public LeaveTypeServiceImpl(LeaveTypeDao dao) {
		super(dao);
		this.dao = dao;
	}
}
