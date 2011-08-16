package com.xpsoft.oa.dao.scheduler.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.scheduler.TaskSchedulerDao;
import com.xpsoft.oa.model.scheduler.TaskScheduler;

public class TaskSchedulerDaoImpl extends BaseDaoImpl<TaskScheduler> implements TaskSchedulerDao{

	public TaskSchedulerDaoImpl() {
		super(TaskScheduler.class);
	}

}
