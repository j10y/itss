package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.SCIRelationShipType;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceType;

/**
 * 服务项关系类型服务
 * @Class Name SCIRelationShipTypeService
 * @Author lee
 * @Create In Mar 10, 2009
 */
public interface SCIRelationShipTypeService {
	/**
	 * 是否是服务项关系核心类型
	 * @Methods Name isKernelType
	 * @Create In Jun 24, 2009 By lee
	 * @param sciRelationShip
	 * @param serviceType
	 * @return boolean
	 */
	boolean isKernelType(SCIRelationShip sciRelationShip,ServiceType serviceType);
	/**
	 * 是否是指定服务类型
	 * @Methods Name checkType
	 * @Create In Jun 24, 2009 By lee
	 * @param sciRelationShip
	 * @param serviceType
	 * @return boolean
	 */
	boolean checkType(SCIRelationShip sciRelationShip,ServiceType serviceType);
	/**
	 * 更新所有与参数服务项相关的服务项关系类型（在服务项类型变更时使用）
	 * @Methods Name updateTypesBySci
	 * @Create In Jun 24, 2009 By lee
	 * @param serviceItem
	 * @param oldType
	 * @param newType void
	 */
	void updateTypesBySci(ServiceItem serviceItem,ServiceType oldType,ServiceType newType);
	/**
	 * 通过服务项关系和服务类型创建关系类型实体
	 * @Methods Name createRelationShipType
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShip
	 * @param keyWord
	 * @return SCIRelationShipType
	 */
	SCIRelationShipType createRelationShipType(SCIRelationShip sciRelationShip,String keyWord);
	/**
	 * 通过服务项关系和服务类型获取关系类型实体
	 * @Methods Name findTypeByRelationShipTypeAndServiceType
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShip
	 * @param serviceType
	 * @return SCIRelationShipType
	 */
	SCIRelationShipType findTypeByRelationShipTypeAndServiceType(
			SCIRelationShip sciRelationShip, ServiceType serviceType);
	/**
	 * 通过服务项关系和服务类型关键字获取关系类型实体
	 * @Methods Name findTypeByRelationShipTypeAndKeyWord
	 * @Create In Mar 10, 2009 By Administrator
	 * @param sciRelationShip
	 * @param keyWord
	 * @return SCIRelationShipType
	 */
	SCIRelationShipType findTypeByRelationShipTypeAndKeyWord(
			SCIRelationShip sciRelationShip, String keyWord);
	/**
	 * 通过关系找到关系类型
	 * @Methods Name findTypesByRelationShip
	 * @Create In Mar 10, 2009 By lee
	 * @return List<SCIRelationShipType>
	 */
	List<SCIRelationShipType> findTypesByRelationShip(SCIRelationShip sciRelationShip);
	/**
	 * 是否包含常规服务
	 * @Methods Name isGeneral
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShipType
	 * @return boolean
	 */
	//boolean isGeneral(SCIRelationShip sciRelationShip);//remove by lee for scrap function in 20090625 
	/**
	 * 是否包含个性化服务
	 * @Methods Name isSpecial
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShip
	 * @return boolean
	 */
	//boolean isSpecial(SCIRelationShip sciRelationShip);//remove by lee for scrap function in 20090625 
	/**
	 * 保存服务项关系类型，同时改变上级服务项关系类型
	 * @Methods Name saveJoin
	 * @Create In Mar 10, 2009 By lee void
	 */
	void saveJoin(SCIRelationShipType sciRelationShipType);
	/**
	 * 删除服务项关系类型，同时改变上级关系类型
	 * @Methods Name removeJoin
	 * @Create In Mar 10, 2009 By Administrator
	 * @param sciRelationShipType void
	 */
	void removeJoin(SCIRelationShipType sciRelationShipType);
}
