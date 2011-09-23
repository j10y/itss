package com.xpsoft.oa.action.hrm;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;

import flexjson.JSONSerializer;

public class HrPostAssessmentAction extends BaseAction{
	@Resource
	private HrPostAssessmentService hrPostAssessmentService;
	private HrPostAssessment hrPostAssessment;
	private Long id;
	
	public HrPostAssessment getHrPostAssessment() {
		return hrPostAssessment;
	}
	public void setHrPostAssessment(HrPostAssessment hrPostAssessment) {
		this.hrPostAssessment = hrPostAssessment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public HrPostAssessmentService getHrPostAssessmentService() {
		return hrPostAssessmentService;
	}
	public void setHrPostAssessmentService(
			HrPostAssessmentService hrPostAssessmentService) {
		this.hrPostAssessmentService = hrPostAssessmentService;
	}
	
	public String listStatus() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_publishStatus_N_NEQ", "0");
		filter.addFilter("Q_publishStatus_N_NEQ", "2");
		filter.addFilter("Q_publishStatus_N_NEQ", "4");
		filter.addFilter("Q_createPerson.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		List<HrPostAssessment> list = this.hrPostAssessmentService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String previewStatus() {
		if(this.id != 0) {
			this.hrPostAssessment = this.hrPostAssessmentService.get(this.id);
		}
		
		return "showStatus";
	}
}
