package com.zsgj.info.appframework.metadata.service;

import com.zsgj.info.framework.dao.support.Page;

public interface IdGeneratorService {

	/**
	 * �������в������ơ�
	 * 
	 * @Methods Name findAllDepartment
	 * @Create In 24 02, 2010 By zhangzy
	 * @param departName
	 * @throws 
	 * @return Page
	 */
	public Page findAllDepartment(String departName , int start,int pageSize);
	
	/**
	 * ���������������ơ�
	 * 
	 * @Methods Name findAllSystemMainTable
	 * @Create In 24 02, 2010 By zhangzy
	 * @param tableCnName
	 * @throws 
	 * @return Page
	 */
	public Page findAllSystemMainTable(String tableCnName , int start,int pageSize);
	
	/**
	 * ��������Ͳ��Ż�ȡ���������
	 * @Methods Name findSystemMainTableIdBuilder
	 * @Create In Mar 10, 2010 By lee
	 * @param tableId
	 * @param departId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findSystemMainTableIdBuilder(String tableId, String departId,int pageNo,
			int pageSize);
}
