package com.digitalchina.itil.actor.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.Customer;
import com.digitalchina.itil.actor.entity.CustomerOutUserInfo;

public interface CustomerOutUserService {
	
	/**
	 * 通过名称获取外部客户，Combo分页查询使用
	 * @Methods Name findCustOutByCustName
	 * @Create In Apr 29, 2009 By sa
	 * @param custName
	 * @param orderProp
	 * @param isAsc
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findCustOutByCustName(String custName, String orderProp, boolean isAsc, int pageNo, int pageSize);
	/**
	 * 获取外部客户的所有用户，同时用户不是锁定的
	 * @Methods Name findCustomerOutUser
	 * @Create In 2009-3-21 By sa
	 * @param params
	 * @param pageNo
	 * @param pagesize
	 * @param propName
	 * @param isAsc
	 * @return Page
	 */
	Page findCustomerOutUser(Map params, int pageNo, int pagesize, String propName, boolean isAsc);
	
	/**
	 * 判断当前的外部用户是否已经存在
	 * @Methods Name findCustomerOutUser
	 * @Create In 2009-3-21 By sa
	 * @param custOut
	 * @param userInfo
	 * @return CustomerOutUserInfo
	 */
	List<CustomerOutUserInfo> findCustomerOutUser(Customer custOut, UserInfo userInfo);
	
	/**
	 * 保存外部用户信息
	 * @Methods Name saveCustomerOutUserInfo
	 * @Create In 2009-3-21 By sa
	 * @param custOut
	 * @param userInfo
	 * @return CustomerOutUserInfo
	 */
	CustomerOutUserInfo saveCustomerOutUserInfo(Customer custOut, UserInfo userInfo);
	
	/**
	 * 保存用户信息
	 * @Methods Name saveUserInfoWithRoles
	 * @Create In Apr 29, 2009 By sa
	 * @param userInfo
	 * @return UserInfo
	 */
	UserInfo saveUserInfoWithRoles(UserInfo userInfo);
	
}
