package com.digitalchina.itil.require.service;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;

public interface ReqTableService {
	/**
	 * 保持用户自定义主表
	 * @Methods Name saveSystemMainTable
	 * @Create In Jun 22, 2009 By lee
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);
	/**
	 * 通过主表生成历史表
	 * @Methods Name saveEventTableByMainTable
	 * @Create In Jun 22, 2009 By lee
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveEventTableByMainTable(SystemMainTable smt);
	/**
	 * 查询历史表
	 * @Methods Name findUserTableEvent
	 * @Create In Jun 22, 2009 By lee
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable findUserTableEvent(SystemMainTable smt);
	/**
	 * 发布系统主表，底层创建数据表和字段
	 * @Methods Name saveSystemMainTableDeploy
	 * @Create In Jun 22, 2009 By lee
	 * @param smt void
	 */
	void saveSystemMainTableDeploy(SystemMainTable smt);
	/**
	 * 利用用户新增的主表名称，包路径和目标路径生成实体和mapping，并更新session工厂。
	 * @Methods Name saveTableEntity
	 * @Create In Jun 22, 2009 By lee
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(String sourcePkg, String sourceClassName, String targetClass);

}
