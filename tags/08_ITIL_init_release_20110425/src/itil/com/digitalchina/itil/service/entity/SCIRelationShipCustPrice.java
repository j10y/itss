package com.digitalchina.itil.service.entity;

import com.digitalchina.itil.actor.entity.CustomerType;

/**
 * �ͻ�������۸�
 * @author Administrator
 *
 */
public class SCIRelationShipCustPrice {
	private Long id;
	private Long customer;
	private CustomerType customerType;
	
	private SCIRelationShip sciRelationShip;
	private Double price;
}
