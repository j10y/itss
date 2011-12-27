package com.xpsoft.oa.service.system;

import java.util.Date;
import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.MrbsSchedule;

public abstract interface MrbsScheduleService extends BaseService<MrbsSchedule>{
	public List<MrbsSchedule> validate(Date startTime, Date endTime, Long roomId);
}
