package com.xpsoft.oa.action.miswap;

import javax.annotation.Resource;

import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.service.miswap.TmSendService;
import com.xpsoft.oa.model.miswap.TmSend;

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
}
