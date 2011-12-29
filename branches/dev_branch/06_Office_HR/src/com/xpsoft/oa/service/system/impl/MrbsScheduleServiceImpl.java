package com.xpsoft.oa.service.system.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public List<MrbsSchedule> validate(Date startTime, Date endTime, Long roomId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeString = sdf.format(startTime);
		String endTimeString = sdf.format(endTime);
		String sql = "select id,start_time,end_time,preside from mrbs_schedule where " +
				"room_id = " + roomId + " and " +
				"((start_time >= '" + startTimeString + "' and end_time < '" + endTimeString + "') or " +
				"(end_time > '" + startTimeString + "' and end_time <= '" + endTimeString + "') or " +
				"(start_time <= '" + startTimeString + "' and end_time >= '" + endTimeString + "')) " +
				"order by start_time asc";
		List<Map<String, Object>> mapList = this.findDataList(sql);
		List<MrbsSchedule> resultList = new ArrayList<MrbsSchedule>();
		for(Map<String, Object> map : mapList) {
			MrbsSchedule msNew = new MrbsSchedule();
			msNew.setId(Long.parseLong(map.get("id").toString()));
			msNew.setStartTime((Date)map.get("start_time"));
			msNew.setEndTime((Date)map.get("end_time"));
			msNew.setPreside((String)map.get("preside"));
			resultList.add(msNew);
		}
		return resultList;
	}
}
