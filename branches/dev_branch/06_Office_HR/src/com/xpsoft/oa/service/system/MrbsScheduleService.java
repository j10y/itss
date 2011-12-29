package com.xpsoft.oa.service.system;

import java.util.Date;
import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.MrbsSchedule;

public abstract interface MrbsScheduleService extends BaseService<MrbsSchedule>{
	/**
	 * 查看 此时间段  是否 已经有人预订，
	 * 返回 预订 的 详细结果 list
	 * @param startTime
	 * @param endTime
	 * @param roomId
	 * @return
	 */
	public List<MrbsSchedule> validate(Date startTime, Date endTime, Long roomId);
}
