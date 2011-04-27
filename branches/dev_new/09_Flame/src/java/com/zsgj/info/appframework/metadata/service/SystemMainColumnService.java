/**
 * @Probject Name: b2b
 * @Path: com.digitalchina.info.appframework.metadata.serviceSystemColumnManageService.java
 * @Create By peixf
 * @Create In 2008-7-16 ����11:11:20
 * TODO
 */
package com.zsgj.info.appframework.metadata.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;

/**
 * ϵͳ�����ֶη��񣬺�̨���ֶε����ù���
 * @Class Name SystemColumnManageService
 * @Author peixf
 * @Create In 2008-7-16
 */
public interface SystemMainColumnService {
	
	/**
	 * ��ȡϵͳ����������ֶ�
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List findSystemMainTableColumns(SystemMainTable smt);
	
	/**
	 * ʹ��JDBCԪ���ݻ�ȡ��ǰ����������ֶ�
	 * @Methods Name findSystemMainTableColumnsFromJdbc
	 * @Create In 2008-8-5 By sa
	 * @param smt
	 * @return List
	 */
	void saveSystemMainTableColumnsLoadFromDb(SystemMainTable smt);

	/**
	 * ��ȡϵͳ����������ֶ�, validteFlagָ���Ƿ����Ѿ����úõ��ֶΣ����������ֶ�������
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @param validteFlag
	 * @return List
	 */
	//List findSystemMainTableColumns(SystemMainTable smt, boolean validteFlag);
	
	/**
	 * ��ȡϵͳ��������е����ֶ�
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	//List findSystemMainTableOutputColumns(SystemMainTable smt);
	
	/**
	 * ͨ��id��ȡϵͳ�����ֶ�
	 * @Methods Name findSystemMainTableColumnById
	 * @Create In 2008-7-16 By peixf
	 * @param smtcId
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn findSystemMainTableColumnById(String smtcId);
	
	/**
	 * ͨ��ϵͳ������ֶ��������ƻ�ȡ�ֶ�
	 * @Methods Name findSystemMainTableColumnByTableAndName
	 * @Create In 2009-1-12 By sa
	 * @param smt
	 * @param propName
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn findSystemMainTableColumnByTableAndName(SystemMainTable smt, String propName);
	
	/**
	 * ����ϵͳ�����ֶ�
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn smtc);
	
	/**
	 * ɾ��ϵͳ�����ֶ�
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc void
	 */
	void removeSystemMainTableColumn(String smtcId);
	
	/**
	 * ɾ��ϵͳ�����ֶ�
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc void
	 */
	void removeSystemMainTableColumn(String[] smtcIds);
	

}
