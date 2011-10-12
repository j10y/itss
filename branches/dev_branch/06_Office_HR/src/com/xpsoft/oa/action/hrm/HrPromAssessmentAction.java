package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessInstance;

import antlr.StringUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.model.hrm.HrPromAssessment;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessFormService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.hrm.HrPromApplyService;
import com.xpsoft.oa.service.hrm.HrPromAssessmentService;
import com.xpsoft.oa.service.hrm.JobService;

import flexjson.JSONSerializer;

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
	
	public String listStatus() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_publishStatus_N_NEQ", "0");
		filter.addFilter("Q_publishStatus_N_NEQ", "2");
		filter.addFilter("Q_publishStatus_N_NEQ", "4");
		filter.addFilter("Q_createPerson.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		List<HrPromAssessment> list = this.hrPromAssessmentService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String listHist() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPromAssessment> list = this.hrPromAssessmentService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
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
		JobService jobService = (JobService)AppUtil.getBean("jobService");
		boolean isView = Boolean.valueOf(getRequest().getParameter("isView"));
		Long applyId = Long.valueOf(getRequest().getParameter("applyId"));
		this.hrPromAssessment = this.hrPromAssessmentService.saveViewByApplyId(applyId);
		Job job = jobService.get(this.hrPromAssessment.getPromApply().getNowPositionId());
		String bandName = job.getBand().getName();
		this.getRequest().setAttribute("bandName", bandName);
		if(isView){
			return "view";
		}
		return "show";
	}
	
	public String getAssessByApplyId() {
		Long applyId = Long.valueOf(getRequest().getParameter("applyId"));
		this.hrPromAssessment = this.hrPromAssessmentService.getByApplyId(applyId);
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(this.hrPromAssessment));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String previewStatus() {
		if(this.id != 0) {
			this.hrPromAssessment = this.hrPromAssessmentService.get(this.id);
		}
		
		return "showStatus";
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
		try {
			Date currentDate = new Date();
			AppUser currentUser = ContextUtil.getCurrentUser();
			this.hrPromAssessment.setModifyDate(currentDate);
			this.hrPromAssessment.setModifyPerson(currentUser);
			this.hrPromAssessmentService.save(this.hrPromAssessment);
			this.getRequest().setAttribute("flag", "1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.getRequest().setAttribute("flag", "0");
		}
		return "result";
	}
	
	public String getHist() {
		HrPromApplyService hrPromApplyService = (HrPromApplyService)AppUtil.getBean("hrPromApplyService");
		this.hrPromAssessment = this.hrPromAssessmentService.get(this.id);
		if(this.hrPromAssessment == null) {
			this.logger.error("ID为：" + this.id.toString() + "的晋升评估表不存在或已删除，请核实！");
		}
		return "viewHist";
	}
	
	public String processHist() {
		JbpmService jbpmService = (JbpmService)AppUtil.getBean("jbpmService");
		ProcessRunService processRunService = (ProcessRunService)AppUtil.getBean("processRunService");
		ProcessFormService processFormService = (ProcessFormService)AppUtil.getBean("processFormService");
		String applyId = this.getRequest().getParameter("applyId");
		String sql = "select runId from process_form where formId = " +
				"(select formId from form_data where fieldName = 'hrPromApply_id' and " +
				"longValue = " + applyId + " order by dataId desc limit 1)";
		List<Map<String, Object>> mapList = this.hrPromAssessmentService.findDataList(sql);
		if(mapList.size() > 0) {
			ProcessRun processRun = null;
			processRun = (ProcessRun)processRunService.get(Long.parseLong(mapList.get(0).get("runId").toString()));
			List pfList = processFormService.getByRunId(Long.parseLong(mapList.get(0).get("runId").toString()));
			getRequest().setAttribute("pfList", pfList);
		}
		
		return "processHist";
	}
	
}
