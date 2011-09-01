package com.xpsoft.oa.action.kpi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;

import flexjson.JSONSerializer;

public class HrPaKpiPBC2UserAction extends BaseAction {
	@Resource
	private HrPaKpiPBC2UserService hrPaKpiPBC2UserService;
	private HrPaKpiPBC2User hrPaKpiPBC2User;
	private long id;
	
	public HrPaKpiPBC2UserService getHrPaKpiPBC2UserService() {
		return hrPaKpiPBC2UserService;
	}
	public void setHrPaKpiPBC2UserService(
			HrPaKpiPBC2UserService hrPaKpiPBC2UserService) {
		this.hrPaKpiPBC2UserService = hrPaKpiPBC2UserService;
	}
	public HrPaKpiPBC2User getHrPaKpiPBC2User() {
		return hrPaKpiPBC2User;
	}
	public void setHrPaKpiPBC2User(HrPaKpiPBC2User hrPaKpiPBC2User) {
		this.hrPaKpiPBC2User = hrPaKpiPBC2User;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaKpiPBC2User> list = this.hrPaKpiPBC2UserService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String listUnFinished() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		//判断是不是部门经理
		//当前user所属部门
		String sql = "select depId from emp_profile where userId = " + currentUser.getUserId();
		List<Map<String, Object>> depIdList = this.hrPaKpiPBC2UserService.findDataList(sql);
		Long depId = Long.parseLong(depIdList.get(0).get("depId").toString());
		//取得所有未完成考核项ID
		List<String> authItemIdList = this.hrPaKpiPBC2UserService.getAllUnfinished(depId);
		//取得详细信息，并拼装成json格式
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < authItemIdList.size(); i++) {
			String sql2 = "select c.pbcName, e.paName from hr_pa_authpbcitem a, hr_pa_authorizepbc b, hr_pa_kpipbc2user c, " +
					"hr_pa_kpiitem2user d, hr_pa_performanceindex e where a.id = " + authItemIdList.get(i) + " and a.apbcId = b.id " +
							"and b.pbcId = c.id and a.akpiItem2uId = d.id and d.piId = e.id";
			List<Map<String, Object>> resultList = this.hrPaKpiPBC2UserService.findDataList(sql2);
			buff.append("{'pbcName':'" + (String)resultList.get(0).get("pbcName") + "','paName':'" + 
					(String)resultList.get(0).get("paName") + "','desc':'有被授权人未打分，请核实'},");
		}
		this.jsonString = buff.toString();
		this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String multiCal() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		//判断是不是部门经理
		//当前user所属部门
		String sql = "select depId from emp_profile where userId = " + currentUser.getUserId();
		List<Map<String, Object>> depIdList = this.hrPaKpiPBC2UserService.findDataList(sql);
		Long depId = Long.parseLong(depIdList.get(0).get("depId").toString());
		//获取部门下所有员工的个人考核模板ID
		String sql2 = "select a.id from hr_pa_kpipbc2user a, emp_profile b where b.depId = " + depId + " and a.belongUser = b.userId";
		List<Map<String, Object>> pbc2UserIdList = this.hrPaKpiPBC2UserService.findDataList(sql2);
		//将list拼装成字符串
		String pbc2UserIds = "";
		for(int i = 0; i < pbc2UserIdList.size() - 1; i++) {
			pbc2UserIds += pbc2UserIdList.get(i).get("id").toString() + ",";
		}
		pbc2UserIds += pbc2UserIdList.get(pbc2UserIdList.size() - 1).get("id").toString();
		//批量计算总分
		this.hrPaKpiPBC2UserService.multiCal(pbc2UserIds);
		
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String listResult() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		//当前user所属部门
		String sql = "select depId from emp_profile where userId = " + currentUser.getUserId();
		List<Map<String, Object>> depIdList = this.hrPaKpiPBC2UserService.findDataList(sql);
		Long depId = Long.parseLong(depIdList.get(0).get("depId").toString());
		//获取部门下所有员工的个人考核模板信息
		String sql2 = "select a.id, a.pbcName, a.totalScore, b.fullname from hr_pa_kpipbc2user a, emp_profile b where " +
				"a.belongUser = b.userId and b.depId = " + depId;
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		List<Map<String, Object>> list = this.hrPaKpiPBC2UserService.findDataList(sql2);
		for(int i = 0; i < list.size(); i++) {
			buff.append("{'fullname':'" + (String)list.get(i).get("fullname"))
					.append("','pbcName':'" + (String)list.get(i).get("pbcName"))
					.append("','totalScore':'" + list.get(i).get("totalScore").toString())
					.append("'},");
		}
		this.jsonString = buff.toString();
		this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String get() {
		return "success";
	}
	
	public String multiDel() {
		return "success";
	}
	
	public String save() {
		return "success";
	}
}
