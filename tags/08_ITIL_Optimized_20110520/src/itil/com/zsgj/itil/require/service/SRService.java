package com.zsgj.itil.require.service;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.zsgj.itil.config.extlist.entity.SRManager;
import com.zsgj.itil.config.extlist.entity.SRProjectNotice;
import com.zsgj.itil.config.extlist.entity.SRServiceItem;
import com.zsgj.itil.config.extlist.entity.SRServiceProvider;
import com.zsgj.itil.config.extlist.entity.SRTransmisEngineer;
import com.zsgj.itil.config.extlist.entity.SRprojectContracts;

public interface SRService {

	/**
	 * ͨ������ʵ��ID��������ȡ��Ӧ�������ͬ
	 * @Methods Name findContractByReq
	 * @Create In Mar 20, 2009 By lee
	 * @param reqId
	 * @param reqClass
	 * @return RequirementContract
	 */
	SRprojectContracts findContractByReq(String reqId);
	/**
	 * ͨ������ʵ��ID��������ȡ��Ӧ����Ŀ��������
	 * @Methods Name findNoticeByReq
	 * @Create In Mar 26, 2009 By lee
	 * @param reqId
	 * @param reqClass
	 * @return ProjectNotice
	 */
	SRProjectNotice findNoticeByReq(String reqId);
	/**
	 * ͨ������ʵ��ID��������ȡ��Ӧ������������ϵ
	 * @Methods Name findRequirementService
	 * @Create In Mar 31, 2009 By lee
	 * @param reqId
	 * @param reqClass
	 * @return RequirementService
	 */
	SRServiceItem findRequirementService(String reqId);
	/**
	 * ͨ������ʵ��ID��������ȡ��Ӧ�������Ӧ����Ӧ��
	 * @Methods Name findRequirementServiceProvider
	 * @Create In Apr 3, 2009 By lee
	 * @param reqId
	 * @param reqClass
	 * @return RequirementServiceProvider
	 */
	SRServiceProvider findRequirementServiceProvider(String reqId);
	/**
	 * ͨ������ʵ��ID��������ȡ���乤��ʦ
	 * @Methods Name findTransmissionEngineer
	 * @Create In Apr 7, 2009 By lee
	 * @param reqId
	 * @param reqClass
	 * @return ProjectTransmissionEngineer
	 */
	SRTransmisEngineer findTransmissionEngineer(String reqId);
	/**
	 * ����ڲ������̹���ʦ
	 * @Methods Name findServiceEngineerIn
	 * @Create In Apr 3, 2009 By lee
	 * @param serviceId
	 * @return List<ServiceEngineerIn>
	 */
	Page findServiceEngineerIn(String serviceId, int pageNo, int pageSize);
	/**
	 * ����ⲿ�����̵ķ��񹤳�ʦ
	 * @Methods Name findServiceEngineerOut
	 * @Create In Apr 3, 2009 By Administrator
	 * @param serviceId
	 * @return List<ServiceEngineerOut>
	 */
//	Page findServiceEngineerOut(String serviceId, int pageNo, int pageSize);
	/**
	 * ��ȡ���Ų�����Ϣ
	 * @Methods Name findGroupGinanceInfo
	 * @Create In Apr 14, 2009 By gaowen
	 * @param dataId
	 * @param reqClass
	 * @return RequirementGroupFinanceInfo
	 */
	SRGroupFinanceInfo findGroupGinanceInfo(String dataId);
	
	
	/**
	 * ��ȡ����������Ϣ
	 * @Methods Name findSRManager
	 * @Create In Jun 25, 2009 By gaowen
	 * @param dataId
	 * @param reqClass
	 * @return  SRManager
	 */
	 SRManager  findSRManager(String dataId);
	 
	 /**
	  * �������������ƻ�ȡӦ�������������
	  * @Methods Name findAppConfigItem
	  * @Create In Nov 25, 2009 By lee
	  * @param ciName
	  * @param appTypeId
	  * @param pageNo
	  * @param pageSize
	  * @return Page
	  */
	 Page findAppConfigItem(String ciName,Long appTypeId, int pageNo, int pageSize);
	 /**
	  * �ж�����Ӧ��ϵͳ�Ƿ���ERPӦ�ã�specialRequirementDev�ã�
	  * @Methods Name isErpRequire
	  * @Create In Nov 25, 2009 By lee
	  * @param reqId
	  * @param erpAppId
	  * @return boolean
	  */
	 boolean isErpRequire(String reqId,String erpAppId);
	 
	 /**
	  * �ж������Ƿ����������(�Ƿ���ERPӦ��)��specialRequirementDev�ã�
	  * @Methods Name isToFinancial
	  * @Create In Dec 22, 2009 By lee
	  * @param reqId
	  * @param sbuId
	  * @return boolean
	  */
	 boolean isToFinancial(String reqId,String sbuId);
	 
	 /**
	  * Ϊ�������������ɸ���ʵ��
	  * @Methods Name initSRAttachment
	  * @Create In Nov 26, 2009 By lee
	  * @param reqId void
	  */
	 void initSRAttachment(String reqId);
}
