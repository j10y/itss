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
import com.xpsoft.oa.service.bandpoor.BandChannelService;

import flexjson.JSONSerializer;

public class BandChannelAction extends BaseAction{
	@Resource
	private BandChannelService bandChannelService;
	private BandChannel bandChannel;
	private Long id;
	
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<BandChannel> list = this.bandChannelService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.bandChannel = this.bandChannelService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.bandChannel));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		//判断唯一性
		Map<String, String> map = new HashMap<String, String>();
		map.put("Q_id_L_NEQ", this.bandChannel.getId() == null ? "0" : this.bandChannel.getId().toString());
		map.put("Q_channelName_S_EQ", this.bandChannel.getChannelName());
		boolean flag = this.bandChannelService.validateUnique(map);
		if(!flag) {
			this.jsonString = "{success:false,msg:'品牌渠道名称已存在，请核实！'}";
			return "success";
		}
		this.bandChannel.setFlag(Floor.CREATE);
		this.bandChannelService.save(this.bandChannel);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		try {
			if(ids != null) {
				for(String id : ids) {
					BandChannel b = this.bandChannelService.get(Long.parseLong(id));
					b.setFlag(BandChannel.DELETE);//置为已删除状态
					this.bandChannelService.save(b);
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
		List<BandChannel> list = this.bandChannelService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(BandChannel bc : list) {
			buff.append("[" +
					"'" + bc.getId() + "'," +
					"'" + bc.getChannelName() + "'" +
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
		List<BandChannel> list = new ArrayList<BandChannel>();
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
				BandChannel bc = new BandChannel();
				if(StringUtils.isEmpty(sheet.getCell(0, i).getContents())) {
					this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称为空，请核实！'}";
					return "success";
				}
				bc.setChannelName(sheet.getCell(0, i).getContents());
				bc.setChannelDesc(sheet.getCell(1, i).getContents());
				bc.setFlag(BandChannel.CREATE);
				boolean flag = true;
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_id_L_NEQ", "0");
				if(StringUtils.isNotEmpty(bc.getChannelName())) {
					map.put("Q_channelName_S_EQ", bc.getChannelName());
					flag = this.bandChannelService.validateUnique(map);
					if(!flag) {
						this.jsonString = "{success:true,flag:'0',msg:'excel中第【" + (i + 1) + "】行数据名称【" + bc.getChannelName() + "】在数据库中已存在，请核实！'}";
						return "success";
					}
				}
				list.add(bc);
			}
			boolean result = this.bandChannelService.multiSave(list);
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
	
	public BandChannelService getBandChannelService() {
		return bandChannelService;
	}
	public void setBandChannelService(BandChannelService bandChannelService) {
		this.bandChannelService = bandChannelService;
	}
	public BandChannel getBandChannel() {
		return bandChannel;
	}
	public void setBandChannel(BandChannel bandChannel) {
		this.bandChannel = bandChannel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
