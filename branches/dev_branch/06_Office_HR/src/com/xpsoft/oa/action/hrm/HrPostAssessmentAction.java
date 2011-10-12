package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessFormService;
import com.xpsoft.oa.service.flow.ProcessRunService;
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
		AppUser currentUser = ContextUtil.getCurrentUser();
		String sql = "select distinct a.id, c.userId, c.fullname, b.postId, b.postName, b.accessionTime, a.publishStatus from " +
				"hr_post_assessment a, hr_post_apply b, app_user c where a.publishStatus not in (0, 2, 4) " +
				"and a.applyId = b.id and b.applyUser = c.userId and c.userId = " + currentUser.getUserId();
		List<Map<String, Object>> mapList = this.hrPostAssessmentService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < mapList.size(); i++) {
			buff.append("{'id':'" + mapList.get(i).get("id"))
					.append("','userId':'" + mapList.get(i).get("userId"))
					.append("','fullname':'" + mapList.get(i).get("fullname"))
					.append("','postId':'" + mapList.get(i).get("postId"))
					.append("','postName':'" + mapList.get(i).get("postName"))
					.append("','accessionTime':'" + mapList.get(i).get("accessionTime"))
					.append("','publishStatus':'" + mapList.get(i).get("publishStatus"))
					.append("'},");
		}
		if(mapList.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String listHist() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		String fullname = this.getRequest().getParameter("fullname");
		String sql2 = "select count(a.id) as total from hr_post_assessment a, hr_post_apply b, app_user c where " +
				"a.applyId = b.id and b.applyUser = c.userId and a.publishStatus = 3";
		sql2 += (fullname == null || "".equals(fullname)) ? "" : " and c.fullname like '%" + fullname + "%'";
		List<Map<String, Object>> mapList2 = this.hrPostAssessmentService.findDataList(sql2);
		String sql = "select distinct a.id, b.id as applyId, c.userId, c.fullname, b.postId, b.postName, b.accessionTime, a.publishStatus from " +
				"hr_post_assessment a, hr_post_apply b, app_user c where " +
				"a.applyId = b.id and b.applyUser = c.userId and a.publishStatus = 3";
		sql += (fullname == null || "".equals(fullname)) ? "" : " and c.fullname like '%" + fullname + "%'";
		sql += " limit " + filter.getPagingBean().getFirstResult() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList = this.hrPostAssessmentService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList2.get(0).get("total") + "',result:[");
		for(int i = 0; i < mapList.size(); i++) {
			buff.append("{'id':'" + mapList.get(i).get("id"))
					.append("','applyId':'" + mapList.get(i).get("applyId"))
					.append("','userId':'" + mapList.get(i).get("userId"))
					.append("','fullname':'" + mapList.get(i).get("fullname"))
					.append("','postId':'" + mapList.get(i).get("postId"))
					.append("','postName':'" + mapList.get(i).get("postName"))
					.append("','accessionTime':'" + mapList.get(i).get("accessionTime"))
					.append("','publishStatus':'" + mapList.get(i).get("publishStatus"))
					.append("'},");
		}
		if(mapList.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
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
		JobService jobService = (JobService)AppUtil.getBean("jobService");
		boolean isView = Boolean.valueOf(this.getRequest().getParameter("isView"));
		Long applyId = Long.valueOf(this.getRequest().getParameter("applyId"));
		this.hrPostAssessment = this.hrPostAssessmentService.saveViewByApplyId(applyId);
		Job job = jobService.get(this.hrPostAssessment.getPostApply().getPostId());
		String bandName = job.getBand().getName();
		this.getRequest().setAttribute("bandName", bandName);
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
				"','hrPostAssessmentId':'" + this.hrPostAssessment.getId() + 
				"'}";
		return "success";
	}
	
	public String processHist() {
		JbpmService jbpmService = (JbpmService)AppUtil.getBean("jbpmService");
		ProcessRunService processRunService = (ProcessRunService)AppUtil.getBean("processRunService");
		ProcessFormService processFormService = (ProcessFormService)AppUtil.getBean("processFormService");
		String applyId = this.getRequest().getParameter("applyId");
		String sql = "select runId from process_form where formId = " +
				"(select formId from form_data where fieldName = 'hrPostApply_id' and " +
				"longValue = " + applyId + " order by dataId desc limit 1)";
		List<Map<String, Object>> mapList = this.hrPostAssessmentService.findDataList(sql);
		if(mapList.size() > 0) {
			ProcessRun processRun = null;
			processRun = (ProcessRun)processRunService.get(Long.parseLong(mapList.get(0).get("runId").toString()));
			List pfList = processFormService.getByRunId(Long.parseLong(mapList.get(0).get("runId").toString()));
			getRequest().setAttribute("pfList", pfList);
		}
		
		return "processHist";
	}
}
