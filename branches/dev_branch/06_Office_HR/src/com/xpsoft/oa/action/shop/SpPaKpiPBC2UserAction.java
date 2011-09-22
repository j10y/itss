package com.xpsoft.oa.action.shop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2User;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserService;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userService;

import flexjson.JSONSerializer;

public class SpPaKpiPBC2UserAction extends BaseAction {
	@Resource
	private SpPaKpiPBC2UserService spPaKpiPBC2UserService;
	private SpPaKpiPBC2User spPaKpiPBC2User;
	private long id;
	
	public SpPaKpiPBC2UserService getSpPaKpiPBC2UserService() {
		return spPaKpiPBC2UserService;
	}
	public void setSpPaKpiPBC2UserService(
			SpPaKpiPBC2UserService spPaKpiPBC2UserService) {
		this.spPaKpiPBC2UserService = spPaKpiPBC2UserService;
	}
	public SpPaKpiPBC2User getSpPaKpiPBC2User() {
		return spPaKpiPBC2User;
	}
	public void setSpPaKpiPBC2User(SpPaKpiPBC2User spPaKpiPBC2User) {
		this.spPaKpiPBC2User = spPaKpiPBC2User;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		AppUser currentUser = ContextUtil.getCurrentUser();
		QueryFilter filter = new QueryFilter(this.getRequest());
		//获取最新日期
		String sql5 = "select a.createDate from sp_pa_kpipbc2user a order by a.createDate desc limit 1";
		List<Map<String, Object>> mapList5 = this.spPaKpiPBC2UserService.findDataList(sql5);
		String newDate = "";
		if(mapList5.size() > 0) {
			newDate = mapList5.get(0).get("createDate").toString().trim().split(" ")[0];
		}
		//判断当前用户是不是部门负责人
		String sql1 = "select depId from arch_rec_user where deptUserId = " + currentUser.getUserId();
		List<Map<String, Object>> mapList1 = this.spPaKpiPBC2UserService.findDataList(sql1);
		if(mapList1.size() == 0) {
			return "success";
		}
		String depIds = "";
		for(int m = 0; m < mapList1.size() - 1; m++) {
			depIds += mapList1.get(m).get("depId").toString() + ",";
		}
		depIds += mapList1.get(mapList1.size() - 1).get("depId").toString();
		String fullname = this.getRequest().getParameter("fullname");
		String sql4 = "";
		if(fullname != null && !"".equals(fullname)) {
			sql4 = "select count(a.id) as total from sp_pa_kpipbc2user a, emp_profile b where " +
					"a.createDate >= '" + newDate + "' and a.publishStatus in (0, 2) and a.belongUser = b.userId and b.depId in (" + depIds + ") and b.fullname like '%" + fullname + "%'";
		} else {
			sql4 = "select count(a.id) as total from sp_pa_kpipbc2user a, emp_profile b where " +
					"a.createDate >= '" + newDate + "' and a.publishStatus in (0, 2) and a.belongUser = b.userId and b.depId in (" + depIds + ") ";
		}
		List<Map<String, Object>> mapList4 = this.spPaKpiPBC2UserService.findDataList(sql4);
		String sql2 = "";
		if(fullname != null && !"".equals(fullname)) {
			sql2 = "select a.id, b.fullname, b.position, a.pbcName from sp_pa_kpipbc2user a, emp_profile b where " +
					"a.createDate >='" + newDate + "' and a.publishStatus in (0, 2) and a.belongUser = b.userId and b.depId in (" + depIds + ") and b.fullname like '%" + fullname + "%' limit " + 
					filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		} else {
			sql2 = "select a.id, b.fullname, b.position, a.pbcName from sp_pa_kpipbc2user a, emp_profile b where " +
					"a.createDate >= '" + newDate + "' and a.publishStatus in (0, 2) and a.belongUser = b.userId and b.depId in (" + depIds + ") limit " +
					filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList4.get(0).get("total") + "',result:[");
		List<Map<String, Object>> mapList2 = this.spPaKpiPBC2UserService.findDataList(sql2);
		for(int j = 0; j < mapList2.size(); j++) {
			buff.append("{'id':'").append(mapList2.get(j).get("id").toString())
					.append("','fullname':'").append(mapList2.get(j).get("fullname").toString())
					.append("','position':'").append(mapList2.get(j).get("position"))
					.append("','pbcName':'").append(mapList2.get(j).get("pbcName")).append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList2.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String listResult() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		SpPaKpiitem2userService spPaKpiitem2userService = (SpPaKpiitem2userService)AppUtil.getBean("spPaKpiitem2userService");
		AppUser currentUser = ContextUtil.getCurrentUser();
		
		String sql4 = "select count(a.id) as total from sp_pa_kpipbc2user a, emp_profile b where " +
		"a.belongUser = b.userId and publishStatus = 1";
		List<Map<String, Object>> mapList4 = this.spPaKpiPBC2UserService.findDataList(sql4);
		//获取部门下所有员工处于审核状态的个人PBC信息
		String sql2 = "select a.id, a.pbcName, a.totalScore, b.fullname from sp_pa_kpipbc2user a, emp_profile b where " +
				"a.belongUser = b.userId and b.depId and publishStatus = 3 order by a.modifyDate desc, a.id limit " + 
				filter.getPagingBean().getStart() + ", " + filter.getPagingBean().getPageSize();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList4.get(0).get("total") + "',result:[");
		List<Map<String, Object>> list = this.spPaKpiPBC2UserService.findDataList(sql2);
		for(int i = 0; i < list.size(); i++) {
			buff.append("{'fullname':'" + (String)list.get(i).get("fullname"))
					.append("','pbcName':'" + (String)list.get(i).get("pbcName"))
					.append("','totalScore':'" + list.get(i).get("totalScore").toString());
			String sql3 = "select b.paName, a.result, a.weight from sp_pa_kpiitem2user a, sp_pa_performanceindex b where " +
					"a.pbcId = " + list.get(i).get("id").toString() + " and a.piId = b.id";
			List<Map<String, Object>> list3 = spPaKpiitem2userService.findDataList(sql3);
			String content = "<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">";
			content += "<tr><td>考核指标名称</td><td>得分</td></tr>";
			for(int j = 0; j < list3.size(); j++) {
				content += "<tr><td>" + list3.get(j).get("paName") + "</td><td>" + list3.get(j).get("result") + "</td></tr>";
			}
			content +="</table>";
			buff.append("','content':'" + content);
			buff.append("'},");
		}
		this.jsonString = buff.toString();
		if(list.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String listHistory() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SpPaKpiitem2userService spPaKpiitem2userService = (SpPaKpiitem2userService)AppUtil.getBean("spPaKpiitem2userService");
		AppUser currentUser = ContextUtil.getCurrentUser();
		String start = filter.getPagingBean().getStart().toString();
		String limit = filter.getPagingBean().getPageSize().toString();
		String fullname = this.getRequest().getParameter("fullname");
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		//判断当前用户是不是部门负责人
		String sql1 = "select depId from arch_rec_user where deptUserId = " + currentUser.getUserId();
		List<Map<String, Object>> mapList1 = this.spPaKpiPBC2UserService.findDataList(sql1);
		if(mapList1.size() == 0) {
			return "success";
		}
		String depIds = "";
		for(int m = 0; m < mapList1.size() - 1; m++) {
			depIds += mapList1.get(m).get("depId").toString() + ",";
		}
		depIds += mapList1.get(mapList1.size() - 1).get("depId").toString();
		//获取部门下所有员工的个人PBC总数
		String sql4 = "select count(a.id) as total from sp_pa_kpipbc2usercmp a, emp_profile b where " +
				"a.belongUser = b.userId and b.depId in (" + depIds + ") ";
		sql4 += (fullname == null || "".equals(fullname)) ? "" : " and b.fullname like '%" + fullname + "%'";
		sql4 += (startDate == null || "".equals(startDate)) ? "" : " and a.createDate >= '" + startDate + "'";
		sql4 += (endDate == null || "".equals(endDate)) ? "" : " and a.createDate <= '" + endDate + " 23:59'";
		List<Map<String, Object>> mapList4 = this.spPaKpiPBC2UserService.findDataList(sql4);
		String totalCounts = mapList4.get(0).get("total").toString();
		//获取部门下所有员工的个人考核模板信息
		String sql2 = "select a.id, a.pbcName, a.totalScore, b.fullname, a.createDate from sp_pa_kpipbc2usercmp a, emp_profile b where " +
		"a.belongUser = b.userId and b.depId in (" + depIds + ") ";
		sql2 += (fullname == null || "".equals(fullname)) ? "" : " and b.fullname like '%" + fullname + "%'";
		sql2 += (startDate == null || "".equals(startDate)) ? "" : " and a.createDate >= '" + startDate + "'";
		sql2 += (endDate == null || "".equals(endDate)) ? "" : " and a.createDate <= '" + endDate + " 23:59'";
		sql2 += " order by a.createDate desc, a.id desc limit " + start + ", " + limit;
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + totalCounts + "',result:[");
		List<Map<String, Object>> list = this.spPaKpiPBC2UserService.findDataList(sql2);
		for(int i = 0; i < list.size(); i++) {
			buff.append("{'fullname':'" + (String)list.get(i).get("fullname"))
					.append("','pbcName':'" + (String)list.get(i).get("pbcName"))
					.append("','totalScore':'" + list.get(i).get("totalScore").toString())
					.append("','createDate':'" + sdf.format(list.get(i).get("createDate")));
			String sql3 = "select b.paName, a.result, a.weight from sp_pa_kpiitem2usercmp a, sp_pa_performanceindex b where " +
					"a.pbcId = " + list.get(i).get("id").toString() + " and a.piId = b.id order by a.id";
			List<Map<String, Object>> list3 = spPaKpiitem2userService.findDataList(sql3);
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
		if(list.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
		return "success";
	}
	
	public String submitToAudit() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		SpPaKpiPBC2UserCmpService spPaKpiPBC2UserCmpService = (SpPaKpiPBC2UserCmpService)AppUtil.getBean("spPaKpiPBC2UserCmpService");
		//取得PBC
		String pbcId = this.getRequest().getParameter("pbcId");
		this.spPaKpiPBC2User = this.spPaKpiPBC2UserService.get(Long.parseLong(pbcId));
		//发起审核，状态置为审核中
		this.spPaKpiPBC2User.setPublishStatus(1);
		this.spPaKpiPBC2User.setModifyDate(currentDate);
		this.spPaKpiPBC2User.setModifyPerson(currentUser);
		//插入数据库
		this.spPaKpiPBC2UserService.save(this.spPaKpiPBC2User);
		
		//移动到完成表
		//spPaKpiPBC2UserCmpService.saveSpPaKpiPBC2UserCmp(pbcId);
		
		this.getRequest().setAttribute("flag", "2");
		
		return "gradeResult";
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
