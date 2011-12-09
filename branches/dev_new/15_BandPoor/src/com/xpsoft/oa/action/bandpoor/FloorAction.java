package com.xpsoft.oa.action.bandpoor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandChannel;
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
	
	public String upload() {
		String filePath = this.getRequest().getParameter("filePath");
		List<Floor> list = new ArrayList<Floor>();
		String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
		int len = defaultProfix.length();
		filePath = filePath.substring(filePath.indexOf(defaultProfix));
		File file = new File(this.getRequest().getRealPath("/") + filePath);
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int col = sheet.getColumns();
			int row = sheet.getRows();
			for(int i = 1; i < row; i++) {
				Floor f = new Floor();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称为空，请核实！'}";
					return "success";
				}
				f.setFloorName(sheet.getCell(0, i).getContents());
				f.setFloorDesc(sheet.getCell(1, i).getContents());
				f.setFlag(Floor.CREATE);
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(f.getFloorName())) {
					map.put("Q_floorName_S_EQ", f.getFloorName());
					flag = this.floorService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称【" + f.getFloorName() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
				}
				list.add(f);
			}
			boolean result = this.floorService.multiSave(list);
			if(result) {
				this.jsonString = "{success:true,flag:'1'}";
			} else {
				this.jsonString = "{success:true,flag:'0',msg:'导入失败，请核实文件和数据！'}";
				return "success";
			}
		} catch(Exception e) {
			e.printStackTrace();
			this.jsonString = "{success:true,flag:'0',msg:'导入失败，请核实文件和数据！'}";
			return "success";
		}
		
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
