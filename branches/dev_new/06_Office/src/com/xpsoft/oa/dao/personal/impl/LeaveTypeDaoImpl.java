package com.xpsoft.oa.dao.personal.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.personal.LeaveTypeDao;
import com.xpsoft.oa.model.personal.LeaveType;

public class LeaveTypeDaoImpl extends BaseDaoImpl<LeaveType> implements
		LeaveTypeDao {
	public LeaveTypeDaoImpl() {
		super(LeaveType.class);
	}
}
