package com.digitalchina.itil.require.service.impl;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.digitalchina.itil.config.extlist.entity.SRManager;
import com.digitalchina.itil.config.extlist.entity.SRProjectNotice;
import com.digitalchina.itil.config.extlist.entity.SRServiceItem;
import com.digitalchina.itil.config.extlist.entity.SRServiceProvider;
import com.digitalchina.itil.config.extlist.entity.SRTransmisEngineer;
import com.digitalchina.itil.config.extlist.entity.SRprojectContracts;
import com.digitalchina.itil.require.dao.SRDao;
import com.digitalchina.itil.require.entity.RequireApplyDefaultAudit;
import com.digitalchina.itil.require.entity.SRIncomePlanFile;
import com.digitalchina.itil.require.entity.SRPaymentPlanFile;
import com.digitalchina.itil.require.entity.SRTestReportFile;
import com.digitalchina.itil.require.entity.SpecialRequirement;
import com.digitalchina.itil.require.service.SRService;

public class SRServiceImpl extends BaseService implements SRService {
	SRDao srdao;

	public SRprojectContracts findContractByReq(String reqId) {
		return srdao.findContractByReq(reqId);
	}

	public SRProjectNotice findNoticeByReq(String reqId) {
		return srdao.findNoticeByReq(reqId);
	}

	public SRServiceItem findRequirementService(String  reqId) {
		return srdao.findRequirementService(reqId);
	}

	public SRServiceProvider findRequirementServiceProvider(String reqId) {
		return srdao.findRequirementServiceProvider(reqId);
	}

	public Page findServiceEngineerIn(String serviceId, int pageNo, int pageSize) {
		return srdao.findServiceEngineerIn(serviceId, pageNo, pageSize);
	}

	/**
	 * ����ⲿ�����̹���ʦ
	 */
	@SuppressWarnings("unchecked")
//	public Page findServiceEngineerOut(String serviceId, int pageNo, int pageSize) {
//		return srdao.findServiceEngineerOut(serviceId, pageNo, pageSize);
//	}

	public SRTransmisEngineer findTransmissionEngineer(String reqId) {
		return srdao.findTransmissionEngineer(reqId);
	}

	public SRGroupFinanceInfo findGroupGinanceInfo(String reqId) {
		return srdao.findGroupGinanceInfo(reqId);
	}

	public SRManager findSRManager(String dataId) {
		return srdao.findSRManager(dataId);
	}

	public Page findAppConfigItem(String ciName,Long appTypeId, int pageNo, int pageSize) {
		return srdao.findAppConfigItem(ciName, appTypeId, pageNo, pageSize);
	}

	public SRDao getSrdao() {
		return srdao;
	}

	public void setSrdao(SRDao srdao) {
		this.srdao = srdao;
	}

	public boolean isErpRequire(String reqId, String erpAppId) {
		SpecialRequirement sp = (SpecialRequirement) this.find(SpecialRequirement.class, reqId);
		ConfigItem ci = sp.getAppConfigItem();
		if(ci!=null&&ci.getId().equals(Long.valueOf(erpAppId))){
			return true;
		}
		return false;
	}

	public void initSRAttachment(String reqId) {
		SpecialRequirement sp = (SpecialRequirement) this.find(SpecialRequirement.class, reqId);
		SRIncomePlanFile incomePlanFile = new SRIncomePlanFile(sp);
		this.save(incomePlanFile);
		SRPaymentPlanFile paymentPlanFile = new SRPaymentPlanFile(sp);
		this.save(paymentPlanFile);
		SRTestReportFile  testReportFile = new SRTestReportFile(sp);
		this.save(testReportFile);
	}

	public boolean isToFinancial(String reqId, String sbuId) {
		if(StringUtils.isBlank(reqId)||StringUtils.isBlank(sbuId)){
			return false;
		}
		SpecialRequirement sp = (SpecialRequirement) this.find(SpecialRequirement.class, reqId);
		RequireApplyDefaultAudit flat = sp.getFlat();
		if(flat!=null&&(flat.getId().toString()).equals(sbuId)){
			return true;
		}
		return false;
	}

	

}
