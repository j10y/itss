package com.digitalchina.itil.actor.dao;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.Customer;

/**
 * 用户Dao
 * @Class Name CustomerDao
 * @Author lee
 * @Create In Nov 19, 2009
 */
public interface CustomerDao {

	/**
	 * 获取用户应该看到的所有服务目录所属的客户id
	 * @Methods Name findCustIdsByUser
	 * @Create In Nov 18, 2009 By lee
	 * @param userInfo
	 * @return List<Long>
	 */
	List<Long> findCustIdsByUser(UserInfo userInfo);
	
	/**
	 * 获取用户直属客户id
	 * @Methods Name findCustIdByUser
	 * @Create In Nov 19, 2009 By lee
	 * @param userInfo
	 * @return Long
	 */
	Long findCustIdByUser(UserInfo userInfo);
	
	/**
	 * 获取部门直属内部客户
	 * @Methods Name getCustomerInByDept
	 * @Create In Nov 19, 2009 By lee
	 * @param dept
	 * @return CustomerIn
	 */
	Customer getCustomerInByDept(Department dept);
	
	/**
	 * 获取所有用户信息
	 * @Methods Name getAllUser
	 * @Create In Dec 15, 2009 By lee
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getAllUser(String userName, int pageNo, int pageSize);
}
