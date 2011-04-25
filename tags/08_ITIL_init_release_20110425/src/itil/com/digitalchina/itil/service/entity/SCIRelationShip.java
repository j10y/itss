package com.digitalchina.itil.service.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.entity.ConfigItemType;

/**
 * ����Ŀ¼�����Ŀ¼���߷�����Ĺ�ϵ��
 * @Class Name ServiceIndexRelation
 * 
 * //SIID (SCIID/SIID) PARENTID,FLAG
 * 
 * @Author sa
 * @Create In 2009-1-14
 */
public class SCIRelationShip extends BaseObject{
	public static String SCI_TYPE_CATALOGUE = "cata";
	public static String SCI_TYPE_ITEM = "item";
	
	public static Integer DISP_FLAG_TRUE = 1;
	public static Integer DISP_FLAG_FALSE = 0;
	
	private Long id;
	private ServiceCatalogue rootServiceCatalogue;
	
	private SCIRelationShip parentRelationShip;
	private Set<SCIRelationShip> childRelationShips = new HashSet<SCIRelationShip>();
	

	private ServiceCatalogue serviceCatalogue;
	
	//private ConfigItemType configItemType;

	private ServiceItem serviceItem;
	
	private Double serviceItemFee;
	
	//���ͱ�־
	private String typeFlag = SCI_TYPE_ITEM;  //Ĭ�Ͼ��Ƿ���������
	
	//�ӹ�˾Ŀ¼�Ľڵ�dispFlag=1�����ڵ���ʾ������Ŀ¼����ͬ�ڵ㲻��ʾ
	//�ӹ�˾Ŀ¼�Ľڵ�dispFlag=0�����ڵ㲻��ʾ������Ŀ¼����ͬ�ڵ�Ҳ����ʾ
	private Integer dispFlag; //��ʾ���;
	
	private Integer order;
	
	private String descn;
	
	//private Integer nomalFlag; //��������־
	
	//private SCIRelationShipNodeType sciRelationShipNodeType;
		
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
	private String tempName;//��Ϊ������Ŀ¼����ʱ��ʶ��
	public String getName() {
		if(typeFlag!=null){
			if(typeFlag.equalsIgnoreCase(SCI_TYPE_CATALOGUE)){
				if(this.getServiceCatalogue()==null) return null;
				return this.getServiceCatalogue().getName();
			}else if(typeFlag.equalsIgnoreCase(SCI_TYPE_ITEM)){
				if(this.getServiceItem()==null) return null;
				return this.getServiceItem().getName();
			}
		}
		if(this.getServiceItem()!=null){
			return this.getServiceItem().getName();
		}else if(this.getServiceCatalogue()!=null){
			return this.getServiceCatalogue().getName();
		}
		return null;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public SCIRelationShip getParentRelationShip() {
		return parentRelationShip;
	}

	public void setParentRelationShip(SCIRelationShip parentRelationShip) {
		this.parentRelationShip = parentRelationShip;
	}

	public ServiceCatalogue getServiceCatalogue() {
		return serviceCatalogue;
	}

	public void setServiceCatalogue(ServiceCatalogue serviceCatalogue) {
		this.serviceCatalogue = serviceCatalogue;
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

	public ServiceCatalogue getRootServiceCatalogue() {
		return rootServiceCatalogue;
	}

	public void setRootServiceCatalogue(ServiceCatalogue rootServiceCatalogue) {
		this.rootServiceCatalogue = rootServiceCatalogue;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Set<SCIRelationShip> getChildRelationShips() {
		return childRelationShips;
	}

	public void setChildRelationShips(Set<SCIRelationShip> childRelationShips) {
		this.childRelationShips = childRelationShips;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((parentRelationShip == null) ? 0 : parentRelationShip.hashCode());
		result = PRIME * result + ((rootServiceCatalogue == null) ? 0 : rootServiceCatalogue.hashCode());
		result = PRIME * result + ((serviceCatalogue == null) ? 0 : serviceCatalogue.hashCode());
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
		final SCIRelationShip other = (SCIRelationShip) obj;
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

//	public SCIRelationShipNodeType getSciRelationShipNodeType() {
//		return sciRelationShipNodeType;
//	}
//
//	public void setSciRelationShipNodeType(
//			SCIRelationShipNodeType sciRelationShipNodeType) {
//		this.sciRelationShipNodeType = sciRelationShipNodeType;
//	}

	public Integer getDispFlag() {
		return dispFlag;
	}

	public void setDispFlag(Integer dispFlag) {
		this.dispFlag = dispFlag;
	}

}
