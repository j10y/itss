package com.zsgj.info.appframework.metadata.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;

/**
 * ϵͳ�ֶη���
 * �ṩ��ϵͳ��������ֶΡ���չ�ֶεĳ�����ʷ�����
 * ���ֶκ���չ�ֶ��й�ͬ�ĸ��ֶ����͡�
 * @Class Name SystemColumnService
 * @Author peixf
 * @Create In 2008-7-22
 */
public interface SystemColumnService {
	/**
	 * Ϊ���������ֶγ��δ�����ʼ�������ֶ�����
	 * @Methods Name saveSystemFileColumnInit
	 * @Create In Apr 15, 2009 By sa
	 * @param column void
	 */
	void saveSystemFileColumnInit(Column column);
	/**
	 * 
	 * @Methods Name findClassName
	 * @Create In 2009-2-2 By sa
	 * @param columnId
	 * @param discValue
	 * @return String
	 */
	String findClassNameByDisc(String discValue, String fdiscTable);
	/**
	 * ��ȡϵͳ����������ֶ�, ������չ�ֶ�
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List findSystemTableColumns(SystemMainTable smt);

	
	/**
	 * ��ȡϵͳ��������е����ֶ�, ������չ�ֶ�
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List findSystemTableExportColumns(SystemMainTable smt);
	
	/**
	 * ͨ��id��ȡϵͳ�����ֶ�, ������չ�ֶ�
	 * @Methods Name findSystemMainTableColumnById
	 * @Create In 2008-7-16 By peixf
	 * @param smtcId
	 * @return SystemMainTableColumn
	 */
	Column findSystemTableColumnById(String smtcId);
	
	/**
	 * ����ϵͳ�����ֶ�, ������չ�ֶ�
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	Column saveSystemTableColumn(Column column);
	
	/**
	 * ɾ��ϵͳ�����ֶ�, ������չ�ֶΡ�
	 * �ײ����ṩid��ȡ�ֶ�Column������Column������
	 * �ֶ�ɾ�����ֶλ���չ�ֶΡ� �ʲ���Ҫ�����ֶ����Ͳ���
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-7-16 By peixf
	 * @param smtc void
	 */
	void removeSystemTableColumn(String smtcId);
	/**
	 * ɾ��ϵͳ�������չ���еĶ�Ӧ����
	 * @Methods Name removeMainAndExtData
	 * @Create In Aug 27, 2008 By Administrator
	 * @param clazz
	 * @param objectId void
	 */
	void removeMainAndExtData(Class clazz, String objectId);
}
