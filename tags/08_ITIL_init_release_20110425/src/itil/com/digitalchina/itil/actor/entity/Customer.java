package com.digitalchina.itil.actor.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;
/**
 * ¿Í»§
 * @Class Name Customer
 * @Author duxh
 * @Create In Jan 27, 2010
 */
public class Customer extends BaseObject {
	private static final long serialVersionUID = 1534673676030770389L;
	private Long id;
	private String customerName; 
	private Department department;
	private UserInfo customerPrincipal;
	private String descn;
	private CustomerType customerType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public UserInfo getCustomerPrincipal() {
		return customerPrincipal;
	}
	public void setCustomerPrincipal(UserInfo customerPrincipal) {
		this.customerPrincipal = customerPrincipal;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
}
