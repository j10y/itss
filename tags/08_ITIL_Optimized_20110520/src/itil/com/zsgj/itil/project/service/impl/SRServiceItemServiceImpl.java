package com.zsgj.itil.project.service.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.itil.config.extlist.entity.SRAnalyse;
import com.zsgj.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.zsgj.itil.config.extlist.entity.SRManager;
import com.zsgj.itil.config.extlist.entity.SRProjectAnalyse;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;
import com.zsgj.itil.config.extlist.entity.SRServiceItem;
import com.zsgj.itil.config.extlist.entity.SRServiceProvider;
import com.zsgj.itil.config.extlist.entity.SRTransmisEngineer;
import com.zsgj.itil.project.service.SRServiceItemService;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.service.entity.ServiceItem;

public class SRServiceItemServiceImpl extends BaseDao implements SRServiceItemService {

	public Object findEntityByReq(String entityClass, SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(toClass(entityClass));
		criteria.add(Restrictions.eq("specialRequire", sr));
		
		Object  obj = criteria.uniqueResult();
		return obj;
	}

	/**
	 * 通过需求类和对象ID获取对应的服务项
	 * @Methods Name findServiceItemByReq
	 * @Create In 5 4, 2009 By gaowen
	 * @param requireId
	 * @return ServiceItem
	 */
	public ServiceItem findServiceItemByReqNew(SpecialRequirement sr) {
		
		Criteria criteria = super.createCriteria(SRServiceItem.class);
		criteria.setFetchMode("serviceItem", FetchMode.JOIN);
		criteria.add(Restrictions.eq("specialRequire", sr));
		//criteria.add(Restrictions.eq("requirementClass", clazz));
		SRServiceItem srsi = (SRServiceItem) criteria.uniqueResult();
		ServiceItem serviceItem = null;
		if(srsi!=null){
			serviceItem = srsi.getServiceItem();
		}
		return serviceItem;
	}
	
	public SRAnalyse findProjectRequireAnalyseByReq(
			SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(SRAnalyse.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
	
		SRAnalyse pra = (SRAnalyse) criteria.uniqueResult();
		return pra;
	}
	
	public SRProjectPlan findProjectPlanByReq(SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(SRProjectPlan.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
		
		SRProjectPlan projectPlan = (SRProjectPlan) criteria.uniqueResult();
		return projectPlan;
	}
	private Class toClass(String className){
		Class clazz = null;
		if(className!=null&&"".equals(className)){
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				System.out.println("类"+className+"不存在!");
				e.printStackTrace();
			}
		}
		return clazz;
	}

	public SRManager findEngineerByReq(SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(SRManager.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
	
		SRManager engineer = (SRManager) criteria.uniqueResult();
		return engineer;
	}

	public SRServiceProvider findRequirementServiceProviderByReq(
			SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(SRServiceProvider.class);
		criteria.setFetchMode("serviceProviderId", FetchMode.JOIN);
		criteria.add(Restrictions.eq("specialRequire", sr));
	
		SRServiceProvider result = (SRServiceProvider) criteria.uniqueResult();
		return result;
	}

	public SRTransmisEngineer findProjectTransmissionEngineerByReq(
			SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(SRTransmisEngineer.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
	
		SRTransmisEngineer pte = (SRTransmisEngineer) criteria.uniqueResult();
		return pte;
	}

	public SRGroupFinanceInfo findRequirementGroupFinanceInfoByReq(
			SpecialRequirement sr) {
		Criteria criteria = super.createCriteria(SRGroupFinanceInfo.class);
		criteria.add(Restrictions.eq("specialRequire", sr));
	
		SRGroupFinanceInfo pte = (SRGroupFinanceInfo) criteria.uniqueResult();
		return pte;
	}
    

	
}
