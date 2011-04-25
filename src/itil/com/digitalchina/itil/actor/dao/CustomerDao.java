package com.digitalchina.itil.actor.dao;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.Customer;

/**
 * �û�Dao
 * @Class Name CustomerDao
 * @Author lee
 * @Create In Nov 19, 2009
 */
public interface CustomerDao {

	/**
	 * ��ȡ�û�Ӧ�ÿ��������з���Ŀ¼�����Ŀͻ�id
	 * @Methods Name findCustIdsByUser
	 * @Create In Nov 18, 2009 By lee
	 * @param userInfo
	 * @return List<Long>
	 */
	List<Long> findCustIdsByUser(UserInfo userInfo);
	
	/**
	 * ��ȡ�û�ֱ���ͻ�id
	 * @Methods Name findCustIdByUser
	 * @Create In Nov 19, 2009 By lee
	 * @param userInfo
	 * @return Long
	 */
	Long findCustIdByUser(UserInfo userInfo);
	
	/**
	 * ��ȡ����ֱ���ڲ��ͻ�
	 * @Methods Name getCustomerInByDept
	 * @Create In Nov 19, 2009 By lee
	 * @param dept
	 * @return CustomerIn
	 */
	Customer getCustomerInByDept(Department dept);
	
	/**
	 * ��ȡ�����û���Ϣ
	 * @Methods Name getAllUser
	 * @Create In Dec 15, 2009 By lee
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getAllUser(String userName, int pageNo, int pageSize);
}
