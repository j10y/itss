package com.digitalchina.itil.require.service;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;

public interface ReqTableService {
	/**
	 * �����û��Զ�������
	 * @Methods Name saveSystemMainTable
	 * @Create In Jun 22, 2009 By lee
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);
	/**
	 * ͨ������������ʷ��
	 * @Methods Name saveEventTableByMainTable
	 * @Create In Jun 22, 2009 By lee
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveEventTableByMainTable(SystemMainTable smt);
	/**
	 * ��ѯ��ʷ��
	 * @Methods Name findUserTableEvent
	 * @Create In Jun 22, 2009 By lee
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable findUserTableEvent(SystemMainTable smt);
	/**
	 * ����ϵͳ�����ײ㴴�����ݱ���ֶ�
	 * @Methods Name saveSystemMainTableDeploy
	 * @Create In Jun 22, 2009 By lee
	 * @param smt void
	 */
	void saveSystemMainTableDeploy(SystemMainTable smt);
	/**
	 * �����û��������������ƣ���·����Ŀ��·������ʵ���mapping��������session������
	 * @Methods Name saveTableEntity
	 * @Create In Jun 22, 2009 By lee
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(String sourcePkg, String sourceClassName, String targetClass);

}
