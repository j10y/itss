package com.xpsoft.oa.action.kpi;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

public class HrPaKpiitem2userAction extends BaseAction {
	@Resource
	private HrPaKpiitem2userService hrPaKpiitem2userService;
	private HrPaKpiitem2user hrPaKpiitem2user;
	private long id;
	
	public HrPaKpiitem2userService getHrPaKpiitem2userService() {
		return hrPaKpiitem2userService;
	}
	public void setHrPaKpiitem2userService(
			HrPaKpiitem2userService hrPaKpiitem2userService) {
		this.hrPaKpiitem2userService = hrPaKpiitem2userService;
	}
	public HrPaKpiitem2user getHrPaKpiitem2user() {
		return hrPaKpiitem2user;
	}
	public void setHrPaKpiitem2user(HrPaKpiitem2user hrPaKpiitem2user) {
		this.hrPaKpiitem2user = hrPaKpiitem2user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public String authorList() {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###%");
		//取得该PBC关联的考核项对应的考核指标为定性考核的列表
		String sql = "select a.id, b.paName from hr_pa_kpiitem2user a, hr_pa_performanceindex b where " +
				"a.pbcId = " + this.getRequest().getParameter("pbcId") + " and a.piId = b.id and b.paMode = 12";
		List<Map<String, Object>> list = this.hrPaKpiitem2userService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		for(int i = 0; i < list.size(); i++) {
			//取得该考核项的授权信息
			String sql2 = "select a.weight, c.fullname from hr_pa_authpbcitem a, hr_pa_authorizepbc b, emp_profile c, hr_pa_kpiitem2user d where " +
					"d.id = " + list.get(i).get("id") + " and a.akpiItem2uId = d.id" +
							" and a.apbcId = b.id and b.userId = c.userId order by b.authDate";
			List<Map<String, Object>> mapList2 = this.hrPaKpiitem2userService.findDataList(sql2);
			String desc = "";
			if(mapList2.size() <= 0) {
				desc = "该考核指标尚未授权给任何人！";
			} else {
				desc = "该考核指标已授权给：";
				for(int j = 0; j < mapList2.size(); j++) {
					desc += mapList2.get(j).get("fullname") + " " + myformat.format(Double.parseDouble(mapList2.get(j).get("weight").toString())) + "，";
				}
				desc = desc.substring(0, desc.length() - 1);
			}
			buff.append("{'id':'" + list.get(i).get("id"))
					.append("','paName':'" + list.get(i).get("paName"))
					.append("','desc':'" + desc + "'},");
		}
		this.jsonString = buff.toString();
		if(list.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
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
