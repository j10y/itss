package com.digitalchina.itil.actor.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.Customer;
import com.digitalchina.itil.actor.entity.CustomerOutUserInfo;

public interface CustomerOutUserService {
	
	/**
	 * ͨ�����ƻ�ȡ�ⲿ�ͻ���Combo��ҳ��ѯʹ��
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
	 * ��ȡ�ⲿ�ͻ��������û���ͬʱ�û�����������
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
	 * �жϵ�ǰ���ⲿ�û��Ƿ��Ѿ�����
	 * @Methods Name findCustomerOutUser
	 * @Create In 2009-3-21 By sa
	 * @param custOut
	 * @param userInfo
	 * @return CustomerOutUserInfo
	 */
	List<CustomerOutUserInfo> findCustomerOutUser(Customer custOut, UserInfo userInfo);
	
	/**
	 * �����ⲿ�û���Ϣ
	 * @Methods Name saveCustomerOutUserInfo
	 * @Create In 2009-3-21 By sa
	 * @param custOut
	 * @param userInfo
	 * @return CustomerOutUserInfo
	 */
	CustomerOutUserInfo saveCustomerOutUserInfo(Customer custOut, UserInfo userInfo);
	
	/**
	 * �����û���Ϣ
	 * @Methods Name saveUserInfoWithRoles
	 * @Create In Apr 29, 2009 By sa
	 * @param userInfo
	 * @return UserInfo
	 */
	UserInfo saveUserInfoWithRoles(UserInfo userInfo);
	
}
