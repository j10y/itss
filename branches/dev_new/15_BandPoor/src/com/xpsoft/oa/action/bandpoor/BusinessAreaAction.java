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
import com.xpsoft.oa.model.bandpoor.BusinessArea;
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
		//判断唯一性
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.businessArea.getId() == null ? "0" : this.businessArea.getId().toString());
		map.put("Q_areaName_S_EQ", this.businessArea.getAreaName());
		boolean flag = this.businessAreaService.validateUnique(map);
		if(!flag) {
			this.jsonString = "{success:false,msg:'商圈名称已存在，请核实！'}";
			return "success";
		}
		this.businessArea.setFlag(BusinessArea.CREATE);
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
	
	public String combo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<BusinessArea> list = this.businessAreaService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(BusinessArea ba : list) {
			buff.append("[" +
					"'" + ba.getId() + "'," +
					"'" + ba.getAreaName() + "'" +
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
		List<BusinessArea> list = new ArrayList<BusinessArea>();
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
				BusinessArea ba = new BusinessArea();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据商圈名称为空，请核实！'}";
					return "success";
				}else{
					ba.setAreaName(sheet.getCell(0, i).getContents());
				}
				if(StringUtils.isNotEmpty(sheet.getCell(1, i).getContents())) {
					ba.setAreaDesc(sheet.getCell(1, i).getContents());
				}
				ba.setFlag(BusinessArea.CREATE);
				
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(ba.getAreaName())) {
					map.put("Q_areaName_S_EQ", ba.getAreaName());
					flag = this.businessAreaService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据中文名称【" + ba.getAreaName()+ "】在数据库中已存在，请核实！'}";
						return "success";
					}
					map.remove("Q_areaName_S_EQ");
				}
				list.add(ba);
			}
			boolean result = this.businessAreaService.multiSave(list);
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
