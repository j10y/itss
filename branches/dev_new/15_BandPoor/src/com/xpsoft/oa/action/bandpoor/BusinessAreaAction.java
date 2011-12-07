package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.BusinessArea;
import com.xpsoft.oa.service.bandpoor.BandStyleService;
import com.xpsoft.oa.service.bandpoor.BusinessAreaService;

import flexjson.JSONSerializer;

public class BusinessAreaAction extends BaseAction{
	@Resource
	private BusinessAreaService businessAreaService;
	private BusinessArea businessArea;
	public BusinessAreaService getBusinessAreaService() {
		return businessAreaService;
	}

	public void setBusinessAreaService(BusinessAreaService businessAreaService) {
		this.businessAreaService = businessAreaService;
	}

	public BusinessArea getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(BusinessArea businessArea) {
		this.businessArea = businessArea;
	}
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BusinessArea> list = this.businessAreaService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.businessArea = this.businessAreaService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.businessArea));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		this.businessArea.setFlag(BandStyle.CREATE);
		this.businessAreaService.save(this.businessArea);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					BusinessArea b = this.businessAreaService.get(Long.parseLong(id));
					b.setFlag(BusinessArea.DELETE);//置为已删除状态
					this.businessAreaService.save(b);
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
