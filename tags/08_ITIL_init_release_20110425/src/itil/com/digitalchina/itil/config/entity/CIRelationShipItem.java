package com.digitalchina.itil.config.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.service.entity.ServiceItem;

/**
 * 配置项关系（之前方案的实体）
 * @deprecated
 * @Class Name CIRelationShipItem
 * @Author sa
 * @Create In 2009-2-11
 */
public class CIRelationShipItem extends BaseObject{
	public static Integer TYPE_CID = 1;
	public static Integer TYPE_SCID = 0;
	private Long id;

	private CIRelationShipPic relationShip;
	
	private CIRelationShipItem parentRelationShipItem;
	
	private Set<CIRelationShipItem> childRelationShipItems = new HashSet<CIRelationShipItem>();
	
	//当前配置项，每个节点显示的是childConfigItem的名称。
	//当前配置项，每个节点显示的是childConfigItem的名称。
	private ConfigItem configItem;	//当前配置项
	
	private ServiceItem serviceItem; //当前服务类配置项
	
	private Integer typeFlag;
	
	//关系类型
	private CIRelationShipType relationShipType;

	//关系紧密程度
	private CIRelationShipGrade relationShipGrade; 
	
	//归集系数，手输数字
	private Double attachQuotiety;
	
	//排序号
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) { 
		this.order = order;
	}

	public Double getAttachQuotiety() {
		return attachQuotiety;
	}

	public void setAttachQuotiety(Double attachQuotiety) {
		this.attachQuotiety = attachQuotiety;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public CIRelationShipPic getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(CIRelationShipPic relationShip) {
		this.relationShip = relationShip;
	}

	public CIRelationShipGrade getRelationShipGrade() {
		return relationShipGrade;
	}

	public void setRelationShipGrade(CIRelationShipGrade relationShipGrade) {
		this.relationShipGrade = relationShipGrade;
	}

	public CIRelationShipType getRelationShipType() {
		return relationShipType;
	}

	public void setRelationShipType(CIRelationShipType relationShipType) {
		this.relationShipType = relationShipType;
	}

	public ConfigItem getConfigItem() {
		return configItem;
	}

	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}

	public CIRelationShipItem getParentRelationShipItem() {
		return parentRelationShipItem;
	}

	public void setParentRelationShipItem(CIRelationShipItem parentRelationShipItem) {
		this.parentRelationShipItem = parentRelationShipItem;
	}

	public Long getId() {
		return id;
	}

	public Set<CIRelationShipItem> getChildRelationShipItems() {
		return childRelationShipItems;
	}

	public void setChildRelationShipItems(
			Set<CIRelationShipItem> childRelationShipItems) {
		this.childRelationShipItems = childRelationShipItems;
	}

	public static Integer getTYPE_CID() {
		return TYPE_CID;
	}

	public static void setTYPE_CID(Integer type_cid) {
		TYPE_CID = type_cid;
	}

	public static Integer getTYPE_SCID() {
		return TYPE_SCID;
	}

	public static void setTYPE_SCID(Integer type_scid) {
		TYPE_SCID = type_scid;
	}

	public ServiceItem getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}

	public Integer getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}

	
}
