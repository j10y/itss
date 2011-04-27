package com.zsgj.itil.service.service;

import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceStatus;

/**服务项服务 
 * @Class Name ServiceItemService
 * @Author sa
 * @Create In 2009-1-14
 */
public interface ServiceItemService {
	/**
	 * 通过ID获取服务项
	 * @Methods Name findServiceItemById
	 * @Create In 2009-1-14 By lee
	 * @param id
	 * @return ServiceItem
	 */
	ServiceItem findServiceItemById(String id);
	/**
	 * 保存服务项
	 * @Methods Name save
	 * @Create In 2009-1-14 By lee
	 * @param newServiceItem
	 * @return ServiceItem
	 */
	ServiceItem save(ServiceItem newServiceItem);
	/**
	 * 保存服务项（生成服务项编号），并修改服务项在服务目录中的默至关系
	 * @Methods Name save
	 * @Create In Aug 20, 2009 By lee
	 * @param dataMap
	 * @return ServiceItem
	 */
	ServiceItem save(Map dataMap);
	/**
	 * 删除服务项,并及连删除SCIDColumn的数据
	 * @Methods Name removeById
	 * @Create In 2009-1-14 By lee
	 * @param serviceItemId
	 */
	void removeById(String serviceItemId);
	/**
	 * 删除服务项
	 * @Methods Name removeByIds
	 * @Create In 2009-1-14 By lee
	 * @param serviceItemIds
	 */
	void removeByIds(String[] serviceItemIds);
	/**
	 * 获取服务项（查找条件：类型、状态、名称）
	 * @Methods Name findServiceItems
	 * @Create In 2009-1-14 By lee
	 * @param servcieItemType
	 * @param serviceState
	 * @param serviceItemName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findServiceItems(ServiceItemType servcieItemType,
			ServiceStatus serviceState, String serviceItemName, int pageNo,
			int pageSize);
	/**
	 * 通过表明获取主表
	 * @Methods Name getReqTables
	 * @Create In Jun 17, 2009 By lee
	 * @param tableName
	 * @param start
	 * @param pageSize
	 * @return Page
	 */
	Page getReqTables(String tableName, int start, int pageSize);
}
