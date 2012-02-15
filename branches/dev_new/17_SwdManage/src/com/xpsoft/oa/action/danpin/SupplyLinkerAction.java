package com.xpsoft.oa.action.danpin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.danpin.SupplyLinker;
import com.xpsoft.oa.service.danpin.SupplyLinkerService;

import flexjson.JSONSerializer;

public class SupplyLinkerAction extends BaseAction{
	private Long sid;
	private SupplyLinker supplyLinker;
	@Resource
	private SupplyLinkerService supplyLinkerService;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public SupplyLinker getSupplyLinker() {
		return supplyLinker;
	}
	public void setSupplyLinker(SupplyLinker supplyLinker) {
		this.supplyLinker = supplyLinker;
	}
	public SupplyLinkerService getSupplyLinkerService() {
		return supplyLinkerService;
	}
	public void setSupplyLinkerService(SupplyLinkerService supplyLinkerService) {
		this.supplyLinkerService = supplyLinkerService;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SupplyLinker> list = this.supplyLinkerService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.supplyLinker = this.supplyLinkerService.get(this.sid);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.supplyLinker));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		if(this.supplyLinker == null || this.supplyLinker.getSid() == null || this.supplyLinker.getSid().longValue() == 0l) {
			String sql = "select sid from supply_linker where rownum < 2 order by sid desc";
			List<Map<String, Object>> list = this.supplyLinkerService.findDataList(sql);
			Long newSid = Long.parseLong(list.get(0).get("sid").toString()) + 1;
			this.supplyLinker.setSid(newSid);
			String insertSql = "insert into supply_linker(sid, supply_info_sid, linker, linker_phone, email, status) " +
					"values(" + this.supplyLinker.getSid() + "," + this.supplyLinker.getSupplyInfoSid() + ",'" + this.supplyLinker.getLinker() + "','" + this.supplyLinker.getLinkerPhone() + "','" + this.supplyLinker.getEmail() + "',1)";
			this.supplyLinkerService.updateDatabySql(insertSql);
		} else {
			this.supplyLinkerService.save(this.supplyLinker);
		}
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] sids = this.getRequest().getParameterValues("sids");
		try {
			if(sids != null) {
				for(String sid : sids) {
					this.supplyLinkerService.remove(Long.parseLong(sid));
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
}
