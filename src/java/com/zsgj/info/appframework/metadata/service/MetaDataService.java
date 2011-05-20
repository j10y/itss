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
	 * ͨ��Ԫ�������͵����ƻ�ȡԪ��������
	 * @Methods Name findMetaTypeByName
	 * @Create In 2008-7-1 By peixf
	 * @param metaTypeName
	 * @return MetaType
	 */
//	MetaType findMetaTypeByName(String metaTypeName);
//	
//	/**
//	 * ͨ�������ͻ�ȡϵͳ����
//	 * @Methods Name findSystemMainTableByClazz
//	 * @Create In 2008-7-16 By peixf
//	 * @param clazz
//	 * @return SystemMainTable
//	 */
//	SystemMainTable findSystemMainTableByClazz(Class clazz);
//	/**
//	 * ��ȡ�û��������ֶΣ����ؽ�������ɼ��벻�ɼ��������ֶΡ����ɼ����ֶμ���ҳ�������������ʽ��š�
//	 * @param sysmt
//	 * @param userColumnType
//	 * @return List
//	 */
//	List findUserColumns(SystemMainTable sysmt, Integer userColumnType);
//	
//	/**
//	 * ��ȡ���е�ϵͳ����
//	 * @Methods Name findSystemMainTablesAll
//	 * @Create In 2008-3-19 By peixf
//	 * @return List
//	 */
//	List findSystemMainTablesAll();
//	
//	/**
//	 * ��ȡ�Ѿ������˱������ֶε�ϵͳ����
//	 * @return
//	 */
//	List findSystemMainTableHasCnId();
	
	/**
	 * ����ID��ȡ��Ŀ����
	 * @Methods Name findSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smtId
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTable(String smtId);
	
	/**
	 * ͨ�������ƻ�ȡϵͳ����
	 * @Methods Name findSystemMainTableByName
	 * @Create In 2008-3-25 By peixf
	 * @param tableName
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTableByName(String tableName);
//	
//	/**
//	 * ����ϵͳ����
//	 * @Methods Name saveSystemMainTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param smt
//	 * @return SystemMainTable
//	 */
//	SystemMainTable saveSystemMainTable(SystemMainTable smt);
//	
//	/**
//	 * ɾ��ϵͳ����
//	 * @Methods Name removeSystemMainTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param smtId void
//	 */
//	void removeSystemMainTable(String smtId);
//	
//	/**
//	 * ��ȡ���е�ϵͳ��չ��
//	 * @Methods Name findSystemSysExtTablesAll
//	 * @Create In 2008-3-19 By peixf
//	 * @return List
//	 */
//	List findSystemExtTablesAll();
//	
//	/**
//	 * ��ȡ���е�ϵͳ��չ��
//	 * @Methods Name findSystemSysExtTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param sxtId
//	 * @return SystemExtTable
//	 */
////	SystemExtTable findSystemExtTable(String sxtId);
//	
//	/**
//	 * ����ϵͳ��չ��
//	 * @Methods Name saveSystemSysExtTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param sxt
//	 * @return SystemExtTable
//	 */
////	SystemExtTable saveSystemExtTable(SystemExtTable sxt);
//	
//	/**
//	 * ����idɾ��ϵͳ��չ��
//	 * @Methods Name removeSystemSysExtTable
//	 * @Create In 2008-3-19 By peixf
//	 * @param smtId void
//	 */
//	void removeSystemExtTable(String smtId);
//	
//	/**
//	 * ��ȡ���еı�����¼
//	 * @Methods Name findTableDefsAll
//	 * @Create In 2008-3-19 By peixf
//	 * @return List
//	 */
//	List findSystemMainTableColumnsAll();
//	
//	List findSystemMainTableColumnsHaveCnNameAll();
//	
//	/**
//	 * ͨ��������ƻ�ȡ����������ֶ�
//	 * @Methods Name findSystemMainTableColumnsByTableName
//	 * @Create In 2008-4-15 By peixf
//	 * @return List
//	 */
//	List findSystemMainTableColumnsByTableName(String tableName);
//	
//	/**
//	 * ��ȡϵͳ����������ֶ�
//	 * @param smt
//	 * @return
//	 */
//	List findSystemMainTableColumns(SystemMainTable smt);
//	
//	/**
//	 * ͨ��������ƻ�ȡ��������е����ֶ�
//	 * @Methods Name findSystemMainTableColumnsByTableName
//	 * @Create In 2008-4-15 By peixf
//	 * @return List
//	 */
//	List findSystemMainTableOutPutColumnsByTableName(String tableName);
//	
//	/**
//	 * ���¼���ϵͳ����������ֶ�
//	 * @Methods Name saveTableDefsAfterReLoadNewColumn
//	 * @Create In 2008-3-20 By peixf void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadNewColumn();
//	
//	/**
//	 * ���¼��ض�ֵ�ֶ�
//	 * @Methods Name saveSystemMainTableColumnsAfterReLoadNewColumn
//	 * @Create In 2008-5-5 By peixf void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadMultiNewColumn();
//	
//	/**
//	 * ���¼���ĳ����������ֶ�
//	 * @Methods Name saveSystemMainTableColumnsAfterReLoadNewColumn
//	 * @Create In 2008-4-28 By peixf
//	 * @param tableName void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadNewColumn(String tableName);
//	
//	/**
//	 * ���¼���ϵͳ����������ֶ�
//	 * @Methods Name saveTableDefsAfterReLoadAllColumn
//	 * @Create In 2008-3-20 By peixf void
//	 */
//	void saveSystemMainTableColumnsAfterReLoadAllColumn();
//	
//	/**
//	 * ��ֹ�û��ֶζ�ʧ������Ϊĳ��ָ�����ʼ�������ֶε��û��ֶοɼ��趨��
//	 * @Methods Name saveMainColumnToUserSettingByMainTable
//	 * @Create In 2008-5-6 By peixf
//	 * @param tableId void
//	 */
//	void saveMainColumnToUserSettingByMainTable(String tableId);
//	
//	/**
//	 * ����id��ȡ�����
//	 * @Methods Name findTableDef
//	 * @Create In 2008-3-19 By peixf
//	 * @param tableDefId
//	 * @return TableDefinition
//	 */
//	SystemMainTableColumn findSystemMainTableColumnByTableId(String tableDefId);
//	
//	/**
//	 * ��������
//	 * @Methods Name saveTableDef
//	 * @Create In 2008-3-19 By peixf
//	 * @param tf
//	 * @return TableDefinition
//	 */
//	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn tf);
//	
//	/**
//	 * ɾ��������¼
//	 * @Methods Name removeTableDef
//	 * @Create In 2008-3-19 By peixf
//	 * @param tableDefId void
//	 */
//	void removeMainColumn(String tableDefId);
//	
//	/**
//	 * ͨ�������ƻ�ȡ��ʵ������м�¼
//	 * @Methods Name findForeignTableEntitysAll
//	 * @Create In 2008-3-21 By peixf
//	 * @param tableName
//	 * @return List
//	 */
//	List findForeignTableEntitysAll(String className);
	
	/**
	 * ͨ��������������Ż�ȡ����һ������ʵ�����
	 * @Methods Name findForeignTableEntity
	 * @Create In 2008-3-21 By peixf
	 * @param className
	 * @param id
	 * @return Object
	 */
	Object findForeignTableEntity(String className, Long id);
//	
//	/**
//	 * ͨ�õı�����չ���ݷ���
//	 * @Methods Name saveExtMetaData
//	 * @Create In 2008-3-24 By peixf
//	 * @param extMetaData
//	 * @return ExtMetaData
//	 */
//	//ExtMetaData saveExtMetaData(ExtMetaData extMetaData);
//	
//	/**
//	 * ����������id����չ�ֶ���ŵ��ı���չ���ȡ��Ӧ��¼ֵ
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
//	 * ����������id����չ�ֶ���ŵ������б���չ���ȡ��Ӧ��¼ֵ
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
//	 * ���������¼�кź��ı����ֶ���Ż�ȡ��չ����
//	 * @Methods Name findExtSelectByMainTableRowIdAndColumnNum
//	 * @Create In 2008-3-24 By peixf
//	 * @param mainId
//	 * @param idx
//	 * @return ExtSelect
//	 */
//	//ExtTextArea findExtTextAreaByMainTableRowIdAndColumnNum(Long mainId,Integer columnNum);
//	
//	/**
//	 * ͨ��ϵͳ����ID��ȡ�ñ�������ֶ�
//	 * @Methods Name findColumnsBySystemTableId
//	 * @Create In 2008-3-24 By peixf
//	 * @param tableId
//	 * @return List ���TableDefinition���͵�Ԫ��
//	 */
//	List findColumnsBySystemTableId(String tableId);
//	
//	List findColumnsHaveCnNameBySystemTableId(String tableId);
//
//	/**
//	 * ͨ���������ƻ�ȡ��������չ�ֶ�
//	 * @Methods Name findExtendColumnsByMainTableName
//	 * @Create In 2008-3-20 By peixf
//	 * @param mainTableName
//	 * @return List
//	 */
//	List findExtendColumnsByMainTableName(String mainTableName);
//	
//	/**
//	 * ͨ������id��ȡ��������չ�ֶ�
//	 * @Methods Name findExtendColumnsByMainTableName
//	 * @Create In 2008-3-20 By peixf
//	 * @param mainTableName
//	 * @return List
//	 */
//	List findExtendColumnsByMainTableId(String mainTableId);
//	/**
//	 * �����ֶ���չ
//	 * @Methods Name saveExtendTable
//	 * @Create In 2008-3-25 By peixf
//	 * @param extendTable
//	 * @return ExtendTable
//	 */
////	SystemMainTableExtColumn saveExtendColumns(SystemMainTableExtColumn extendTable);
//	
//	/**
//	 * ͨ����չ�ֶ�id��ȡһ����չ�ֶΣ���չ��
//	 * @Methods Name findExtendColumn
//	 * @Create In 2008-3-26 By peixf
//	 * @param extendTableId
//	 * @return ExtendTable
//	 */
////	SystemMainTableExtColumn findExtendColumn(String extendTableId);
//	
//	/**
//	 * ͨ����չ��idɾ����չ�ֶ�
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
	 * ĳ���������ֶλ�����չ�ֶκ󱣴����ֶε�UserTableSetting��
	 * @Methods Name saveNewColumnToUserTableSetting
	 * @Create In 2008-3-28 By peixf
	 * @param userInfo
	 * @param systemMainTable void
	 */
	void saveNewColumnToUserTableSetting(UserInfo userInfo, SystemMainTable systemMainTable);
	
	/**
	 * ��ȡ�û���ָ������������ֶμ���չ�ֶ�������Ϣ
	 * @Methods Name findUserTableSetting
	 * @Create In 2008-3-28 By peixf
	 * @param userInfo
	 * @param systemMainTable
	 * @return List
	 */
	List findUserTableSetting(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	/**
	 * ��ȡ�û��ɼ����ֶΣ���ָ����������пɼ��ֶμ���ɼ���չ�ֶ�
	 * @Methods Name findUserTableColumnsVisible
	 * @Create In 2008-4-1 By peixf
	 * @param userInfo
	 * @param systemMainTable
	 * @return List
	 */
	List findTableAllColumns(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType, boolean showVisibleOnly);
//	
//	/**
//	 * ��ȡ�û��ɼ����ֶΣ���ָ����������пɼ��ֶμ���ɼ���չ�ֶ�
//	 * @Methods Name findUserTableColumnsVisible
//	 * @Create In 2008-4-1 By peixf
//	 * @param userInfo
//	 * @param systemMainTable
//	 * @return List
//	 */
//	List findUserTableExtendColumnsVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType);
	
	/**
	 * �����û����ֶε�����
	 * @Methods Name saveUserTableSetting
	 * @Create In 2008-3-31 By peixf
	 * @param uts
	 * @return UserTableSetting
	 */
	UserTableSetting saveUserTableSetting(UserTableSetting uts);
//	
//	/**
//	 * ������չselect���ֶ���Ż�ȡ��������չoption
//	 * @Methods Name selectExtOptionsByExtSelectColumnNum
//	 * @Create In 2008-4-3 By peixf
//	 * @param ExtSelectColumnNum
//	 * @return List
//	 */
//	List findExtOptionsByExtSelectColumnNum(Integer extSelectColumnNum);
//	
//	/**
//	 * ����id��ȡ��չoption
//	 * @Methods Name findExtOptionById
//	 * @Create In 2008-4-3 By peixf
//	 * @param id
//	 * @return ExtOption
//	 */
//	//ExtOption findExtOptionById(String id);
//	
//	/**
//	 * ������չoption
//	 * @Methods Name saveExtOption
//	 * @Create In 2008-4-3 By peixf
//	 * @param smt
//	 * @return ExtOption
//	 */
//	//ExtOption saveExtOption(ExtOption smt);
//	
//	/**
//	 * ɾ��ָ����չselect�ֶ���ŵ���չoption
//	 * @Methods Name removeExtOptionByExtSelectColumnNum
//	 * @Create In 2008-4-3 By peixf
//	 * @param ExtSelectColumnNum void
//	 */
//	void removeExtOptionByExtSelectColumnNum(Integer ExtSelectColumnNum);
//	
//	/**
//	 * ɾ����չoption
//	 * @Methods Name removeExtOptionById
//	 * @Create In 2008-4-3 By peixf
//	 * @param id void
//	 */
//	void removeExtOptionById(String id);
//	
//	/**
//	 * ɾ����չ���ݣ�ɾ�������¼����ɾ����չ���ݡ�������չ�ֶε���ź���չ�ֶ����ͼ��ɶ�λ�ĸ���չ���ݱ�
//	 * @Methods Name removeExtendData
//	 * @Create In 2008-4-11 By peixf
//	 * @param columnNum
//	 * @param sysExtTable void
//	 */
//	//void removeExtendData(String mainTableId, SystemExtTable sysExtTable);
//	
//	/**
//	 * ɾ��ϵͳ���ѯ��ע��˷����ἶ��ɾ�����ѯ�ֶΣ��������û��ɼ��ֶ����ñ��еĹ�����¼
//	 * @Methods Name removeSystemTableQuery
//	 * @Create In 2008-4-29 By peixf
//	 * @param sysTableQueryId void
//	 */
//	void removeSystemTableQuery(String sysTableQueryId);
//	/**
//	 * �����û����ѯ
//	 * @Methods Name saveUserTableQuery
//	 * @Create In 2008-4-16 By peixf
//	 * @param utq
//	 * @return UserTableQuery
//	 */
//	SystemTableQuery saveUserTableQuery(SystemTableQuery utq);
//	
//	/**
//	 * ���ݱ����ƻ�ȡ�û����ѯ
//	 * @Methods Name selectUserTableQueryByTableName
//	 * @Create In 2008-4-16 By peixf
//	 * @param tableName
//	 * @return List
//	 */
//	List<SystemTableQuery>  findSystemTableQuery(SystemMainTable smt);
//	
//	/**
//	 * ���ݱ����ƻ�ȡ�û��ĵ����ѯ
//	 * @Methods Name selectUserTableQueryByTableName
//	 * @Create In 2008-4-16 By peixf
//	 * @param tableName
//	 * @return List
//	 */
//	SystemTableQuery findSystemTableSingleTableQuery(SystemMainTable smt);
//	
//	/**
//	 * ��ѯָ�����û����ѯ����Щ��Ҫ��ʾ�Ĳ�ѯ�ֶΣ�������
//	 * @Methods Name selectUserTableQueryColumn
//	 * @Create In 2008-4-16 By peixf
//	 * @param utq
//	 * @return List
//	 */
//	List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemTableQuery utq);
//	
//	/**
//	 * ��ȡָ���û��Ĳ�ѯ�ֶ�
//	 * @Methods Name findQueryColumn
//	 * @Create In 2008-4-17 By peixf
//	 * @param mainTable
//	 * @param stq
//	 * @return List<UserTableQueryColumn>
//	 */
//	List<UserTableQueryColumn> findUserQueryColumn(SystemTableQuery stq, boolean onlyShowVisible);
//	
//	/**
//	 * �����û����ѯ�ֶ�
//	 * @Methods Name saveUserTableQueryColumn
//	 * @Create In 2008-4-22 By peixf
//	 * @param utqc
//	 * @return UserTableQueryColumn
//	 */
//	UserTableQueryColumn saveUserTableQueryColumn(UserTableQueryColumn utqc);
//	
//	/**
//	 * ����������������ʵ��
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
//	 * ��������ʽ��ȡ"����"ָ��ʵ��ļ��ϣ����������������ͨ�ù����޷�ʵ�֣�������Criteria�������Թ��˲�ѯ������
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
//	 * ��������ʽ�ͷ�ҳ������ȡָ��ʵ��ļ��ϣ����������������ͨ�ù����޷�ʵ�֣�������Criteria�������Թ��˲�ѯ������
//	 * @Methods Name findDataListByEntityAndParam
//	 * @Create In 2008-5-26 By peixf
//	 * @param entityClazz
//	 * @param criteria ��ѯ����
//	 * @param orderProp
//	 * @param isAsc
//	 * @return Page ����Page����
//	 */
//	Page findDataListByEntityAndParam(Class entityClazz, int pageNo, int pageSize, 
//			String orderProp, boolean isAsc, Criterion... criterions);
//	
//	/**
//	 * ��������ʽ����ҳ��������ѯ�ֶ�Ϊkey�Ĳ�ѯ������һ���ĳ�ʼCriterion�����ط�ҳ����
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
//	 * ����������������ʵ�壬�����淽�������Ƿ��ؽ��Ϊ��ҳ��
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
//	 * ����������������ʵ��
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
//	 * ��ȡ���ϲ�ѯ�����ֶΣ���ҳ�涥���Ĳ�ѯ����
//	 * @Methods Name findMultiQueryColumn
//	 * @Create In 2008-4-29 By peixf
//	 * @param sysTableQueryName
//	 * @return List
//	 */
//	List findMultiQueryColumn(SystemMainTable smt, String sysTableQueryName);
//	
//	/**
//	 * ��ȡ���ϲ�ѯ�ɼ��ֶΣ�����ʾ��������ɼ����ֶ�
//	 * @Methods Name findMultiQueryVisibleColumn
//	 * @Create In 2008-4-29 By peixf
//	 * @param sysTableQueryName
//	 * @return List
//	 */
//	List findMultiQueryVisibleColumn(SystemMainTable smt, String sysTableQueryName);
//	
//	/**
//	 * �������û���ʼ��ϵͳ�ֶε���ʾ˳�򣬱����ÿ���û���������ҳ���ֶε���ʾ˳��
//	 * @Methods Name initMainColumnOrderToUsers
//	 * @Create In 2008-5-8 By peixf
//	 * @param mainTableId void
//	 */
//	void initMainColumnOrderToUsers(String mainTableId);
	
	/**
	 * ��ʼ�������ֶε�ϵͳ�û����ñ�
	 * @Methods Name initColumnsToSysTableSetting
	 * @Create In 2008-5-12 By peixf
	 * @param mainTableId void
	 */
	void initColumnsToSysTableSetting(SystemMainTable mainTable);
	
	/**
	 * ��ȡϵͳ�ֶ����ñ��е��ֶ����ü�¼
	 * @Methods Name findSysTableSettingColumns
	 * @Create In 2008-5-12 By peixf
	 * @param mainTableId	 * @return List
	 */
	List findSysTableSettingColumns(SystemMainTable mainTable, Integer settingType);
	
	/**
	 * ����ϵͳ������
	 * @Methods Name saveSystemTableSetting
	 * @Create In 2008-5-12 By peixf
	 * @param sts
	 * @return SystemTableSetting
	 */
	SystemTableSetting saveSystemTableSetting(SystemTableSetting sts);
	
	/**
	 * �������������ȡ�����û�
	 * @Methods Name findUserInfosOrderByName
	 * @Create In 2008-5-12 By peixf
	 * @return List
	 */
	List findUserInfosOrderByName();
	
	/**
	 * ͬ��ϵͳ�ֶ����õ������û���ָ���û�
	 * @Methods Name synchrSysColumnToUserTableSetting
	 * @Create In 2008-5-12 By peixf
	 * @param isAllUser
	 * @param assignedUser void
	 */
	void saveForSynchrSysColumnToUserTableSetting(SystemMainTable smt, Integer settingType, boolean isAllUser, UserInfo assignedUser);
//
//	/**
//	 * ͬ��ϵͳ���ѯ�ֶε������û���ָ���û�
//	 * @Methods Name saveForSynchrSysQueryColumnToUser
//	 * @Create In 2008-5-19 By peixf
//	 * @param stq
//	 * @param isAllUser
//	 * @param assignedUser void
//	 */
//	void saveForSynchrSysQueryColumnToUser(SystemTableQuery stq, boolean isAllUser, UserInfo assignedUser);
//	
//	/**
//	 * ��ȡָ����ѯ�����в�ѯ�ֶ�
//	 * @Methods Name findUserQueryColumnsByQuery
//	 * @Create In 2008-5-19 By peixf
//	 * @param stq
//	 * @param user
//	 * @return List
//	 */
//	List findQueryColumnsByQuery(SystemTableQuery stq);
	
	/**
	 * ����ĳһ����������͵������ֶΣ���ȡ��ʵ����������ֶΡ����������Ŀͻ�����customer����ͨ����������Ŀͻ���
	 * ��customerAddress����������ʵ������ֶΣ�������nullForeiTableColumn���͵��ֶΡ�
	 * @Methods Name findMainForeiColumn
	 * @Create In 2008-5-20 By peixf
	 * @param foreiTable
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn findMainForeiColumn(SystemMainTable mainTable, SystemMainTable foreiTable);
//	
	/**
	 * ��ȡָ��ģ�������ϵͳ����
	 * @Methods Name findSystemMainTableByModule
	 * @Create In 2008-5-29 By peixf
	 * @param module
	 * @return List
	 */
	//List<SystemMainTable>  findSystemMainTableByModule(Module module);
}
