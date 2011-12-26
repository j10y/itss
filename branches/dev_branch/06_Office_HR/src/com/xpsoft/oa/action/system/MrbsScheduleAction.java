package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.MrbsSchedule;
import com.xpsoft.oa.service.system.MrbsScheduleService;

public class MrbsScheduleAction extends BaseAction {

	@Resource
	private MrbsScheduleService MrbsScheduleService;
	private MrbsSchedule mrbsSchedule;
	public MrbsScheduleService getMrbsScheduleService() {
		return MrbsScheduleService;
	}

	public void setMrbsScheduleService(MrbsScheduleService MrbsScheduleService) {
		this.MrbsScheduleService = MrbsScheduleService;
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
				this.MrbsScheduleService.remove(new Long(id));
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		MrbsSchedule f = (MrbsSchedule) this.MrbsScheduleService
				.get(this.id);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		       sb.append("{")
		       		.append("'id':'" + f.getId() + "',")
					.append("'roomName':'" + f.getDescription() + "',")
					.append("'desc':'" + f.getPresideEmail() + "',")
					.append("'capacity':'" + f.getTitle() + "',")
					.append("'adminEmail':'" + f.getNum() + "'},");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	/*public String save() {
		String data = getRequest().getParameter("funUrls");
		String[] funUrls = data.split(",");
		this.MrbsScheduleService.save(this.mrbsSchedule);
		if(this.mrbsSchedule.getId()!=null){
			this.MrbsScheduleService.updateFunUrl(funUrls,this.mrbsSchedule.getId());
		}
		setJsonString("{success:true}");
		return "success";
	}*/
}
