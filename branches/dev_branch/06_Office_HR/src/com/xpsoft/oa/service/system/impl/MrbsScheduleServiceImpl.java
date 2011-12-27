package com.xpsoft.oa.service.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.MrbsScheduleDao;
import com.xpsoft.oa.model.system.MrbsSchedule;
import com.xpsoft.oa.service.system.MrbsScheduleService;

public class MrbsScheduleServiceImpl extends BaseServiceImpl<MrbsSchedule> implements MrbsScheduleService{
	private MrbsScheduleDao dao;
	
	public MrbsScheduleServiceImpl(MrbsScheduleDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	public List<MrbsSchedule> validate(String startTime, String endTime, Long roomId) {
		String sql = "select id from mrbs_schedule where " +
				"room_id = " + roomId + " and " +
				"((start_time >= '" + startTime + "' and end_time < '" + endTime + "') or " +
				"(end_time > '" + startTime + "' and end_time <= '" + endTime + "') or " +
				"(start_time <= '" + startTime + "' and end_time >= '" + endTime + "')) " +
				"order by start_time asc";
		List<Map<String, Object>> mapList = this.findDataList(sql);
		List<MrbsSchedule> resultList = new ArrayList<MrbsSchedule>();
		for(Map<String, Object> map : mapList) {
			MrbsSchedule msNew = new MrbsSchedule();
			msNew.setId(Long.parseLong(map.get("id").toString()));
		}
		return resultList;
	}
}
