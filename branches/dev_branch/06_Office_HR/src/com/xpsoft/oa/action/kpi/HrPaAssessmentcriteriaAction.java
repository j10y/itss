package com.xpsoft.oa.action.kpi;

import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.service.kpi.HrPaAssessmentcriteriaService;

public class HrPaAssessmentcriteriaAction extends BaseAction{
	@Resource
	private HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService;
	private HrPaAssessmentcriteria hrPaAssessmentcriteria;
	private long id;
	
	public HrPaAssessmentcriteriaService getHrPaAssessmentcriteriaService() {
		return hrPaAssessmentcriteriaService;
	}
	public void setHrPaAssessmentcriteriaService(
			HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService) {
		this.hrPaAssessmentcriteriaService = hrPaAssessmentcriteriaService;
	}
	public HrPaAssessmentcriteria getHrPaAssessmentcriteria() {
		return hrPaAssessmentcriteria;
	}
	public void setHrPaAssessmentcriteria(
			HrPaAssessmentcriteria hrPaAssessmentcriteria) {
		this.hrPaAssessmentcriteria = hrPaAssessmentcriteria;
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
	
	public String save() {
		this.hrPaAssessmentcriteriaService.save(this.hrPaAssessmentcriteria);
		this.jsonString = new String("{success:true}");
		return "success";
	}
	
	public String load() {
		Map<String, String> map = this.hrPaAssessmentcriteriaService.getKeyAndName();
		
		StringBuffer buff = new StringBuffer("[");
		for(Map.Entry<String, String> entry : map.entrySet()) {
			buff.append("['").append(entry.getKey()).append("',")
					.append("'").append(entry.getValue()).append("'],");
		}
		if(!map.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
