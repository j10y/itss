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
import com.xpsoft.oa.model.hrm.HrPromApply;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.hrm.HrPromApplyService;
import com.xpsoft.oa.service.hrm.JobService;

import flexjson.JSONSerializer;

public class HrPromApplyAction extends BaseAction{
	private HrPromApply hrPromApply;
	@Resource
	private HrPromApplyService hrPromApplyService;
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
	
	public String sameDeptUser() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		AppUser currentUser = ContextUtil.getCurrentUser();
		Date currentDate = new Date();
		String fullname = this.getRequest().getParameter("fullname");
		String sql = "select userId, fullname, position, depName, accessionTime, startWorkDate from emp_profile where " +
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
					.append("','position':'" + mapList.get(i).get("position"))
					.append("','depName':'" + mapList.get(i).get("depName"))
					.append("','accessionTime':'" + DateUtil.formatStringToDate((Date)mapList.get(i).get("accessionTime")))
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
	
	public String preview() {
		Date currentDate = new Date();
		EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
		if(this.id != 0) {
			this.hrPromApply = this.hrPromApplyService.get(this.id);
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_userId_L_EQ", this.hrPromApply.getApplyUser().getUserId().toString());
			QueryFilter filter = new QueryFilter(map);
			List<EmpProfile> list = empProfileService.getAll(filter);
			if(list.size() > 0) {
				Date startWorkDate = list.get(0).getStartWorkDate();
				Date accessionTime = list.get(0).getAccessionTime();
				Long workYear = (currentDate.getTime() - startWorkDate.getTime()) / 1000 / 60 / 60 / 24 / 365;
				Long workHereYear = (currentDate.getTime() - accessionTime.getTime()) / 1000 / 60 / 60 / 24 / 365;
				this.getRequest().setAttribute("position", list.get(0).getPosition());
				this.getRequest().setAttribute("depName", list.get(0).getDepName());
				this.getRequest().setAttribute("accessionTime", list.get(0).getAccessionTime());
				this.getRequest().setAttribute("workYear", workYear);
				this.getRequest().setAttribute("workHereYear", workHereYear);
			}
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
			promApply.setApplyUser(this.hrPromApply.getApplyUser());
			promApply.setApplyPosition(this.hrPromApply.getApplyPosition());
			promApply.setApplyReason(this.hrPromApply.getApplyReason());
			promApply.setApplyDate(this.hrPromApply.getApplyDate());
			promApply.setTarget1(this.hrPromApply.getTarget1());
			promApply.setTarget2(this.hrPromApply.getTarget2());
			promApply.setTarget3(this.hrPromApply.getTarget3());
			promApply.setIntRecord(this.hrPromApply.getIntRecord());
			promApply.setPostManagerId(currentUser.getUserId());
			promApply.setPostManagerName(currentUser.getFullname());
			promApply.setPublishStatus(0);
			this.hrPromApplyService.save(promApply);
			this.getRequest().setAttribute("flag", "1");
		} catch(Exception e) {
			this.getRequest().setAttribute("flag", "0");
			e.printStackTrace();
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
	
}
