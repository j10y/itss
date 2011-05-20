package com.zsgj.itil.service.entity;

import com.zsgj.itil.actor.entity.CustomerType;

/**
 * 客户服务项价格
 * @author Administrator
 *
 */
public class SCIRelationShipCustPrice {
	private Long id;
	private Long customer;
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
	/**
	 * @Return the Long customer
	 */
	public Long getCustomer() {
		return customer;
	}
	/**
	 * @Param Long customer to set
	 */
	public void setCustomer(Long customer) {
		this.customer = customer;
	}
	/**
	 * @Return the CustomerType customerType
	 */
	public CustomerType getCustomerType() {
		return customerType;
	}
	/**
	 * @Param CustomerType customerType to set
	 */
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	/**
	 * @Return the SCIRelationShip sciRelationShip
	 */
	public SCIRelationShip getSciRelationShip() {
		return sciRelationShip;
	}
	/**
	 * @Param SCIRelationShip sciRelationShip to set
	 */
	public void setSciRelationShip(SCIRelationShip sciRelationShip) {
		this.sciRelationShip = sciRelationShip;
	}
	/**
	 * @Return the Double price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @Param Double price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	private CustomerType customerType;
	
	private SCIRelationShip sciRelationShip;
	private Double price;
}
