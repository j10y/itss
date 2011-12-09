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
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.MainPrice;
import com.xpsoft.oa.service.bandpoor.MainPriceService;

import flexjson.JSONSerializer;

public class MainPriceAction extends BaseAction{
	@Resource
	private MainPriceService mainPriceService;
	private MainPrice mainPrice;
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<MainPrice> list = this.mainPriceService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.mainPrice = this.mainPriceService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.mainPrice));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		//唯一性判断
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.mainPrice.getId() == null ? "0" : this.mainPrice.getId().toString());
		map.put("Q_priceName_S_EQ", this.mainPrice.getPriceName());
		boolean flag = this.mainPriceService.validateUnique(map);
		if(!flag) {
			this.jsonString = "{success:false,msg:'主力价格名称已存在，请核实！'}";
			return "success";
		}
		this.mainPrice.setFlag(MainPrice.CREATE);
		this.mainPriceService.save(this.mainPrice);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					MainPrice b = this.mainPriceService.get(Long.parseLong(id));
					b.setFlag(MainPrice.DELETE);//置为已删除状态
					this.mainPriceService.save(b);
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
		List<MainPrice> list = this.mainPriceService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(MainPrice mp : list) {
			buff.append("[" +
					"'" + mp.getId() + "'," +
					"'" + mp.getPriceName() + "'" +
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
		List<MainPrice> list = new ArrayList<MainPrice>();
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
				MainPrice mp = new MainPrice();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称为空，请核实！'}";
					return "success";
				}
				mp.setPriceName(sheet.getCell(0, i).getContents());
				mp.setPriceDesc(sheet.getCell(1, i).getContents());
				mp.setFlag(MainPrice.CREATE);
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(mp.getPriceName())) {
					map.put("Q_priceName_S_EQ", mp.getPriceName());
					flag = this.mainPriceService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称【" + mp.getPriceName() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
				}
				list.add(mp);
			}
			boolean result = this.mainPriceService.multiSave(list);
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
	
	public MainPriceService getMainPriceService() {
		return mainPriceService;
	}
	public void setMainPriceService(MainPriceService mainPriceService) {
		this.mainPriceService = mainPriceService;
	}
	public MainPrice getMainPrice() {
		return mainPrice;
	}
	public void setMainPrice(MainPrice mainPrice) {
		this.mainPrice = mainPrice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
