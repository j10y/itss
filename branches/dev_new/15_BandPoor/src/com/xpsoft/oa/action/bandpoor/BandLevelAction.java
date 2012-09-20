package com.xpsoft.oa.action.bandpoor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.service.bandpoor.BandLevelService;

import flexjson.JSONSerializer;

public class BandLevelAction extends BaseAction{
	@Resource
	private BandLevelService bandLevelService;
	private BandLevel bandLevel;
	public BandLevelService getBandLevelService() {
		return bandLevelService;
	}

	public void setBandLevelService(BandLevelService bandLevelService) {
		this.bandLevelService = bandLevelService;
	}

	public BandLevel getBandLevel() {
		return bandLevel;
	}

	public void setBandLevel(BandLevel bandLevel) {
		this.bandLevel = bandLevel;
	}
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BandLevel> list = this.bandLevelService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.bandLevel = this.bandLevelService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.bandLevel));
		buff.append("}");
		this.jsonString = buff.toString();
		return "success";
	}
	
	public String save() {
		//判断唯一性
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.bandLevel.getId() == null ? "0" : this.bandLevel.getId().toString());
		map.put("Q_levelName_S_EQ", this.bandLevel.getLevelName());
		map.put("Q_proClassName_S_EQ", this.bandLevel.getProClassName());
		map.put("Q_flag_N_EQ", BandLevel.CREATE.toString());
		boolean flag = this.bandLevelService.validateUnique(map);
		if(!flag) {
			this.jsonString = "{success:false,msg:'可应用品牌池分类名称已存在，请核实！'}";
			return "success";
		}
		this.bandLevel.setFlag(BandLevel.CREATE);
		this.bandLevelService.save(this.bandLevel);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					BandLevel b = this.bandLevelService.get(Long.parseLong(id));
					b.setFlag(BandLevel.DELETE);//置为已删除状态
					this.bandLevelService.save(b);
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
		List<BandLevel> list = this.bandLevelService.getAll(filter);
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
