package com.xpsoft.oa.action.hrm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import antlr.StringUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;

public class HrPromAssessmentAction extends BaseAction{
	private HrPromAssessment hrPromAssessment;
	@Resource
	private HrPromAssessmentService hrPromAssessmentService;
	private Long id;
	
	public HrPromAssessment getHrPromAssessment() {
		return hrPromAssessment;
	}
	public void setHrPromAssessment(HrPromAssessment hrPromAssessment) {
		this.hrPromAssessment = hrPromAssessment;
	}
	public HrPromAssessmentService getHrPromAssessmentService() {
		return hrPromAssessmentService;
	}
	public void setHrPromAssessmentService(
			HrPromAssessmentService hrPromAssessmentService) {
		this.hrPromAssessmentService = hrPromAssessmentService;
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
	
	public String preview() {
		if(this.id != 0) {
			this.hrPromAssessment = this.hrPromAssessmentService.get(this.id);
		}
		return "show";
	}
	
	/**
	 * 通过申请表ID查询评估表，如果不存在则建立
	 * @return
	 */
	public String getViewByApplyId() {
		boolean isView = Boolean.valueOf(getRequest().getParameter("isView"));
		Long applyId = Long.valueOf(getRequest().getParameter("applyId"));
		this.hrPromAssessment = this.hrPromAssessmentService.saveViewByApplyId(applyId);
		if(isView){
			return "view";
		}
		return "show";
	}
	
	/**
	 * 验证评估表是否存在且部分字段是否填写完毕
	 */
	public String validateAssessByApplyId() {
		Long applyId = Long.valueOf(getRequest().getParameter("applyId"));
		this.hrPromAssessment = this.hrPromAssessmentService.getByApplyId(applyId);
		if(this.hrPromAssessment!=null){
			String reached1 = this.hrPromAssessment.getReached1();
			if(reached1==null||org.apache.commons.lang.StringUtils.isEmpty(reached1)){
				this.jsonString = "{success:false,'msg':'请填写晋升评估表【工作目标完成情况信息】！'}";
				return "success";
			}else{
				String performResult = this.hrPromAssessment.getPerformResult();
				if(performResult==null||org.apache.commons.lang.StringUtils.isEmpty(performResult)){
					this.jsonString = "{success:false,'msg':'请填写晋升评估表【绩效结果信息】！'}";
					return "success";
				}else{
					String ratingResult = this.hrPromAssessment.getRatingResult();
					if(ratingResult==null||org.apache.commons.lang.StringUtils.isEmpty(ratingResult)){
						this.jsonString = "{success:false,'msg':'请填写晋升评估表【工作能力匹配信息】评级结果！'}";
						return "success";
					}
				}
			}		
						
		}else{
			this.jsonString = "{success:false,'msg':'请填写晋升评估表！'}";
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	
	public String get() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
}
