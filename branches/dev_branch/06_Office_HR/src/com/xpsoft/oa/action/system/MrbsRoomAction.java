package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.MrbsRoom;
import com.xpsoft.oa.service.system.MrbsRoomService;
import com.xpsoft.oa.service.system.MrbsScheduleService;

public class MrbsRoomAction extends BaseAction {

	@Resource
	private MrbsRoomService mrbsRoomService;
	@Resource
	private MrbsScheduleService mrbsScheduleService;
	public MrbsScheduleService getMrbsScheduleService() {
		return mrbsScheduleService;
	}

	public void setMrbsScheduleService(MrbsScheduleService mrbsScheduleService) {
		this.mrbsScheduleService = mrbsScheduleService;
	}

	private MrbsRoom mrbsRoom;
	public MrbsRoomService getMrbsRoomService() {
		return mrbsRoomService;
	}

	public void setMrbsRoomService(MrbsRoomService mrbsRoomService) {
		this.mrbsRoomService = mrbsRoomService;
	}

	public MrbsRoom getMrbsRoom() {
		return mrbsRoom;
	}

	public void setMrbsRoom(MrbsRoom mrbsRoom) {
		this.mrbsRoom = mrbsRoom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;

	public String listInit(){
		QueryFilter filter = new QueryFilter(getRequest());
		List<MrbsRoom> list = this.mrbsRoomService.getAll(filter);

		Type type = new TypeToken<List<MrbsRoom>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		Gson gson = new Gson();
		toJson(list,buff);
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<MrbsRoom> list = this.mrbsRoomService.getAll(filter);
		//list.get(0).getArea().getId()
		String areaId = getRequest().getParameter("areaId");
		String searchDate = (getRequest().getParameter("searchDate") == null || "".equals(getRequest().getParameter("searchDate"))) ? DateUtil.convertDateToString(new Date()) : getRequest().getParameter("searchDate");
		String sql = "select a.id, a.room_id,b.room_admin_email, a.start_time, a.end_time, c.fullname as create_by, a.description from " +
				"mrbs_schedule a, mrbs_room b, app_user c where " +
				"a.room_id = b.id and b.area_id = " + areaId + " and a.create_by = c.userId and  b.flag=1  and  " +
				"a.start_time > '"  +searchDate +  "' and " +
				"a.start_time < '"+ DateUtil.convertDateToString(DateUtil.addDays(DateUtil.parseDate(searchDate),1)) + "' order by a.end_time asc ";
		
		List<Map> list_s = this.mrbsRoomService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		int mf = 0;
		for(MrbsRoom room : list) {
			mf++;
			buff.append("{'id':'" + room.getId() + "',")
					.append("'roomName':'" + room.getRoomName() + "',")
					.append("'room_admin_email':'"+ room.getRoomAdminEmail()+"',");
			StringBuffer content = new StringBuffer("<div>");
			Date endTime = DateUtil.parseDate(searchDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i = 0; i < list_s.size(); i++){
				Map m = list_s.get(i);
				if(room.getId().toString().equals(m.get("room_id").toString())) {
					Date d_ = (Date)m.get("start_time");
					Date d= DateUtil.parseDate(sdf.format(d_));
					
					Date d_1 = (Date)m.get("end_time");
					Date dd= DateUtil.parseDate(sdf.format(d_1));
					
					String hstart = (d.getHours()>9)?d.getHours()+"":"0"+d.getHours();
					String mstart = (d.getMinutes()>9)?d.getMinutes()+"":"0"+d.getMinutes();
					String hend = (dd.getHours()>9)?dd.getHours()+"":"0"+dd.getHours();
					String mend = (dd.getMinutes()>9)?dd.getMinutes()+"":"0"+dd.getMinutes();
					
					content.append(d.getDate()+"日"+hstart+":" + mstart + "-"+hend+":" + mend).append("&nbsp;&nbsp;&nbsp;"+m.get("create_by")).append("<br/><br/>");
					endTime = d_1;
					list_s.remove(i);
				};
			}
			String h = (endTime.getHours()>9)?endTime.getHours()+"":"0"+endTime.getHours();
			String m = (endTime.getMinutes()>9)?endTime.getMinutes()+"":"0"+endTime.getMinutes();
			h = "00".equals(h) ? "08" : h;
			int flag  = 0;
			if(endTime.getHours()<20){
				content.append(endTime.getDate()+"日"+h+":"+m+"-"+"20:00").append("&nbsp;&nbsp;&nbsp;").append("空闲，<input type=\"button\"  onclick=\"orderFun("+room.getId()+",\\'"+room.getRoomName()+"\\')\" style=\"width:60px\" name=\"预&nbsp;&nbsp;订\" value=\"预&nbsp;订\"/>").append("<br/><br/>");
				flag = 1;
			}
			content.append("</div>");
			buff.append("'flag':'"+flag+"',");
			buff.append("'content':'" + content + "'},");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");

		this.jsonString = buff.toString();

		return "success";
	}
	
	public String listResult() {
		//获取参数
		String attendNum = this.getRequest().getParameter("attendNum");
		String meetingTime = this.getRequest().getParameter("meetingTime");
		Long meetingTimeLong = (long) (Float.parseFloat(meetingTime) * 60 * 60 * 1000);
		Date startDate = DateUtil.parseDate(this.getRequest().getParameter("startDate"));
		Date endDate = DateUtil.parseDate(this.getRequest().getParameter("endDate"));
		Long areaId = Long.parseLong(this.getRequest().getParameter("areaId"));
		boolean referTime = "1".equals(this.getRequest().getParameter("referTime")) ? true : false;
		String meetingHour = this.getRequest().getParameter("meetingHour");
		String meetingMin = this.getRequest().getParameter("meetingMin");
		Long referStartTime = Long.MAX_VALUE;
		Long referEndTime = new Long(0);
		
		//取到会议室列表
		String roomSql = "select id, room_name, room_admin_email from mrbs_room where area_id = " + areaId.toString() + " and capacity >= " + attendNum + " and flag = 1";
		List<Map<String, Object>> roomIdList = this.mrbsRoomService.findDataList(roomSql);
		
		Map<String, List<Map<String, Object>>> allRoomMap = new HashMap<String,List<Map<String,Object>>>();
		//循环对每个会议室生成数据
		for(int i = 0; i < roomIdList.size(); i++) {
			String roomId = roomIdList.get(i).get("id").toString();
			//循环查询每天会议室预定情况
			Calendar c_start = Calendar.getInstance();
			c_start.setTime(startDate);
			Long startDateLong = c_start.getTime().getTime();
			Calendar c_end = Calendar.getInstance();
			c_end.setTime(endDate);
			Long endDateLong = c_end.getTime().getTime();
			Long dateNum = (endDateLong - startDateLong) / (1000 * 60 * 60 * 24) + 1;
			List<Map<String, Object>> roomUnMeetingList = new ArrayList<Map<String,Object>>();
			for(int j = 0; j < dateNum; j++) {
				Calendar startc = Calendar.getInstance();
				startc.setTime(startDate);
				startc.add(Calendar.DAY_OF_YEAR, j);
				
				Calendar endc = Calendar.getInstance();
				endc.setTime(startDate);
				endc.add(Calendar.DAY_OF_YEAR, j + 1);
				String roomMeetingSql = "select id, start_time, end_time from mrbs_schedule where room_id = " + roomId + " and " +
						"start_time >= '" + DateUtil.convertDateToString(startc.getTime()) + "' and " +
						"end_time < '" + DateUtil.convertDateToString(endc.getTime()) + "'";
				List<Map<String, Object>> roomMeetingList = this.mrbsRoomService.findDataList(roomMeetingSql);
				String startHour = DateUtil.convertDateToString(startc.getTime()) + " 08:00:00";
				String endHour = DateUtil.convertDateToString(startc.getTime()) + " 20:00:00";
				if(referTime) {
					referStartTime = DateUtil.parseDate(DateUtil.convertDateToString(startc.getTime()) + " " + meetingHour + ":" + meetingMin + ":00").getTime();
					referEndTime = new Date(referStartTime + meetingTimeLong).getTime();
				}
				if(roomMeetingList.size() == 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("startTime", DateUtil.parseDate(startHour));
					map.put("endTime", DateUtil.parseDate(endHour));
					roomUnMeetingList.add(map);
				} else {
					Date firstTime = (Date)roomMeetingList.get(0).get("start_time");
					Date lastTime = (Date)roomMeetingList.get(roomMeetingList.size() - 1).get("end_time");
					if(firstTime.getTime() - DateUtil.parseDate(startHour).getTime() >= meetingTimeLong && 
							DateUtil.parseDate(startHour).getTime() <= referStartTime && 
							firstTime.getTime() >= referEndTime) {
						Map<String, Object> startMap = new HashMap<String, Object>();
						startMap.put("startTime", DateUtil.parseDate(startHour));
						startMap.put("endTime", firstTime);
						roomUnMeetingList.add(startMap);
					}
					for(int k = 0; k < roomMeetingList.size() - 1; k++) {
						Date prevTime = (Date)roomMeetingList.get(k).get("end_time");
						Date nextTime = (Date)roomMeetingList.get(k + 1).get("start_time");
						if(nextTime.getTime() - prevTime.getTime() >= meetingTimeLong && 
								prevTime.getTime() <= referStartTime && 
								nextTime.getTime() >= referEndTime) {
							Map<String, Object> inMap = new HashMap<String, Object>();
							inMap.put("startTime", prevTime);
							inMap.put("endTime", nextTime);
							roomUnMeetingList.add(inMap);
						}
					}
					if(DateUtil.parseDate(endHour).getTime() - lastTime.getTime() >= meetingTimeLong && 
							lastTime.getTime() <= referStartTime && 
							DateUtil.parseDate(endHour).getTime() >= referEndTime) {
						Map<String, Object> endMap = new HashMap<String, Object>();
						endMap.put("startTime", lastTime);
						endMap.put("endTime", DateUtil.parseDate(endHour));
						roomUnMeetingList.add(endMap);
					}
				}
			}
			if(roomUnMeetingList.size() > 0) {
				allRoomMap.put(roomId, roomUnMeetingList);
			}
		}
		
		//将返回数据拼装成JSON
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < roomIdList.size(); i++) {
			String roomId = roomIdList.get(i).get("id").toString();
			String roomName = roomIdList.get(i).get("room_name").toString();
			String roomAdminEmail = roomIdList.get(i).get("room_admin_email").toString();
			if(allRoomMap.containsKey(roomId)) {
				buff.append("{'id':'" + roomId + "',")
						.append("'roomName':'" + roomName + "',")
						.append("'room_admin_email':'" + roomAdminEmail + "',");
				StringBuffer content = new StringBuffer("<div>");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				List<Map<String, Object>> list = allRoomMap.get(roomId);
				for(int j = 0; j < list.size(); j++) {
					Date end_date = (Date)list.get(j).get("endTime");
					String end_hour = (end_date.getHours()<10)?"0"+end_date.getHours():end_date.getHours()+"";
					String end_mini = (end_date.getMinutes()<10)?"0"+end_date.getMinutes():end_date.getMinutes()+"";
					content.append(sdf.format((Date)list.get(j).get("startTime")) + "-" + end_hour+":"+end_mini)
							.append("&nbsp;&nbsp;&nbsp;")
							.append("空闲，<input type=\"button\"  onclick=\"orderFun("+roomId+",\\'"+roomName+"\\')\" style=\"width:60px\" name=\"预&nbsp;&nbsp;订\" value=\"预&nbsp;订\"/>").append("<br/><br/>");
				}
				content.append("</div>");
				buff.append("'flag':'1',");
				buff.append("'content':'" + content + "'},");
			}
		}
		if(roomIdList.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");

		this.jsonString = buff.toString();
		
		return "success";
	}

	public static void  toJson(List<MrbsRoom> list,StringBuffer sb){
		sb.append("[");
		for(MrbsRoom f:list){
			sb.append("{")
			.append("'id':'" + f.getId() + "',")
			.append("'roomName':'" + f.getRoomName() + "',")
			.append("'areaName':'" + f.getArea().getAreaName() + "',")
			.append("'areaId':'" + f.getArea().getId() + "',")
			.append("'desc':'" + f.getDescription() + "',")
			.append("'capacity':'" + f.getCapacity() + "',")
			.append("'adminEmail':'" + f.getRoomAdminEmail() + "'},");
		}
		if(list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		  if (ids != null) {
			 for (String id : ids) {
				MrbsRoom mr = this.mrbsRoomService.get(new Long(id));
				mr.setFlag(0);
				this.mrbsRoomService.save(mr);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		MrbsRoom f = (MrbsRoom) this.mrbsRoomService
				.get(this.id);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		       sb.append("{")
		       		.append("'id':'" + f.getId() + "',")
					.append("'roomName':'" + f.getRoomName() + "',")
					.append("'areaName':'" + f.getArea().getAreaName() + "',")
					.append("'areaId':'" + f.getArea().getId() + "',")
					.append("'desc':'" + f.getDescription() + "',")
					.append("'capacity':'" + f.getCapacity() + "',")
					.append("'roomAdminEmail':'" + f.getRoomAdminEmail() + "'},");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		this.mrbsRoom.setFlag(1);
		this.mrbsRoomService.save(this.mrbsRoom);
		setJsonString("{success:true}");
		return "success";
	}
	
	
}
