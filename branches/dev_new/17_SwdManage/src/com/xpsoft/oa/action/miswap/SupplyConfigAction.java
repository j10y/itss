package com.xpsoft.oa.action.miswap;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.danpin.SupplyInfo;
import com.xpsoft.oa.model.miswap.EmailTemplate;
import com.xpsoft.oa.model.miswap.SupplyConfig;
import com.xpsoft.oa.model.miswap.TmTemplate;
import com.xpsoft.oa.service.danpin.SupplyInfoService;
import com.xpsoft.oa.service.miswap.EmailTemplateService;
import com.xpsoft.oa.service.miswap.SupplyConfigService;
import com.xpsoft.oa.service.miswap.TmTemplateService;

import flexjson.JSONSerializer;

public class SupplyConfigAction extends BaseAction{
	private Long id;
	private SupplyConfig supplyConfig;
	@Resource
	private SupplyConfigService supplyConfigService;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SupplyConfig getSupplyConfig() {
		return supplyConfig;
	}
	public void setSupplyConfig(SupplyConfig supplyConfig) {
		this.supplyConfig = supplyConfig;
	}
	public SupplyConfigService getSupplyConfigService() {
		return supplyConfigService;
	}
	public void setSupplyConfigService(SupplyConfigService supplyConfigService) {
		this.supplyConfigService = supplyConfigService;
	}
	
	public String save() {
		if(this.supplyConfig.getReceiveTm() == null) {
			this.supplyConfig.setReceiveTm(0);
		}
		if(this.supplyConfig.getReceiveEmail() == null) {
			this.supplyConfig.setReceiveEmail(0);
		}
		this.supplyConfigService.save(this.supplyConfig);
		this.jsonString = "{success:true}";
		
		return "success";
	}
	
	public String get() {
		SupplyInfoService supplyInfoService = (SupplyInfoService)AppUtil.getBean("supplyInfoService");
		EmailTemplateService emailTemplateService = (EmailTemplateService)AppUtil.getBean("emailTemplateService");
		TmTemplateService tmTemplateService = (TmTemplateService)AppUtil.getBean("tmTemplateService");
		
		Long supplyInfoSid = Long.parseLong(this.getRequest().getParameter("Q_supplyInfoSid_L_EQ"));
		SupplyInfo supplyInfo = supplyInfoService.get(supplyInfoSid);
		EmailTemplate et = new EmailTemplate();
		TmTemplate tt = new TmTemplate();
		
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SupplyConfig> list = this.supplyConfigService.getAll(filter);
		if(list.size() > 0) {
			this.supplyConfig = list.get(0);
			if(this.supplyConfig.getEmailId() != null) {
				et = emailTemplateService.get(this.supplyConfig.getEmailId());
			}
			if(this.supplyConfig.getTmId() != null) {
				tt = tmTemplateService.get(this.supplyConfig.getTmId());
			}
		}
		
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append("{")
				.append("'id':'" + (this.supplyConfig == null ? "" : this.supplyConfig.getId()) + "',")
				.append("'supplyInfoSid':'" + supplyInfo.getSid() + "',")
				.append("'supplyId':'" + supplyInfo.getSupplyId() + "',")
				.append("'companyName':'" + supplyInfo.getCompanyName() + "',")
				.append("'receiveTm':'" + (this.supplyConfig == null ? "" : this.supplyConfig.getReceiveTm()) + "',")
				.append("'tmId':'" + (tt.getId() == null ? "" : tt.getId()) + "',")
				.append("'tmName':'" + (tt.getName() == null ? "" : tt.getName()) + "',")
				.append("'receiveEmail':'" + (this.supplyConfig == null ? "" : this.supplyConfig.getReceiveEmail()) + "',")
				.append("'emailId':'" + (et.getId() == null ? "" : et.getId()) + "',")
				.append("'emailName':'" + (et.getName() == null ? "" : et.getName()) + "'")
				.append("}");
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
