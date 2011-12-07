package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
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
