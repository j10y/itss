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
 * 需求对应服务项服务
 * @Class Name RequirementServiceItemService
 * @Author gaowen
 * @Create In May 16, 2009
 */
public interface SRServiceItemService {

	/**
	 * 通过需求类和对象ID获取对应的关系实体
	 * @Methods Name findEntityByReq
	 * @Create In May 16, 2009 By gaowen
	 * @param entityClass关联实体类名
	 * @param requireId需求实体ID
	 * @return Object
	 */
	Object findEntityByReq(String entityClass,SpecialRequirement sr);

	/**
	 * 通过需求类和对象ID获取对应的需求分析
	 * @Methods Name findProjectRequireAnalyseByReq
	 * @Create In Mar 12, 2009 By lee
	 * @param requireId
	 * @param clazz
	 * @return ProjectRequireAnalyse
	 */
	SRAnalyse findProjectRequireAnalyseByReq(SpecialRequirement sr);
	/**
	 * 通过需求类和对象ID获取对应的项目计划
	 * @Methods Name findProjectPlanByReq
	 * @Create In May 12, 2009 By gaowen
	 * @param requireId
	 * @param clazz
	 * @return ProjectPlan
	 */
	SRProjectPlan findProjectPlanByReq(SpecialRequirement sr);
	/**
	 * 通过需求类和对象ID获取对应的服务需求分析工程师
	 * @Methods Name findEngineerByReq
	 * @Create In May 17, 2009 By gaowen
	 * @param requireId
	 * @param clazz
	 * @return RequirementEngineer
	 */
	SRManager findEngineerByReq(SpecialRequirement sr);
	/**
	 * 通过需求类和对象ID获取对应服务商
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
