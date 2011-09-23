package com.xpsoft.oa.action.hrm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.hrm.HrPostApply;
import com.xpsoft.oa.model.hrm.HrPostAssessment;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.hrm.HrPostApplyService;

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
						"','deptName':'" +postApplyNew.getDeptName() + 
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
		HrPostApply postApply = this.hrPostApplyService.get(this.id);
		Integer publishStatus = Integer.valueOf(getRequest().getParameter("publishStatus"));
		boolean isAssess = Boolean.valueOf(getRequest().getParameter("isAssess"));//评估阶段
		if(!isAssess){
			postApply.setPublishStatus(publishStatus);//申请阶段可以修改状态，或者指定节点可以修改
		}
		HrPostAssessment assessment = null;
		if(getRequest().getParameter("auditStep")!=null){
			String auditStep = getRequest().getParameter("auditStep");
			if(auditStep.equalsIgnoreCase("LineManagerAudit")){//直线经理审核
				postApply.setPostManagerId(ContextUtil.getCurrentUserId());
				postApply.setPostManagerName(ContextUtil.getCurrentUser().getFullname());
				postApply.setPostManagerAuditDate(new Date());
			}else if(auditStep.equalsIgnoreCase("HRConfirmAudit")){//人力资源复核
				
			}else if(auditStep.equalsIgnoreCase("VicePresidentConfirm")){//分管副总裁确认
				
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