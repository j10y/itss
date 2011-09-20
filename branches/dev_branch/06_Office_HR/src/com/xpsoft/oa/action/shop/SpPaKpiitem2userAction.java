package com.xpsoft.oa.action.shop;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;
import com.xpsoft.oa.model.shop.SpPaKpiitem2user;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userService;

public class SpPaKpiitem2userAction extends BaseAction {
	@Resource
	private SpPaKpiitem2userService spPaKpiitem2userService;
	private SpPaKpiitem2user spPaKpiitem2user;
	private long id;
	
	public SpPaKpiitem2userService getSpPaKpiitem2userService() {
		return spPaKpiitem2userService;
	}
	public void setSpPaKpiitem2userService(
			SpPaKpiitem2userService spPaKpiitem2userService) {
		this.spPaKpiitem2userService = spPaKpiitem2userService;
	}
	public SpPaKpiitem2user getSpPaKpiitem2user() {
		return spPaKpiitem2user;
	}
	public void setSpPaKpiitem2user(SpPaKpiitem2user spPaKpiitem2user) {
		this.spPaKpiitem2user = spPaKpiitem2user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public String authorList() {
		//获取最新日期
		String sql3 = "select a.authDate from sp_pa_authorizepbc a order by a.authDate desc limit 1";
		List<Map<String, Object>> mapList3 = this.spPaKpiitem2userService.findDataList(sql3);
		String newDate = "";
		if(mapList3.size() > 0) {
			newDate = mapList3.get(0).get("authDate").toString().trim().split(" ")[0];
		}
		//取得该PBC关联的考核项对应的考核指标为定性考核的列表
		String sql = "select a.id, b.paName from sp_pa_kpiitem2user a, sp_pa_performanceindex b where " +
				"a.pbcId = " + this.getRequest().getParameter("pbcId") + " and a.piId = b.id and b.paMode = " + SpPaDatadictionary.PA_QUALITATIVE_ASSESSMENT;
		List<Map<String, Object>> list = this.spPaKpiitem2userService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < list.size(); i++) {
			//取得该考核项的授权信息
			String sql2 = "select c.fullname from sp_pa_authpbcitem a, sp_pa_authorizepbc b, emp_profile c, sp_pa_kpiitem2user d where " +
					"b.authDate >= '" + newDate + "' and d.id = " + list.get(i).get("id") + " and a.akpiItem2uId = d.id" +
							" and a.apbcId = b.id and b.userId = c.userId order by b.authDate";
			List<Map<String, Object>> mapList2 = this.spPaKpiitem2userService.findDataList(sql2);
			String desc = "";
			if(mapList2.size() <= 0) {
				desc = "该考核指标尚未授权给任何人！";
			} else {
				desc = "该考核指标已授权给：";
				for(int j = 0; j < mapList2.size(); j++) {
					desc += mapList2.get(j).get("fullname") + "，";
				}
				desc = desc.substring(0, desc.length() - 1);
			}
			buff.append("{'id':'" + list.get(i).get("id"))
					.append("','paName':'" + list.get(i).get("paName"))
					.append("','desc':'" + desc + "'},");
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
