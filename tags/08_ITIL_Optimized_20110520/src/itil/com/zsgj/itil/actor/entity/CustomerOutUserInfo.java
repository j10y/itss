package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 外部客户用户表
 * @Class Name CustomerOutUserInfo
 * @Author sa
 * @Create In 2009-3-21
 */
public class CustomerOutUserInfo extends BaseObject {
	private Long id;
	private Customer customerOut; //主要同一个用户的所属外部客户唯一
	private UserInfo userInfo;
	
	public Customer getCustomerOut() {
		return customerOut;
	}
	public void setCustomerOut(Customer customerOut) {
		this.customerOut = customerOut;
	}
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
}
