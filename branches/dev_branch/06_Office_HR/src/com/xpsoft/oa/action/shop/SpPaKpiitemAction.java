package com.xpsoft.oa.action.shop;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaKpiitem;
import com.xpsoft.oa.service.shop.SpPaKpiitemService;

import flexjson.JSONSerializer;

public class SpPaKpiitemAction extends BaseAction{
	@Resource
	private SpPaKpiitemService spPaKpiitemService;
	private SpPaKpiitem spPaKpiitem;
	private long id;
	
	public SpPaKpiitemService getSpPaKpiitemService() {
		return spPaKpiitemService;
	}
	public void setSpPaKpiitemService(SpPaKpiitemService spPaKpiitemService) {
		this.spPaKpiitemService = spPaKpiitemService;
	}
	public SpPaKpiitem getSpPaKpiitem() {
		return spPaKpiitem;
	}
	public void setSpPaKpiitem(SpPaKpiitem spPaKpiitem) {
		this.spPaKpiitem = spPaKpiitem;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SpPaKpiitem> list = this.spPaKpiitemService.getAll(filter);
		//将权重转换成整数值
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setWeight(list.get(i).getWeight()*100);
		}
		
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
					this.spPaKpiitemService.remove(new Long(id));
				}
			}
		}
		return "success";
	}
	
	public String save(){
		this.spPaKpiitemService.save(this.spPaKpiitem);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
