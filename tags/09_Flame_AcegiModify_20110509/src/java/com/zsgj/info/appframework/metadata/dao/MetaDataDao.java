package com.zsgj.info.appframework.metadata.dao;

import java.util.List;
import java.util.Set;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.security.entity.UserInfo;

public interface MetaDataDao {
	
	//系统主表
	List selectMainTablesAll();
	
	SystemMainTable selectMainTableById(Long id);
	
	SystemMainTable selectMainTableByTableName(String tableName);
	
	SystemMainTable insertOrUpdateMainTable(SystemMainTable smt);
	
	void deleteMainTableById(Long id);
	
	//系统扩展表
	List selectSysExtTablesAll();

//	SystemExtTable selectSysExtTableById(Long id);
//
//	SystemExtTable insertOrUpdateExtendColumnType(SystemExtTable SysExtTableId);
//	
	void deleteSysExtTableById(Long id);

	//系统表格定义
	List selectColumnsAll();

	SystemMainTableColumn selectMainColumnById(Long id);

	SystemMainTableColumn insertOrUpdateMainColumn(SystemMainTableColumn tableDef);
	
	void deleteMainColumnById(Long id);
	
	void reLoadSysMainTableNewColumns();
	
	//将指定表的主字段排序号初始化给所有的系统有效用户）
	void initMainColumnOrderToUsers(SystemMainTable smt);
	
	void reLoadSysMainTableMultiNewColumnsToUsers();
	
	//重新加载新增字段
	void reLoadSysMainTableNewColumnsByTableName(String tableName);
	
	//防止用户字段丢失，可以为某个指定表初始化所有字段到用户字段可见设定表
	void initSysMainTableAllColumnsToUser(String mainTableId);
	
	//void reLoadSysMainTableAllColumns();
	void initSysMainTableAllColumnsToUser();
	
	List selectExtendTableColumnsByMainTableName(String tableName);
	
	List selectExtendTableColumnsByMainTableId(Long mainTableId);
	
	List selectEntitysAllByClassName(String clazz);
	
	Object selectEntityByClassNameAndId(String className, Long id);
	
	//扩展字段值相关表
//	ExtText insertOrUpdateExtText(ExtText extText);
//	
//	ExtText selectExtTextByMainTableRowIdAndColumnNum(Long mainId,Integer idx);
//	
//	ExtSelect insertOrUpdateExtSelect(ExtSelect extText);
//	
//	ExtSelect selectExtSelectByMainTableRowIdAndColumnNum(Long mainId,Integer idx);
//	
//	ExtTextArea insertOrUpdateExtTextArea(ExtTextArea extTextArea);
//	
//	ExtTextArea selectExtTextAreaByMainTableRowIdAndColumnNum(Long mainId,Integer idx);
	
	List selectMainColumnsByTableId(Long tableId);

	List selectMainColumnsByTableName(String tableName);
	
	List selectMainOutputColumnsByTableName(String tableName);
	
	//扩展字段
	List selectExtendTablesAll();
	
	void deleteExtendTableById(Long id);
	
//	void deleteExtMetaDataByColumnNumberAndType(Integer columnNum, SystemExtTable sysExtTable);
//	
	//用户扩展表设置

	List selectUserExtendTableSettingsAll();

	UserTableSetting selectUserExtendTableSettingById(Long id);

	UserTableSetting insertOrUpdateUserColumnSetting(UserTableSetting uets);
	
	void deleteUserExtendTableSettingById(Long id);
	
	//设置用户表字段设置表数据
	void insertNewColumnToUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable);
	
	//通过用户和主表获取用户所有字段，按order排序，字段定制界面使用
	List selectUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable);
	
	List selectUserColumnSetsVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	List selectUserExtendColumnSetVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	List selectUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	UserTableSetting updateUserColumnSet(UserTableSetting uts);
	
	//自定义下拉列表
	List selectExtOptionsByExtSelectColumnNum(Integer ExtSelectColumnNum);
	
//	ExtOption selectExtOptionById(Long id);
//	
//	ExtOption insertOrUpdateExtOption(ExtOption smt);
	
	void deleteExtOptionByExtSelectColumnNum(Integer ExtSelectColumnNum);
	
	void deleteExtOptionById(Long id);
	
//	void deleteExtendData(Long mainTableId, SystemExtTable sysExtTable);
//	
	//单表的查询条件
    void insertRelateTableColumnToQuery(SystemTableQuery utq, SystemMainTable smt);
	 
	void insertRelateTableColumnToQuery(SystemTableQuery utq, SystemMainTable smt, Set loopedTable);
	
	//void insertUserTableQueryColumnToUsers(SystemTableQuery stq);
	
	void insertUserTableQueryColumnToUsers(SystemTableQueryColumn stqc);
}
