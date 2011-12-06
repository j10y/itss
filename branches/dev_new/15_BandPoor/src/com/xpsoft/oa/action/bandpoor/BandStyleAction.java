package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.service.bandpoor.BandStyleService;

import flexjson.JSONSerializer;

public class BandStyleAction extends BaseAction{
	@Resource
	private BandStyleService bandStyleService;
	private BandStyle bandStyle;
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BandStyle> list = this.bandStyleService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.bandStyle = this.bandStyleService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.bandStyle));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		this.bandStyle.setFlag(BandStyle.CREATE);
		this.bandStyleService.save(this.bandStyle);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					BandStyle b = this.bandStyleService.get(Long.parseLong(id));
					b.setFlag(BandStyle.DELETE);//置为已删除状态
					this.bandStyleService.save(b);
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public BandStyleService getBandStyleService() {
		return bandStyleService;
	}
	public void setBandStyleService(BandStyleService bandStyleService) {
		this.bandStyleService = bandStyleService;
	}
	public BandStyle getBandStyle() {
		return bandStyle;
	}
	public void setBandStyle(BandStyle bandStyle) {
		this.bandStyle = bandStyle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
