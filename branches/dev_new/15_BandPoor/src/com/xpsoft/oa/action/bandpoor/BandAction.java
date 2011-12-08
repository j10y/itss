package com.xpsoft.oa.action.bandpoor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.service.bandpoor.BandService;

import flexjson.JSONSerializer;

public class BandAction extends BaseAction {
	@Resource
	private BandService bandService;
	private Band band;
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<Band> list = this.bandService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.band = this.bandService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.band));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		this.band = new Band();
		if(this.getRequest().getParameter("band.id") != null && !"".equals(this.getRequest().getParameter("band.id"))) {
			this.band.setId(Long.parseLong(this.getRequest().getParameter("band.id")));
		}
		this.band.setBandChName(this.getRequest().getParameter("band.bandChName"));
		this.band.setBandEnName(this.getRequest().getParameter("band.bandEnName"));
		this.band.setBandDesc(this.getRequest().getParameter("band.bandDesc"));
		this.band.setBandStatus(Integer.parseInt(this.getRequest().getParameter("band.bandStatus")));
		this.band.setFlag(Band.CREATE);
		//判断唯一性
		boolean flag = true;
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.band.getId() == null ? "0" : this.band.getId().toString());
		if(this.band.getBandChName() != null && !"".equals(this.band.getBandChName())) {
			map.put("Q_bandChName_S_EQ", this.band.getBandChName());
			flag = this.bandService.validateUnique(map);
			if(!flag) {
				this.jsonString = "{success:false,msg:'中文名称已存在，请核实！'}";
				return "success";
			}
			map.remove("Q_bandChName_S_EQ");
		}
		if(this.band.getBandEnName() != null && !"".equals(this.band.getBandEnName())) {
			map.put("Q_bandEnName_S_EQ", this.band.getBandEnName());
			flag = this.bandService.validateUnique(map);
			if(!flag) {
				this.jsonString = "{success:false,msg:'英文名称已存在，请核实！'}";
				return "success";
			}
		}
		this.bandService.save(this.band);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					Band b = this.bandService.get(Long.parseLong(id));
					b.setFlag(Band.DELETE);//置为已删除状态
					this.bandService.save(b);
				}
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			this.jsonString = "{success:false}";
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public BandService getBandService() {
		return bandService;
	}
	public void setBandService(BandService bandService) {
		this.bandService = bandService;
	}
	public Band getBand() {
		return band;
	}
	public void setBand(Band band) {
		this.band = band;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
