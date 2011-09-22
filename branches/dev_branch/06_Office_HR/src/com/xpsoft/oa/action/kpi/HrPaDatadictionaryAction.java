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
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.service.kpi.HrPaDatadictionaryService;

import flexjson.JSONSerializer;

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
	
	/*
	public String list(){
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaDatadictionary> list = this.hrPaDatadictionaryService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(HrPaDatadictionary hpd : list) {
			buff.append("{id:'" + hpd.getId() + "',text:'" + hpd.getName() + "',leaf:true},");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		
		this.jsonString = buff.toString();
		
		return "success";
	}
	*/
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		String sql = "select id, name from hr_pa_datadictionary where parentId = 1 and id != 1";
		List<Map<String, Object>> mapList = this.hrPaDatadictionaryService.findDataList(sql);
		String name = this.getRequest().getParameter("itemName");
		String parentIds = "";
		for(int i = 0; i < mapList.size(); i++) {
			parentIds += mapList.get(i).get("id") + ",";
		}
		if(mapList.size() > 0) {
			parentIds = parentIds.substring(0, parentIds.length() - 1);
		}
		String sql4 = "select count(*) as total from hr_pa_datadictionary where parentId in (" + parentIds + ")";
		sql4 += (name == null || "".equals(name)) ? "" : " and name like '%" + name + "%'";
		List<Map<String, Object>> mapList4 = this.hrPaDatadictionaryService.findDataList(sql4);
		String sql2 = "select id, name, parentId from hr_pa_datadictionary where parentId in (" + parentIds + ")";
		sql2 += (name == null || "".equals(name)) ? "" : " and name like '%" + name + "%'";
		sql2 += " limit " + filter.getPagingBean().getFirstResult() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList2 = this.hrPaDatadictionaryService.findDataList(sql2);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':'" + mapList4.get(0).get("total") + "',result:[");
		for(int j = 0; j < mapList2.size(); j++) {
			String sql3 = "select id, name from hr_pa_datadictionary where id = " + mapList2.get(j).get("parentId");
			List<Map<String, Object>> mapList3 = this.hrPaDatadictionaryService.findDataList(sql3);
			buff.append("{'id':'" + mapList2.get(j).get("id"))
					.append("','parentName':'" + mapList3.get(0).get("name"))
					.append("','name':'" + mapList2.get(j).get("name"))
					.append("'},");
		}
		this.jsonString = buff.toString();
		if(mapList2.size() > 0) {
			this.jsonString = this.jsonString.substring(0, this.jsonString.length() - 1);
		}
		this.jsonString += "]}";
		
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
	
	public String loadEntry(){
		String sql = "select id, name from hr_pa_datadictionary where parentId = 1 and id not in (1, 4)";
		List<Map<String, Object>> mapList = this.hrPaDatadictionaryService.findDataList(sql);
		StringBuffer buff = new StringBuffer("[");
		for(int i = 0; i < mapList.size(); i++) {
			buff.append("['" + mapList.get(i).get("id") + "','" + mapList.get(i).get("name") + "'],");
		}
		if(mapList.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get(){
		if(this.id != 0) {
			this.hrPaDatadictionary = this.hrPaDatadictionaryService.get(this.id);
			String sql = "select id, name from hr_pa_datadictionary where id = " + this.hrPaDatadictionary.getParentId();
			List<Map<String, Object>> mapList = this.hrPaDatadictionaryService.findDataList(sql);
			JSONSerializer json = new JSONSerializer();
			StringBuffer buff = new StringBuffer("{success:true,'parentName':'" + mapList.get(0).get("name") + "',data:");
			buff.append(json.exclude(new String[] {}).serialize(this.hrPaDatadictionary));
			buff.append("}");
			this.jsonString = buff.toString();
		}
		return "success";
	}
	
	public String save(){
		this.hrPaDatadictionaryService.save(this.hrPaDatadictionary);
		this.jsonString = new String("{success:true}");
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					HrPaDatadictionary dict = this.hrPaDatadictionaryService.get(Long.parseLong(id));
					dict.setParentId(null);
					this.hrPaDatadictionaryService.save(dict);
				}
			}
			this.jsonString = "{success:true,'flag':'1'}";
		} catch(Exception e) {
			System.out.println("删除失败！");
			this.jsonString = "{success:true,'flag':'0'}";
			e.printStackTrace();
		}
		
		return "success";
	}
}
