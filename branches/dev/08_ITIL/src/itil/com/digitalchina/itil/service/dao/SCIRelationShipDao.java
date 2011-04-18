package com.digitalchina.itil.service.dao;

import java.util.List;

import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.ServiceCatalogue;
import com.digitalchina.itil.service.entity.ServiceItemType;
import com.digitalchina.itil.service.entity.ServiceType;
import com.digitalchina.info.framework.dao.support.Page;

public interface SCIRelationShipDao {
	
	/**
	 * �ṩ�ͻ�id��ȡ���еĸ�����Ŀ¼
	 * @Methods Name findRootServiceCatalogueByCust
	 * @Create In Nov 18, 2009 By lee
	 * @param custIds
	 * @param userInfo
	 * @return List<ServiceCatalogue>
	 */
	List<ServiceCatalogue> findRootServiceCatalogueByCust(List<Long> custIds, UserInfo userInfo);
	/**
	 * ��ȡ����Ŀ¼���ڵ�
	 * @Methods Name findRootRelationShip
	 * @Create In Nov 18, 2009 By lee
	 * @param serviceCatalogue
	 * @return SCIRelationShip
	 */
	SCIRelationShip findRootRelationShip(ServiceCatalogue serviceCatalogue);
	
	/**
	 * ��ȡ�ӹ�ϵ����
	 * @Methods Name getChildShips
	 * @Create In Nov 18, 2009 By lee
	 * @param sciRelationShip
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getChildShips(SCIRelationShip sciRelationShip);
	
	/**
	 * ͨ���������ͻ�ȡ����Ŀ¼�з�����
	 * @Methods Name getShipsByServiceType
	 * @Create In Nov 19, 2009 By lee
	 * @param serviceType
	 * @param rootServiceCatalogues
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getShipsByServiceType(ServiceType serviceType, List<ServiceCatalogue> rootServiceCatalogues);
	
	/**
	 * ��ȡ����Ŀ¼�з�����
	 * @Methods Name getShipsByServiceType
	 * @Create In 15 7, 2010 By zhangzy
	 * @param rootServiceCatalogues
	 * @return List<SCIRelationShip>
	 */
	List<SCIRelationShip> getShipsByServiceType(String serviceItemName, ServiceItemType serviceItemType, ServiceType serviceType, List<ServiceCatalogue> rootServiceCatalogues);
}
