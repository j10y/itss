package com.digitalchina.info.appframework.metadata.dao;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.framework.dao.Dao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.DaoException;
import com.digitalchina.info.framework.security.entity.Department;


public interface IdGeneratorDao{

	/**
	 * �������в������ơ�
	 * 
	 * @Methods Name selectAllDepartNameInfo
	 * @Create In 24 02, 2010 By zhangzy
	 * @param departName
	 * @throws 
	 * @return Page
	 */
	public Page selectAllDepartNameInfo(
		String departName,int start,int pageSize);
	/**
	 * ���������������ơ�
	 * 
	 * @Methods Name selectAllSystemMainTableInfo
	 * @Create In 24 02, 2010 By zhangzy
	 * @param tableCnName
	 * @throws 
	 * @return Page
	 */
	public Page selectAllSystemMainTableInfo(
			String tableCnName,int start,int pageSize);
	/**
	 * ��������Ͳ��Ų�ѯ���������
	 * @Methods Name getSystemMainTableIdBuilder
	 * @Create In Mar 10, 2010 By lee
	 * @param table
	 * @param depart
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getSystemMainTableIdBuilder(String tableId, String departId, int pageNo,
			int pageSize);
}
