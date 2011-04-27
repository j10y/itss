package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;


/**
 * ���빤����ϸʵ��
 * @Class Name RequireFactoryInfo
 * @Author zhanzy
 * @Create In 03 24, 2010
 */
public class RequireFactoryInfo extends BaseObject {
	
	private Long id ;//�Զ����
	private ERP_NormalNeed requireData;//��������ʵ��
	private String factoryId;//�������
	private String stockAddressId;//���ر��
	private String wareHouseId;//�ֿ��/��ϲֿ�
	private String stockAddressDesc;//��������
	private String mrpFlag;//MRP��ʶ
	private String shipCondition;//װ������
	private String shipPoint;//װ�˵�
	private String noOrdersTransportShipPoint;//�޵�����װ�˵�
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public ERP_NormalNeed getRequireData() {
		return requireData;
	}
	public void setRequireData(ERP_NormalNeed requireData) {
		this.requireData = requireData;
	}

	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getStockAddressId() {
		return stockAddressId;
	}
	public void setStockAddressId(String stockAddressId) {
		this.stockAddressId = stockAddressId;
	}
	public String getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
	public String getStockAddressDesc() {
		return stockAddressDesc;
	}
	public void setStockAddressDesc(String stockAddressDesc) {
		this.stockAddressDesc = stockAddressDesc;
	}
	public String getMrpFlag() {
		return mrpFlag;
	}
	public void setMrpFlag(String mrpFlag) {
		this.mrpFlag = mrpFlag;
	}
	public String getShipCondition() {
		return shipCondition;
	}
	public void setShipCondition(String shipCondition) {
		this.shipCondition = shipCondition;
	}
	public String getShipPoint() {
		return shipPoint;
	}
	public void setShipPoint(String shipPoint) {
		this.shipPoint = shipPoint;
	}
	public String getNoOrdersTransportShipPoint() {
		return noOrdersTransportShipPoint;
	}
	public void setNoOrdersTransportShipPoint(String noOrdersTransportShipPoint) {
		this.noOrdersTransportShipPoint = noOrdersTransportShipPoint;
	}
	
	

}
