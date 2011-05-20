package com.zsgj.itil.service.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.entity.ServiceType;
/**
 * ������������������ϵʵ�����
 * @Class Name ServiceItemUserTableService
 * @author lee
 * @Create In 2008-2-23 
 * TODO
 */
public interface ServiceItemUserTableService {
	
	/**
	 * ��ȡ�����Ŀɼ��ֶΣ�һЩ������Ҫ��ʾ���ֶβ����û�����
	 * @Methods Name findRequireTableColumns
	 * @Create In 2009-3-18 By sa
	 * @param smt
	 * @return List
	 */
	List findRequireTableColumns(SystemMainTable smt);
	/**
	 * ͨ�����������ƺ������������ƻ�ȡ��ϵ
	 * @Methods Name findServiceItemUserTables
	 * @Create In 2009-2-23 By lee
	 * @param serviceItemName
	 * @param tableName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */ 
	Page findServiceItemUserTables(String serviceItemName,String tableName,int pageNo,int pageSize);
	/**
	 * ͨ��id��ȡ���������ƺ����������ϵ
	 * @Methods Name findServiceItemUserTabelById
	 * @Create In 2009-2-23 By lee
	 * @param id
	 * @return ServiceItemUserTable
	 */
	ServiceItemUserTable findServiceItemUserTableById(String id);
	/**
	 * ͨ���������ȡ���������������ϵ
	 * @Methods Name findServiceItemUserTableByServiceItem
	 * @Create In Feb 25, 2009 By lee
	 * @param serviceItem
	 * @return ServiceItemUserTable
	 */
	ServiceItemUserTable findServiceItemUserTableByServiceItem(ServiceItem serviceItem);
	/**
	 * ���ɷ��������Ͷ�ӦEXCELģ���ļ�
	 * @Methods Name saveConfigItemTypeExcel
	 * @Create In 2009-1-19 By sa
	 * @param citype
	 * @return String �������ɵ��ļ�·��
	 */
	String saveUserMainTableExcel(SystemMainTable userTable, String fileRootPath);
	
	/**
	 * ��ȡ���л�����Ϣ���
	 * @Methods Name findAllBasePanels
	 * @Create In 2008-12-29 By sa
	 * @return List<PagePanel>
	 */
	List<PagePanel> findAllBasePanels();
	
	/**
	 * ��ȡ���з�����Ϣ���
	 * @Methods Name findAllGroupPanels
	 * @Create In 2008-12-29 By sa
	 * @return List<PagePanel>
	 */
	List<PagePanel> findAllGroupPanels();
	
	/**
	 * ��ȡ���������Ͷ�Ӧ�������ʷ��
	 * @Methods Name findUserTableEvent
	 * @Create In 2009-2-1 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable findUserTableEvent(SystemMainTable smt);
	
	/**
	 * ͨ������������ʷ��
	 * @Methods Name saveEventTableByMainTable
	 * @Create In Apr 7, 2009 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveEventTableByMainTable(SystemMainTable smt);
	
	/**
	 * ����ϵͳ�����ײ㴴�����ݱ���ֶ�
	 * @Methods Name deployTable
	 * @Create In 2008-12-25 By sa
	 * @param smt void
	 */
	void saveSystemMainTableDeploy(ServiceItem scid, SystemMainTable smt);
	
	/**
	 * ���������������壬����ͷ���ʱ�����Ե����������
	 * @Methods Name saveConfigItemTablePanel
	 * @Create In 2009-2-21 By sa
	 * @param smt
	 * @param deployFlag void
	 */
	void saveServiceItemUserTablePanel(ServiceItem sicd, SystemMainTable smt, int deployFlag);	
	/**
	 * ��ȡ�û������Ӧ����������
	 * @Methods Name findConfigItemTypeByTable
	 * @Create In 2008-12-23 By sa
	 * @param smt
	 * @return ConfigItemType
	 */
	ConfigItemType findConfigItemTypeByTable(SystemMainTable smt);
	/**
	 * ͨ������id��ȡ�������
	 * @Methods Name findUserTableById
	 * @Create In 2008-12-23 By sa
	 * @param tableId
	 * @return SystemMainTable
	 */
	SystemMainTable findCustomerTableById(String tableId);
	/**
	 * ��ȡ���ж������������
	 * @Methods Name findAllConfigItemTop
	 * @Create In 2008-12-23 By sa
	 * @return List<ConfigItemType>
	 */
	List<ConfigItemType> findAllTopConfigItems();
	
	/**
	 * ��ȡ���е�ҳ�����
	 * @Methods Name findAllPagePanels
	 * @Create In 2008-12-27 By sa
	 * @return List<PagePanel>
	 */
	List<PagePanel> findAllPagePanels();
	/**
	 * �����û��������������ƣ���·����Ŀ��·������ʵ���mapping��������session������
	 * itil��Ŀ�����ӵ�������:
	 * 	cts.genNewEntityAndMap("com.zsgj.itil.config.entity", "ServicePortfolio", ConfigItem.class);
	 * ��2�������Ƕ�Ӧ���������͵���չ����ע����������д��ͷ��ǰ��Ҫ��֤��
	 * @Methods Name genNewEntityAndMap
	 * @Create In 2008-12-2 By sa
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(ServiceItem scid, String sourcePkg, String sourceClassName, String targetClass);
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
	/**
	 * ͨ���Զ�������õ���Ӧ������
	 * @Methods Name findServiceItemByMainTable
	 * @Create In Mar 31, 2009 By lee
	 * @param smt
	 * @return ServiceItem
	 */
	ServiceItem findServiceItemByMainTable(SystemMainTable smt);
}
