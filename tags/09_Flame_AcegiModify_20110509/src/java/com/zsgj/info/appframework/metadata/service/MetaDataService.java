package com.zsgj.info.appframework.metadata.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemExtTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.type.MetaType;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;

public interface MetaDataService {

	public final static int RELOAD_TABLE_DEF_NEW_COLUMN = 1;
	public final static int RELOAD_TABLE_DEF_ALL_COLUMN = 2;
	public final static int JUST_SHOW_FROM_DEF_TABLE = 3;
	
	/**
	 * 通过元数据类型的名称获取元数据类型
	 * @Methods Name findMetaTypeByName
	 * @Create In 2008-7-1 By peixf
	 * @param metaTypeName
	 * @return MetaType
	 */
//	MetaType findMetaTypeByName(String metaTypeName);
//	
//	/**
//	 * 通过类类型获取系统主表
//	 * @Methods Name findSystemMainTableByClazz
//	 * @Create In 2008-7-16 By peixf
//	 * @param clazz
//	 * @return SystemMainTable
//	 */
//	SystemMainTable findSystemMainTableByClazz(Class clazz);
//	/**
//	 * 获取用户的所有字段，返回结果包括可见与不可见的所有字段。不可见的字段即在页面以隐藏域的形式存放。
//	 * @param sysmt
//	 * @param userColumnType
//	 * @return List
//	 */
//	List findUserColumns(SystemMainTable sysmt, Integer userColumnType);
//	
//	/**
//	 * 获取所有的系统主表
//	 * @Methods Name findSystemMainTablesAll
//	 * @Create In 2008-3-19 By peixf
//	 * @return List
//	 */
//	List findSystemMainTablesAll();
//	
//	/**
//	 * 获取已经配置了表主键字段的系统主表
//	 * @return
//	 */
//	List findSystemMainTableHasCnId();
	
	/**
	 * 根据ID获取项目主表
	 * @Methods Name findSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smtId
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTable(String smtId);
	
	/**
	 * 通过表名称获取系统主表
	 * @Methods Name findSystemMainTableByName
	 * @Create In 2008-3-25 By peixf
	 * @param tableName
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTableByName(String tableName);
//	
//	/**
//	 * 保存系统主表
//	 * @Methods Name saveSystemMainTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param smt
//	 * @return SystemMainTable
//	 */
//	SystemMainTable saveSystemMainTable(SystemMainTable smt);
//	
//	/**
//	 * 删除系统主表
//	 * @Methods Name removeSystemMainTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param smtId void
//	 */
//	void removeSystemMainTable(String smtId);
//	
//	/**
//	 * 获取所有的系统扩展表
//	 * @Methods Name findSystemSysExtTablesAll
//	 * @Create In 2008-3-19 By peixf
//	 * @return List
//	 */
//	List findSystemExtTablesAll();
//	
//	/**
//	 * 获取所有的系统扩展表
//	 * @Methods Name findSystemSysExtTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param sxtId
//	 * @return SystemExtTable
//	 */
////	SystemExtTable findSystemExtTable(String sxtId);
//	
//	/**
//	 * 保存系统扩展表
//	 * @Methods Name saveSystemSysExtTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param sxt
//	 * @return SystemExtTable
//	 */
////	SystemExtTable saveSystemExtTable(SystemExtTable sxt);
//	
//	/**
//	 * 根据id删除系统扩展表
//	 * @Methods Name removeSystemSysExtTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param smtId void
//	 */
//	void removeSystemExtTable(String smtId);
//	
//	/**
//	 * 获取所有的表格定义记录
//	 * @Methods Name findTableDefsAll
//	 * @Create In 2008-3-19 By peixf
//	 * @return List
//	 */
//	List findSystemMainTableColumnsAll();
//	
//	List findSystemMainTableColumnsHaveCnNameAll();
//	
//	/**
//	 * 通过表格名称获取主表的所有字段
//	 * @Methods Name findSystemMainTableColumnsByTableName
//	 * @Create In 2008-4-15 By peixf
//	 * @return List
//	 */
//	List findSystemMainTableColumnsByTableName(String tableName);
//	
//	/**
//	 * 获取系统主表的所有字段
//	 * @param smt
//	 * @return
//	 */
//	List findSystemMainTableColumns(SystemMainTable smt);
//	
//	/**
//	 * 通过表格名称获取主表的所有导出字段
//	 * @Methods Name findSystemMainTableColumnsByTableName
//	 * @Create In 2008-4-15 By peixf
//	 * @return List
//	 */
//	List findSystemMainTableOutPutColumnsByTableName(String tableName);
//	
//	/**
//	 * 重新加载系统表定义的新增字段
//	 * @Methods Name saveTableDefsAfterReLoadNewColumn
//	 * @Create In 2008-3-20 By peixf void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadNewColumn();
//	
//	/**
//	 * 重新加载多值字段
//	 * @Methods Name saveSystemMainTableColumnsAfterReLoadNewColumn
//	 * @Create In 2008-5-5 By peixf void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadMultiNewColumn();
//	
//	/**
//	 * 重新加载某个表的新增字段
//	 * @Methods Name saveSystemMainTableColumnsAfterReLoadNewColumn
//	 * @Create In 2008-4-28 By peixf
//	 * @param tableName void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadNewColumn(String tableName);
//	
//	/**
//	 * 重新加载系统表定义的所有字段
//	 * @Methods Name saveTableDefsAfterReLoadAllColumn
//	 * @Create In 2008-3-20 By peixf void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadAllColumn();
//	
//	/**
//	 * 防止用户字段丢失，可以为某个指定表初始化所有字段到用户字段可见设定表
//	 * @Methods Name saveMainColumnToUserSettingByMainTable
//	 * @Create In 2008-5-6 By peixf
//	 * @param tableId void
//	 */
//	void saveMainColumnToUserSettingByMainTable(String tableId);
//	
//	/**
//	 * 根据id获取表格定义
//	 * @Methods Name findTableDef
//	 * @Create In 2008-3-19 By peixf
//	 * @param tableDefId
//	 * @return TableDefinition
//	 */
//	SystemMainTableColumn findSystemMainTableColumnByTableId(String tableDefId);
//	
//	/**
//	 * 保存表格定义
//	 * @Methods Name saveTableDef
//	 * @Create In 2008-3-19 By peixf
//	 * @param tf
//	 * @return TableDefinition
//	 */
//	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn tf);
//	
//	/**
//	 * 删除表格定义记录
//	 * @Methods Name removeTableDef
//	 * @Create In 2008-3-19 By peixf
//	 * @param tableDefId void
//	 */
//	void removeMainColumn(String tableDefId);
//	
//	/**
//	 * 通过类名称获取该实体的所有记录
//	 * @Methods Name findForeignTableEntitysAll
//	 * @Create In 2008-3-21 By peixf
//	 * @param tableName
//	 * @return List
//	 */
//	List findForeignTableEntitysAll(String className);
	
	/**
	 * 通过类名和主键编号获取返回一个关联实体对象
	 * @Methods Name findForeignTableEntity
	 * @Create In 2008-3-21 By peixf
	 * @param className
	 * @param id
	 * @return Object
	 */
	Object findForeignTableEntity(String className, Long id);
//	
//	/**
//	 * 通用的保存扩展数据方法
//	 * @Methods Name saveExtMetaData
//	 * @Create In 2008-3-24 By peixf
//	 * @param extMetaData
//	 * @return ExtMetaData
//	 */
//	//ExtMetaData saveExtMetaData(ExtMetaData extMetaData);
//	
//	/**
//	 * 根据主表行id和扩展字段序号到文本扩展表获取相应记录值
//	 * @Methods Name findExtTextByMainTableRowIdAndColumnNum
//	 * @Create In 2008-3-21 By peixf
//	 * @param mainId
//	 * @param idx
//	 * @return ExtText
//	 */
//	//ExtText findExtTextByMainTableRowIdAndColumnNum(Long mainId,Integer columnNum);
//
//	
//	/**
//	 * 根据主表行id和扩展字段序号到下拉列表扩展表获取相应记录值
//	 * @Methods Name findExtSelectByMainTableRowIdAndColumnNum
//	 * @Create In 2008-3-21 By peixf
//	 * @param mainId
//	 * @param idx
//	 * @return ExtSelect
//	 */
//	//ExtSelect findExtSelectByMainTableRowIdAndColumnNum(Long mainId,Integer columnNum);
//	
//	
//	/**
//	 * 根据主表记录行号和文本域字段序号获取扩展数据
//	 * @Methods Name findExtSelectByMainTableRowIdAndColumnNum
//	 * @Create In 2008-3-24 By peixf
//	 * @param mainId
//	 * @param idx
//	 * @return ExtSelect
//	 */
//	//ExtTextArea findExtTextAreaByMainTableRowIdAndColumnNum(Long mainId,Integer columnNum);
//	
//	/**
//	 * 通过系统主表ID获取该表的所有字段
//	 * @Methods Name findColumnsBySystemTableId
//	 * @Create In 2008-3-24 By peixf
//	 * @param tableId
//	 * @return List 存放TableDefinition类型的元素
//	 */
//	List findColumnsBySystemTableId(String tableId);
//	
//	List findColumnsHaveCnNameBySystemTableId(String tableId);
//
//	/**
//	 * 通过主表名称获取其所有扩展字段
//	 * @Methods Name findExtendColumnsByMainTableName
//	 * @Create In 2008-3-20 By peixf
//	 * @param mainTableName
//	 * @return List
//	 */
//	List findExtendColumnsByMainTableName(String mainTableName);
//	
//	/**
//	 * 通过主表id获取其所有扩展字段
//	 * @Methods Name findExtendColumnsByMainTableName
//	 * @Create In 2008-3-20 By peixf
//	 * @param mainTableName
//	 * @return List
//	 */
//	List findExtendColumnsByMainTableId(String mainTableId);
//	/**
//	 * 保存字段扩展
//	 * @Methods Name saveExtendTable
//	 * @Create In 2008-3-25 By peixf
//	 * @param extendTable
//	 * @return ExtendTable
//	 */
////	SystemMainTableExtColumn saveExtendColumns(SystemMainTableExtColumn extendTable);
//	
//	/**
//	 * 通过扩展字段id获取一个扩展字段（扩展表）
//	 * @Methods Name findExtendColumn
//	 * @Create In 2008-3-26 By peixf
//	 * @param extendTableId
//	 * @return ExtendTable
//	 */
////	SystemMainTableExtColumn findExtendColumn(String extendTableId);
//	
//	/**
//	 * 通过扩展表id删除扩展字段
//	 * @Methods Name removeExtendColumn
//	 * @Create In 2008-3-25 By peixf
//	 * @param extendColumnId void
//	 */
//	//void removeExtendColumn(String extendColumnId, Integer extMetaDataColumNum, SystemExtTable sysExtTable);
//	
//	
//	List findUserExtendTableSettingsAll();
//
//	UserTableSetting findUserExtendTableSettingById(Long id);
//
//	UserTableSetting saveUserExtendTableSetting(UserTableSetting uets);
//	
//	void removeUserExtendTableSettingById(Long uetsId);
//	
	/**
	 * 某主表新增字段或新扩展字段后保存新字段到UserTableSetting里
	 * @Methods Name saveNewColumnToUserTableSetting
	 * @Create In 2008-3-28 By peixf
	 * @param userInfo
	 * @param systemMainTable void
	 */
	void saveNewColumnToUserTableSetting(UserInfo userInfo, SystemMainTable systemMainTable);
	
	/**
	 * 获取用户的指定主表的所有字段及扩展字段设置信息
	 * @Methods Name findUserTableSetting
	 * @Create In 2008-3-28 By peixf
	 * @param userInfo
	 * @param systemMainTable
	 * @return List
	 */
	List findUserTableSetting(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	/**
	 * 获取用户可见的字段，即指定主表的所有可见字段及其可见扩展字段
	 * @Methods Name findUserTableColumnsVisible
	 * @Create In 2008-4-1 By peixf
	 * @param userInfo
	 * @param systemMainTable
	 * @return List
	 */
	List findTableAllColumns(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType, boolean showVisibleOnly);
//	
//	/**
//	 * 获取用户可见的字段，即指定主表的所有可见字段及其可见扩展字段
//	 * @Methods Name findUserTableColumnsVisible
//	 * @Create In 2008-4-1 By peixf
//	 * @param userInfo
//	 * @param systemMainTable
//	 * @return List
//	 */
//	List findUserTableExtendColumnsVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	/**
	 * 保存用户表字段的设置
	 * @Methods Name saveUserTableSetting
	 * @Create In 2008-3-31 By peixf
	 * @param uts
	 * @return UserTableSetting
	 */
	UserTableSetting saveUserTableSetting(UserTableSetting uts);
//	
//	/**
//	 * 根据扩展select的字段序号获取其所有扩展option
//	 * @Methods Name selectExtOptionsByExtSelectColumnNum
//	 * @Create In 2008-4-3 By peixf
//	 * @param ExtSelectColumnNum
//	 * @return List
//	 */
//	List findExtOptionsByExtSelectColumnNum(Integer extSelectColumnNum);
//	
//	/**
//	 * 根据id获取扩展option
//	 * @Methods Name findExtOptionById
//	 * @Create In 2008-4-3 By peixf
//	 * @param id
//	 * @return ExtOption
//	 */
//	//ExtOption findExtOptionById(String id);
//	
//	/**
//	 * 保存扩展option
//	 * @Methods Name saveExtOption
//	 * @Create In 2008-4-3 By peixf
//	 * @param smt
//	 * @return ExtOption
//	 */
//	//ExtOption saveExtOption(ExtOption smt);
//	
//	/**
//	 * 删除指定扩展select字段序号的扩展option
//	 * @Methods Name removeExtOptionByExtSelectColumnNum
//	 * @Create In 2008-4-3 By peixf
//	 * @param ExtSelectColumnNum void
//	 */
//	void removeExtOptionByExtSelectColumnNum(Integer ExtSelectColumnNum);
//	
//	/**
//	 * 删除扩展option
//	 * @Methods Name removeExtOptionById
//	 * @Create In 2008-4-3 By peixf
//	 * @param id void
//	 */
//	void removeExtOptionById(String id);
//	
//	/**
//	 * 删除扩展数据，删除主表记录后级联删除扩展数据。传递扩展字段的序号和扩展字段类型即可定位哪个扩展数据表。
//	 * @Methods Name removeExtendData
//	 * @Create In 2008-4-11 By peixf
//	 * @param columnNum
//	 * @param sysExtTable void
//	 */
//	//void removeExtendData(String mainTableId, SystemExtTable sysExtTable);
//	
//	/**
//	 * 删除系统表查询，注意此方法会级联删除其查询字段，及其在用户可见字段设置表中的关联记录
//	 * @Methods Name removeSystemTableQuery
//	 * @Create In 2008-4-29 By peixf
//	 * @param sysTableQueryId void
//	 */
//	void removeSystemTableQuery(String sysTableQueryId);
//	/**
//	 * 保存用户表查询
//	 * @Methods Name saveUserTableQuery
//	 * @Create In 2008-4-16 By peixf
//	 * @param utq
//	 * @return UserTableQuery
//	 */
//	SystemTableQuery saveUserTableQuery(SystemTableQuery utq);
//	
//	/**
//	 * 根据表名称获取用户表查询
//	 * @Methods Name selectUserTableQueryByTableName
//	 * @Create In 2008-4-16 By peixf
//	 * @param tableName
//	 * @return List
//	 */
//	List<SystemTableQuery>  findSystemTableQuery(SystemMainTable smt);
//	
//	/**
//	 * 根据表名称获取用户的单表查询
//	 * @Methods Name selectUserTableQueryByTableName
//	 * @Create In 2008-4-16 By peixf
//	 * @param tableName
//	 * @return List
//	 */
//	SystemTableQuery findSystemTableSingleTableQuery(SystemMainTable smt);
//	
//	/**
//	 * 查询指定的用户表查询有哪些需要显示的查询字段（条件）
//	 * @Methods Name selectUserTableQueryColumn
//	 * @Create In 2008-4-16 By peixf
//	 * @param utq
//	 * @return List
//	 */
//	List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemTableQuery utq);
//	
//	/**
//	 * 获取指定用户的查询字段
//	 * @Methods Name findQueryColumn
//	 * @Create In 2008-4-17 By peixf
//	 * @param mainTable
//	 * @param stq
//	 * @return List<UserTableQueryColumn>
//	 */
//	List<UserTableQueryColumn> findUserQueryColumn(SystemTableQuery stq, boolean onlyShowVisible);
//	
//	/**
//	 * 保存用户表查询字段
//	 * @Methods Name saveUserTableQueryColumn
//	 * @Create In 2008-4-22 By peixf
//	 * @param utqc
//	 * @return UserTableQueryColumn
//	 */
//	UserTableQueryColumn saveUserTableQueryColumn(UserTableQueryColumn utqc);
//	
//	/**
//	 * 根据搜索条件搜索实体
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-4-22 By peixf
//	 * @param entityClazz
//	 * @param paramValues
//	 * @param orderProp
//	 * @param isAsc
//	 * @return List<BaseObject>
//	 */
//	List<BaseObject> findDataListByEntityAndParam(Class entityClazz, Map paramValues, String orderProp, boolean isAsc);
//	
//	/**
//	 * 根据排序方式获取"所有"指定实体的集合，考虑特殊需求可能通用功能无法实现，故增加Criteria参数，以过滤查询条件。
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-5-26 By peixf
//	 * @param entityClazz
//	 * @param criteria
//	 * @param orderProp
//	 * @param isAsc
//	 * @return List
//	 */
//	List findDataListByEntityAndParam(Class entityClazz, String orderProp, 
//			boolean isAsc, Criterion... criterions);
//	
//	/**
//	 * 根据排序方式和分页参数获取指定实体的集合，考虑特殊需求可能通用功能无法实现，故增加Criteria参数，以过滤查询条件。
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-5-26 By peixf
//	 * @param entityClazz
//	 * @param criteria 查询条件
//	 * @param orderProp
//	 * @param isAsc
//	 * @return Page 返回Page类型
//	 */
//	Page findDataListByEntityAndParam(Class entityClazz, int pageNo, int pageSize, 
//			String orderProp, boolean isAsc, Criterion... criterions);
//	
//	/**
//	 * 根据排序方式，分页参数，查询字段为key的查询条件和一定的初始Criterion来返回分页数据
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-5-29 By peixf
//	 * @param entityClazz
//	 * @param paramValues
//	 * @param pageNo
//	 * @param pageSize
//	 * @param orderProp
//	 * @param isAsc
//	 * @param criterions
//	 * @return Page
//	 */
//	Page findDataListByEntityAndParam(Class entityClazz, Map paramValues, int pageNo, int pageSize, 
//			String orderProp, boolean isAsc, Criterion... criterions);
//	
//	/**
//	 * 根据搜索条件搜索实体，与上面方法区别是返回结果为分页器
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-4-22 By peixf
//	 * @param entityClazz
//	 * @param paramValues
//	 * @param orderProp
//	 * @param isAsc
//	 * @return List<BaseObject>
//	 */
//	Page findDataListByEntityAndParam(Class entityClazz, Map paramValues, Map initParam, int pageNo, int pageSize, String orderProp, boolean isAsc);
//	
//	/**
//	 * 根据搜索条件搜索实体
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-5-16 By peixf
//	 * @param entityClazz
//	 * @param paramValues
//	 * @param pageNo
//	 * @param pageSize
//	 * @param orderProp
//	 * @param isAsc
//	 * @return Page
//	 */
//	Page findDataListByEntityAndParam(Class entityClazz, Map paramValues, int pageNo, int pageSize, String orderProp, boolean isAsc);
//	
//	List findDataListByEntityAndParam(String className, Map params);
//	
//	List findAllWithNullParentProp(String className, String parentPropName);
//	
//	List findAllWithNotNullParentProp(String className, String parentPropName);
//	
//	/**
//	 * 获取复合查询条件字段，即页面顶部的查询条件
//	 * @Methods Name findMultiQueryColumn
//	 * @Create In 2008-4-29 By peixf
//	 * @param sysTableQueryName
//	 * @return List
//	 */
//	List findMultiQueryColumn(SystemMainTable smt, String sysTableQueryName);
//	
//	/**
//	 * 获取复合查询可见字段，即显示搜索结果可见的字段
//	 * @Methods Name findMultiQueryVisibleColumn
//	 * @Create In 2008-4-29 By peixf
//	 * @param sysTableQueryName
//	 * @return List
//	 */
//	List findMultiQueryVisibleColumn(SystemMainTable smt, String sysTableQueryName);
//	
//	/**
//	 * 给所有用户初始化系统字段的显示顺序，避免给每个用户单独设置页面字段的显示顺序
//	 * @Methods Name initMainColumnOrderToUsers
//	 * @Create In 2008-5-8 By peixf
//	 * @param mainTableId void
//	 */
//	void initMainColumnOrderToUsers(String mainTableId);
	
	/**
	 * 初始化所有字段到系统用户设置表
	 * @Methods Name initColumnsToSysTableSetting
	 * @Create In 2008-5-12 By peixf
	 * @param mainTableId void
	 */
	void initColumnsToSysTableSetting(SystemMainTable mainTable);
	
	/**
	 * 获取系统字段设置表中的字段设置记录
	 * @Methods Name findSysTableSettingColumns
	 * @Create In 2008-5-12 By peixf
	 * @param mainTableId	 * @return List
	 */
	List findSysTableSettingColumns(SystemMainTable mainTable, Integer settingType);
	
	/**
	 * 保存系统表设置
	 * @Methods Name saveSystemTableSetting
	 * @Create In 2008-5-12 By peixf
	 * @param sts
	 * @return SystemTableSetting
	 */
	SystemTableSetting saveSystemTableSetting(SystemTableSetting sts);
	
	/**
	 * 按照姓名排序获取所有用户
	 * @Methods Name findUserInfosOrderByName
	 * @Create In 2008-5-12 By peixf
	 * @return List
	 */
	List findUserInfosOrderByName();
	
	/**
	 * 同步系统字段设置到所有用户或指定用户
	 * @Methods Name synchrSysColumnToUserTableSetting
	 * @Create In 2008-5-12 By peixf
	 * @param isAllUser
	 * @param assignedUser void
	 */
	void saveForSynchrSysColumnToUserTableSetting(SystemMainTable smt, Integer settingType, boolean isAllUser, UserInfo assignedUser);
//
//	/**
//	 * 同步系统表查询字段到所有用户或指定用户
//	 * @Methods Name saveForSynchrSysQueryColumnToUser
//	 * @Create In 2008-5-19 By peixf
//	 * @param stq
//	 * @param isAllUser
//	 * @param assignedUser void
//	 */
//	void saveForSynchrSysQueryColumnToUser(SystemTableQuery stq, boolean isAllUser, UserInfo assignedUser);
//	
//	/**
//	 * 获取指定查询的所有查询字段
//	 * @Methods Name findUserQueryColumnsByQuery
//	 * @Create In 2008-5-19 By peixf
//	 * @param stq
//	 * @param user
//	 * @return List
//	 */
//	List findQueryColumnsByQuery(SystemTableQuery stq);
	
	/**
	 * 对于某一个外键表类型的所有字段，获取真实外键关联的字段。如解决方案的客户属性customer，是通过外键关联的客户。
	 * 而customerAddress，不存在真实的这个字段，其属于nullForeiTableColumn类型的字段。
	 * @Methods Name findMainForeiColumn
	 * @Create In 2008-5-20 By peixf
	 * @param foreiTable
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn findMainForeiColumn(SystemMainTable mainTable, SystemMainTable foreiTable);
//	
	/**
	 * 获取指定模块的所有系统主表
	 * @Methods Name findSystemMainTableByModule
	 * @Create In 2008-5-29 By peixf
	 * @param module
	 * @return List
	 */
	//List<SystemMainTable>  findSystemMainTableByModule(Module module);
}
