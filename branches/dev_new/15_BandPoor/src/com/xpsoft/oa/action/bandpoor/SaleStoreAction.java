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
import com.xpsoft.oa.model.bandpoor.SaleStore;
import com.xpsoft.oa.service.bandpoor.BusinessAreaService;
import com.xpsoft.oa.service.bandpoor.SaleStoreService;

import flexjson.JSONSerializer;

public class SaleStoreAction extends BaseAction{
	@Resource
	private SaleStoreService saleStoreServiece;
	private SaleStore saleStore;
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<SaleStore> list = this.saleStoreServiece.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.saleStore = this.saleStoreServiece.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.saleStore));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		//判断唯一性
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.saleStore.getId() == null ? "0" : this.saleStore.getId().toString());
		map.put("Q_storeName_S_EQ", this.saleStore.getStoreName());
		map.put("Q_allowAreaId.id_L_EQ", this.saleStore.getAllowAreaId().getId().toString());
		boolean flag = this.saleStoreServiece.validateUnique(map);
		if(!flag) {
			this.jsonString = "{success:false,msg:'可评分商场名称在该商圈下已存在，请核实！'}";
			return "success";
		}
		this.saleStore.setFlag(SaleStore.CREATE);
		this.saleStoreServiece.save(this.saleStore);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					SaleStore b = this.saleStoreServiece.get(Long.parseLong(id));
					b.setFlag(SaleStore.DELETE);//置为已删除状态
					this.saleStoreServiece.save(b);
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
		//获取全部商圈
		BusinessAreaService businessAreaService = (BusinessAreaService)AppUtil.getBean("businessAreaService");
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("Q_flag_N_EQ", BusinessArea.CREATE.toString());
		QueryFilter filter = new QueryFilter(queryMap);
		List<BusinessArea> pcList = businessAreaService.getAll(filter);
		Map<String, BusinessArea> pcMap = new HashMap<String, BusinessArea>();
		for(BusinessArea pc : pcList) {
			pcMap.put(pc.getAreaName(), pc);
		}
		
		String filePath = this.getRequest().getParameter("filePath");
		List<SaleStore> list = new ArrayList<SaleStore>();
		String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
		int len = defaultProfix.length();
		filePath = filePath.substring(filePath.indexOf(defaultProfix));
		File file = new File(this.getRequest().getRealPath("/") + filePath);
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int col = sheet.getColumns();
			int row = sheet.getRows();
			for(int i = 2; i < row; i++) {
				SaleStore ss = new SaleStore();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据商场名称为空，请核实！'}";
					return "success";
				}else{
					ss.setStoreName(sheet.getCell(0, i).getContents());
				}
				
				if(StringUtils.isEmpty(sheet.getCell(1, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据:商圈名称为空，请核实！'}";
					return "success";
				}else if(pcMap.containsKey(sheet.getCell(1, i).getContents())){
					ss.setAllowAreaId(pcMap.get(sheet.getCell(1, i).getContents()));
				}else{
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据:商圈名称："+sheet.getCell(1, i).getContents()+"不存在，请核实！'}";
					return "success";
				}
				if(StringUtils.isEmpty(sheet.getCell(2, i).getContents()) || !StringUtils.isNumeric(sheet.getCell(2, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据:商场评分为空或格式不正确，请核实！'}";
					return "success";
				}else{
					ss.setStoreScore(Double.valueOf(sheet.getCell(2, i).getContents()));
				}
				
				
				ss.setFlag(SaleStore.CREATE);
				
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(ss.getStoreName())) {
					map.put("Q_storeName_S_EQ", ss.getStoreName());
					flag = this.saleStoreServiece.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据:商场名称【" + ss.getStoreName()+ "】在数据库中已存在，请核实！'}";
						return "success";
					}
					map.remove("Q_storeName_S_EQ");
				}
				list.add(ss);
			}
			if(row > 2){
				boolean result = this.saleStoreServiece.multiSave(list);
				if(result) {
					this.jsonString = "{success:true,flag:'1'}";
				} else {
					this.jsonString = "{success:true,flag:'0',msg:'导入失败，请核实文件和数据！'}";
					return "success";
				}
			}else{
				this.jsonString = "{success:true,flag:'0',msg:'本次上传为空，请核实文件和数据！'}";
				return "success";
			}
		} catch(Exception e) {
			e.printStackTrace();
			this.jsonString = "{success:true,flag:'0',msg:'导入失败，请核实文件和数据！'}";
			return "success";
		}
		
		return "success";
	}
	
	public SaleStoreService getSaleStoreService() {
		return saleStoreServiece;
	}
	public void setSaleStoreService(SaleStoreService saleStoreServiece) {
		this.saleStoreServiece = saleStoreServiece;
	}
	public SaleStore getSaleStore() {
		return saleStore;
	}
	public void setSaleStore(SaleStore saleStore) {
		this.saleStore = saleStore;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
