package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.MrbsRepeat;
import com.xpsoft.oa.service.system.MrbsRepeatService;

public class MrbsRepeatAction extends BaseAction {

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
		this.mrbsRepeatService.save(this.mrbsRepeat);

		this.jsonString = "{success:true}";

		return "success";
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
