package com.xpsoft.oa.action.kpi;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

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
		HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService=(HrPaKpiPBC2UserCmpService) AppUtil.getBean("hrPaKpiPBC2UserCmpService");
		AppUser currentUser = ContextUtil.getCurrentUser();
		//当前user所属部门
		String sql = "select depId from emp_profile where userId = " + currentUser.getUserId();
		List<Map<String, Object>> depIdList = this.hrPaKpiPBC2UserService.findDataList(sql);
		Long depId = Long.parseLong(depIdList.get(0).get("depId").toString());
		//判断是不是部门经理
		String chiefSql = "select deptUserId from arch_rec_user where depId = " + depId;
		List<Map<String, Object>> chiefList = this.hrPaKpiPBC2UserService.findDataList(chiefSql);
		if(currentUser.getUserId() != Long.parseLong(chiefList.get(0).get("deptUserId").toString())) {
			return "success";
		}
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
		List<Map<String, String>> list = hrPaKpiPBC2UserCmpService.isKpiItemScoreForUser(currentUser.getUserId().toString(), depId.toString());
		//取得定量考核未导入数据记录
		for(Map<String, String> map : list) {
			buff.append("{'pbcName':'" + map.get("pbcName") + "','paName':'")
					.append(map.get("paName") + "','desc':'" + map.get("desc") + "'},");
		}
		this.jsonString = buff.toString();
		this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String listResult() {
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
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
					.append("','totalScore':'" + list.get(i).get("totalScore").toString());
			String sql3 = "select b.paName, a.result, a.weight from hr_pa_kpiitem2user a, hr_pa_performanceindex b where " +
					"a.pbcId = " + list.get(i).get("id").toString() + " and a.piId = b.id";
			List<Map<String, Object>> list3 = hrPaKpiitem2userService.findDataList(sql3);
			String content = "<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">";
			content += "<tr><td>考核指标名称</td><td>得分</td><td>权重</td></tr>";
			for(int j = 0; j < list3.size(); j++) {
				content += "<tr><td>" + list3.get(j).get("paName") + "</td><td>" + list3.get(j).get("result") + "</td><td>" + 
						list3.get(j).get("weight") + "</td></tr>";
			}
			content +="</table>";
			buff.append("','content':'" + content);
			buff.append("'},");
		}
		this.jsonString = buff.toString();
		this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String listHistory() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		HrPaKpiitem2userService hrPaKpiitem2userService = (HrPaKpiitem2userService)AppUtil.getBean("hrPaKpiitem2userService");
		AppUser currentUser = ContextUtil.getCurrentUser();
		String start = filter.getPagingBean().getStart().toString();
		String limit = filter.getPagingBean().getPageSize().toString();
		String fullname = this.getRequest().getParameter("fullname");
		//当前user所属部门
		String sql = "select depId from emp_profile where userId = " + currentUser.getUserId();
		List<Map<String, Object>> depIdList = this.hrPaKpiPBC2UserService.findDataList(sql);
		Long depId = Long.parseLong(depIdList.get(0).get("depId").toString());
		//获取部门下所有员工的个人PBC总数
		String sql4 = "";
		if(fullname != null && !"".equals(fullname)) {
			sql4 = "select count(a.id) as total from hr_pa_kpipbc2usercmp a, emp_profile b where " +
			"a.belongUser = b.userId and b.depId = " + depId + " and b.fullname like '%" + fullname + "%'";
		} else {
			sql4 = "select count(a.id) as total from hr_pa_kpipbc2usercmp a, emp_profile b where " +
			"a.belongUser = b.userId and b.depId = " + depId;
		}
		
		List<Map<String, Object>> mapList4 = this.hrPaKpiPBC2UserService.findDataList(sql4);
		String totalCounts = mapList4.get(0).get("total").toString();
		//获取部门下所有员工的个人考核模板信息
		String sql2 = "";
		if(fullname != null && !"".equals(fullname)) {
			sql2 = "select a.id, a.pbcName, a.totalScore, b.fullname, a.createDate from hr_pa_kpipbc2usercmp a, emp_profile b where " +
			"a.belongUser = b.userId and b.depId = " + depId + " and b.fullname like '%" + fullname + "%' order by a.createDate desc limit " + start + ", " + limit;
		} else {
			sql2 = "select a.id, a.pbcName, a.totalScore, b.fullname, a.createDate from hr_pa_kpipbc2usercmp a, emp_profile b where " +
			"a.belongUser = b.userId and b.depId = " + depId + " order by a.createDate desc limit " + start + ", " + limit;
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + totalCounts + "',result:[");
		List<Map<String, Object>> list = this.hrPaKpiPBC2UserService.findDataList(sql2);
		for(int i = 0; i < list.size(); i++) {
			buff.append("{'fullname':'" + (String)list.get(i).get("fullname"))
					.append("','pbcName':'" + (String)list.get(i).get("pbcName"))
					.append("','totalScore':'" + list.get(i).get("totalScore").toString())
					.append("','createDate':'" + sdf.format(list.get(i).get("createDate")));
			String sql3 = "select b.paName, a.result, a.weight from hr_pa_kpiitem2usercmp a, hr_pa_performanceindex b where " +
					"a.pbcId = " + list.get(i).get("id").toString() + " and a.piId = b.id order by a.id";
			List<Map<String, Object>> list3 = hrPaKpiitem2userService.findDataList(sql3);
			String content = "<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">";
			content += "<tr><td>考核指标名称</td><td>得分</td><td>权重</td></tr>";
			for(int j = 0; j < list3.size(); j++) {
				content += "<tr><td>" + list3.get(j).get("paName") + "</td><td>" + list3.get(j).get("result") + "</td><td>" + 
						list3.get(j).get("weight") + "</td></tr>";
			}
			content +="</table>";
			buff.append("','content':'" + content);
			buff.append("'},");
		}
		this.jsonString = buff.toString();
		this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String saveToHist() {
		HrPaKpiPBC2UserCmpService hrPaKpiPBC2UserCmpService = (HrPaKpiPBC2UserCmpService)AppUtil.getBean("hrPaKpiPBC2UserCmpService");
		AppUser currentUser = ContextUtil.getCurrentUser();
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
		//移到历史表
		hrPaKpiPBC2UserCmpService.saveHrPaKpiPBC2UserCmp(pbc2UserIds);
		
		this.jsonString = "{success:true}";
		
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
