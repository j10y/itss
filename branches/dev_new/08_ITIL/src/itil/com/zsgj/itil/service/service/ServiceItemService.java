package com.zsgj.itil.service.service;

import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceStatus;

/**��������� 
 * @Class Name ServiceItemService
 * @Author sa
 * @Create In 2009-1-14
 */
public interface ServiceItemService {
	/**
	 * ͨ��ID��ȡ������
	 * @Methods Name findServiceItemById
	 * @Create In 2009-1-14 By lee
	 * @param id
	 * @return ServiceItem
	 */
	ServiceItem findServiceItemById(String id);
	/**
	 * ���������
	 * @Methods Name save
	 * @Create In 2009-1-14 By lee
	 * @param newServiceItem
	 * @return ServiceItem
	 */
	ServiceItem save(ServiceItem newServiceItem);
	/**
	 * �����������ɷ������ţ������޸ķ������ڷ���Ŀ¼�е�Ĭ����ϵ
	 * @Methods Name save
	 * @Create In Aug 20, 2009 By lee
	 * @param dataMap
	 * @return ServiceItem
	 */
	ServiceItem save(Map dataMap);
	/**
	 * ɾ��������,������ɾ��SCIDColumn������
	 * @Methods Name removeById
	 * @Create In 2009-1-14 By lee
	 * @param serviceItemId
	 */
	void removeById(String serviceItemId);
	/**
	 * ɾ��������
	 * @Methods Name removeByIds
	 * @Create In 2009-1-14 By lee
	 * @param serviceItemIds
	 */
	void removeByIds(String[] serviceItemIds);
	/**
	 * ��ȡ������������������͡�״̬�����ƣ�
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
	 * ͨ��������ȡ����
	 * @Methods Name getReqTables
	 * @Create In Jun 17, 2009 By lee
	 * @param tableName
	 * @param start
	 * @param pageSize
	 * @return Page
	 */
	Page getReqTables(String tableName, int start, int pageSize);
}
