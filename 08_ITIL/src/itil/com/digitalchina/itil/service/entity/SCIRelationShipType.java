package com.digitalchina.itil.service.entity;

import com.digitalchina.info.framework.dao.BaseObject;
/**
 * �����ϵ�ڵ�����������͹�ϵʵ��
 * @Class Name SCIRelationShipTable
 * @Author lee
 * @Create In Mar 9, 2009
 */
public class SCIRelationShipType extends BaseObject{
	private Long id;
	private SCIRelationShip sciRelationShip;
	private ServiceType serviceType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SCIRelationShip getSciRelationShip() {
		return sciRelationShip;
	}
	public void setSciRelationShip(SCIRelationShip sciRelationShip) {
		this.sciRelationShip = sciRelationShip;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
}
