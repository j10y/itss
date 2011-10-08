package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPostApply;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.hrm.StandSalary;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.hrm.HrPostApplyService;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;
import com.xpsoft.oa.service.hrm.StandSalaryService;

import flexjson.JSONSerializer;

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
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_publishStatus_N_NEQ", "1");
		filter.addFilter("Q_publishStatus_N_NEQ", "3");
		filter.addFilter("Q_publishStatus_N_NEQ", "4");
		filter.addFilter("Q_publishStatus_N_NEQ", "5");
		filter.addFilter("Q_publishStatus_N_NEQ", "6");
		filter.addFilter("Q_publishStatus_N_NEQ", "7");
		filter.addFilter("Q_applyUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		List<HrPostApply> list = this.hrPostApplyService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String listStatus() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_publishStatus_N_NEQ", "0");
		filter.addFilter("Q_publishStatus_N_NEQ", "2");
		filter.addFilter("Q_publishStatus_N_NEQ", "4");
		filter.addFilter("Q_applyUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		List<HrPostApply> list = this.hrPostApplyService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	/*
	 * 获取指定ID转正申请记录
	 * */
	public String preview() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_userId_L_EQ", currentUser.getUserId().toString());
		QueryFilter filter = new QueryFilter(map);
		List<EmpProfile> empProfileList = empProfileService.getAll(filter);
		//判断当前用户是否已经建立档案
		if(empProfileList.size() <= 0) {
			this.getRequest().setAttribute("message", "您目前尚没有档案，请先建立档案！");
			return "createResult";
		}
		//判断当前用户是否已经转正
		String sql = "select count(*) as total from hr_post_assessment a, hr_post_apply b, emp_profile c, app_user d where " +
				"a.applyId = b.id and b.applyUser = c.userId and c.userId = d.userId and a.publishStatus = 3";
		List<Map<String, Object>> mapList = this.hrPostApplyService.findDataList(sql);
		if(Integer.parseInt(mapList.get(0).get("total").toString()) > 0) {
			this.getRequest().setAttribute("message", "您已经转正，请核实！");
			return "createResult";
		}
		if(this.id == 0) {
			this.hrPostApply = new HrPostApply();
			this.hrPostApply.setId(0l);
			this.hrPostApply.setApplyUser(currentUser);
			this.hrPostApply.setDeptName(empProfileList.get(0).getDepName());
			this.hrPostApply.setPostName(empProfileList.get(0).getPosition());
			this.hrPostApply.setGender(empProfileList.get(0).getSex());
			this.hrPostApply.setAccessionTime(empProfileList.get(0).getAccessionTime());
		} else {
			this.hrPostApply = this.hrPostApplyService.get(this.id);
		}
		return "show";
	}
	
	public String previewStatus() {
		if(this.id != 0) {
			this.hrPostApply = this.hrPostApplyService.get(this.id);
		}
		return "showStatus";
	}
	
	public String save() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		Date currentDate = new Date();
		HrPostApply postApply = new HrPostApply();
		boolean isSubmit = false;//是否提交工作流
		if(this.getRequest().getParameter("isSubmit") != null && Boolean.valueOf(this.getRequest().getParameter("isSubmit")).booleanValue()) {
			isSubmit = true;
		}
		try {
			if(this.hrPostApply.getId() == 0) {//新增
				EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_userId_L_EQ", currentUser.getUserId().toString());
				QueryFilter filter = new QueryFilter(map);
				List<EmpProfile> empProfileList = empProfileService.getAll(filter);
				postApply.setApplyUser(currentUser);
				postApply.setGender(empProfileList.get(0).getSex());
				postApply.setAge(this.hrPostApply.getAge());
				postApply.setDeptId(empProfileList.get(0).getDepId());
				postApply.setDeptName(empProfileList.get(0).getDepName());
				postApply.setPostId(empProfileList.get(0).getJobId());
				postApply.setPostName(empProfileList.get(0).getPosition());
				postApply.setAccessionTime(empProfileList.get(0).getAccessionTime());
				postApply.setProSummary(this.hrPostApply.getProSummary());
				postApply.setUserManagerAuditDate(currentDate);
				postApply.setPublishStatus(this.hrPostApply.getPublishStatus());
				postApply.setCreateDate(currentDate);
				postApply.setCreatePerson(currentUser);
				postApply.setModifyDate(currentDate);
				postApply.setModifyPerson(currentUser);
			} else {//修改
				postApply = this.hrPostApplyService.get(this.hrPostApply.getId());
				postApply.setAge(this.hrPostApply.getAge());
				postApply.setProSummary(this.hrPostApply.getProSummary());
				postApply.setModifyPerson(currentUser);
				postApply.setModifyDate(currentDate);
				postApply.setUserManagerAuditDate(currentDate);
				postApply.setPublishStatus(this.hrPostApply.getPublishStatus());
			}
			this.getRequest().setAttribute("flag", "1");
			HrPostApply postApplyNew = this.hrPostApplyService.save(postApply);
			if(isSubmit) {
				this.jsonString = "{success:true,'applyId':'" + postApplyNew.getId() + 
						"','fullname':'" + postApplyNew.getApplyUser().getFullname() + 
						"','deptId':'" + postApplyNew.getDeptId() + 
						"','deptName':'" +postApplyNew.getDeptName() + 
						"','postId':'" + postApplyNew.getPostId() + 
						"','postName':'" + postApplyNew.getPostName() + 
						"','accessionTime':'" + postApplyNew.getAccessionTime() + 
						"','proSummary':'" + postApplyNew.getProSummary() + 
						"'}";
				return "success";
			}
		} catch(Exception e) {
			this.getRequest().setAttribute("flag", "0");
			e.printStackTrace();
		}
		
		return "result";
	}
	
	/**
	 * 仅修改状态
	 * @return
	 */
	public String modStatus() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		HrPostAssessmentService hrPostAssessmentService = (HrPostAssessmentService)AppUtil.getBean("hrPostAssessmentService");
		StandSalaryService standSalaryService = (StandSalaryService)AppUtil.getBean("standSalaryService");
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		HrPostApply postApply = this.hrPostApplyService.get(this.hrPostApply.getId());
		Integer publishStatus = Integer.valueOf(this.getRequest().getParameter("publishStatus"));
		boolean isAssess = Boolean.valueOf(this.getRequest().getParameter("isAssess"));//评估阶段
		//if(!isAssess){
			postApply.setPublishStatus(publishStatus);
		//}
		HrPostAssessment assessment = null;
		if(this.getRequest().getParameter("auditStep")!=null){
			String auditStep = this.getRequest().getParameter("auditStep");
			if(auditStep.equalsIgnoreCase("LineManagerAudit")){//直线经理审核
				postApply.setPostManagerId(ContextUtil.getCurrentUserId());
				postApply.setPostManagerName(ContextUtil.getCurrentUser().getFullname());
				postApply.setPostManagerAuditDate(new Date());
				postApply.setModifyDate(currentDate);
				postApply.setModifyPerson(currentUser);
			}else if(auditStep.equalsIgnoreCase("HRConfirmAudit")){//人力资源复核
				assessment = hrPostAssessmentService.getByApplyId(this.hrPostApply.getId());
				assessment.setActualPostDate(DateUtil.parseDate(this.getRequest().getParameter("hrPostAssessment.actualPostDate")));
				assessment.setStandardPostId(Long.parseLong(this.getRequest().getParameter("hrPostAssessment.standardPostId")));
				assessment.setStandardPostName(this.getRequest().getParameter("hrPostAssessment.standardPostName"));
				assessment.setNewSalaryLevelId(Long.parseLong(this.getRequest().getParameter("hrPostAssessment.newSalaryLevelId")));
				assessment.setNewSalaryLevelName(this.getRequest().getParameter("hrPostAssessment.newSalaryLevelName"));
				assessment.setHrOpinion(this.getRequest().getParameter("leaderRead.leaderOpinion"));
				String[] bandGrade = this.getRequest().getParameter("hrPostAssessment.newSalaryLevelName").trim().split("_");
				if(bandGrade.length == 3) {
					assessment.setPostBand(bandGrade[1]);
					assessment.setPostGrade(bandGrade[2]);
				} else {
					this.logger.error("该薪资标准名称不符合规范，请重新命名！");
				}
				StandSalary newSalary = standSalaryService.get(assessment.getNewSalaryLevelId());
				if(newSalary != null) {
					assessment.setNewFixedSalary(newSalary.getTotalMoney().subtract(newSalary.getPerCoefficient()));
					assessment.setNewFloatSalary(newSalary.getPerCoefficient());
					assessment.setYearEndBonusCoefficient(newSalary.getYearEndBonusCoefficient());
					assessment.setTotalYearSalary(newSalary.getYearTotalMoney());
				} else {
					this.logger.error("该薪资标准不存在或已删除，请联系管理员！");
				}
				assessment.setModifyDate(currentDate);
				assessment.setModifyPerson(currentUser);
				assessment.setPublishStatus(publishStatus);
				hrPostAssessmentService.save(assessment);
				if(publishStatus == 3) {//更新档案表
					Map<String, String> map = new HashMap<String, String>();
					map.put("Q_userId_L_EQ", assessment.getPostApply().getApplyUser().getUserId().toString());
					QueryFilter filter = new QueryFilter(map);
					List<EmpProfile> empProfileList = empProfileService.getAll(filter);
					if(empProfileList.size() > 0) {
						empProfileList.get(0).setJobId(assessment.getStandardPostId());
						empProfileList.get(0).setPosition(assessment.getStandardPostName());
						empProfileList.get(0).setStandardMoney(assessment.getNewFixedSalary().add(assessment.getNewFloatSalary()));
						empProfileList.get(0).setStandardId(assessment.getNewSalaryLevelId());
						empProfileList.get(0).setStandardName(assessment.getNewSalaryLevelName());
						empProfileList.get(0).setPerCoefficient(assessment.getNewFloatSalary());
						empProfileList.get(0).setAccessionTime(assessment.getActualPostDate());
					}
				}
			}else if(auditStep.equalsIgnoreCase("VicePresidentConfirm")){//分管副总裁确认
				assessment = hrPostAssessmentService.getByApplyId(this.hrPostApply.getId());
				//assessment.setDeptOpinion(this.getRequest().getParameter("leaderRead.leaderOpinion"));
				assessment.setModifyDate(currentDate);
				assessment.setModifyPerson(currentUser);
				assessment.setPublishStatus(publishStatus);
				hrPostAssessmentService.save(assessment);
				if(publishStatus == 3) {//更新档案表
					Map<String, String> map = new HashMap<String, String>();
					map.put("Q_userId_L_EQ", assessment.getPostApply().getApplyUser().getUserId().toString());
					QueryFilter filter = new QueryFilter(map);
					List<EmpProfile> empProfileList = empProfileService.getAll(filter);
					if(empProfileList.size() > 0) {
						empProfileList.get(0).setJobId(assessment.getStandardPostId());
						empProfileList.get(0).setPosition(assessment.getStandardPostName());
						empProfileList.get(0).setStandardMoney(assessment.getNewFixedSalary().add(assessment.getNewFloatSalary()));
						empProfileList.get(0).setStandardId(assessment.getNewSalaryLevelId());
						empProfileList.get(0).setStandardName(assessment.getNewSalaryLevelName());
						empProfileList.get(0).setPerCoefficient(assessment.getNewFloatSalary());
						empProfileList.get(0).setAccessionTime(assessment.getActualPostDate());
					}
				}
			}
		}	
		this.hrPostApplyService.save(postApply);
		this.jsonString = "{success:true}";		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPostApply pa = this.hrPostApplyService.get(Long.parseLong(id));
				pa.setPublishStatus(4);//置为已删除状态
				this.hrPostApplyService.save(pa);
			}
		}
		this.jsonString = "{success:true}";
		
		return "success";
	}
}