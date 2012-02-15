package com.xpsoft.oa.action.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.engine.AsynMeetingMailSendProcess;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.MrbsRepeat;
import com.xpsoft.oa.model.system.MrbsRoom;
import com.xpsoft.oa.model.system.MrbsSchedule;
import com.xpsoft.oa.model.system.MrbsScheduleExtend;
import com.xpsoft.oa.model.system.MrbsScheduleUser;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.MrbsScheduleService;
import com.xpsoft.oa.service.system.MrbsScheduleUserService;

import flexjson.JSONSerializer;

public class MrbsScheduleAction extends BaseAction {
	@Resource
	private MrbsScheduleUserService mrbsScheduleUserService;
	public MrbsScheduleUserService getMrbsScheduleUserService() {
		return mrbsScheduleUserService;
	}

	public void setMrbsScheduleUserService(
			MrbsScheduleUserService mrbsScheduleUserService) {
		this.mrbsScheduleUserService = mrbsScheduleUserService;
	}

	@Resource
	private MrbsScheduleService mrbsScheduleService;
	private MrbsSchedule mrbsSchedule;
	public MrbsScheduleService getMrbsScheduleService() {
		return mrbsScheduleService;
	}

	public void setMrbsScheduleService(MrbsScheduleService mrbsScheduleService) {
		this.mrbsScheduleService = mrbsScheduleService;
	}

	public MrbsSchedule getMrbsSchedule() {
		return mrbsSchedule;
	}

	public void setMrbsSchedule(MrbsSchedule mrbsSchedule) {
		this.mrbsSchedule = mrbsSchedule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;

	public String delete(){
		this.mrbsScheduleUserService.removeByScheduleId(this.id);
		this.mrbsScheduleService.remove(this.id);
		this.jsonString ="{success:true,result:''}";
		return "success";
	}
	
	/*
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<MrbsSchedule> list = this.MrbsScheduleService.getAll(filter);

		Type type = new TypeToken<List<MrbsSchedule>>() {
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
	}*/
	
	public String save(){
		HttpServletRequest request = getRequest();
		this.mrbsSchedule = new MrbsSchedule(Long.valueOf(request.getParameter("schedule_id")));
		this.mrbsSchedule.setConferenceCall(null);
		this.mrbsSchedule.setCreateBy(ContextUtil.getCurrentUser());
		String desc = request.getParameter("mrbsRepeat.description");
		this.mrbsSchedule.setDescription(desc.replace("\n", "\\n"));
			String date = request.getParameter("mrbsRepeat.startDate");
			String start_hour = request.getParameter("mrbsRepeat.startHour");
			String start_mini = request.getParameter("mrbsRepeat.startMini");
			String end_hour = request.getParameter("mrbsRepeat.endHour");
			String end_mini = request.getParameter("mrbsRepeat.endMini");
		this.mrbsSchedule.setStartTime(DateUtil.parseDate(date+" "+start_hour+":"+start_mini+":00"));
		this.mrbsSchedule.setEndTime(DateUtil.parseDate(date+" "+end_hour+":"+end_mini+":00"));
		this.mrbsSchedule.setModifyBy(ContextUtil.getCurrentUser());
		this.mrbsSchedule.setModifyDate(new Date());
		this.mrbsSchedule.setNum(Integer.valueOf(request.getParameter("mrbsRepeat.num")));
		this.mrbsSchedule.setPreside(request.getParameter("mrbsRepeat.orderman"));
		this.mrbsSchedule.setProjector((request.getParameter("mrbsRepeat.projector")==null)?null:Integer.valueOf(request.getParameter("mrbsRepeat.projector")));
		this.mrbsSchedule.setRepeat(new MrbsRepeat(Long.valueOf(request.getParameter("repeat_id"))));
		this.mrbsSchedule.setPresideEmail(ContextUtil.getCurrentUser().getEmail());
		this.mrbsSchedule.setRoom(new MrbsRoom(Long.valueOf(request.getParameter("mrbsRepeat.room.id"))));
		this.mrbsSchedule.getRoom().setRoomName(request.getParameter("mrbsRepeat.room.roomName"));
		this.mrbsSchedule.setConferenceCall(request.getParameter("mrbsRepeat.conferenceCall")==null?null:Integer.valueOf(request.getParameter("mrbsRepeat.conferenceCall")));
		if(this.mrbsSchedule.getEndTime().after(this.mrbsSchedule.getStartTime())){
			//验证是否已经有人预订
			List<MrbsSchedule> list = this.mrbsScheduleService.validate(this.mrbsSchedule.getStartTime(), this.mrbsSchedule.getEndTime(), Long.valueOf(request.getParameter("mrbsRepeat.room.id")));
			if(list != null && list.size()>0){
				if(list.size() == 1){
					if(this.mrbsSchedule.getId().longValue() == list.get(0).getId().longValue()){
						this.mrbsScheduleService.save(this.mrbsSchedule);
						// 重新保存 参会人员 名单
						saveScheduleAttender(this.mrbsSchedule,request.getParameter("attendIdList"));
						// 修改会议预订后 ，重新发邮件
						sendEmailForMeeting(this.mrbsSchedule,request.getParameter("attendIdList"));
						this.jsonString ="{success:true,result:''}";
					}
				}else{
					StringBuffer sb = new StringBuffer();
					MrbsSchedule m = list.get(0);
					sb.append("此会议 在").append(DateUtil.convertDateToString(m.getStartTime())+" "+
							((m.getStartTime().getHours()<10)?"0"+m.getStartTime().getHours():m.getStartTime().getHours())+":"+
							((m.getStartTime().getMinutes()<10)?"0"+m.getStartTime().getMinutes():m.getStartTime().getMinutes())+" - "+
							//DateUtil.convertDateToString(m.getEndTime())+" "+
							(m.getEndTime().getHours()<10?"0"+m.getEndTime().getHours():m.getEndTime().getHours())+":"+
							(m.getEndTime().getMinutes()<10?"0"+m.getEndTime().getMinutes():m.getEndTime().getMinutes())+" 时间段已经被【")
							.append(m.getPreside()).append("】预订");
					this.jsonString = "{success:false,msg:'"+sb.toString()+"'}";
				}
			}else{
				this.mrbsScheduleService.save(this.mrbsSchedule);
				// 重新保存 参会人员 名单
				saveScheduleAttender(this.mrbsSchedule,request.getParameter("attendIdList"));
				// 修改会议预订后 ，重新发邮件
				sendEmailForMeeting(this.mrbsSchedule,request.getParameter("attendIdList"));
				this.jsonString ="{success:true,result:''}";
			}
		}else{
			this.jsonString ="{success:false,msg:'开始时间  不能大于 结束时间！'}";
		}
		return "success";
	}
	private String saveScheduleAttender(MrbsSchedule ms,String attendIdListStr){
		this.mrbsScheduleUserService.removeByScheduleId(ms.getId());
		if(attendIdListStr != null && attendIdListStr.length()>0){
			String[] ids = attendIdListStr.split(",");
			System.out.println(attendIdListStr);
			if(ids != null && ids.length >0){
				for(int i = 0 ;i<ids.length;i++){
					MrbsScheduleUser msu = new MrbsScheduleUser();
					msu.setConferee(new AppUser(Long.valueOf(ids[i])));
					msu.setSchedule(ms);
					this.mrbsScheduleUserService.save(msu);
				}
			}
		}
		return "";
	}
	public String list() {
		SimpleDateFormat sdfForDate = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
		
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_startTime_D_GT", DateUtil.convertDateToString(new Date()));
		List<MrbsSchedule> oldList = this.mrbsScheduleService.getAll(filter);
		List<MrbsScheduleExtend> newList = new ArrayList<MrbsScheduleExtend>();
		for(MrbsSchedule ms : oldList) {
			MrbsScheduleExtend mse = new MrbsScheduleExtend();
			BeanUtils.copyProperties(ms, mse);
			mse.setDate(sdfForDate.format(ms.getStartTime()));
			mse.setStartHour(sdfHour.format(ms.getStartTime()));
			mse.setEndHour(sdfHour.format(ms.getEndTime()));
			mse.setWeek(DateUtil.getDays(ms.getStartTime()));
			newList.add(mse);
		}
		oldList = null;
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(newList));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}

	public static void  toJson(List<MrbsSchedule> list,StringBuffer sb){
		sb.append("[");
		for(MrbsSchedule f:list){
			sb.append("{")
					.append("'id':'" + f.getId() + "',")
					.append("'roomName':'" + f.getDescription() + "',")
					.append("'desc':'" + f.getPresideEmail() + "',")
					.append("'capacity':'" + f.getTitle() + "',")
					.append("'adminEmail':'" + f.getNum() + "'},");
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
				this.mrbsScheduleService.remove(new Long(id));
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		MrbsSchedule f = (MrbsSchedule) this.mrbsScheduleService
				.get(this.id);
		Map params = new HashMap();
		params.put("Q_schedule.id_L_EQ", this.id.toString());
		QueryFilter filter = new QueryFilter(params);
		List<MrbsScheduleUser> list = this.mrbsScheduleUserService.getAll(filter);
		//String 
		StringBuffer sbName = new StringBuffer();
		StringBuffer sbId = new StringBuffer();
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				sbName.append(list.get(i).getConferee().getFullname());
				sbId.append(list.get(i).getConferee().getId());
				if(i<list.size()-1){
					sbName.append(",");
					sbId.append(",");
				}
			}
			
		}
		StringBuffer sb = new StringBuffer("{success:true,data:");
		       sb.append("{")
		       		.append("'schedule_id':'" + f.getId() + "',")
		       		.append("'mrbsRepeat.startDate':'" +DateUtil.convertDateToString(f.getStartTime()) + "',")
		       		//时、分，必须以Mrbs_schedule.startTime为准，用户可能单独修改 某条 schedule 时、分，而相应的Repeat表中的start_hour、start_mini 没有修改， 
		       		.append("'mrbsRepeat.startHour':'" +(f.getStartTime().getHours()<10?"0"+f.getStartTime().getHours():f.getStartTime().getHours())+ "',")
		       		.append("'mrbsRepeat.startMini':'" +(f.getStartTime().getMinutes()<10?"0"+f.getStartTime().getMinutes():f.getStartTime().getMinutes()) + "',")
		       		
		       		.append("'mrbsRepeat.endDate':'" +DateUtil.convertDateToString(f.getEndTime()) + "',")
		       		.append("'mrbsRepeat.endHour':'" +(f.getEndTime().getHours()<10?"0"+f.getEndTime().getHours():f.getEndTime().getHours()) + "',")
		       		.append("'mrbsRepeat.endMini':'" +(f.getEndTime().getMinutes()<10?"0"+f.getEndTime().getMinutes():f.getEndTime().getMinutes()) + "',")
		       		.append("'attendList':'" +sbName.toString() + "',")
		       		.append("'attendIdList':'" +sbId.toString() + "',")
		       		.append("'mrbsRepeat.room.id':'" +f.getRoom().getId() + "',")
		       		.append("'roomName':'" +f.getRoom().getRoomName() + "',")
		       		.append("'mrbsRepeat.orderman':'" +f.getPreside() + "',")
		       		.append("'mrbsRepeat.description':'" +f.getDescription() + "',")
		       		.append("'mrbsRepeat.num':'" +f.getNum() + "',")
		       		.append("'mrbsRepeat.projector':'"+f.getProjector()+"',")
		       		//select name from Appuser
					.append("'mrbsRepeat.orderman':'" +f.getPreside() + "',")
					.append("'repeat_id':'" +f.getRepeat().getId() + "',")
					.append("'presideEmail':'" +f.getPresideEmail() + "',")
					.append("'mrbsRepeat.conferenceCall':'"+f.getConferenceCall()+"'},");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	
	private List<AppUser> getAssignUserEmail(String assignIds) {
		String[] userIds = assignIds.split(",");
		List<AppUser> mailList = new ArrayList<AppUser>();
		if(userIds.length > 0){
			for (String id : userIds) {
				mailList.add(((AppUser) this.appUserService.get(Long.parseLong(id))));
			}
		}else{
			mailList.add(((AppUser) this.appUserService.get(Long.parseLong(assignIds))));
		}

		return mailList;
	}
	/**
	 * 
	 */
	private void sendEmailForMeeting(MrbsSchedule ms,String attendIdListStr){
		List<AppUser> mailList = new ArrayList<AppUser>();
		mailList = getAssignUserEmail(attendIdListStr);
		
		Map model = new HashMap();
		model.put("startTime", DateUtil.formatDateTimeToString(ms.getStartTime(),"yyyy-MM-dd hh:mm"));
		model.put("roomName", ms.getRoom().getRoomName());
		model.put("description", ms.getDescription());
		model.put("presideEmail", ms.getPresideEmail());
		
		AsynMeetingMailSendProcess amsp = new AsynMeetingMailSendProcess(mailList, null, attendIdListStr,model);
		Thread td = new Thread(amsp);
		td.start();
	}
	@Resource
	private AppUserService appUserService;
	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
}
