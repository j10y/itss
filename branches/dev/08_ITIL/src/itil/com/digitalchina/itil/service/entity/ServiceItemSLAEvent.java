package com.digitalchina.itil.service.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 *  
 * @Class Name ServiceItemSLA
 * @Author sa
 * @Create In 2009-2-27
 */
public class ServiceItemSLAEvent extends BaseObject {
	private Long id;
	//��������Ŀ¼
	private ServiceCatalogueEvent serviceCatalogueEvent;
	//��������������
	private String serviceItemName;
	//������
	private ServiceItem serviceItem;
	//�����ṩʱ��
	private Double provideTime;
	//������ʱ��
	private Double problemHandleTime;
	//���������
	private ServiceItemSatisfaction satisfaction;

	/**
	 * @Return the String serviceItemName
	 */
	public String getServiceItemName() {
		return serviceItemName;
	}
	/**
	 * @Param String serviceItemName to set
	 */
	public void setServiceItemName(String serviceItemName) {
		this.serviceItemName = serviceItemName;
	}
	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	public ServiceCatalogueEvent getServiceCatalogueEvent() {
		return serviceCatalogueEvent;
	}
	public void setServiceCatalogueEvent(ServiceCatalogueEvent serviceCatalogueEvent) {
		this.serviceCatalogueEvent = serviceCatalogueEvent;
	}
	/**
	 * @Return the ServiceItem serviceItem
	 */
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	/**
	 * @Param ServiceItem serviceItem to set
	 */
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	
	/**
	 * @Return the ServiceItemSatisfaction satisfaction
	 */
	public ServiceItemSatisfaction getSatisfaction() {
		return satisfaction;
	}
	/**
	 * @Param ServiceItemSatisfaction satisfaction to set
	 */
	public void setSatisfaction(ServiceItemSatisfaction satisfaction) {
		this.satisfaction = satisfaction;
	}
	/**
	 * @Return the Double provideTime
	 */
	public Double getProvideTime() {
		return provideTime;
	}
	/**
	 * @Param Double provideTime to set
	 */
	public void setProvideTime(Double provideTime) {
		this.provideTime = provideTime;
	}
	/**
	 * @Return the Double problemHandleTime
	 */
	public Double getProblemHandleTime() {
		return problemHandleTime;
	}
	/**
	 * @Param Double problemHandleTime to set
	 */
	public void setProblemHandleTime(Double problemHandleTime) {
		this.problemHandleTime = problemHandleTime;
	}
	
}
