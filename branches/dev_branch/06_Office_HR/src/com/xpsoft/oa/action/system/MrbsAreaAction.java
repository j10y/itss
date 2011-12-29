package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.MrbsArea;
import com.xpsoft.oa.service.system.MrbsAreaService;

public class MrbsAreaAction extends BaseAction {

	@Resource
	private MrbsAreaService mrbsAreaService;
	private MrbsArea mrbsArea;
	public MrbsAreaService getMrbsAreaService() {
		return mrbsAreaService;
	}

	public void setMrbsAreaService(MrbsAreaService mrbsAreaService) {
		this.mrbsAreaService = mrbsAreaService;
	}

	public MrbsArea getMrbsArea() {
		return mrbsArea;
	}

	public void setMrbsArea(MrbsArea mrbsArea) {
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
		List<MrbsArea> list = this.mrbsAreaService.getAll(filter);

		Type type = new TypeToken<List<MrbsArea>>() {
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

	public static void  toJson(List<MrbsArea> list,StringBuffer sb){
		sb.append("[");
		for(MrbsArea f:list){
			sb.append("{")
					.append("'id':'" + f.getId() + "',")
					.append("'areaName':'" + f.getAreaName() + "',")
					.append("'linkman':'" + f.getLinkman() + "',")
					.append("'descn':'" + f.getDescn() + "',")
					.append("'shortdescn':'" + f.getShortdescn() + "'},");
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
				MrbsArea ma =  this.mrbsAreaService.get(new Long(id));
				ma.setFlag(MrbsArea.DELETED);
				this.mrbsAreaService.save(ma);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		MrbsArea f = (MrbsArea) this.mrbsAreaService
				.get(this.id);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		       sb.append("{")
		       			.append("'id':'" + f.getId() + "',")
					.append("'areaName':'" + f.getAreaName() + "',")
					.append("'linkman':'" + f.getLinkman() + "',")
					.append("'descn':'" + f.getDescn() + "',")
					.append("'shortdescn':'" + f.getShortdescn() + "'},");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		this.mrbsAreaService.save(this.mrbsArea);
		setJsonString("{success:true}");
		return "success";
	}
	
	
	public String combo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<MrbsArea> list = this.mrbsAreaService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(MrbsArea pc : list) {
			buff.append("[" +
					"'" + pc.getId() + "'," +
					"'" + pc.getAreaName()+ "'" +
					"],");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
