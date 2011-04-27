/**
 * @Probject Name: b2b
 * @Path: com.digitalchina.info.appframework.metadata.serviceSystemColumnManageService.java
 * @Create By peixf
 * @Create In 2008-7-16 上午11:11:20
 * TODO
 */
package com.zsgj.info.appframework.metadata.service;

import java.util.List;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;

/**
 * 系统主表服务
 * @Class Name SystemColumnManageService
 * @Author peixf
 * @Create In 2008-7-16
 */
public interface SystemMainTableService {
	
	/**
	 * 通过系统主表的主键id获取主表
	 * @Methods Name findSystemMainTable
	 * @Create In 2008-7-16 By peixf
	 * @param smtId
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTable(String smtId);
	
	/**
	 * 通过系统主表的主键id获取主表,级联加载所有关联的字段和可见字段
	 * @Methods Name findSystemMainTableWithColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtId
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTableWithColumn(String smtId);
	
	/**
	 * 获取某个模块下的所有系统主表
	 * @Methods Name findSystemMainTableByModule
	 * @Create In 2008-7-16 By peixf
	 * @param module
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable>  findSystemMainTableByModule(Module module);
	
	/**
	 * 获取某个模块下的所有系统主表，分页形式返回数据
	 * @Methods Name findSystemMainTableByModuleForPage
	 * @Create In 2008-7-25 By peixf
	 * @param module
	 * @return Page
	 */
	Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize);
	/**
	 * 获取制定类型对应的系统主表
	 * @Methods Name findSystemMainTableByClazz
	 * @Create In 2008-7-16 By peixf
	 * @param clazz
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTableByClazz(Class clazz);
	
	/**
	 * 保存系统主表
	 * @Methods Name saveSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);
	
	/**
	 * 加载新的映射实体并保存到系统主表
	 * @Methods Name saveSystemMainTableLoadNew
	 * @Create In 2008-8-5 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	void saveSystemMainTableFromMapping();
	
	/**
	 * 删除系统主表
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smtId void
	 */
	void removeSystemMainTable(String smtId);
	
	/**
	 * 删除系统可见字段
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-12-12 By sa
	 * @param smtId void
	 */
	void removeSystemTableSetting(String stsId);
	
	/**
	 * 批量删除系统主表
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smtId void
	 */
	void removeSystemMainTable(String[] smtIds);
	
	/**
	 * 根据主表列ID查找系统主表字段
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param mainTableColumnId
	 * @return TODO
	 */
	SystemMainTableColumn findSystemMainTableColumnByColumnId(String mainTableColumnId);
	
	/**
	 * 根据列ID查找系统主表扩展字段
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param extColumnId
	 * @return TODO
	 */
//	SystemMainTableExtColumn  findSystemMainTableExtColumnByColumnId(String extColumnId);
}
