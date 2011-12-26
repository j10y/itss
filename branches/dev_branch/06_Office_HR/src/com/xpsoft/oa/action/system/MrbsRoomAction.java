package com.xpsoft.oa.action.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
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

	private MrbsRoom mrbsArea;
	public MrbsRoomService getMrbsRoomService() {
		return mrbsRoomService;
	}

	public void setMrbsRoomService(MrbsRoomService mrbsRoomService) {
		this.mrbsRoomService = mrbsRoomService;
	}

	public MrbsRoom getMrbsRoom() {
		return mrbsArea;
	}

	public void setMrbsRoom(MrbsRoom mrbsArea) {
		this.mrbsArea = mrbsArea;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;

	

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		String areaId = getRequest().getParameter("areaId");
		List<MrbsRoom> list = this.mrbsRoomService.getAll(filter);
		String sql = "select a.id, a.room_id,b.room_admin_email, a.start_time, a.end_time, c.fullname as create_by, a.description from " +
				"mrbs_schedule a, mrbs_room b, app_user c where " +
				"a.room_id = b.id and b.area_id = " + areaId + " and a.create_by = c.userId and " +
				"a.start_time > '"  + DateUtil.convertDateToString(new Date())+  "' and " +
				"a.start_time < '"+DateUtil.convertDateToString(DateUtil.addDays(new Date(),1)) + "' order by a.end_time asc";
		
		List<Map> list_s = this.mrbsRoomService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		int mf = 0;
		for(MrbsRoom room : list) {
			mf++;
			buff.append("{'id':'" + room.getId() + "',")
					.append("'roomName':'" + room.getRoomName() + "',")
					.append("'room_admin_email':'"+ room.getRoomAdminEmail()+"',");
			StringBuffer content = new StringBuffer("<div id=\"cc\">");
			Date endTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			for(int i = 0; i < list_s.size(); i++){
				Map m = list_s.get(i);
				if(room.getId().toString().equals(m.get("room_id").toString())) {
					Date d_ = (Date)m.get("start_time");
					Date d= DateUtil.parseDate(sdf.format(d_));
					
					Date d_1 = (Date)m.get("end_time");
					Date dd= DateUtil.parseDate(sdf.format(d_1));
					
					String hstart = (d.getHours()>9)?d.getHours()+"":"0"+d.getHours();
					String hend = (dd.getHours()>9)?dd.getHours()+"":"0"+dd.getHours();
					
					content.append(d.getDate()+"日"+hstart+":00-"+hend+":00").append("&nbsp;&nbsp;&nbsp;"+m.get("create_by")).append("<br/><br/>");
					endTime = d_1;
					list_s.remove(i);
				};
			}
			String h = (endTime.getHours()>9)?endTime.getHours()+"":"0"+endTime.getHours();
			int flag  = 0;
			if(endTime.getHours()<20){
				content.append(endTime.getDate()+"日"+h+":00-"+"20:00").append("&nbsp;&nbsp;&nbsp;").append("空闲，<input type=\"button\" name=\"预订\" value=\"预订\"/>");
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

	public static void  toJson(List<MrbsRoom> list,StringBuffer sb){
		sb.append("[");
		for(MrbsRoom f:list){
			sb.append("{")
					.append("'id':'" + f.getId() + "',")
					.append("'roomName':'" + f.getRoomName() + "',")
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
				this.mrbsRoomService.remove(new Long(id));
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
					.append("'desc':'" + f.getDescription() + "',")
					.append("'capacity':'" + f.getCapacity() + "',")
					.append("'adminEmail':'" + f.getRoomAdminEmail() + "'},");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	/*public String save() {
		String data = getRequest().getParameter("funUrls");
		String[] funUrls = data.split(",");
		this.mrbsRoomService.save(this.mrbsArea);
		if(this.mrbsArea.getId()!=null){
			this.mrbsRoomService.updateFunUrl(funUrls,this.mrbsArea.getId());
		}
		setJsonString("{success:true}");
		return "success";
	}*/
}
