package com.xpsoft.oa.action.kpi;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;
import com.xpsoft.oa.service.kpi.HrPaKpiitemService;

import flexjson.JSONSerializer;

public class HrPaKpiitemAction extends BaseAction{
	@Resource
	private HrPaKpiitemService hrPaKpiitemService;
	private HrPaKpiitem hrPaKpiitem;
	private long id;
	
	public HrPaKpiitemService getHrPaKpiitemService() {
		return hrPaKpiitemService;
	}
	public void setHrPaKpiitemService(HrPaKpiitemService hrPaKpiitemService) {
		this.hrPaKpiitemService = hrPaKpiitemService;
	}
	public HrPaKpiitem getHrPaKpiitem() {
		return hrPaKpiitem;
	}
	public void setHrPaKpiitem(HrPaKpiitem hrPaKpiitem) {
		this.hrPaKpiitem = hrPaKpiitem;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaKpiitem> list = this.hrPaKpiitemService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get(){
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				if(!id.equals("0")) {
					this.hrPaKpiitemService.remove(new Long(id));
				}
			}
		}
		return "success";
	}
	
	public String save(){
		this.hrPaKpiitemService.save(this.hrPaKpiitem);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
