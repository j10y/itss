package com.zsgj.info.appframework.pagemodel.service;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;

/**
 * �û��Զ����������
 * @Class Name CustomerTableService
 * @Author sa
 * @Create In 2008-11-27
 */
public interface UserListTableService {
	
	void saveSystemMainTableDeploy(SystemMainTable smt);
	
	/**
	 * ͨ������id��ȡ�������
	 * @Methods Name findUserTableById
	 * @Create In 2008-12-23 By sa
	 * @param tableId
	 * @return SystemMainTable
	 */
	SystemMainTable findCustomerTableById(String tableId);
	
	/**
	 * �����û��������������ƣ���·����Ŀ��·������ʵ���mapping��������session������
	 * itil��Ŀ�����ӵ�������:
	 * 	cts.genNewEntityAndMap("com.digitalchina.itil.config.entity", "ServicePortfolio", ConfigItem.class);
	 * ��2�������Ƕ�Ӧ���������͵���չ����ע����������д��ͷ��ǰ��Ҫ��֤��
	 * @Methods Name genNewEntityAndMap
	 * @Create In 2008-12-2 By sa
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(String sourcePkg, String sourceClassName, Class targetClass);
	/**
	 * ��ȡ���е��û��Զ�������
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
	 * �����û��Զ�������
	 * @Methods Name saveSystemMainTable
	 * @Create In 2008-11-27 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);
	
	/**
	 * �����û��Զ���������ֶ��������޸�
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-11-27 By sa
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn smtc);
	
	/**
	 * ɾ���û��Զ�������
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-11-27 By sa
	 * @param smtIds void
	 */
	void removeSystemMainTable(String[] smtIds);
	
	/**
	 * ɾ���û��Զ�����ֶ�
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-12-15 By sa
	 * @param smtcIds void
	 */
	void removeSystemMainTableColumn(String[] smtcIds);
}



