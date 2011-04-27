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
	
	//ϵͳ����
	List selectMainTablesAll();
	
	SystemMainTable selectMainTableById(Long id);
	
	SystemMainTable selectMainTableByTableName(String tableName);
	
	SystemMainTable insertOrUpdateMainTable(SystemMainTable smt);
	
	void deleteMainTableById(Long id);
	
	//ϵͳ��չ��
	List selectSysExtTablesAll();

//	SystemExtTable selectSysExtTableById(Long id);
//
//	SystemExtTable insertOrUpdateExtendColumnType(SystemExtTable SysExtTableId);
//	
	void deleteSysExtTableById(Long id);

	//ϵͳ�����
	List selectColumnsAll();

	SystemMainTableColumn selectMainColumnById(Long id);

	SystemMainTableColumn insertOrUpdateMainColumn(SystemMainTableColumn tableDef);
	
	void deleteMainColumnById(Long id);
	
	void reLoadSysMainTableNewColumns();
	
	//��ָ��������ֶ�����ų�ʼ�������е�ϵͳ��Ч�û���
	void initMainColumnOrderToUsers(SystemMainTable smt);
	
	void reLoadSysMainTableMultiNewColumnsToUsers();
	
	//���¼��������ֶ�
	void reLoadSysMainTableNewColumnsByTableName(String tableName);
	
	//��ֹ�û��ֶζ�ʧ������Ϊĳ��ָ�����ʼ�������ֶε��û��ֶοɼ��趨��
	void initSysMainTableAllColumnsToUser(String mainTableId);
	
	//void reLoadSysMainTableAllColumns();
	void initSysMainTableAllColumnsToUser();
	
	List selectExtendTableColumnsByMainTableName(String tableName);
	
	List selectExtendTableColumnsByMainTableId(Long mainTableId);
	
	List selectEntitysAllByClassName(String clazz);
	
	Object selectEntityByClassNameAndId(String className, Long id);
	
	//��չ�ֶ�ֵ��ر�
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
	
	//��չ�ֶ�
	List selectExtendTablesAll();
	
	void deleteExtendTableById(Long id);
	
//	void deleteExtMetaDataByColumnNumberAndType(Integer columnNum, SystemExtTable sysExtTable);
//	
	//�û���չ������

	List selectUserExtendTableSettingsAll();

	UserTableSetting selectUserExtendTableSettingById(Long id);

	UserTableSetting insertOrUpdateUserColumnSetting(UserTableSetting uets);
	
	void deleteUserExtendTableSettingById(Long id);
	
	//�����û����ֶ����ñ�����
	void insertNewColumnToUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable);
	
	//ͨ���û��������ȡ�û������ֶΣ���order�����ֶζ��ƽ���ʹ��
	List selectUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable);
	
	List selectUserColumnSetsVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	List selectUserExtendColumnSetVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	List selectUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	UserTableSetting updateUserColumnSet(UserTableSetting uts);
	
	//�Զ��������б�
	List selectExtOptionsByExtSelectColumnNum(Integer ExtSelectColumnNum);
	
//	ExtOption selectExtOptionById(Long id);
//	
//	ExtOption insertOrUpdateExtOption(ExtOption smt);
	
	void deleteExtOptionByExtSelectColumnNum(Integer ExtSelectColumnNum);
	
	void deleteExtOptionById(Long id);
	
//	void deleteExtendData(Long mainTableId, SystemExtTable sysExtTable);
//	
	//����Ĳ�ѯ����
    void insertRelateTableColumnToQuery(SystemTableQuery utq, SystemMainTable smt);
	 
	void insertRelateTableColumnToQuery(SystemTableQuery utq, SystemMainTable smt, Set loopedTable);
	
	//void insertUserTableQueryColumnToUsers(SystemTableQuery stq);
	
	void insertUserTableQueryColumnToUsers(SystemTableQueryColumn stqc);
}
