package com.xpsoft.oa.action.bandpoor;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.SaleStore;
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
