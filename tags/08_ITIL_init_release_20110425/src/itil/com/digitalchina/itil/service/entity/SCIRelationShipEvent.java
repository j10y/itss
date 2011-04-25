package com.digitalchina.itil.service.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.entity.ConfigItemType;

/**
 * 服务目录与服务目录或者服务项的关系表
 * @Class Name ServiceIndexRelation
 * 
 * //SIID (SCIID/SIID) PARENTID,FLAG
 * 
 * @Author sa
 * @Create In 2009-1-14
 */
public class SCIRelationShipEvent extends BaseObject{
	public static String SCI_TYPE_CATALOGUE = "cata";
	public static String SCI_TYPE_ITEM = "item";
	private Long id;
	private ServiceCatalogueEvent rootServiceCatalogueEvent;
	
	private SCIRelationShipEvent parentRelationShip;
	private Set<SCIRelationShipEvent> childRelationShips = new HashSet<SCIRelationShipEvent>();
	

	private ServiceCatalogue serviceCatalogueEvent;
	
	//private ConfigItemType configItemType;

	private ServiceItem serviceItem;
	
	private Double serviceItemFee;
	
	//类型标志
	private String typeFlag = SCI_TYPE_ITEM;  //默认就是服务项类型
	
	private Integer dispFlag; //显示标记;
	
	private Integer order;
	
	private String descn;
	
	//private Integer nomalFlag; //常规服务标志
	
	private SCIRelationShipNodeType sciRelationShipNodeType;
		
	/**
	 * @Return the String tempName
	 */
	public String getTempName() {
		return tempName;
	}

	/**
	 * @Param String tempName to set
	 */
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	private String tempName;//作为树服务目录的临时标识，
	public String getName() {
		if(typeFlag!=null){
			if(typeFlag.equalsIgnoreCase(SCI_TYPE_CATALOGUE)){
				if(this.getServiceCatalogueEvent()==null) return null;
				return this.getServiceCatalogueEvent().getName();
			}else if(typeFlag.equalsIgnoreCase(SCI_TYPE_ITEM)){
				if(this.getServiceItem()==null) return null;
				return this.getServiceItem().getName();
			}
		}
		if(this.getServiceItem()!=null){
			return this.getServiceItem().getName();
		}else if(this.getServiceCatalogueEvent()!=null){
			return this.getServiceCatalogueEvent().getName();
		}
		return null;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public SCIRelationShipEvent getParentRelationShip() {
		return parentRelationShip;
	}

	public void setParentRelationShip(SCIRelationShipEvent parentRelationShip) {
		this.parentRelationShip = parentRelationShip;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}


	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Set<SCIRelationShipEvent> getChildRelationShips() {
		return childRelationShips;
	}

	public void setChildRelationShips(Set<SCIRelationShipEvent> childRelationShips) {
		this.childRelationShips = childRelationShips;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((parentRelationShip == null) ? 0 : parentRelationShip.hashCode());
		result = PRIME * result + ((rootServiceCatalogueEvent == null) ? 0 : rootServiceCatalogueEvent.hashCode());
		result = PRIME * result + ((serviceCatalogueEvent == null) ? 0 : serviceCatalogueEvent.hashCode());
		result = PRIME * result + ((serviceItem == null) ? 0 : serviceItem.hashCode());
		result = PRIME * result + ((typeFlag == null) ? 0 : typeFlag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SCIRelationShipEvent other = (SCIRelationShipEvent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
//		if (parentRelationShip == null) {
//			if (other.parentRelationShip != null)
//				return false;
//		} else if (!parentRelationShip.equals(other.parentRelationShip))
//			return false;
//		if (rootServiceCatalogue == null) {
//			if (other.rootServiceCatalogue != null)
//				return false;
//		} else if (!rootServiceCatalogue.equals(other.rootServiceCatalogue))
//			return false;
//		if (serviceCatalogue == null) {
//			if (other.serviceCatalogue != null)
//				return false;
//		} else if (!serviceCatalogue.equals(other.serviceCatalogue))
//			return false;
//		if (serviceItem == null) {
//			if (other.serviceItem != null)
//				return false;
//		} else if (!serviceItem.equals(other.serviceItem))
//			return false;
		if (typeFlag == null) {
			if (other.typeFlag != null)
				return false;
		} else if (!typeFlag.equals(other.typeFlag))
			return false;
		return true;
	}

	public Double getServiceItemFee() {
		return serviceItemFee;
	}

	public void setServiceItemFee(Double serviceItemFee) {
		this.serviceItemFee = serviceItemFee;
	}

	public SCIRelationShipNodeType getSciRelationShipNodeType() {
		return sciRelationShipNodeType;
	}

	public void setSciRelationShipNodeType(
			SCIRelationShipNodeType sciRelationShipNodeType) {
		this.sciRelationShipNodeType = sciRelationShipNodeType;
	}

	public ServiceCatalogueEvent getRootServiceCatalogueEvent() {
		return rootServiceCatalogueEvent;
	}

	public void setRootServiceCatalogueEvent(
			ServiceCatalogueEvent rootServiceCatalogueEvent) {
		this.rootServiceCatalogueEvent = rootServiceCatalogueEvent;
	}

	public ServiceCatalogue getServiceCatalogueEvent() {
		return serviceCatalogueEvent;
	}

	public void setServiceCatalogueEvent(ServiceCatalogue serviceCatalogueEvent) {
		this.serviceCatalogueEvent = serviceCatalogueEvent;
	}

	public Integer getDispFlag() {
		return dispFlag;
	}

	public void setDispFlag(Integer dispFlag) {
		this.dispFlag = dispFlag;
	}

}
