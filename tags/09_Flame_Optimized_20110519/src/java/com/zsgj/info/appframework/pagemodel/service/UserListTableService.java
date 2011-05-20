package com.zsgj.info.appframework.pagemodel.service;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;

/**
 * 用户自定义主表服务
 * @Class Name CustomerTableService
 * @Author sa
 * @Create In 2008-11-27
 */
public interface UserListTableService {
	
	void saveSystemMainTableDeploy(SystemMainTable smt);
	
	/**
	 * 通过主表id获取主表对象
	 * @Methods Name findUserTableById
	 * @Create In 2008-12-23 By sa
	 * @param tableId
	 * @return SystemMainTable
	 */
	SystemMainTable findCustomerTableById(String tableId);
	
	/**
	 * 利用用户新增的主表名称，包路径和目标路径生成实体和mapping，并更新session工厂。
	 * itil项目的例子调用如下:
	 * 	cts.genNewEntityAndMap("com.digitalchina.itil.config.entity", "ServicePortfolio", ConfigItem.class);
	 * 第2个参数是对应配置项类型的扩展主表，注意表名必须大写开头，前端要验证。
	 * @Methods Name genNewEntityAndMap
	 * @Create In 2008-12-2 By sa
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(String sourcePkg, String sourceClassName, Class targetClass);
	/**
	 * 获取所有的用户自定义主表
	 * @Methods Name findSystemMainTableByModule
	 * @Create In 2008-12-1 By sa
	 * @param module
	 * @param tableName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize);
	/**
	 * 保持用户自定义主表
	 * @Methods Name saveSystemMainTable
	 * @Create In 2008-11-27 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);
	
	/**
	 * 保持用户自定义主表的字段新增或修改
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-11-27 By sa
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn smtc);
	
	/**
	 * 删除用户自定义主表
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-11-27 By sa
	 * @param smtIds void
	 */
	void removeSystemMainTable(String[] smtIds);
	
	/**
	 * 删除用户自定义表字段
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-12-15 By sa
	 * @param smtcIds void
	 */
	void removeSystemMainTableColumn(String[] smtcIds);
}



