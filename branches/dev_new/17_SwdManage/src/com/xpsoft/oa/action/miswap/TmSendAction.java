package com.xpsoft.oa.action.miswap;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.service.miswap.TmSendService;
import com.xpsoft.oa.model.miswap.EmailTemplate;
import com.xpsoft.oa.model.miswap.TmSend;

import flexjson.JSONSerializer;

public class TmSendAction extends BaseAction{
	private Long id;
	private TmSend tmSend;
	@Resource
	private TmSendService tmSendService;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TmSend getTmSend() {
		return tmSend;
	}
	public void setTmSend(TmSend tmSend) {
		this.tmSend = tmSend;
	}
	public TmSendService getTmSendService() {
		return tmSendService;
	}
	public void setTmSendService(TmSendService tmSendService) {
		this.tmSendService = tmSendService;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<TmSend> list = this.tmSendService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
