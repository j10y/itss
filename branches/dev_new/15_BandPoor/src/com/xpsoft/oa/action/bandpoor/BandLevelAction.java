package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.service.bandpoor.BandLevelService;

import flexjson.JSONSerializer;

public class BandLevelAction extends BaseAction{
	@Resource
	private BandLevelService businessAreaService;
	private BandLevel businessArea;
	public BandLevelService getBandLevelService() {
		return businessAreaService;
	}

	public void setBandLevelService(BandLevelService businessAreaService) {
		this.businessAreaService = businessAreaService;
	}

	public BandLevel getBandLevel() {
		return businessArea;
	}

	public void setBandLevel(BandLevel businessArea) {
		this.businessArea = businessArea;
	}
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BandLevel> list = this.businessAreaService.getAll(filter);
		
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
		this.businessArea.setFlag(BandLevel.CREATE);
		this.businessAreaService.save(this.businessArea);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					BandLevel b = this.businessAreaService.get(Long.parseLong(id));
					b.setFlag(BandLevel.DELETE);//置为已删除状态
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
	
	public String combo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<BandLevel> list = this.businessAreaService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(BandLevel ba : list) {
			buff.append("[" +
					"'" + ba.getId() + "'," +
					"'" + ba.getLevelName() + "'" +
					"],");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
