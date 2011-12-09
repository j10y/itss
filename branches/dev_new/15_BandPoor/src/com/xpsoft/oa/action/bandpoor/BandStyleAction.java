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
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.service.bandpoor.BandStyleService;
import com.xpsoft.oa.service.bandpoor.ProClassService;

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
		//判断唯一性
		boolean flag = true;
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.bandStyle.getId() == null ? "0" : this.bandStyle.getId().toString());
		map.put("Q_proClassId.id_L_EQ", this.bandStyle.getProClassId().getId().toString());
		if(this.bandStyle.getStyleNum() != null && !"".equals(this.bandStyle.getStyleNum())) {
			map.put("Q_styleNum_S_EQ", this.bandStyle.getStyleNum());
			flag = this.bandStyleService.validateUnique(map);
			if(!flag) {
				this.jsonString = "{success:false,msg:'品牌风格编号在该品类下已存在，请核实！'}";
				return "success";
			}
			map.remove("Q_styleNum_S_EQ");
		}
		if(this.bandStyle.getStyleName() != null && !"".equals(this.bandStyle.getStyleName())) {
			map.put("Q_styleName_S_EQ", this.bandStyle.getStyleName());
			flag = this.bandStyleService.validateUnique(map);
			if(!flag) {
				this.jsonString = "{success:false,msg:'品牌风格名称在该品类下已存在，请核实！'}";
				return "success";
			}
		}
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
	
	public String upload() {
		ProClassService proClassService = (ProClassService)AppUtil.getBean("proClassService");
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("Q_flag_N_EQ", ProClass.CREATE.toString());
		QueryFilter filter = new QueryFilter(queryMap);
		List<ProClass> pcList = proClassService.getAll(filter);
		Map<String, ProClass> pcMap = new HashMap<String, ProClass>();
		for(ProClass pc : pcList) {
			pcMap.put(pc.getProClassNum(), pc);
		}
		String filePath = this.getRequest().getParameter("filePath");
		List<BandStyle> list = new ArrayList<BandStyle>();
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
				BandStyle bs = new BandStyle();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据编号为空，请核实！'}";
					return "success";
				}
				if(StringUtils.isEmpty(sheet.getCell(1, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称为空，请核实！'}";
					return "success";
				}
				if(!queryMap.containsKey(sheet.getCell(2, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据所属品类不存在，请核实！'}";
					return "success";
				}
				bs.setStyleNum(sheet.getCell(0, i).getContents());
				bs.setStyleName(sheet.getCell(1, i).getContents());
				bs.setProClassId(pcMap.get(sheet.getCell(2, i).getContents()));
				bs.setStyleDesc(sheet.getCell(3, i).getContents());
				bs.setFlag(BandStyle.CREATE);
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(bs.getStyleNum())) {
					map.put("Q_styleNum_S_EQ", bs.getStyleNum());
					flag = this.bandStyleService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据编号【" + bs.getStyleNum() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
					map.remove("Q_styleNum_S_EQ");
				}
				if(StringUtils.isNotEmpty(bs.getStyleName())) {
					map.put("Q_styleName_S_EQ", bs.getStyleName());
					flag = this.bandStyleService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称【" + bs.getStyleNum() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
				}
				list.add(bs);
			}
			boolean result = this.bandStyleService.multiSave(list);
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
