package com.xpsoft.oa.action.kpi;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;

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
