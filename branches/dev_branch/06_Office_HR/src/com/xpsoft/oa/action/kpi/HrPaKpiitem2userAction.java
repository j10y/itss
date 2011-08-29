package com.xpsoft.oa.action.kpi;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;

import flexjson.JSONSerializer;

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
	
	public String authorList() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaKpiitem2user> list = this.hrPaKpiitem2userService.getAll(filter);
		//移除定量考核指标
		for(int i = 0; i < list.size(); i++) {
			String sql = "select paMode from hr_pa_performanceindex where id = " + list.get(i).getPiId();
			List<Map<String, Object>> list2 = this.hrPaKpiitem2userService.findDataList(sql);
			if("13".equals(list2.get(0).get("paMode").toString())) {
				list.remove(i);
			}
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
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
