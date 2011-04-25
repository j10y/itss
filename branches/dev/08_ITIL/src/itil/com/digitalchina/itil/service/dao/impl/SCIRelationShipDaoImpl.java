package com.digitalchina.itil.service.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.itil.actor.entity.CustomerType;
import com.digitalchina.itil.service.dao.SCIRelationShipDao;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.ServiceCatalogue;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemType;
import com.digitalchina.itil.service.entity.ServiceType;

public class SCIRelationShipDaoImpl extends BaseDao implements SCIRelationShipDao{
	
	public List<ServiceCatalogue> findRootServiceCatalogueByCust(List<Long> custIds,UserInfo userInfo) {
		Integer externalFlag = userInfo.getExternalFlag();
		CustomerType customerType = null;
		if(externalFlag==null||externalFlag.intValue()==0){
			customerType = this.findUniqueBy(CustomerType.class, "id", Long.valueOf(1));//内部用户
		}else{
			customerType = this.findUniqueBy(CustomerType.class, "id", Long.valueOf(2));//外部用户
		}
		List<ServiceCatalogue> scList = new ArrayList<ServiceCatalogue>();
		//通过这些内部客户id获取服务目录，要求返回rootFlag=1的
		for(Long custId : custIds){
			Criteria c = super.getCriteria(ServiceCatalogue.class);
			c.add(Restrictions.eq("customer.id", custId));
			c.add(Restrictions.eq("rootFlag", Integer.valueOf(1)));
			c.add(Restrictions.eq("customerType", customerType));
			c.add(Restrictions.eq("status", Integer.valueOf(1))); //不等于-1
			Date currentDate = DateUtil.getCurrentDate();
			c.add(Restrictions.le("beginDate", currentDate));
			c.add(Restrictions.ge("endDate", currentDate));
			
			List<ServiceCatalogue> list = c.list();
			if(!list.isEmpty()){
				scList.addAll(list);
			}
		}
		
		return scList;
	}

	public SCIRelationShip findRootRelationShip(
			ServiceCatalogue serviceCatalogue) {
		if (serviceCatalogue == null) {
			return null;
		}
		SCIRelationShip result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("serviceCatalogue", serviceCatalogue));
		result = (SCIRelationShip) c.uniqueResult();
		return result;
	}

	public List<SCIRelationShip> getChildShips(SCIRelationShip sciRelationShip) {
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("parentRelationShip", sciRelationShip));
		c.addOrder(Order.asc("order"));
		return c.list();
	}

	public List<SCIRelationShip> getShipsByServiceType(ServiceType serviceType,
			List<ServiceCatalogue> rootServiceCatalogues) {
		Criteria c = super.getCriteria(SCIRelationShip.class);
		if(rootServiceCatalogues!=null&&rootServiceCatalogues.size()>0){
			c.add(Restrictions.in("rootServiceCatalogue", rootServiceCatalogues));
			c.setFetchMode("rootServiceCatalogue", FetchMode.JOIN);
		}
		c.add(Restrictions.eq("typeFlag", SCIRelationShip.SCI_TYPE_ITEM));
		c.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem", FetchMode.JOIN);
		if(serviceType!=null){//add by lee for 增加对类型为空的处理 in 20091123
			c.add(Restrictions.eq("serviceItem.serviceType", serviceType));
		}//add by lee for 增加对类型为空的处理 in 20091123
		c.add(Restrictions.eq("serviceItem.deleteFlag", ServiceItem.DELETE_FALSE));
		Date currentDate = DateUtil.getCurrentDate();
		c.add(Restrictions.le("serviceItem.beginDate", currentDate));
		c.add(Restrictions.ge("serviceItem.endDate", currentDate));
		List<SCIRelationShip> list = c.list();
		return list;
	}
	
	public List<SCIRelationShip> getShipsByServiceType(String serviceItemName , ServiceItemType serviceItemType,
			ServiceType serviceType, 	List<ServiceCatalogue> rootServiceCatalogues ) {
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.in("rootServiceCatalogue", rootServiceCatalogues));
		c.setFetchMode("rootServiceCatalogue", FetchMode.JOIN);
		c.add(Restrictions.eq("typeFlag", SCIRelationShip.SCI_TYPE_ITEM));
		c.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem", FetchMode.JOIN);
		c.add(Restrictions.eq("serviceItem.deleteFlag", ServiceItem.DELETE_FALSE));
		Date currentDate = DateUtil.getCurrentDate();
		c.add(Restrictions.le("serviceItem.beginDate", currentDate));
		c.add(Restrictions.ge("serviceItem.endDate", currentDate));
		if(serviceItemType != null){
			c.add(Restrictions.eq("serviceItem.serviceItemType", serviceItemType));
		}
		if(serviceType != null){
			c.add(Restrictions.eq("serviceItem.serviceType", serviceType));
		}
		if(StringUtils.isNotBlank(serviceItemName)){
			c.add(Restrictions.like("serviceItem.name", serviceItemName,MatchMode.ANYWHERE));//模糊匹配
		}
		c.addOrder(Order.asc("serviceItem.serviceItemCode"));//按此字段排序
		c.add(Restrictions.isNotNull("serviceItem.serviceType"));//serviceType字段不为空表示此服务项正在使用
		
		List<SCIRelationShip> list = c.list();
		return list;
	}
}
