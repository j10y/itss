package com.xpsoft.oa.service.scheduler.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.scheduler.TaskSchedulerDao;
import com.xpsoft.oa.model.scheduler.TaskScheduler;
import com.xpsoft.oa.service.scheduler.TaskSchedulerService;

public class TaskSchedulerServiceImpl extends BaseServiceImpl<TaskScheduler> implements TaskSchedulerService {
	private TaskSchedulerDao dao;
	public TaskSchedulerServiceImpl(TaskSchedulerDao dao) {
		super(dao);
		this.dao = dao;
	}

}
