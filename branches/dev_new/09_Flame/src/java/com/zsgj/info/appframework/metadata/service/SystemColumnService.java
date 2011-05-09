package com.zsgj.info.appframework.metadata.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;

/**
 * 系统字段服务。
 * 提供对系统主表的主字段、扩展字段的抽象访问方法；
 * 主字段和扩展字段有共同的父字段类型。
 * @Class Name SystemColumnService
 * @Author peixf
 * @Create In 2008-7-22
 */
public interface SystemColumnService {
	/**
	 * 为附件类型字段初次创建初始化关联字段配置
	 * @Methods Name saveSystemFileColumnInit
	 * @Create In Apr 15, 2009 By sa
	 * @param column void
	 */
	void saveSystemFileColumnInit(Column column);
	/**
	 * 
	 * @Methods Name findClassName
	 * @Create In 2009-2-2 By sa
	 * @param columnId
	 * @param discValue
	 * @return String
	 */
	String findClassNameByDisc(String discValue, String fdiscTable);
	/**
	 * 获取系统主表的所有字段, 包括扩展字段
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List findSystemTableColumns(SystemMainTable smt);

	
	/**
	 * 获取系统主表的所有导出字段, 包括扩展字段
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List findSystemTableExportColumns(SystemMainTable smt);
	
	/**
	 * 通过id获取系统主表字段, 包括扩展字段
	 * @Methods Name findSystemMainTableColumnById
	 * @Create In 2008-7-16 By peixf
	 * @param smtcId
	 * @return SystemMainTableColumn
	 */
	Column findSystemTableColumnById(String smtcId);
	
	/**
	 * 保存系统主表字段, 包括扩展字段
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	Column saveSystemTableColumn(Column column);
	
	/**
	 * 删除系统主表字段, 包括扩展字段。
	 * 底层先提供id获取字段Column，根据Column的类型
	 * 字段删除主字段或扩展字段。 故不需要传递字段类型参数
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc void
	 */
	void removeSystemTableColumn(String smtcId);
	/**
	 * 删除系统主表和扩展表中的对应数据
	 * @Methods Name removeMainAndExtData
	 * @Create In Aug 27, 2008 By Administrator
	 * @param clazz
	 * @param objectId void
	 */
	void removeMainAndExtData(Class clazz, String objectId);
}
