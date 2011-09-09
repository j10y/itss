package com.xpsoft.oa.action.hrm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.hrm.HrPromApplyService;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;
import com.xpsoft.oa.service.hrm.JobService;

import flexjson.JSONSerializer;

public class HrPromApplyAction extends BaseAction{
	private HrPromApply hrPromApply;
	@Resource
	private HrPromApplyService hrPromApplyService;
	
	@Resource
	private HrPromAssessmentService hrPromAssessmentService;
	
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
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPromApply> list = this.hrPromApplyService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	/*
	 * 查找当前用户所在部门员工信息
	 * */
	public String sameDeptUser() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QueryFilter filter = new QueryFilter(this.getRequest());
		AppUser currentUser = ContextUtil.getCurrentUser();
		Date currentDate = new Date();
		String fullname = this.getRequest().getParameter("fullname");
		String sql = "select userId, fullname, jobId, position, depId, depName, accessionTime, startWorkDate from emp_profile where " +
				"depId = " + currentUser.getDepartment().getDepId();
		sql += (fullname == null || "".equals(fullname)) ? "" : " and fullname like '%" + fullname + "%'";
		sql += " limit " + filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList = this.hrPromApplyService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < mapList.size(); i++) {
			Date startWorkDate = (Date)mapList.get(i).get("startWorkDate");
			Date accessionTime = (Date)mapList.get(i).get("accessionTime");
			Long workYear = (currentDate.getTime() - startWorkDate.getTime()) / 1000 / 60 / 60 / 24 / 365;
			Long workHereYear = (currentDate.getTime() - accessionTime.getTime()) / 1000 / 60 / 60 / 24 / 365;
			buff.append("{'userId':'" + mapList.get(i).get("userId"))
					.append("','fullname':'" + mapList.get(i).get("fullname"))
					.append("','nowPositionId':'" + mapList.get(i).get("jobId"))
					.append("','nowPositionName':'" + mapList.get(i).get("position"))
					.append("','depId':'" + mapList.get(i).get("depId"))
					.append("','depName':'" + mapList.get(i).get("depName"))
					.append("','accessionTime':'" + sdf.format((Date)mapList.get(i).get("accessionTime")))
					.append("','workYear':'" + workYear)
					.append("','workHereYear':'" + workHereYear)
					.append("'},");
		}
		
		this.jsonString = buff.toString();
		if(mapList.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	/*
	 * 查找当前用户所在部门岗位信息
	 * */
	public String applyPosition() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		JobService jobService = (JobService)AppUtil.getBean("jobService");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_department.depId_L_EQ", currentUser.getDepartment().getDepId().toString());
		QueryFilter filter = new QueryFilter(map);
		List<Job> list = jobService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	/*
	 * 获取指定ID晋升申请记录
	 * */
	public String preview() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		if(this.id != 0) {
			this.hrPromApply = this.hrPromApplyService.get(this.id);
		}
		return "show";
	}
	
	public String get() {
		return "success";
	}
	
	public String save() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		Date currentDate = new Date();
		HrPromApply promApply = new HrPromApply();
		boolean isSubmit = false;//是否提交工作流
		if(getRequest().getParameter("isSubmit")!=null && Boolean.valueOf(getRequest().getParameter("isSubmit")).booleanValue()){
			isSubmit=true;
		}
		try {
			if(this.hrPromApply.getId() == null) {
				promApply.setCreateDate(currentDate);
				promApply.setCreatePerson(currentUser);
				promApply.setModifyDate(currentDate);
				promApply.setModifyPerson(currentUser);
			} else {
				promApply = this.hrPromApplyService.get(this.hrPromApply.getId());
				promApply.setModifyDate(currentDate);
				promApply.setModifyPerson(currentUser);
			}
			promApply.setDepId(this.hrPromApply.getDepId());
			promApply.setDepName(this.hrPromApply.getDepName());
			promApply.setNowPositionId(this.hrPromApply.getNowPositionId());
			promApply.setNowPositionName(this.hrPromApply.getNowPositionName());
			promApply.setWorkYear(this.hrPromApply.getWorkYear());
			promApply.setWorkHereYear(this.hrPromApply.getWorkHereYear());
			promApply.setApplyUser(this.hrPromApply.getApplyUser());
			promApply.setAccessionTime(this.hrPromApply.getAccessionTime());
			promApply.setApplyPositionId(this.hrPromApply.getApplyPositionId());
			promApply.setApplyPositionName(this.hrPromApply.getApplyPositionName());
			promApply.setApplyReason(this.hrPromApply.getApplyReason());
			promApply.setApplyDate(this.hrPromApply.getApplyDate());
			promApply.setTarget1(this.hrPromApply.getTarget1());
			promApply.setTarget2(this.hrPromApply.getTarget2());
			promApply.setTarget3(this.hrPromApply.getTarget3());
			promApply.setIntRecord(this.hrPromApply.getIntRecord());
			promApply.setPostManagerId(currentUser.getUserId());
			promApply.setPostManagerName(currentUser.getFullname());
			promApply.setPublishStatus(this.hrPromApply.getPublishStatus()!=null ? this.hrPromApply.getPublishStatus() : 0);
			this.hrPromApplyService.save(promApply);
			this.getRequest().setAttribute("flag", "1");
		} catch(Exception e) {
			this.getRequest().setAttribute("flag", "0");
			e.printStackTrace();
		}
		if(isSubmit){
			this.jsonString = "{success:true,'applyId':'" + promApply.getId() + "'}";
			return "success";
		}
		return "result";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPromApply pa = this.hrPromApplyService.get(Long.parseLong(id));
				pa.setPublishStatus(4);//置为已删除状态
				this.hrPromApplyService.save(pa);
			}
		}
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	/**
	 * 仅修改状态
	 * @return
	 */
	public String modStatus() {		
		HrPromApply promApply = this.hrPromApplyService.get(this.id);
		Integer publishStatus = Integer.valueOf(getRequest().getParameter("publishStatus"));
		boolean isAssess = Boolean.valueOf(getRequest().getParameter("isAssess"));//评估阶段
		if(!isAssess){
			promApply.setPublishStatus(publishStatus);//申请阶段可以修改状态，或者指定节点可以修改
		}
		HrPromAssessment assessment = null;
		if(getRequest().getParameter("auditStep")!=null){
			String auditStep = getRequest().getParameter("auditStep");
			if(auditStep.equalsIgnoreCase("setTarget")){//目标设定关于面谈
				//目标
				promApply.setTarget1(getRequest().getParameter("target1"));
				promApply.setTarget2(getRequest().getParameter("target2"));
				promApply.setTarget3(getRequest().getParameter("target3"));
				//面谈记录
				promApply.setIntRecord(getRequest().getParameter("intRecord"));				
			}else if(auditStep.equalsIgnoreCase("assess")){//考核期评估
				assessment = this.hrPromAssessmentService.getByApplyId(promApply.getId());
				//申请表通过审批，评估表继续进行
				promApply.setPublishStatus(HrPromApply.STATUS_APPROVED);
				assessment.setPublishStatus(publishStatus);
				this.hrPromAssessmentService.save(assessment);
			}else if(auditStep.equalsIgnoreCase("headerApprove")){//领导批准
				assessment = this.hrPromAssessmentService.getByApplyId(promApply.getId());
				//申请表通过审批，评估表继续进行
				assessment.setPublishStatus(publishStatus);
				this.hrPromAssessmentService.save(assessment);
			}else if(auditStep.equalsIgnoreCase("hrConfirmAssess")){
				assessment = this.hrPromAssessmentService.getByApplyId(promApply.getId());
				//申请表通过审批，评估表继续进行
//				protected String postRank;//岗位职级
//				protected Long salaryLevelId;//薪资等级ID
//				protected String salaryLevelName;//薪资等级名称
				assessment.setPostRank(getRequest().getParameter("hrPromAssessment.postRank"));
				assessment.setSalaryLevelId(Long.valueOf(getRequest().getParameter("hrPromAssessment.salaryLevelId")));
				assessment.setSalaryLevelName(getRequest().getParameter("hrPromAssessment.salaryLevelName"));
				
				assessment.setPublishStatus(publishStatus);
				this.hrPromAssessmentService.save(assessment);
			}
		}	
		this.hrPromApplyService.save(promApply);
		this.jsonString = "{success:true}";		
		return "success";
	}
	
}
