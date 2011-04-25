package com.digitalchina.itil.service.service;

import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.ServiceCatalogue;

public interface ServiceCatalogueService {
	/**
	 * 搜索服务目录
	 * @Methods Name findServiceCatalogue
	 * @Create In 2009-1-15 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findServiceCatalogue(Map params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	/**
	 * 保存服务目录
	 * @Methods Name save
	 * @Create In 2009-1-14 By lee
	 * @param serviceCatalogue
	 * @return ServiceCatalogue
	 */
	ServiceCatalogue save(ServiceCatalogue serviceCatalogue);
	
	/**
	 * 通过ID获取服务目录
	 * @Methods Name findServiceCatalogueById
	 * @Create In 2009-1-14 By lee
	 * @param id
	 * @return ServiceCatalogue
	 */
	ServiceCatalogue findServiceCatalogueById(String id);
	
	/**
	 * 删除服务目录
	 * @Methods Name removeServiceCatalogue
	 * @Create In 2009-1-15 By sa
	 * @param scIds void
	 */
	void removeServiceCatalogue(String scIds);
	/**
	 * 保存服务项价格SCIRelationShip
	 * @Methods Name saveSCIRelationShip
	 * @Create In Jan 16, 2009 By Administrator
	 * @param sciRelationShip
	 * @return SCIRelationShip
	 */
	SCIRelationShip saveSCIRelationShip(SCIRelationShip sciRelationShip);
	/**
	 * 根据id查找SCIRelationShip
	 * @Methods Name findSCIRelationShip
	 * @Create In Jan 16, 2009 By Administrator
	 * @param id
	 * @return SCIRelationShip
	 */
	SCIRelationShip findSCIRelationShip(String id);
}
