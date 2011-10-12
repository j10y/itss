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
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.hrm.StandSalary;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.kpi.HrPaKpipbc;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.hrm.HrPostAssessmentService;
import com.xpsoft.oa.service.hrm.HrPromApplyService;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;
import com.xpsoft.oa.service.hrm.JobService;
import com.xpsoft.oa.service.hrm.StandSalaryService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;
import com.xpsoft.oa.service.kpi.HrPaKpiitemService;
import com.xpsoft.oa.service.kpi.HrPaKpipbcService;
import com.xpsoft.oa.service.system.AppUserService;

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
		filter.addFilter("Q_publishStatus_N_NEQ", "1");
		filter.addFilter("Q_publishStatus_N_NEQ", "3");
		filter.addFilter("Q_publishStatus_N_NEQ", "4");
		filter.addFilter("Q_publishStatus_N_NEQ", "5");
		filter.addFilter("Q_publishStatus_N_NEQ", "6");
		filter.addFilter("Q_publishStatus_N_NEQ", "7");
		filter.addFilter("Q_createPerson.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		List<HrPromApply> list = this.hrPromApplyService.getAll(filter);
		
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
		filter.addFilter("Q_createPerson.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
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
		String sql = "select b.userId, b.username, a.fullname, a.jobId, a.position, a.depId, a.depName, a.accessionTime, a.startWorkDate from emp_profile a, app_user b where " +
				"a.userId = b.userId and a.depId = " + currentUser.getDepartment().getDepId();
		sql += (fullname == null || "".equals(fullname)) ? "" : " and (a.fullname like '%" + fullname + "%' or b.username like '%" + fullname + "%')";
		sql += " limit " + filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList = this.hrPromApplyService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < mapList.size(); i++) {
			Date startWorkDate = mapList.get(i).get("startWorkDate") == null ? new Date() : (Date)mapList.get(i).get("startWorkDate");
			Date accessionTime = (Date)mapList.get(i).get("accessionTime");
			Long workYear = (currentDate.getTime() - startWorkDate.getTime()) / 1000 / 60 / 60 / 24 / 365;
			Long workHereYear = (currentDate.getTime() - accessionTime.getTime()) / 1000 / 60 / 60 / 24 / 365;
			buff.append("{'userId':'" + mapList.get(i).get("userId"))
					.append("','fullname':'" + mapList.get(i).get("fullname") + "/" + mapList.get(i).get("username"))
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
		if(this.id != 0) {
			this.hrPromApply = this.hrPromApplyService.get(this.id);
		}
		return "show";
	}
	
	public String previewStatus() {
		if(this.id != 0) {
			this.hrPromApply = this.hrPromApplyService.get(this.id);
		}
		return "showStatus";
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
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		StandSalaryService standSalaryService = (StandSalaryService)AppUtil.getBean("standSalaryService");
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		HrPaKpipbcService hrPaKpipbcService = (HrPaKpipbcService)AppUtil.getBean("hrPaKpipbcService");
		HrPaKpiPBC2UserService hrPaKpiPBC2UserService = (HrPaKpiPBC2UserService)AppUtil.getBean("hrPaKpiPBC2UserService");
		HrPaKpiitemService hrPaKpiitemService = (HrPaKpiitemService)AppUtil.getBean("hrPaKpiitemService");
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		AppUserService appUserService = (AppUserService)AppUtil.getBean("appUserService");
		HrPromApply promApply = this.hrPromApplyService.get(this.id);
		Integer publishStatus = Integer.valueOf(getRequest().getParameter("publishStatus"));
		boolean isAssess = Boolean.valueOf(getRequest().getParameter("isAssess"));//评估阶段
		if(!isAssess){
			promApply.setPublishStatus(publishStatus);//申请阶段可以修改状态，或者指定节点可以修改
		}
		HrPromAssessment assessment = null;
		if(getRequest().getParameter("auditStep")!=null){
			String auditStep = getRequest().getParameter("auditStep");
			if(auditStep.equalsIgnoreCase("superAudit")){//上报审批
				promApply.setPostManagerId(ContextUtil.getCurrentUserId());
				promApply.setPostManagerName(ContextUtil.getCurrentUser().getFullname());
				promApply.setPostManagerAuditDate(new Date());
			}else if(auditStep.equalsIgnoreCase("setTarget")){//目标设定关于面谈
				//modify by guansq at 2011-10-10 begin
				/*
				 * 目标设定提前到提交申请表之前
				 * */
				//目标
				/*
				promApply.setTarget1(getRequest().getParameter("target1"));
				promApply.setTarget2(getRequest().getParameter("target2"));
				promApply.setTarget3(getRequest().getParameter("target3"));
				*/
				//modify by guansq at 2011-10-10 end
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
			}else if(auditStep.equalsIgnoreCase("promotionInterviews")){//晋升面谈
				assessment = this.hrPromAssessmentService.getByApplyId(promApply.getId());
				assessment.setAppointDate(DateUtil.parseDate(getRequest().getParameter("hrPromAssessment.actualPostDate")));
				assessment.setPromIntRecord(getRequest().getParameter("hrPromAssessment.promIntRecord"));				
				assessment.setPublishStatus(publishStatus);
				this.hrPromAssessmentService.save(assessment);
				//add by guansq at 2011-10-10 begin
				/*
				 * 更新档案表，为该用户增加个人PBC
				 * */
				if(publishStatus == 3) {
					//更新档案表
					Map<String, String> map = new HashMap<String, String>();
					map.put("Q_userId_L_EQ", assessment.getPromApply().getApplyUser().getUserId().toString());
					QueryFilter filter = new QueryFilter(map);
					List<EmpProfile> empProfileList = empProfileService.getAll(filter);
					if(empProfileList.size() > 0) {
						StandSalary standSalary = standSalaryService.get(assessment.getSalaryLevelId());
						if(standSalary != null) {
							empProfileList.get(0).setStandardMoney(standSalary.getTotalMoney());
							empProfileList.get(0).setStandardId(standSalary.getStandardId());
							empProfileList.get(0).setStandardName(standSalary.getStandardName());
							empProfileList.get(0).setPerCoefficient(standSalary.getPerCoefficient());
						} else {
							this.logger.error("ID为：" + assessment.getSalaryLevelId() + "的薪资标准不存在或已删除，请核实！");
						}
						empProfileList.get(0).setJobId(assessment.getPromApply().getApplyPositionId());
						empProfileList.get(0).setPosition(assessment.getPromApply().getApplyPositionName());
					}
					//为该用户增加个人PBC
					String sql = "select distinct a.id from hr_pa_kpipbc a, emp_profile b, app_user c where " +
							"a.belongPost = " + assessment.getPromApply().getApplyPositionId() + " and a.belongPost = b.jobId and " +
							"b.userId = c.userId and a.publishStatus = 3";
					List<Map<String, Object>> mapList = this.hrPromApplyService.findDataList(sql);
					AppUser user = appUserService.get(assessment.getPromApply().getApplyUser().getUserId());
					for(int i = 0; i < mapList.size(); i++) {
						HrPaKpipbc pbc = hrPaKpipbcService.get(Long.parseLong(mapList.get(i).get("id").toString()));
						//取得PBC的考核频度
						String sql2 = "select name from hr_pa_datadictionary where id = " + pbc.getFrequency().getId();
						List<Map<String, Object>> mapList2 = hrPaKpipbcService.findDataList(sql2);
						String frequencyName = mapList2.get(0).get("name").toString();
						//取出要插入PBC考核模板关联的考核项
						Map<String, String> map2 = new HashMap<String, String>();
						map2.put("Q_pbc.id_L_EQ", String.valueOf(pbc.getId()));
						QueryFilter filter2 = new QueryFilter(map2);
						List<HrPaKpiitem> hrPaKpiitemList = hrPaKpiitemService.getAll(filter2);
						//2.1.1. 保存个人考核模板基本信息
						HrPaKpiPBC2User hrPaKpiPBC2User = new HrPaKpiPBC2User();
						hrPaKpiPBC2User.setPbcName(user.getFullname() + "的" + frequencyName + "PBC");
						hrPaKpiPBC2User.setFromPBC(String.valueOf(pbc.getId()));
						hrPaKpiPBC2User.setBelongUser(user);
						hrPaKpiPBC2User.setFrequency(pbc.getFrequency());
						hrPaKpiPBC2User.setCreatePerson(pbc.getCreatePerson());
						hrPaKpiPBC2User.setCreateDate(currentDate);
						hrPaKpiPBC2User.setPublishStatus(0);//默认为草稿状态
						hrPaKpiPBC2User.setTotalScore(pbc.getTotalScore());
						hrPaKpiPBC2User.setModifyDate(currentDate);
						hrPaKpiPBC2User.setModifyPerson(currentUser);
						hrPaKpiPBC2User.setCoefficient(new Double(0));
						hrPaKpiPBC2User.setLineManager(pbc.getLineManager());
						//插入数据库
						hrPaKpiPBC2User = hrPaKpiPBC2UserService.save(hrPaKpiPBC2User);
						
						//2.1.2. 保存个人考核模板关联的考核项
						for(int j = 0; j < hrPaKpiitemList.size(); j++) {
							HrPaKpiitem2user hrPaKpiitem2user = new HrPaKpiitem2user();
							hrPaKpiitem2user.setPbc2User(hrPaKpiPBC2User);
							hrPaKpiitem2user.setPiId(hrPaKpiitemList.get(j).getPi().getId());
							hrPaKpiitem2user.setWeight(hrPaKpiitemList.get(j).getWeight());//直接将岗位PBC模板权值复制给个人
							hrPaKpiitem2user.setResult(new Double(0));//等待计算时设置结果
							hrPaKpiitem2user.setCoefficient(new Double(0));//等待计算时设置结果
							hrPaKpiitem2user.setRemark("");
							//插入数据库
							hrPaKpiitem2userService.save(hrPaKpiitem2user);
						}
					}
					//add by guansq at 2011-10-10 end
				}
			}else if(auditStep.equalsIgnoreCase("promotionPublish")){
				assessment = this.hrPromAssessmentService.getByApplyId(promApply.getId());
				Date date = null;
				date = DateUtil.parseDate(getRequest().getParameter("hrPromAssessment.appointDate"));
				assessment.setAppointDate(date);				
				assessment.setPublishStatus(publishStatus);
				this.hrPromAssessmentService.save(assessment);
			}
		}	
		this.hrPromApplyService.save(promApply);
		this.jsonString = "{success:true}";		
		return "success";
	}
	
}
