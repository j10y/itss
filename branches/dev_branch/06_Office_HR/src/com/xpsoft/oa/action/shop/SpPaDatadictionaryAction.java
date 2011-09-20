package com.xpsoft.oa.action.shop;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaDatadictionary;
import com.xpsoft.oa.service.shop.SpPaDatadictionaryService;

public class SpPaDatadictionaryAction extends BaseAction{
	@Resource
	private SpPaDatadictionaryService spPaDatadictionaryService;
	private SpPaDatadictionary spPaDatadictionary;
	private long id;
	
	public SpPaDatadictionaryService getSpPaDatadictionaryService() {
		return spPaDatadictionaryService;
	}
	public void setSpPaDatadictionaryService(
			SpPaDatadictionaryService spPaDatadictionaryService) {
		this.spPaDatadictionaryService = spPaDatadictionaryService;
	}
	public SpPaDatadictionary getSpPaDatadictionary() {
		return spPaDatadictionary;
	}
	public void setSpPaDatadictionary(SpPaDatadictionary spPaDatadictionary) {
		this.spPaDatadictionary = spPaDatadictionary;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SpPaDatadictionary> list = this.spPaDatadictionaryService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(SpPaDatadictionary hpd : list) {
			buff.append("{id:'" + hpd.getId() + "',text:'" + hpd.getName() + "',leaf:true},");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String load(){
		long parentId = Long.parseLong(ServletActionContext.getRequest().getParameter("parentId"));
		Map<Long, String> map = this.spPaDatadictionaryService.getAllByParentId(parentId);
		
		StringBuffer buff = new StringBuffer("[");
		for(Map.Entry<Long, String> entry : map.entrySet()) {
			buff.append("['").append(entry.getKey()).append("',")
					.append("'").append(entry.getValue()).append("'],");
		}
		if(!map.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get(){
		return "success";
	}
	
	public String save(){
		this.spPaDatadictionaryService.save(this.spPaDatadictionary);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
