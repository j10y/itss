package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.ShopinBand;
import com.xpsoft.oa.service.bandpoor.ShopinBandService;

public class ShopinBandAction extends BaseAction{
	@Resource
	ShopinBandService shopinBandService;
	ShopinBand shopinBand;
	Long id;
	
	public String combo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<ShopinBand> list = this.shopinBandService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(ShopinBand sb : list) {
			buff.append("[" + 
					"'" + sb.getId() + "'," + 
					"'" + sb.getBandName() + "'" + 
					"],");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public ShopinBandService getShopinBandService() {
		return shopinBandService;
	}
	public void setShopinBandService(ShopinBandService shopinBandService) {
		this.shopinBandService = shopinBandService;
	}
	public ShopinBand getShopinBand() {
		return shopinBand;
	}
	public void setShopinBand(ShopinBand shopinBand) {
		this.shopinBand = shopinBand;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
