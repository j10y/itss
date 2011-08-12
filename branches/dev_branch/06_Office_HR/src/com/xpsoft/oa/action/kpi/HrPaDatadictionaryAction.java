package com.xpsoft.oa.action.kpi;

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
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.service.kpi.HrPaDatadictionaryService;

public class HrPaDatadictionaryAction extends BaseAction{
	@Resource
	private HrPaDatadictionaryService hrPaDatadictionaryService;
	private HrPaDatadictionary hrPaDatadictionary;
	private long id;
	
	public HrPaDatadictionaryService getHrPaDatadictionaryService() {
		return hrPaDatadictionaryService;
	}
	public void setHrPaDatadictionaryService(
			HrPaDatadictionaryService hrPaDatadictionaryService) {
		this.hrPaDatadictionaryService = hrPaDatadictionaryService;
	}
	public HrPaDatadictionary getHrPaDatadictionary() {
		return hrPaDatadictionary;
	}
	public void setHrPaDatadictionary(HrPaDatadictionary hrPaDatadictionary) {
		this.hrPaDatadictionary = hrPaDatadictionary;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		QueryFilter filter = new QueryFilter(getRequest());
		List<HrPaDatadictionary> list = this.hrPaDatadictionaryService.getAll(filter);
		
		Type type = new TypeToken<List<HrPaDatadictionary>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String load(){
		long parentId = Long.parseLong(ServletActionContext.getRequest().getParameter("parentId"));
		Map<Long, String> map = this.hrPaDatadictionaryService.getAllByParentId(parentId);
		
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
		this.hrPaDatadictionaryService.save(this.hrPaDatadictionary);
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
