package com.zsgj.itil.service.service;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;

/**服务项服务项类型 
 * @Class Name ServiceItemTypeService
 * @Author tongjp
 * @Create In 2009-1-14
 */
public interface ServiceItemTypeService {
	/**
	 * 通过ID获取服务项类型
	 * @Methods Name findServiceItemTypeById
	 * @Create In 2009-1-14 By tongjp
	 * @param id
	 * @return ServiceItemType
	 */
	ServiceItemType findServiceItemTypeById(String id);
	/**
	 * 保存服务项类型
	 * @Methods Name save
	 * @Create In 2009-1-14 By tongjp
	 * @param serviceItemType
	 * @return ServiceItemType
	 */
	ServiceItemType saveServiceItemType(ServiceItemType serviceItemType);
	
	/**
	 * 获取全部服务项类型
	 * @author tongjp
	 * @return
	 */
	List findAllServiceItemType();
	
	/**
	 * 获取全部服务项类型
	 * @author tongjp
	 * @return
	 */
	Page findAllServiceItemType( int pageNo, int pageSize);
	/**
	 * 通过名字查找对象
	 * @param name
	 * @author tongjp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page findServiceItemTypeByName(String name, int pageNo, int pageSize);
	/**
	 * 通过id删除对象,并删除ServiceItemType下关联的所有SCIColumn
	 * @param id
	 * @author tongjp
	 */
	void removeServiceItemTypeById(Long id);
	/**
	 * 通过数组ids删除对象
	 * @param id
	 * @author tongjp
	 */
	void removeServiceItemTypeByIds(String[] ids);
	
}
