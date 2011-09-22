package com.xpsoft.oa.action.hrm;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.HrPostApply;
import com.xpsoft.oa.service.hrm.HrPostApplyService;

public class HrPostApplyAction extends BaseAction{
	@Resource
	private HrPostApplyService hrPostApplyService;
	private HrPostApply hrPostApply;
	private Long id;
	public HrPostApplyService getHrPostApplyService() {
		return hrPostApplyService;
	}
	public void setHrPostApplyService(HrPostApplyService hrPostApplyService) {
		this.hrPostApplyService = hrPostApplyService;
	}
	public HrPostApply getHrPostApply() {
		return hrPostApply;
	}
	public void setHrPostApply(HrPostApply hrPostApply) {
		this.hrPostApply = hrPostApply;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}