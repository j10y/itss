package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.ShopinProClass;
import com.xpsoft.oa.service.bandpoor.ShopinProClassService;

public class ShopinProClassAction extends BaseAction{
	@Resource
	ShopinProClassService shopinProClassService;
	ShopinProClass shopinProClass;
	Long id;
	
	public String combo() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<ShopinProClass> list = this.shopinProClassService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		for(ShopinProClass sb : list) {
			buff.append("[" + 
					"'" + sb.getId() + "'," + 
					"'" + sb.getProClassName() + "'" + 
					"],");
		}
		if(list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public ShopinProClassService getShopinProClassService() {
		return shopinProClassService;
	}
	public void setShopinProClassService(ShopinProClassService shopinProClassService) {
		this.shopinProClassService = shopinProClassService;
	}
	public ShopinProClass getShopinProClass() {
		return shopinProClass;
	}
	public void setShopinProClass(ShopinProClass shopinProClass) {
		this.shopinProClass = shopinProClass;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
