package com.zsgj.itil.require.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.extci.entity.Application;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.extlist.entity.SRGroupFinanceInfo;
import com.zsgj.itil.config.extlist.entity.SRManager;
import com.zsgj.itil.config.extlist.entity.SRProjectNotice;
import com.zsgj.itil.config.extlist.entity.SRServiceItem;
import com.zsgj.itil.config.extlist.entity.SRServiceProvider;
import com.zsgj.itil.config.extlist.entity.SRTransmisEngineer;
import com.zsgj.itil.config.extlist.entity.SRprojectContracts;
import com.zsgj.itil.require.dao.SRDao;

public class SRDaoImpl extends BaseDao implements SRDao{

	public SRprojectContracts findContractByReq(String reqId) {
		SRprojectContracts  result= null;
		if(StringUtils.isNotBlank(reqId)){
			Criteria criteria = super.createCriteria(SRprojectContracts .class);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(reqId)));
			result= (SRprojectContracts ) criteria.uniqueResult();
		}
		return result;
	}

	public SRProjectNotice findNoticeByReq(String reqId) {
		SRProjectNotice result= null;
		if(StringUtils.isNotBlank(reqId)){
			Criteria criteria = super.createCriteria(SRProjectNotice.class);
			criteria.setFetchMode("notice", FetchMode.JOIN);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(reqId)));
			result= (SRProjectNotice) criteria.uniqueResult();
		}
		return result;
	}

	public SRServiceItem findRequirementService(String  reqId) {
		SRServiceItem result= null;
		if(StringUtils.isNotBlank(reqId)){
			Criteria criteria = super.createCriteria(SRServiceItem.class);
			criteria.setFetchMode("serviceItem", FetchMode.JOIN);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(reqId)));
			result= (SRServiceItem) criteria.uniqueResult();
		}
		return result;
	}

	public SRServiceProvider findRequirementServiceProvider(String reqId) {
		SRServiceProvider result= null;
		if(StringUtils.isNotBlank(reqId)){
			Criteria criteria = super.createCriteria(SRServiceProvider.class);
			criteria.setFetchMode("serviceProviderType", FetchMode.JOIN);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(reqId)));
			result= (SRServiceProvider) criteria.uniqueResult();
		}
		return result;
	}

	public Page findServiceEngineerIn(String serviceId, int pageNo, int pageSize) {
		Criteria c = super.createCriteria(ServiceEngineer.class);
		DeliveryTeam serviceProviderIn = new DeliveryTeam();
		serviceProviderIn.setId(Long.parseLong(serviceId));
		c.add(Restrictions.eq("serviceProviderIn", serviceProviderIn));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	/**
	 * 获得外部服务商工程师
	 */
	@SuppressWarnings("unchecked")
/***
 *   remove by zhangzy in 20100707 for 方法没有被用到

	public Page findServiceEngineerOut(String serviceId, int pageNo, int pageSize) {
		Criteria c = super.createCriteria(ServiceEngineerOut.class);
		ServiceProviderOut serviceProviderOut = new ServiceProviderOut();
		serviceProviderOut.setId(Long.parseLong(serviceId));
		c.add(Restrictions.eq("serviceProviderOut", serviceProviderOut));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
 * 
 */		

	public SRTransmisEngineer findTransmissionEngineer(String reqId) {
		SRTransmisEngineer result = null;
		if(StringUtils.isNotBlank(reqId)){
			Criteria criteria = super.createCriteria(SRTransmisEngineer.class);
			criteria.setFetchMode("userInfo", FetchMode.JOIN);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(reqId)));
			result= (SRTransmisEngineer) criteria.uniqueResult();
		}
		return result;
	}

	public SRGroupFinanceInfo findGroupGinanceInfo(String reqId) {
		SRGroupFinanceInfo result= null;
		if(StringUtils.isNotBlank(reqId)){
			Criteria criteria = super.createCriteria(SRGroupFinanceInfo.class);
			criteria.setFetchMode("financeType", FetchMode.JOIN);
			criteria.setFetchMode("batchType", FetchMode.JOIN);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(reqId)));
			result= (SRGroupFinanceInfo) criteria.uniqueResult();
		}
		return result;
	}

	public SRManager findSRManager(String dataId) {
		SRManager result= null;
		if(StringUtils.isNotBlank(dataId)){
			Criteria criteria = super.createCriteria(SRManager.class);
			criteria.setFetchMode("mainManager", FetchMode.JOIN);
			criteria.add(Restrictions.eq("specialRequire.id", Long.valueOf(dataId)));
			result= (SRManager) criteria.uniqueResult();
		}
		return result;
	}

	public Page findAppConfigItem(String ciName,Long appTypeId, int pageNo, int pageSize) {
		Criteria c1 = super.createCriteria(Application.class);
		c1.add(Restrictions.eq("type.id", appTypeId));
		c1.setProjection(Projections.property("this.cisn"));
		List<String> appCisns = c1.list();	//得到应用系统类配置项编号
		
		Criteria c2 = super.createCriteria(ConfigItem.class);
		c2.add(Restrictions.in("this.cisn", appCisns));
		c2.add(Restrictions.eq("status", ConfigItem.VALID_STATUS));
		if(StringUtils.isNotBlank(ciName)){
			c2.add(Restrictions.like("name", ciName, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c2, pageNo, pageSize);
		return page;
	}
}
