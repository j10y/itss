package com.digitalchina.itil.service.service;

import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.ServiceCatalogue;

public interface ServiceCatalogueService {
	/**
	 * ��������Ŀ¼
	 * @Methods Name findServiceCatalogue
	 * @Create In 2009-1-15 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findServiceCatalogue(Map params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	/**
	 * �������Ŀ¼
	 * @Methods Name save
	 * @Create In 2009-1-14 By lee
	 * @param serviceCatalogue
	 * @return ServiceCatalogue
	 */
	ServiceCatalogue save(ServiceCatalogue serviceCatalogue);
	
	/**
	 * ͨ��ID��ȡ����Ŀ¼
	 * @Methods Name findServiceCatalogueById
	 * @Create In 2009-1-14 By lee
	 * @param id
	 * @return ServiceCatalogue
	 */
	ServiceCatalogue findServiceCatalogueById(String id);
	
	/**
	 * ɾ������Ŀ¼
	 * @Methods Name removeServiceCatalogue
	 * @Create In 2009-1-15 By sa
	 * @param scIds void
	 */
	void removeServiceCatalogue(String scIds);
	/**
	 * ���������۸�SCIRelationShip
	 * @Methods Name saveSCIRelationShip
	 * @Create In Jan 16, 2009 By Administrator
	 * @param sciRelationShip
	 * @return SCIRelationShip
	 */
	SCIRelationShip saveSCIRelationShip(SCIRelationShip sciRelationShip);
	/**
	 * ����id����SCIRelationShip
	 * @Methods Name findSCIRelationShip
	 * @Create In Jan 16, 2009 By Administrator
	 * @param id
	 * @return SCIRelationShip
	 */
	SCIRelationShip findSCIRelationShip(String id);
}
