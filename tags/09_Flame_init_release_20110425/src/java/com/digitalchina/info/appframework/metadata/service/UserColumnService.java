/**
 * @Probject Name: b2b
 * @Path: com.digitalchina.info.appframework.metadata.serviceUserColumnManageService.java
 * @Create By peixf
 * @Create In 2008-7-16 ����11:12:49
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
 * �û��ֶι������а����� �б����룬��ѯ���͵����ֶε����ù���
 * @Class Name UserColumnManageService
 * @Author peixf
 * @Create In 2008-7-16
 */
public interface UserColumnService {

	/**
	 * ���ݱ����ƻ�ȡ�û��ĵ����ѯ, һ����������ж��ϵͳ��ѯ����
	 * ������ֻ��ȡ�����ѯ�����ϲ�ѯ������δʵ�֡�
	 * ��������չ����Ϊϵͳ��ѯ������ͨ��ϵͳ��ѯ���ƻ�ȡ��ͬ�Ĳ�ѯ������
	 * ��ʵ��ϵͳһ�������Ĳ�ѯ���ܡ�
	 * @Methods Name findSystemTableQuery
	 * @Create In 2008-4-16 By peixf
	 * @param tableName
	 * @return List
	 */
	SystemTableQuery findSystemTableQuery(SystemMainTable smt);
	
	/**
	 * ����ϵͳ��ѯ
	 * @Methods Name saveSystemTableQuery
	 * @Create In 2008-8-13 By sa
	 * @param stq
	 * @return SystemTableQuery
	 */
	SystemTableQuery saveSystemTableQuery(SystemTableQuery stq);

	/**
	 * ����ϵͳ��ɫ
	 * @Methods Name saveSystemTableRole
	 * @Create In 2008-8-13 By sa
	 * @param str
	 * @return SystemTableRole
	 */
	SystemTableRole saveSystemTableRole(SystemTableRole str);
	
	/**
	 * ɾ��ϵͳ��ѯ
	 * @Methods Name removeSystemTableQuery
	 * @Create In 2008-8-13 By sa
	 * @param ids void
	 */
	void removeSystemTableQuery(String[] ids);
	
	/**
	 * ɾ��ϵͳ��ɫ�ɼ��ֶ�ģ��
	 * @Methods Name removeSystemTableRole
	 * @Create In 2008-9-4 By sa
	 * @param ids void
	 */
	void removeSystemTableRole(String[] ids);
	
	/**
	 * ����
	 * @Methods Name saveEntityData
	 * @Create In Aug 26, 2008 By Administrator
	 * @param clazz
	 * @param object void
	 */
	Object saveMainAndExtData(Class clazz, Map requestParams);
	
	/**
	 * ���е�ϵͳ��ѯ
	 * @Methods Name findSystemTableQuery
	 * @Create In 2008-8-7 By sa
	 * @param smt
	 * @return List<SystemTableQuery>
	 */
	List<SystemTableQuery> findSystemTableQueryAll(SystemMainTable smt);

	/**
	 * ���е�ϵͳ��ѯ
	 * @Methods Name findSystemTableQuery
	 * @Create In 2008-8-7 By sa
	 * @param smt
	 * @return List<SystemTableQuery>
	 */
	List<SystemTableRole> findSystemTableRoleAll(SystemMainTable smt);
	
	/**
	 * ��ѯָ������ϵͳ��ѯ����Щ��Ҫ��ʾ�Ĳ�ѯ�ֶΣ�������
	 * @Methods Name selectUserTableQueryColumn
	 * @Create In 2008-4-16 By peixf
	 * @param utq
	 * @return List
	 */
	List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemTableQuery utq);

	/**
	 * ��ѯָ������ϵͳ��ѯ����Щ��Ҫ��ʾ�Ĳ�ѯ�ֶΣ�������
	 * @Methods Name selectUserTableQueryColumn
	 * @Create In 2008-4-16 By peixf
	 * @param utq
	 * @return List
	 */
	List<SystemTableRoleColumn> findSystemTableRoleColumn(SystemTableRole str);
	
	/**
	 * ϵͳ��ѯ�ֶ�����
	 * @Methods Name saveSystemTableQueryColumnSort
	 * @Create In 2008-8-10 By sa
	 * @param stq ϵͳ��ѯ
	 * @param targetStqcId Ŀ���ѯ�ֶεı��
	 * @param stqcIds void ��Ҫ�ƶ��Ĳ�ѯ�ֶα������
	 */
	void saveSystemTableQueryColumnSort(SystemTableQuery stq, String targetOrderFlag, String[] sourceOrderFlags);

	/**
	 * ϵͳ��ѯ�ֶ�����
	 * @Methods Name saveSystemTableQueryColumnSort
	 * @Create In 2008-8-10 By sa
	 * @param stq ϵͳ��ѯ
	 * @param targetStqcId Ŀ���ѯ�ֶεı��
	 * @param stqcIds void ��Ҫ�ƶ��Ĳ�ѯ�ֶα������
	 */
	void saveSystemTableRoleColumnSort(SystemTableRole str, Integer settingType, String targetOrderFlag, String[] sourceOrderFlags);


	/**
	 * ϵͳ�ɼ��ֶ�����
	 * @Methods Name saveSystemTableSettingColumnSort
	 * @Create In 2008-8-13 By sa
	 * @param stq
	 * @param targetStqcId
	 * @param stqcIds void
	 */
	void saveSystemTableSettingColumnSort(SystemMainTable smt, Integer settingType, String targetOrderFlag, String[] sourceOrderFlags);
	
	/**
	 * �����û��ɼ��ֶ�����
	 * @Methods Name saveUserTableSettingColumnSort
	 * @Create In 2008-9-17 By sa
	 * @param smt
	 * @param settingType
	 * @param targetOrderFlag
	 * @param sourceOrderFlags void
	 */
	void saveUserTableSettingColumnSort(SystemMainTable smt, Integer settingType, String targetOrderFlag, String[] sourceOrderFlags);
	
	/**
	 * ͬ���ֶε������û�
	 * @Methods Name saveSystemTableQueryColumnToUsersettingType
	 * @Create In 2008-8-11 By sa
	 * @param stq void
	 */
	void saveSystemTableQueryColumnToUser(SystemTableQuery stq);
	
	/**
	 * ͬ����ɫ�ɼ��ֶε�ָ����ɫ�������û�
	 * @Methods Name saveSystemTableRoleColumnToUser
	 * @Create In 2008-9-5 By sa
	 * @param stq void
	 */
	void saveSystemTableRoleColumnToUser(SystemTableRole str);

	/**
	 * ����ϵͳ�ɼ��ֶε��޸İ汾��Ϣ
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2009-1-27 By sa
	 * @param smt
	 * @param settingType
	 * @param params void
	 */
	void saveAllSystemTableSettingColumn(SystemMainTable smt, Integer settingType, Map<String,String> params);
	
	/**
	 * ����ϵͳ�ɼ��ֶε������û�
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2008-8-12 By sa
	 * @param smt void
	 */
	void saveSystemTableSettingColumnToUser(SystemMainTable smt, Integer settingType);
	
	/**
	 * ����ϵͳ�ɼ��ֶε�ָ����ɫ�������û�
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2008-9-4 By sa
	 * @param smt
	 * @param role
	 * @param settingType void
	 */
	void saveSystemTableSettingColumnToUser(SystemMainTable smt, Role role, Integer settingType);
	
	/**
	 * ����ϵͳ��ѯ�ֶε�ָ���û�
	 * @Methods Name saveSystemTableSettingColumnToUser
	 * @Create In 2008-8-12 By sa
	 * @param smt
	 * @param user void
	 */
	void saveSystemTableSettingColumnToUser(SystemMainTable smt, UserInfo user, Integer settingType);
	
	/**
	 * ͬ���ֶε�ָ���û�
	 * @Methods Name saveSystemTableQueryColumnToUser
	 * @Create In 2008-8-11 By sa
	 * @param stq
	 * @param user void
	 */
	void saveSystemTableQueryColumnToUser(SystemTableQuery stq, UserInfo user);
//	/**
//	 * ��ȡϵͳ�����ѯ�ֶ�,�Զ���������ز�ѯ�ֶΣ�
//	 * ����ϵͳ��ѯ�ֶΡ����¼���ǰ��ɾ��ϵͳ��ѯ�ֶΡ�
//	 * @Methods Name findSystemTableQueryColumn
//	 * @Create In 2008-7-24 By peixf
//	 * @param smt
//	 * @return List<SystemTableQueryColumn>
//	 */
//	List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemMainTable smt);
	/**
	 * ��ȡָ���û��ɼ��Ĳ�ѯ�ֶ�<br>
	 * ��һ��Ĵ˷�����boolean onlyShowVisible���Ͳ���
	 * @Methods Name findUserQueryColumn
	 * @Create In 2008-7-16 By peixf
	 * @param stq
	 * @return List<UserTableQueryColumn>
	 */
	List<UserTableQueryColumn> findUserQueryColumn(SystemTableQuery stq);
	
	
	/**
	 * ��ȡϵͳ�ֶ����ñ��е��ֶ����ã�������ϵͳ�ֶι�������ֶ�Ϊ�û����ÿɼ��ֶ�
	 * @Methods Name findSystemColumns ��findSystemColumnSetting��
	 * @Create In 2008-7-16 By peixf
	 * @param mainTable
	 * @param settingType
	 * @return List<SystemTableSetting>
	 */
	List<SystemTableSetting> findSystemColumns(SystemMainTable mainTable, Integer settingType);
	
	/**
	 * ��ȡ�û��Ŀɼ��������ֶΣ����ڷ��ؽ�������ɼ��벻�ɼ��������ֶΡ�
	 * ���ɼ����ֶ���ҳ�������������ʽ���ڣ������û����ص��ֶ��ڱ�������ÿա�
	 * ������SystemTableSetting.LIST�б�ҳ��ֻ���ؿɼ��ֶ�.
	 * @Methods Name findUserColumns
	 * @Create In 2008-7-16 By peixf
	 * @param sysmt
	 * @param settingType
	 * @return List
	 */
	List<UserTableSetting> findUserColumns(SystemMainTable sysmt, Integer settingType);
	
	/**
	 * ��ȡ�û��ɼ��ֶ���������ֶΣ���ǰ��ҳ�湩�û������趨
	 * @Methods Name findUserColumns
	 * @Create In 2008-9-17 By sa
	 * @param sysmt
	 * @param settingType
	 * @return List<UserTableSetting>
	 */
	List<UserTableSetting> findUserColumnsAll(SystemMainTable sysmt, Integer settingType);

	/**
	 * ����,����UserContext����ȡ
	 * @param clazz
	 * @author tongjp
	 * @return
	 */
	Object saveMainAndExtDataForUser(Class clazz, Map requestParams,UserInfo user);
	
}
