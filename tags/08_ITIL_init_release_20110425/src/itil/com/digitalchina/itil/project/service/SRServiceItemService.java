package com.digitalchina.itil.project.service;

import com.digitalchina.itil.config.extlist.entity.SRAnalyse;
import com.digitalchina.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.digitalchina.itil.config.extlist.entity.SRManager;
import com.digitalchina.itil.config.extlist.entity.SRProjectAnalyse;
import com.digitalchina.itil.config.extlist.entity.SRProjectPlan;
import com.digitalchina.itil.config.extlist.entity.SRServiceItem;
import com.digitalchina.itil.config.extlist.entity.SRServiceProvider;
import com.digitalchina.itil.config.extlist.entity.SRTransmisEngineer;
import com.digitalchina.itil.require.entity.SpecialRequirement;
import com.digitalchina.itil.service.entity.ServiceItem;


/**
 * �����Ӧ���������
 * @Class Name RequirementServiceItemService
 * @Author gaowen
 * @Create In May 16, 2009
 */
public interface SRServiceItemService {

	/**
	 * ͨ��������Ͷ���ID��ȡ��Ӧ�Ĺ�ϵʵ��
	 * @Methods Name findEntityByReq
	 * @Create In May 16, 2009 By gaowen
	 * @param entityClass����ʵ������
	 * @param requireId����ʵ��ID
	 * @return Object
	 */
	Object findEntityByReq(String entityClass,SpecialRequirement sr);

	/**
	 * ͨ��������Ͷ���ID��ȡ��Ӧ���������
	 * @Methods Name findProjectRequireAnalyseByReq
	 * @Create In Mar 12, 2009 By lee
	 * @param requireId
	 * @param clazz
	 * @return ProjectRequireAnalyse
	 */
	SRAnalyse findProjectRequireAnalyseByReq(SpecialRequirement sr);
	/**
	 * ͨ��������Ͷ���ID��ȡ��Ӧ����Ŀ�ƻ�
	 * @Methods Name findProjectPlanByReq
	 * @Create In May 12, 2009 By gaowen
	 * @param requireId
	 * @param clazz
	 * @return ProjectPlan
	 */
	SRProjectPlan findProjectPlanByReq(SpecialRequirement sr);
	/**
	 * ͨ��������Ͷ���ID��ȡ��Ӧ�ķ��������������ʦ
	 * @Methods Name findEngineerByReq
	 * @Create In May 17, 2009 By gaowen
	 * @param requireId
	 * @param clazz
	 * @return RequirementEngineer
	 */
	SRManager findEngineerByReq(SpecialRequirement sr);
	/**
	 * ͨ��������Ͷ���ID��ȡ��Ӧ������
	 * @Methods Name findRequirementServiceProviderByReq
	 * @Create In May 2, 2009 By gaowen
	 * @param requireId
	 * @param clazz
	 * @return RequirementServiceProvider
	 */
	SRServiceProvider findRequirementServiceProviderByReq(SpecialRequirement sr);
	/**
	 * 
	 * @Methods Name findProjectTransmissionEngineerByReq
	 * @Create In may 10, 2009 By gaowen
	 * @param requireId
	 * @param clazz
	 * @return ProjectTransmissionEngineer
	 */
	SRTransmisEngineer findProjectTransmissionEngineerByReq(SpecialRequirement sr);
	 
	SRGroupFinanceInfo findRequirementGroupFinanceInfoByReq(SpecialRequirement sr);

}
