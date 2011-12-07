package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
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
