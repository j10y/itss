package com.zsgj.itil.config.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.mapping.PersistentClass;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.itil.config.entity.ConfigItemType;

/**
 * �û��Զ����������
 * @Class Name CustomerTableService
 * @Author sa
 * @Create In 2008-11-27
 */
public interface CustomerTableService {
	
	/**
	 * Ϊ�����������ϵ���ݣ���������������
	 * @Methods Name exportAllConfigItemForRelation
	 * @Create In Jun 22, 2009 By sa
	 * @param fileRootPath
	 * @return String
	 */
	String exportAllConfigItemForRelation(String fileRootPath);
	/**
	 * �������������Excel
	 * @Methods Name getConfigItemExcel
	 * @Create In Feb 1, 2010 By duxh
	 * @param citype
	 * @return 
	 * @Return HSSFWorkbook
	 */
	HSSFWorkbook getConfigItemExcel(ConfigItemType citype);
	
	/**
	 * ���������ģ��
	 * @Methods Name getConfigItemTemplateExcel
	 * @Create In Feb 1, 2010 By duxh
	 * @Return String
	 */
	HSSFWorkbook getConfigItemTemplateExcel(SystemMainTable userTable);
	/**
	 * ��Excel���ݸ��µ����ݿ⡣
	 * @Methods Name saveConfigItemExcel
	 * @Create In Feb 2, 2010 By duxh
	 * @param wb
	 * @param citype 
	 * @Return void
	 */
	public void saveConfigItemExcel(HSSFWorkbook wb,SystemMainTable smt);
	
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
	void saveSystemMainTableDeploy(SystemMainTable smt);
	
	/**
	 * ����������������壬����ͷ���ʱ�����Ե����������
	 * @Methods Name saveConfigItemTablePanel
	 * @Create In 2009-2-21 By sa
	 * @param smt
	 * @param deployFlag void
	 */
	PagePanel saveConfigItemTablePanel(SystemMainTable smt, int deployFlag);	
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
	 * ��ȡ���ж�������������
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
	void saveTableEntity(String sourcePkg, String sourceClassName, String targetClass);
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
	 * ����hibernate�־û����ͣ�����ϵͳ����
	 * @Methods Name genPersistentClass
	 * @Create In 2009-2-24 By sa
	 * @param model
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass
	 * @param prefix ���ɱ�ṹ��ǰ׺
	 * @return SystemMainTable
	 */
	SystemMainTable genPersistentClass(PersistentClass model, String sourcePkg, 
			String sourceClassName, String targetClass, String prefix);
	
	/**
	 * ���淢�������������ֶΣ�Ҳ������չ�ֶ�
	 * @Methods Name saveExtendProps
	 * @Create In 2009-2-24 By sa
	 * @param sourceClassName
	 * @param model void
	 */
	void saveExtendProps(String sourceClassName, PersistentClass model);
	
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
	 * ���������ϵexcelͬ�������ݿ�
	 * @Methods Name saveCIRExcel
	 * @Create In Feb 23, 2010 By duxh
	 * @param wb 
	 * @Return void
	 */
	public void saveCIRExcel(HSSFWorkbook wb);
}



