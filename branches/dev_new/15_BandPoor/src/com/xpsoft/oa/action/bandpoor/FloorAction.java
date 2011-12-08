package com.xpsoft.oa.action.bandpoor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.service.bandpoor.FloorService;

import flexjson.JSONSerializer;

public class FloorAction extends BaseAction{
	@Resource
	private FloorService floorService;
	private Floor floor;
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<Floor> list = this.floorService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.floor = this.floorService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.floor));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		//判断唯一性
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.floor.getId() == null ? "0" : this.floor.getId().toString());
		map.put("Q_floorName_S_EQ", this.floor.getFloorName());
		boolean flag = this.floorService.validateUnique(map);
		if(!flag) {
			this.jsonString = "{success:false,msg:'楼层名称已存在，请核实！'}";
			return "success";
		}
		this.floor.setFlag(Floor.CREATE);
		this.floorService.save(this.floor);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					Floor b = this.floorService.get(Long.parseLong(id));
					b.setFlag(Floor.DELETE);//置为已删除状态
					this.floorService.save(b);
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
		List<Floor> list = this.floorService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(Floor f : list) {
			buff.append("[" +
					"'" + f.getId() + "'," +
					"'" + f.getFloorName() + "'" +
					"],");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public FloorService getFloorService() {
		return floorService;
	}
	public void setFloorService(FloorService floorService) {
		this.floorService = floorService;
	}
	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
