package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;
import com.xpsoft.oa.service.hrm.JobService;

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
	
	/**
	 * 通过申请表ID查询评估表，如果不存在则建立
	 * @return
	 */
	public String getViewByApplyId() {
		boolean isView = Boolean.valueOf(this.getRequest().getParameter("isView"));
		Long applyId = Long.valueOf(this.getRequest().getParameter("applyId"));
		this.hrPostAssessment = this.hrPostAssessmentService.saveViewByApplyId(applyId);
		if(isView){
			return "view";
		}
		return "show";
	}
	
	public String save() {
		try {
			Date currentDate = new Date();
			AppUser currentUser = ContextUtil.getCurrentUser();
			HrPostAssessment postAssNew = this.hrPostAssessmentService.get(this.hrPostAssessment.getId());
			postAssNew.setProKnowledge(this.hrPostAssessment.getProKnowledge());
			postAssNew.setCommEffect(this.hrPostAssessment.getCommEffect());
			postAssNew.setSolveAbility(this.hrPostAssessment.getSolveAbility());
			postAssNew.setDifficultyManage(this.hrPostAssessment.getDifficultyManage());
			postAssNew.setBusinessFieldEffect(this.hrPostAssessment.getBusinessFieldEffect());
			postAssNew.setRatingResult(this.hrPostAssessment.getRatingResult());
			postAssNew.setProPerformance(this.hrPostAssessment.getProPerformance());
			postAssNew.setPostManagerId(currentUser.getUserId());
			postAssNew.setPostManagerName(currentUser.getFullname());
			postAssNew.setPostManagerAuditDate(currentDate);
			postAssNew.setModifyDate(currentDate);
			postAssNew.setModifyPerson(currentUser);
			this.hrPostAssessmentService.save(postAssNew);
			this.getRequest().setAttribute("flag", "1");
		} catch(Exception e) {
			e.printStackTrace();
			this.getRequest().setAttribute("flag", "0");
		}
		
		return "result";
	}
	
	/*
	 * 查找指定用户所在部门岗位信息
	 * */
	public String applyPosition() {
		JobService jobService = (JobService)AppUtil.getBean("jobService");
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<Job> list = jobService.getAll(filter);
		System.out.println(this.getRequest().getParameter("Q_department.depId_L_EQ"));
		StringBuffer buff = new StringBuffer("{success:true,result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	/**
	 * 验证评估表是否存在且部分字段是否填写完毕
	 */
	public String validateAssessByApplyId() {
		Long applyId = Long.valueOf(getRequest().getParameter("applyId"));
		this.hrPostAssessment = this.hrPostAssessmentService.getByApplyId(applyId);
		if(this.hrPostAssessment!=null){
			String ratingResult = this.hrPostAssessment.getRatingResult();
			if(ratingResult==null||org.apache.commons.lang.StringUtils.isEmpty(ratingResult)){
				this.jsonString = "{success:false,'msg':'请填写试用期评估表【评级结果】！'}";
				return "success";
			}else{
				String proPerformance = this.hrPostAssessment.getProPerformance();
				if(proPerformance==null||org.apache.commons.lang.StringUtils.isEmpty(proPerformance)){
					this.jsonString = "{success:false,'msg':'请填写试用期评估表【员工试用（实习）期间工作表现评价】！'}";
					return "success";
				}
			}		
		}else{
			this.jsonString = "{success:false,'msg':'请填写试用期评估表！'}";
			return "success";
		}
		this.jsonString = "{success:true,'standardPostId':'" + this.hrPostAssessment.getStandardPostId() + 
				"','standardPostName':'" + this.hrPostAssessment.getStandardPostName() + 
				"','newSalaryLevelId':'" + this.hrPostAssessment.getNewSalaryLevelId() + 
				"','newSalaryLevelName':'" + this.hrPostAssessment.getNewSalaryLevelName() + 
				"'}";
		return "success";
	}
}
