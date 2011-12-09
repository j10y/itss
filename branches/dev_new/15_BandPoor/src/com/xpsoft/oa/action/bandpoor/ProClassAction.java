package com.xpsoft.oa.action.bandpoor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import jxl.Sheet;
import jxl.Workbook;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
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
		//判断唯一性
		boolean flag = true;
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.proClass.getId() == null ? "0" : this.proClass.getId().toString());
		if(this.proClass.getProClassNum() != null && !"".equals(this.proClass.getProClassNum())) {
			map.put("Q_proClassNum_S_EQ", this.proClass.getProClassNum());
			flag = this.proClassService.validateUnique(map);
			if(!flag) {
				this.jsonString = "{success:false,msg:'品类编号已存在，请核实！'}";
				return "success";
			}
			map.remove("Q_proClassNum_S_EQ");
		}
		if(this.proClass.getProClassName() != null && !"".equals(this.proClass.getProClassName())) {
			map.put("Q_proClassName_S_EQ", this.proClass.getProClassName());
			flag = this.proClassService.validateUnique(map);
			if(!flag) {
				this.jsonString = "{success:false,msg:'品类名称已存在，请核实！'}";
				return "success";
			}
		}
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
	
	public String upload() {
		String filePath = this.getRequest().getParameter("filePath");
		List<ProClass> list = new ArrayList<ProClass>();
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
				ProClass pc = new ProClass();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据编号为空，请核实！'}";
					return "success";
				}
				if(StringUtils.isEmpty(sheet.getCell(1, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称为空，请核实！'}";
					return "success";
				}
				pc.setProClassNum(sheet.getCell(0, i).getContents());
				pc.setProClassName(sheet.getCell(1, i).getContents());
				pc.setFlag(ProClass.CREATE);
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(pc.getProClassNum())) {
					map.put("Q_proClassNum_S_EQ", pc.getProClassNum());
					flag = this.proClassService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据编号【" + pc.getProClassNum() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
					map.remove("Q_proClass_S_EQ");
				}
				if(StringUtils.isNotEmpty(pc.getProClassName())) {
					map.put("Q_proClassName", pc.getProClassName());
					flag = this.proClassService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称【" + pc.getProClassName() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
				}
				list.add(pc);
			}
			boolean result = this.proClassService.multiSave(list);
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
