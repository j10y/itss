/**
 * @Probject Name: b2b
 * @Path: com.digitalchina.info.appframework.metadata.serviceSystemColumnManageService.java
 * @Create By peixf
 * @Create In 2008-7-16 上午11:11:20
 * TODO
 */
package com.zsgj.info.appframework.metadata.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;

/**
 * 系统主表字段服务，后台对字段的配置管理
 * @Class Name SystemColumnManageService
 * @Author peixf
 * @Create In 2008-7-16
 */
public interface SystemMainColumnService {
	
	/**
	 * 获取系统主表的所有字段
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List findSystemMainTableColumns(SystemMainTable smt);
	
	/**
	 * 使用JDBC元数据获取当前主表的新增字段
	 * @Methods Name findSystemMainTableColumnsFromJdbc
	 * @Create In 2008-8-5 By sa
	 * @param smt
	 * @return List
	 */
	void saveSystemMainTableColumnsLoadFromDb(SystemMainTable smt);

	/**
	 * 获取系统主表的所有字段, validteFlag指定是否是已经配置好的字段，如配置了字段中文名
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @param validteFlag
	 * @return List
	 */
	//List findSystemMainTableColumns(SystemMainTable smt, boolean validteFlag);
	
	/**
	 * 获取系统主表的所有导出字段
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	//List findSystemMainTableOutputColumns(SystemMainTable smt);
	
	/**
	 * 通过id获取系统主表字段
	 * @Methods Name findSystemMainTableColumnById
	 * @Create In 2008-7-16 By peixf
	 * @param smtcId
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn findSystemMainTableColumnById(String smtcId);
	
	/**
	 * 通过系统主表和字段属性名称获取字段
	 * @Methods Name findSystemMainTableColumnByTableAndName
	 * @Create In 2009-1-12 By sa
	 * @param smt
	 * @param propName
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn findSystemMainTableColumnByTableAndName(SystemMainTable smt, String propName);
	
	/**
	 * 保存系统主表字段
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn smtc);
	
	/**
	 * 删除系统主表字段
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc void
	 */
	void removeSystemMainTableColumn(String smtcId);
	
	/**
	 * 删除系统主表字段
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc void
	 */
	void removeSystemMainTableColumn(String[] smtcIds);
	

}
