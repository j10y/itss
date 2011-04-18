package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemProcess;

public interface ServiceItemProcessService {
	/**
	 * ͨ��ID��ȡ����������
	 * @Methods Name findServiceItemProcessById
	 * @Create In Feb 24, 2009 By lee
	 * @param id
	 * @return ServiceItemProcess
	 */
	ServiceItemProcess findServiceItemProcessById(String id);
	/**
	 * �������������
	 * @Methods Name save
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItemProcess
	 * @return ServiceItemProcess
	 */
	ServiceItemProcess save(ServiceItemProcess serviceItemProcess);
	/**
	 * ɾ������������
	 * @Methods Name remove
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItemProcess void
	 */
	void remove(ServiceItemProcess serviceItemProcess);
	/**
	 * ��ȡ����������ķ���������
	 * @Methods Name findProcessesByServiceItem
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItem
	 * @param processType
	 * @return ServiceItemProcess
	 */
	ServiceItemProcess findProcessesByServiceItemAndType(ServiceItem serviceItem,Integer processType);
	/**
	 * ��ȡ����������ķ���������
	 * @Methods Name findProcessesByServiceItem
	 * @Create In Feb 24, 2009 By lee
	 * @param serviceItem
	 * @return List<ServiceItemProcess>
	 */
	List<ServiceItemProcess> findProcessesByServiceItem(ServiceItem serviceItem);

}
