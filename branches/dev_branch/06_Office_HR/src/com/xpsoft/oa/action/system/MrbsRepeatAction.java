package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
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
import com.xpsoft.core.engine.AsynMeetingMailSendProcess;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.MrbsRepeat;
import com.xpsoft.oa.model.system.MrbsSchedule;
import com.xpsoft.oa.model.system.MrbsScheduleUser;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.MrbsRepeatService;
import com.xpsoft.oa.service.system.MrbsScheduleService;
import com.xpsoft.oa.service.system.MrbsScheduleUserService;

public class MrbsRepeatAction extends BaseAction {
	
	@Resource
	private AppUserService appUserService;
	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

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
	public MrbsScheduleService getMrbsScheduleService() {
		return mrbsScheduleService;
	}

	public void setMrbsScheduleService(MrbsScheduleService mrbsScheduleService) {
		this.mrbsScheduleService = mrbsScheduleService;
	}

	@Resource
	private MrbsRepeatService mrbsRepeatService;
	private MrbsRepeat mrbsRepeat;
	public MrbsRepeatService getMrbsRepeatService() {
		return mrbsRepeatService;
	}

	public void setMrbsRepeatService(MrbsRepeatService mrbsRepeatService) {
		this.mrbsRepeatService = mrbsRepeatService;
	}

	public MrbsRepeat getMrbsRepeat() {
		return mrbsRepeat;
	}

	public void setMrbsRepeat(MrbsRepeat mrbsRepeat) {
		this.mrbsRepeat = mrbsRepeat;
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
		List<MrbsRepeat> list = this.mrbsRepeatService.getAll(filter);

		Type type = new TypeToken<List<MrbsRepeat>>() {
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
	
	
	public String save() {
		QueryFilter filter = new QueryFilter(getRequest());
		//List<MrbsRepeat> list = this.mrbsRepeatService.getAll(filter);
		
		String attendIdListStr = getRequest().getParameter("attendIdList"); 
		String start_date = DateUtil.convertDateToString(this.mrbsRepeat.getStartDate()) + " " + this.mrbsRepeat.getStartHour() + ":" + this.mrbsRepeat.getStartMini() + ":00";
		this.mrbsRepeat.setStartDate(DateUtil.parseDate(start_date));
		//当 预订为 “当天” 时，结束日期 与 开始日期相同
		if(this.mrbsRepeat.getRepOpt() == 0){
			this.mrbsRepeat.setEndDate(this.mrbsRepeat.getStartDate());
		}
		//判断 开始时间 >= 结束时间
		if(Integer.valueOf(this.mrbsRepeat.getStartHour())*60+Integer.valueOf(this.mrbsRepeat.getStartMini())>=
			Integer.valueOf(this.mrbsRepeat.getEndHour())*60+Integer.valueOf(this.mrbsRepeat.getEndMini())){
			this.jsonString ="{success:false,msg:'开始时间  不能大于 结束时间！'}";
			return "success";
		}
		
		String end_date = DateUtil.convertDateToString(this.mrbsRepeat.getEndDate()) + " " + this.mrbsRepeat.getEndHour() + ":" + this.mrbsRepeat.getEndMini() + ":00";
		this.mrbsRepeat.setEndDate(DateUtil.parseDate(end_date));
		
		//保存预订申请
		this.mrbsRepeat.setCreateBy(ContextUtil.getCurrentUser());
		this.mrbsRepeatService.save(this.mrbsRepeat);
		//拆分预订
		List<MrbsSchedule> list = SaveToSchedule(this.mrbsRepeat,attendIdListStr);
		if(list == null || list.size() <1){
			this.jsonString = "{success:true}";
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
	/**
	 * 保存 与会人员名单
	 * @return
	 */
	private String saveScheduleAttender(MrbsSchedule ms,String attendIdListStr){
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
			//发送邮件
			sendEmailForMeeting(ms,attendIdListStr);
		}
		return "";
	}
	/**
	 * 申请原始记录保存在mrbs_Repeat 表
	 * 
	 * 详情：mrbs_Schedule表
	 * @param peat
	 * @return
	 */
	private List<MrbsSchedule> SaveToSchedule(MrbsRepeat peat,String attendIdListStr){
		List<MrbsSchedule> list = null;
		
		AppUser currentUser = ContextUtil.getCurrentUser();
		int repeat_opt = this.mrbsRepeat.getRepOpt();
		Date d  = new Date();
		Date startDate = peat.getStartDate();
		Date endDate = peat.getEndDate();
		switch(repeat_opt){
			case 0:{
				list = this.mrbsScheduleService.validate(peat.getStartDate(), peat.getEndDate(), peat.getRoom().getId());
				if(list != null && list.size()>0){
					return list;
				}else{
					MrbsSchedule ms = new MrbsSchedule();
					ms.setRepeat(peat);
					ms.setModifyBy(currentUser);
					ms.setCreateBy(currentUser);
					ms.setCreateDate(d);
					ms.setModifyDate(d);
					ms.setStartTime(peat.getStartDate());
					ms.setEndTime(peat.getEndDate());
					ms.setProjector(peat.getProjector());
					ms.setDescription(peat.getDescription());
					ms.setNum(peat.getNum());
					ms.setPreside(currentUser.getFullname());
					ms.setPresideEmail(currentUser.getEmail());
					ms.setRoom(peat.getRoom());
					this.mrbsScheduleService.save(ms);
					saveScheduleAttender(ms,attendIdListStr);
					//System.out.println(ms.getId());
				}
				 
			}break;
			case 1:{
				Calendar c_start = Calendar.getInstance();
				c_start.setTime(startDate);
				int startdayofyear = c_start.get(Calendar.DAY_OF_YEAR);
				Calendar c_end = Calendar.getInstance();
				c_end.setTime(endDate);
				int enddayofyear = c_end.get(Calendar.DAY_OF_YEAR);
				
				for(int i=0;i<=enddayofyear-startdayofyear;i++){
					//从startDate开始，以后每天都预订，时间天+1
					Calendar startc = Calendar.getInstance();
					startc.setTime(startDate);
					startc.add(Calendar.DAY_OF_YEAR, i);
					
					//endDate 时间与 startDate 是同一天，但 时 和 分不同
					
					
					Calendar endc = Calendar.getInstance();
					endc.setTime(endDate);
					endc.add(Calendar.DAY_OF_YEAR, startdayofyear-enddayofyear+i);
					list = this.mrbsScheduleService.validate(startc.getTime(), endc.getTime(), peat.getRoom().getId());
					if(list != null && list.size()>0){
						return list;
					}else{
						MrbsSchedule ms = new MrbsSchedule();
						ms.setRepeat(peat);
						ms.setModifyBy(currentUser);
						ms.setCreateBy(currentUser);
						ms.setCreateDate(d);
						ms.setModifyDate(d);
						ms.setStartTime(startc.getTime());
						ms.setEndTime(endc.getTime());
						ms.setProjector(peat.getProjector());
						ms.setDescription(peat.getDescription());
						ms.setNum(peat.getNum());
						ms.setPreside(currentUser.getFullname());
						ms.setPresideEmail(currentUser.getEmail());
						ms.setRoom(peat.getRoom());
						this.mrbsScheduleService.save(ms);
						saveScheduleAttender(ms,attendIdListStr);
						//System.out.println(ms.getId());
					}
				};
				
			}break;
			case 2:{
				Calendar c_start = Calendar.getInstance();
				Calendar c_end = Calendar.getInstance();
				c_start.setTime(startDate);
				c_end.setTime(endDate);
				//预订每周 的第几天
				int dayOfWeek_selected = peat.getRepeatWeekDay()+1;
				while(c_end.after(c_start)){
					if(c_start.get(Calendar.DAY_OF_WEEK)== dayOfWeek_selected){
						MrbsSchedule ms = new MrbsSchedule();
						ms.setRepeat(peat);
						ms.setModifyBy(currentUser);
						ms.setCreateBy(currentUser);
						ms.setCreateDate(d);
						ms.setModifyDate(d);
						ms.setStartTime(c_start.getTime());
						// endDate
						String end_date_str = DateUtil.convertDateToString(c_start.getTime()) + " " + peat.getEndHour() + ":" + peat.getEndMini() + ":00";
						ms.setEndTime(DateUtil.parseDate(end_date_str));
						ms.setProjector(peat.getProjector());
						ms.setDescription(peat.getDescription());
						ms.setNum(peat.getNum());
						ms.setPreside(currentUser.getFullname());
						ms.setPresideEmail(currentUser.getEmail());
						ms.setRoom(peat.getRoom());
						
						list = this.mrbsScheduleService.validate(ms.getStartTime(),ms.getEndTime(), peat.getRoom().getId());
						if(list != null && list.size()>0){
							return list;
						}else{
							this.mrbsScheduleService.save(ms);
							saveScheduleAttender(ms,attendIdListStr);
						}
					}
					c_start.add(Calendar.DAY_OF_YEAR, 1);
				};
				
			}break;
			case 3:{
				// 预订一周的第几天
				int dayOfWeek_selected = peat.getRepeatWeekDay()+1;
				// 隔几周
				int week_span = peat.getWeekSpan();
				
				Calendar c_start = Calendar.getInstance();
				Calendar c_end = Calendar.getInstance();
				c_start.setTime(startDate);
				c_end.setTime(endDate);
				
				// 找出第一次会议时间，以后 时间+2×7
				for(int i =0 ;i<=7;i++){
					if(c_start.get(Calendar.DAY_OF_WEEK) == dayOfWeek_selected){
						break;
					}
					c_start.add(Calendar.DAY_OF_YEAR, 1);
				}
				
				while(c_end.after(c_start)){
					MrbsSchedule ms = new MrbsSchedule();
					ms.setRepeat(peat);
					ms.setModifyBy(currentUser);
					ms.setCreateBy(currentUser);
					ms.setCreateDate(d);
					ms.setModifyDate(d);
					ms.setStartTime(c_start.getTime());
					// endDate
					String end_date_str = DateUtil.convertDateToString(c_start.getTime()) + " " + peat.getEndHour() + ":" + peat.getEndMini() + ":00";
					ms.setEndTime(DateUtil.parseDate(end_date_str));
					ms.setProjector(peat.getProjector());
					ms.setDescription(peat.getDescription());
					ms.setNum(peat.getNum());
					ms.setPreside(currentUser.getFullname());
					ms.setPresideEmail(currentUser.getEmail());
					ms.setRoom(peat.getRoom());
					
					list = this.mrbsScheduleService.validate(ms.getStartTime(),ms.getEndTime(), peat.getRoom().getId());
					if(list != null && list.size()>0){
						return list;
					}else{
						this.mrbsScheduleService.save(ms);
						saveScheduleAttender(ms,attendIdListStr);
					}
					c_start.add(Calendar.DAY_OF_YEAR, 7*week_span);
				}
				
				
			}break;
		}
		
		return list;
	}
	

	public static void  toJson(List<MrbsRepeat> list,StringBuffer sb){
		sb.append("[");
//		for(MrbsRepeat f:list){
//			sb.append("{")
//					.append("'id':'" + f.getId() + "',")
//					.append("'areaName':'" + f.getAreaName() + "',")
//					.append("'linkman':'" + f.getLinkman() + "',")
//					.append("'descn':'" + f.getDescn() + "',")
//					.append("'shortdescn':'" + f.getShortdescn() + "'},");
//		}
//		if(list.size() > 0) {
//			sb.deleteCharAt(sb.length() - 1);
//		}
		sb.append("]");
		
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		  if (ids != null) {
			 for (String id : ids) {
				this.mrbsRepeatService.remove(new Long(id));
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		MrbsRepeat f = (MrbsRepeat) this.mrbsRepeatService
				.get(this.id);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
//		       sb.append("{")
//		       			.append("'id':'" + f.getId() + "',")
//					.append("'areaName':'" + f.getAreaName() + "',")
//					.append("'linkman':'" + f.getLinkman() + "',")
//					.append("'descn':'" + f.getDescn() + "',")
//					.append("'shortdescn':'" + f.getShortdescn() + "'},");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	/*public String save() {
		String data = getRequest().getParameter("funUrls");
		String[] funUrls = data.split(",");
		this.mrbsRepeatService.save(this.mrbsRepeat);
		if(this.mrbsRepeat.getId()!=null){
			this.mrbsRepeatService.updateFunUrl(funUrls,this.mrbsRepeat.getId());
		}
		setJsonString("{success:true}");
		return "success";
	}*/
}
