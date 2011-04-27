package com.zsgj.itil.service.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.itil.service.entity.SCIDColumn;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.entity.ServiceStatus;
import com.zsgj.itil.service.entity.ServiceType;
import com.zsgj.itil.service.service.SCIRelationShipTypeService;
import com.zsgj.itil.service.service.ServiceItemService;

public class ServiceItemServiceImpl extends BaseDao implements ServiceItemService {
	
	private SCIRelationShipTypeService sciRelationShipTypeService;//add by lee for 级联处理服务项关系实体 in 20090624
	private MetaDataManager metaDataManager;//add by lee for 级联处理服务项关系实体 in 20090820
	public ServiceItem findServiceItemById(String id) {
		ServiceItem result = null;
		Criteria c = super.getCriteria(ServiceItem.class);
		c.setFetchMode("serviceType", FetchMode.JOIN);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		result = (ServiceItem) c.uniqueResult();
		return result;
	}
	
	public ServiceItem save(ServiceItem newServiceItem) {
		//add by lee for deal with service's serviceType in 20090624 begin
		Long oldId = newServiceItem.getId();
		if(oldId!=null){
			ServiceItem oldSci = this.findServiceItemById(oldId.toString());
			ServiceType oldType = oldSci.getServiceType();
			ServiceType newType = newServiceItem.getServiceType();
			sciRelationShipTypeService.updateTypesBySci(newServiceItem, oldType, newType);
		}
		//add by lee for deal with service's serviceType in 20090624 end
		ServiceItem result = (ServiceItem) super.save(newServiceItem);
		return result;
	}

	public Page findServiceItems(ServiceItemType servcieItemType,
			ServiceStatus serviceState, String serviceItemName, int pageNo,
			int pageSize) {
		Criteria c = super.createCriteria(ServiceItem.class);
		if(StringUtils.isNotBlank(serviceItemName)){
			c.add(Restrictions.ilike("name", serviceItemName, MatchMode.ANYWHERE));
		}
		if(servcieItemType!=null){
			c.add(Restrictions.eq("serviceItemType", servcieItemType));
		}
		if(serviceState!=null){
			c.add(Restrictions.eq("serviceStatus", serviceState));
		}
		c.addOrder(Order.desc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public void removeById(String dataId) {
		if(dataId!=null&&!dataId.equals("")){
			ServiceItem serviceItem=this.findServiceItemById(dataId);
			ServiceItemUserTable serviceItemUserTable = (ServiceItemUserTable) super.findUniqueBy(ServiceItemUserTable.class,"serviceItem", serviceItem);
			if(serviceItemUserTable!=null){
				super.remove(serviceItemUserTable);
			}
			List<ServiceItemProcess> serviceItemProcesses = super.findBy(ServiceItemProcess.class, "serviceItem", serviceItem);
			for(ServiceItemProcess process : serviceItemProcesses){
				super.remove(process);
			}
			Criteria c = super.createCriteria(SCIDColumn.class);
			c.add(Restrictions.eq("serviceItem",serviceItem));
			List<SCIDColumn> list=c.list();
			for(SCIDColumn sCIDColumn:list){
				this.remove(sCIDColumn);
			}
			this.remove(serviceItem);
		}else{
			throw new ServiceException("请选着要删除的选项！");
		}
	}

	public void removeByIds(String[] serviceItemIds) {
		if(serviceItemIds!=null&&serviceItemIds.length>0){
			for(String idstr : serviceItemIds){
				this.removeById(idstr);
			}
		}else{
			throw new ServiceException("请选着要删除的选项！");
		}
		
	}
	public SCIRelationShipTypeService getSciRelationShipTypeService() {
		return sciRelationShipTypeService;
	}

	public void setSciRelationShipTypeService(
			SCIRelationShipTypeService sciRelationShipTypeService) {
		this.sciRelationShipTypeService = sciRelationShipTypeService;
	}
	public Page getReqTables(String tableName, int start, int pageSize) {
		Criteria c = super.createCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("userScidFlag", Integer.valueOf(1)));
		if(StringUtils.isNotBlank(tableName)){
			c.add(Restrictions.disjunction()
					.add(Restrictions.ilike("tableName", tableName, MatchMode.ANYWHERE)) //忽略大小写
					.add(Restrictions.ilike("tableCnName", tableName, MatchMode.ANYWHERE)));
		}
		Page page = super.pagedQuery(c, start, pageSize);
		return page;
	}

	public ServiceItem save(Map dataMap) {
		ServiceItem newServiceItem =(ServiceItem) BeanUtil.getObject(dataMap, ServiceItem.class);
		Long oldId = newServiceItem.getId();
		if(oldId!=null){
			ServiceItem oldSci = this.findServiceItemById(oldId.toString());
			ServiceType oldType = oldSci.getServiceType();
			ServiceType newType = newServiceItem.getServiceType();
			sciRelationShipTypeService.updateTypesBySci(newServiceItem, oldType, newType);
		}
		ServiceItem curItem = (ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, dataMap);
		return curItem;
	}

	public MetaDataManager getMetaDataManager() {
		return metaDataManager;
	}

	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}

}
