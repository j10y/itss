package com.zsgj.itil.service.service;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;

/**��������������� 
 * @Class Name ServiceItemTypeService
 * @Author tongjp
 * @Create In 2009-1-14
 */
public interface ServiceItemTypeService {
	/**
	 * ͨ��ID��ȡ����������
	 * @Methods Name findServiceItemTypeById
	 * @Create In 2009-1-14 By tongjp
	 * @param id
	 * @return ServiceItemType
	 */
	ServiceItemType findServiceItemTypeById(String id);
	/**
	 * �������������
	 * @Methods Name save
	 * @Create In 2009-1-14 By tongjp
	 * @param serviceItemType
	 * @return ServiceItemType
	 */
	ServiceItemType saveServiceItemType(ServiceItemType serviceItemType);
	
	/**
	 * ��ȡȫ������������
	 * @author tongjp
	 * @return
	 */
	List findAllServiceItemType();
	
	/**
	 * ��ȡȫ������������
	 * @author tongjp
	 * @return
	 */
	Page findAllServiceItemType( int pageNo, int pageSize);
	/**
	 * ͨ�����ֲ��Ҷ���
	 * @param name
	 * @author tongjp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page findServiceItemTypeByName(String name, int pageNo, int pageSize);
	/**
	 * ͨ��idɾ������,��ɾ��ServiceItemType�¹���������SCIColumn
	 * @param id
	 * @author tongjp
	 */
	void removeServiceItemTypeById(Long id);
	/**
	 * ͨ������idsɾ������
	 * @param id
	 * @author tongjp
	 */
	void removeServiceItemTypeByIds(String[] ids);
	
}
