package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 客户联系人
 * @Class Name CustomerLinkman
 * @Author duxh
 * @Create In Jan 27, 2010
 */
public class CustomerLinkman extends BaseObject {
	private static final long serialVersionUID = -8660713915589087564L;
	private Long id;
	private UserInfo userInfo;
	private Customer customer;
	private String descn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
