package com.xpsoft.oa.action.hrm;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.service.hrm.HrPromApplyService;

public class HrPromApplyAction extends BaseAction{
	private HrPromApply hrPromApply;
	private HrPromApplyService hrPromApplyService;
	private Long id;
	
	public HrPromApply getHrPromApply() {
		return hrPromApply;
	}
	public void setHrPromApply(HrPromApply hrPromApply) {
		this.hrPromApply = hrPromApply;
	}
	public HrPromApplyService getHrPromApplyService() {
		return hrPromApplyService;
	}
	public void setHrPromApplyService(HrPromApplyService hrPromApplyService) {
		this.hrPromApplyService = hrPromApplyService;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String list() {
		return "success";
	}
	
	public String get() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
	
}
