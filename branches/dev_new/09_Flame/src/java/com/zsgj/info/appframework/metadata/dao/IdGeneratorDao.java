package com.zsgj.info.appframework.metadata.dao;

import com.zsgj.info.framework.dao.support.Page;


public interface IdGeneratorDao{

	/**
	 * 查找所有部门名称。
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
	 * 查找所有主表名称。
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
	 * 根据主表和部门查询编号生成器
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
