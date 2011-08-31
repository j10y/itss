package com.xpsoft.oa.action.kpi;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;

import flexjson.JSONSerializer;

public class HrPaKpiPBC2UserAction extends BaseAction {
	@Resource
	private HrPaKpiPBC2UserService hrPaKpiPBC2UserService;
	private HrPaKpiPBC2User hrPaKpiPBC2User;
	private long id;
	
	public HrPaKpiPBC2UserService getHrPaKpiPBC2UserService() {
		return hrPaKpiPBC2UserService;
	}
	public void setHrPaKpiPBC2UserService(
			HrPaKpiPBC2UserService hrPaKpiPBC2UserService) {
		this.hrPaKpiPBC2UserService = hrPaKpiPBC2UserService;
	}
	public HrPaKpiPBC2User getHrPaKpiPBC2User() {
		return hrPaKpiPBC2User;
	}
	public void setHrPaKpiPBC2User(HrPaKpiPBC2User hrPaKpiPBC2User) {
		this.hrPaKpiPBC2User = hrPaKpiPBC2User;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaKpiPBC2User> list = this.hrPaKpiPBC2UserService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiCal() {
		String[] ids = this.getRequest().getParameterValues("ids");
		String idString = "";
		for(int i = 0; i < ids.length - 1; i++) {
			idString += ids[i] + ",";
		}
		idString += ids[ids.length - 1];
		//批量计算总分
		this.hrPaKpiPBC2UserService.multiCal(idString);
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String get() {
		return "success";
	}
	
	public String multiDel() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
}
