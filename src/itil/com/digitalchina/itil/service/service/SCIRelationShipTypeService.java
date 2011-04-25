package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.SCIRelationShipType;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceType;

/**
 * �������ϵ���ͷ���
 * @Class Name SCIRelationShipTypeService
 * @Author lee
 * @Create In Mar 10, 2009
 */
public interface SCIRelationShipTypeService {
	/**
	 * �Ƿ��Ƿ������ϵ��������
	 * @Methods Name isKernelType
	 * @Create In Jun 24, 2009 By lee
	 * @param sciRelationShip
	 * @param serviceType
	 * @return boolean
	 */
	boolean isKernelType(SCIRelationShip sciRelationShip,ServiceType serviceType);
	/**
	 * �Ƿ���ָ����������
	 * @Methods Name checkType
	 * @Create In Jun 24, 2009 By lee
	 * @param sciRelationShip
	 * @param serviceType
	 * @return boolean
	 */
	boolean checkType(SCIRelationShip sciRelationShip,ServiceType serviceType);
	/**
	 * ���������������������صķ������ϵ���ͣ��ڷ��������ͱ��ʱʹ�ã�
	 * @Methods Name updateTypesBySci
	 * @Create In Jun 24, 2009 By lee
	 * @param serviceItem
	 * @param oldType
	 * @param newType void
	 */
	void updateTypesBySci(ServiceItem serviceItem,ServiceType oldType,ServiceType newType);
	/**
	 * ͨ���������ϵ�ͷ������ʹ�����ϵ����ʵ��
	 * @Methods Name createRelationShipType
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShip
	 * @param keyWord
	 * @return SCIRelationShipType
	 */
	SCIRelationShipType createRelationShipType(SCIRelationShip sciRelationShip,String keyWord);
	/**
	 * ͨ���������ϵ�ͷ������ͻ�ȡ��ϵ����ʵ��
	 * @Methods Name findTypeByRelationShipTypeAndServiceType
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShip
	 * @param serviceType
	 * @return SCIRelationShipType
	 */
	SCIRelationShipType findTypeByRelationShipTypeAndServiceType(
			SCIRelationShip sciRelationShip, ServiceType serviceType);
	/**
	 * ͨ���������ϵ�ͷ������͹ؼ��ֻ�ȡ��ϵ����ʵ��
	 * @Methods Name findTypeByRelationShipTypeAndKeyWord
	 * @Create In Mar 10, 2009 By Administrator
	 * @param sciRelationShip
	 * @param keyWord
	 * @return SCIRelationShipType
	 */
	SCIRelationShipType findTypeByRelationShipTypeAndKeyWord(
			SCIRelationShip sciRelationShip, String keyWord);
	/**
	 * ͨ����ϵ�ҵ���ϵ����
	 * @Methods Name findTypesByRelationShip
	 * @Create In Mar 10, 2009 By lee
	 * @return List<SCIRelationShipType>
	 */
	List<SCIRelationShipType> findTypesByRelationShip(SCIRelationShip sciRelationShip);
	/**
	 * �Ƿ�����������
	 * @Methods Name isGeneral
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShipType
	 * @return boolean
	 */
	//boolean isGeneral(SCIRelationShip sciRelationShip);//remove by lee for scrap function in 20090625 
	/**
	 * �Ƿ�������Ի�����
	 * @Methods Name isSpecial
	 * @Create In Mar 10, 2009 By lee
	 * @param sciRelationShip
	 * @return boolean
	 */
	//boolean isSpecial(SCIRelationShip sciRelationShip);//remove by lee for scrap function in 20090625 
	/**
	 * ����������ϵ���ͣ�ͬʱ�ı��ϼ��������ϵ����
	 * @Methods Name saveJoin
	 * @Create In Mar 10, 2009 By lee void
	 */
	void saveJoin(SCIRelationShipType sciRelationShipType);
	/**
	 * ɾ���������ϵ���ͣ�ͬʱ�ı��ϼ���ϵ����
	 * @Methods Name removeJoin
	 * @Create In Mar 10, 2009 By Administrator
	 * @param sciRelationShipType void
	 */
	void removeJoin(SCIRelationShipType sciRelationShipType);
}
