package com.zsgj.itil.service.service;

import java.util.List;

import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemProcess;

public interface ServiceItemProcessService {
	/**
	 * 通过ID获取服务项流程
	 * @Methods Name findServiceItemProcessById
	 * @Create In Feb 24, 2009 By lee
	 * @param id
	 * @return ServiceItemProcess
	 */
	ServiceItemProcess findServiceItemProcessById(String id);
	/**
	 * 保存服务项流程
	 * @Methods Name save
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItemProcess
	 * @return ServiceItemProcess
	 */
	ServiceItemProcess save(ServiceItemProcess serviceItemProcess);
	/**
	 * 删除服务项流程
	 * @Methods Name remove
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItemProcess void
	 */
	void remove(ServiceItemProcess serviceItemProcess);
	/**
	 * 获取服务项关联的服务项流程
	 * @Methods Name findProcessesByServiceItem
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItem
	 * @param processType
	 * @return ServiceItemProcess
	 */
	ServiceItemProcess findProcessesByServiceItemAndType(ServiceItem serviceItem,Integer processType);
	/**
	 * 获取服务项关联的服务项流程
	 * @Methods Name findProcessesByServiceItem
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItem
	 * @return List<ServiceItemProcess>
	 */
	List<ServiceItemProcess> findProcessesByServiceItem(ServiceItem serviceItem);

}
