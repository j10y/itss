package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;


/**
 * 申请工厂明细实体
 * @Class Name RequireFactoryInfo
 * @Author zhanzy
 * @Create In 03 24, 2010
 */
public class RequireFactoryInfo extends BaseObject {
	
	private Long id ;//自动编号
	private ERP_NormalNeed requireData;//库存地申请实体
	private String factoryId;//工厂编号
	private String stockAddressId;//库存地编号
	private String wareHouseId;//仓库号/混合仓库
	private String stockAddressDesc;//库存地描述
	private String mrpFlag;//MRP标识
	private String shipCondition;//装运条件
	private String shipPoint;//装运点
	private String noOrdersTransportShipPoint;//无单运输装运点
	
	
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
