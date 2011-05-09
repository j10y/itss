package com.zsgj.itil.service.dao;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceType;

public interface SCIRelationShipDao {
	
	/**
	 * 提供客户id获取所有的根服务目录
	 * @Methods Name findRootServiceCatalogueByCust
	 * @Create In Nov 18, 2009 By lee
	 * @param custIds
	 * @param userInfo
	 * @return List<ServiceCatalogue>
	 */
	List<ServiceCatalogue> findRootServiceCatalogueByCust(List<Long> custIds, UserInfo userInfo);
	/**
	 * 获取服务目录根节点
	 * @Methods Name findRootRelationShip
	 * @Create In Nov 18, 2009 By lee
	 * @param serviceCatalogue
	 * @return SCIRelationShip
	 */
	SCIRelationShip findRootRelationShip(ServiceCatalogue serviceCatalogue);
	
	/**
	 * 获取子关系集合
	 * @Methods Name getChildShips
	 * @Create In Nov 18, 2009 By lee
	 * @param sciRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getChildShips(SCIRelationShip sciRelationShip);
	
	/**
	 * 通过服务类型获取服务目录中服务项
	 * @Methods Name getShipsByServiceType
	 * @Create In Nov 19, 2009 By lee
	 * @param serviceType
	 * @param rootServiceCatalogues
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getShipsByServiceType(ServiceType serviceType, List<ServiceCatalogue> rootServiceCatalogues);
	
	/**
	 * 获取服务目录中服务项
	 * @Methods Name getShipsByServiceType
	 * @Create In 15 7, 2010 By zhangzy
	 * @param rootServiceCatalogues
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getShipsByServiceType(String serviceItemName, ServiceItemType serviceItemType, ServiceType serviceType, List<ServiceCatalogue> rootServiceCatalogues);
}
