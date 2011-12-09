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
	
	public String upload() {
		String filePath = this.getRequest().getParameter("filePath");
		List<Band> list = new ArrayList<Band>();
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
				Band band1 = new Band();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents()) && StringUtils.isEmpty(sheet.getCell(1, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中存在既没有中文名称又没有英文名称的数据，请核实！'}";
					return "success";
				}
				if(StringUtils.isNotEmpty(sheet.getCell(0, i).getContents())) {
					band1.setBandChName(sheet.getCell(0, i).getContents());
				}
				if(StringUtils.isNotEmpty(sheet.getCell(1, i).getContents())) {
					band1.setBandEnName(sheet.getCell(1, i).getContents());
				}
				if("0".equals(sheet.getCell(2, i).getContents())) {
					band1.setBandStatus(0);
				} else if("1".equals(sheet.getCell(2, i).getContents())) {
					band1.setBandStatus(1);
				} else {
					this.jsonString = "{success:true,flag:'0',msg:'excel中存在类型不符合要求的数据，请核实！'}";
					return "success";
				}
				band1.setBandDesc(sheet.getCell(3, i).getContents());
				band1.setFlag(Band.CREATE);
				list.add(band1);
			}
			List<Band> list2 = list;
			for(int i = 0; i < list.size(); i++) {
				Band band1 = list.get(i);
				for(int j = i + 1; j < list2.size(); j ++) {
					Band band2 = list2.get(j);
					if(StringUtils.isNotEmpty(band1.getBandChName())) {
						if(band1.getBandChName().equals(band2.getBandChName())) {
							this.jsonString = "{success:true,flag:'0',msg:'excel中存在中文名称相同的数据，请核实！'}";
							return "success";
						}
					}
					if(StringUtils.isNotEmpty(band1.getBandEnName())) {
						if(band1.getBandEnName().equals(band2.getBandEnName())) {
							this.jsonString = "{success:true,flag:'0',msg:'excel中存在英文名称相同的数据，请核实！'}";
							return "success";
						}
					}
				}
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(band1.getBandChName())) {
					map.put("Q_bandChName_S_EQ", band1.getBandChName());
					flag = this.bandService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{failure:true,flag:'0',msg:'中文名称【" + band1.getBandChName() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
					map.remove("Q_bandChName_S_EQ");
				}
				if(StringUtils.isNotEmpty(band1.getBandEnName())) {
					map.put("Q_bandEnName_S_EQ", band1.getBandEnName());
					flag = this.bandService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'英文名称【" + band1.getBandEnName() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
				}
			}
			boolean result = this.bandService.multiSave(list);
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
