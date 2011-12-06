package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.service.bandpoor.ProClassService;

import flexjson.JSONSerializer;

public class ProClassAction extends BaseAction {
	@Resource
	ProClassService proClassService;
	ProClass proClass;
	Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<ProClass> list = this.proClassService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.proClass = this.proClassService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.proClass));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		/*
		this.proClass = new ProClass();
		if(this.getRequest().getParameter("proClass.id") != null && !"".equals(this.getRequest().getParameter("proClass.id"))) {
			this.proClass.setId(Long.parseLong(this.getRequest().getParameter("proClass.id")));
		}
		this.proClass.setProClassNum(this.getRequest().getParameter("proClass.proClassNum"));
		this.proClass.setProClassName(this.getRequest().getParameter("proClass.proClassName"));
		this.proClass.setFlag(1);
		*/
		this.proClass.setFlag(ProClass.CREATE);
		this.proClassService.save(this.proClass);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					ProClass b = this.proClassService.get(Long.parseLong(id));
					b.setFlag(ProClass.DELETE);//置为已删除状态
					this.proClassService.save(b);
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public String combo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<ProClass> list = this.proClassService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(ProClass pc : list) {
			buff.append("[" +
					"'" + pc.getId() + "'," +
					"'" + pc.getProClassName() + "'" +
					"],");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public ProClassService getProClassService() {
		return proClassService;
	}
	public void setProClassService(ProClassService proClassService) {
		this.proClassService = proClassService;
	}
	public ProClass getProClass() {
		return proClass;
	}
	public void setProClass(ProClass proClass) {
		this.proClass = proClass;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
