/**
 * @Probject Name: b2b
 * @Path: com.digitalchina.info.appframework.metadata.serviceUserColumnManageService.java
 * @Create By peixf
 * @Create In 2008-7-16 上午11:12:49
 * TODO
 */
package com.digitalchina.info.appframework.metadata.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemTableQuery;
import com.digitalchina.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableRole;
import com.digitalchina.info.appframework.metadata.entity.SystemTableRoleColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.metadata.entity.UserTableQueryColumn;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 用户字段管理。其中包括对 列表，输入，查询，和导出字段的配置管理。
 * @Class Name UserColumnManageService
 * @Author peixf
 * @Create In 2008-7-16
 */
public interface UserColumnService {

	/**
	 * 根据表名称获取用户的单表查询, 一个主表可以有多个系统查询，但
	 * 本方法只获取单表查询，复合查询功能尚未实现。
	 * 后续可扩展功能为系统查询命名，通过系统查询名称获取不同的查询条件。
	 * 可实现系统一级更灵活的查询功能。
	 * @Methods Name findSystemTableQuery
	 * @Create In 2008-4-16 By peixf
	 * @param tableName
	 * @return List
	 */
	SystemTableQuery findSystemTableQuery(SystemMainTable smt);
	
	/**
	 * 保存系统查询
	 * @Methods Name saveSystemTableQuery
	 * @Create In 2008-8-13 By sa
	 * @param stq
	 * @return SystemTableQuery
	 */
	SystemTableQuery saveSystemTableQuery(SystemTableQuery stq);

	/**
	 * 保存系统角色
	 * @Methods Name saveSystemTableRole
	 * @Create In 2008-8-13 By sa
	 * @param str
	 * @return SystemTableRole
	 */
	SystemTableRole saveSystemTableRole(SystemTableRole str);
	
	/**
	 * 删除系统查询
	 * @Methods Name removeSystemTableQuery
	 * @Create In 2008-8-13 By sa
	 * @param ids void
	 */
	void removeSystemTableQuery(String[] ids);
	
	/**
	 * 删除系统角色可见字段模板
	 * @Methods Name removeSystemTableRole
	 * @Create In 2008-9-4 By sa
	 * @param ids void
	 */
	void removeSystemTableRole(String[] ids);
	
	/**
	 * 保存
	 * @Methods Name saveEntityData
	 * @Create In Aug 26, 2008 By Administrator
	 * @param clazz
	 * @param object void
	 */
	Object saveMainAndExtData(Class clazz, Map requestParams);
	
	/**
	 * 所有的系统查询
	 * @Methods Name findSystemTableQuery
	 * @Create In 2008-8-7 By sa
	 * @param smt
	 * @return List<SystemTableQuery>
	 */
	List<SystemTableQuery> findSystemTableQueryAll(SystemMainTable smt);

	/**
	 * 所有的系统查询
	 * @Methods Name findSystemTableQuery
	 * @Create In 2008-8-7 By sa
	 * @param smt
	 * @return List<SystemTableQuery>
	 */
	List<SystemTableRole> findSystemTableRoleAll(SystemMainTable smt);
	
	/**
	 * 查询指定的用系统查询有哪些需要显示的查询字段（条件）
	 * @Methods Name selectUserTableQueryColumn
	 * @Create In 2008-4-16 By peixf
	 * @param utq
	 * @return List
	 */
	List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemTableQuery utq);

	/**
	 * 查询指定的用系统查询有哪些需要显示的查询字段（条件）
	 * @Methods Name selectUserTableQueryColumn
	 * @Create In 2008-4-16 By peixf
	 * @param utq
	 * @return List
	 */
	List<SystemTableRoleColumn> findSystemTableRoleColumn(SystemTableRole str);
	
	/**
	 * 系统查询字段排序
	 * @Methods Name saveSystemTableQueryColumnSort
	 * @Create In 2008-8-10 By sa
	 * @param stq 系统查询
	 * @param targetStqcId 目标查询字段的编号
	 * @param stqcIds void 需要移动的查询字段编号数组
	 */
	void saveSystemTableQueryColumnSort(SystemTableQuery stq, String targetOrderFlag, String[] sourceOrderFlags);

	/**
	 * 系统查询字段排序
	 * @Methods Name saveSystemTableQueryColumnSort
	 * @Create In 2008-8-10 By sa
	 * @param stq 系统查询
	 * @param targetStqcId 目标查询字段的编号
	 * @param stqcIds void 需要移动的查询字段编号数组
	 */
	void saveSystemTableRoleColumnSort(SystemTableRole str, Integer settingType, String targetOrderFlag, String[] sourceOrderFlags);


	/**
	 * 系统可见字段排序
	 * @Methods Name saveSystemTableSettingColumnSort
	 * @Create In 2008-8-13 By sa
	 * @param stq
	 * @param targetStqcId
	 * @param stqcIds void
	 */
	void saveSystemTableSettingColumnSort(SystemMainTable smt, Integer settingType, String targetOrderFlag, String[] sourceOrderFlags);
	
	/**
	 * 保存用户可见字段排序
	 * @Methods Name saveUserTableSettingColumnSort
	 * @Create In 2008-9-17 By sa
	 * @param smt
	 * @param settingType
	 * @param targetOrderFlag
	 * @param sourceOrderFlags void
	 */
	void saveUserTableSettingColumnSort(SystemMainTable smt, Integer settingType, String targetOrderFlag, String[] sourceOrderFlags);
	
	/**
	 * 同步字段到所有用户
	 * @Methods Name saveSystemTableQueryColumnToUsersettingType
	 * @Create In 2008-8-11 By sa
	 * @param stq void
	 */
	void saveSystemTableQueryColumnToUser(SystemTableQuery stq);
	
	/**
	 * 同步角色可见字段到指定角色的所有用户
	 * @Methods Name saveSystemTableRoleColumnToUser
	 * @Create In 2008-9-5 By sa
	 * @param stq void
	 */
	void saveSystemTableRoleColumnToUser(SystemTableRole str);

	/**
	 * 保存系统可见字段的修改版本信息
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2009-1-27 By sa
	 * @param smt
	 * @param settingType
	 * @param params void
	 */
	void saveAllSystemTableSettingColumn(SystemMainTable smt, Integer settingType, Map<String,String> params);
	
	/**
	 * 保存系统可见字段到所有用户
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2008-8-12 By sa
	 * @param smt void
	 */
	void saveSystemTableSettingColumnToUser(SystemMainTable smt, Integer settingType);
	
	/**
	 * 保存系统可见字段到指定角色的所有用户
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2008-9-4 By sa
	 * @param smt
	 * @param role
	 * @param settingType void
	 */
	void saveSystemTableSettingColumnToUser(SystemMainTable smt, Role role, Integer settingType);
	
	/**
	 * 保存系统查询字段到指定用户
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2008-8-12 By sa
	 * @param smt
	 * @param user void
	 */
	void saveSystemTableSettingColumnToUser(SystemMainTable smt, UserInfo user, Integer settingType);
	
	/**
	 * 同步字段到指定用户
	 * @Methods Name saveSystemTableQueryColumnToUser
	 * @Create In 2008-8-11 By sa
	 * @param stq
	 * @param user void
	 */
	void saveSystemTableQueryColumnToUser(SystemTableQuery stq, UserInfo user);
//	/**
//	 * 获取系统单表查询字段,自动从主表加载查询字段，
//	 * 更新系统查询字段。重新加载前先删除系统查询字段。
//	 * @Methods Name findSystemTableQueryColumn
//	 * @Create In 2008-7-24 By peixf
//	 * @param smt
//	 * @return List<SystemTableQueryColumn>
//	 */
//	List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemMainTable smt);
	/**
	 * 获取指定用户可见的查询字段<br>
	 * 上一版的此方法有boolean onlyShowVisible类型参数
	 * @Methods Name findUserQueryColumn
	 * @Create In 2008-7-16 By peixf
	 * @param stq
	 * @return List<UserTableQueryColumn>
	 */
	List<UserTableQueryColumn> findUserQueryColumn(SystemTableQuery stq);
	
	
	/**
	 * 获取系统字段设置表中的字段设置，用于再系统字段管理功能里，手动为用户设置可见字段
	 * @Methods Name findSystemColumns （findSystemColumnSetting）
	 * @Create In 2008-7-16 By peixf
	 * @param mainTable
	 * @param settingType
	 * @return List<SystemTableSetting>
	 */
	List<SystemTableSetting> findSystemColumns(SystemMainTable mainTable, Integer settingType);
	
	/**
	 * 获取用户的可见的所有字段，对于返回结果包括可见与不可见的所有字段。
	 * 不可见的字段在页面以隐藏域的形式存在，否则被用户隐藏的字段在表单保存后将置空。
	 * 但对于SystemTableSetting.LIST列表页面只返回可见字段.
	 * @Methods Name findUserColumns
	 * @Create In 2008-7-16 By peixf
	 * @param sysmt
	 * @param settingType
	 * @return List
	 */
	List<UserTableSetting> findUserColumns(SystemMainTable sysmt, Integer settingType);
	
	/**
	 * 获取用户可见字段里的所有字段，给前端页面供用户进行设定
	 * @Methods Name findUserColumns
	 * @Create In 2008-9-17 By sa
	 * @param sysmt
	 * @param settingType
	 * @return List<UserTableSetting>
	 */
	List<UserTableSetting> findUserColumnsAll(SystemMainTable sysmt, Integer settingType);

	/**
	 * 保存,不从UserContext里面取
	 * @param clazz
	 * @author tongjp
	 * @return
	 */
	Object saveMainAndExtDataForUser(Class clazz, Map requestParams,UserInfo user);
	
}
