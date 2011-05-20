package com.zsgj.info.appframework.metadata.service;

import com.zsgj.info.framework.dao.support.Page;

public interface IdGeneratorService {

	/**
	 * 查找所有部门名称。
	 * 
	 * @Methods Name findAllDepartment
	 * @Create In 24 02, 2010 By zhangzy
	 * @param departName
	 * @throws 
	 * @return Page
	 */
	public Page findAllDepartment(String departName , int start,int pageSize);
	
	/**
	 * 查找所有主表名称。
	 * 
	 * @Methods Name findAllSystemMainTable
	 * @Create In 24 02, 2010 By zhangzy
	 * @param tableCnName
	 * @throws 
	 * @return Page
	 */
	public Page findAllSystemMainTable(String tableCnName , int start,int pageSize);
	
	/**
	 * 根据主表和部门获取编号生成器
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
