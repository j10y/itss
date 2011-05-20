/**
 * @Probject Name: b2b
 * @Path: com.digitalchina.info.appframework.metadata.serviceSystemColumnManageService.java
 * @Create By peixf
 * @Create In 2008-7-16 ����11:11:20
 * TODO
 */
package com.zsgj.info.appframework.metadata.service;

import java.util.List;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;

/**
 * ϵͳ�������
 * @Class Name SystemColumnManageService
 * @Author peixf
 * @Create In 2008-7-16
 */
public interface SystemMainTableService {
	
	/**
	 * ͨ��ϵͳ���������id��ȡ����
	 * @Methods Name findSystemMainTable
	 * @Create In 2008-7-16 By peixf
	 * @param smtId
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTable(String smtId);
	
	/**
	 * ͨ��ϵͳ���������id��ȡ����,�����������й������ֶκͿɼ��ֶ�
	 * @Methods Name findSystemMainTableWithColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtId
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTableWithColumn(String smtId);
	
	/**
	 * ��ȡĳ��ģ���µ�����ϵͳ����
	 * @Methods Name findSystemMainTableByModule
	 * @Create In 2008-7-16 By peixf
	 * @param module
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable>  findSystemMainTableByModule(Module module);
	
	/**
	 * ��ȡĳ��ģ���µ�����ϵͳ������ҳ��ʽ��������
	 * @Methods Name findSystemMainTableByModuleForPage
	 * @Create In 2008-7-25 By peixf
	 * @param module
	 * @return Page
	 */
	Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize);
	/**
	 * ��ȡ�ƶ����Ͷ�Ӧ��ϵͳ����
	 * @Methods Name findSystemMainTableByClazz
	 * @Create In 2008-7-16 By peixf
	 * @param clazz
	 * @return SystemMainTable
	 */
	SystemMainTable findSystemMainTableByClazz(Class clazz);
	
	/**
	 * ����ϵͳ����
	 * @Methods Name saveSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);
	
	/**
	 * �����µ�ӳ��ʵ�岢���浽ϵͳ����
	 * @Methods Name saveSystemMainTableLoadNew
	 * @Create In 2008-8-5 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	void saveSystemMainTableFromMapping();
	
	/**
	 * ɾ��ϵͳ����
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smtId void
	 */
	void removeSystemMainTable(String smtId);
	
	/**
	 * ɾ��ϵͳ�ɼ��ֶ�
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-12-12 By sa
	 * @param smtId void
	 */
	void removeSystemTableSetting(String stsId);
	
	/**
	 * ����ɾ��ϵͳ����
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-3-19 By peixf
	 * @param smtId void
	 */
	void removeSystemMainTable(String[] smtIds);
	
	/**
	 * ����������ID����ϵͳ�����ֶ�
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param mainTableColumnId
	 * @return TODO
	 */
	SystemMainTableColumn findSystemMainTableColumnByColumnId(String mainTableColumnId);
	
	/**
	 * ������ID����ϵͳ������չ�ֶ�
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param extColumnId
	 * @return TODO
	 */
//	SystemMainTableExtColumn  findSystemMainTableExtColumnByColumnId(String extColumnId);
}
