package com.digitalchina.info.framework.dao;

import java.util.List;

import org.springframework.jdbc.support.KeyHolder;

public interface JdbcDao {
	
	/**
	 * ����SQL����
	 * @param sql
	 */
	public void createTableBySQL(String sql);

	/**
	 * ͳ��firstName��ͬ������
	 * @param firstName
	 * @return
	 */
	public int findCountOfBaseObjectsByFirstName(String firstName);

	/**
	 * �����¼�������Զ����ɵ�����Id
	 * @param ps
	 * @return
	 */
	public KeyHolder insertBaseObject(final BaseObject actor);

	/**
	 * ��SimpleJdbcInsert����һ����¼:mysql���Գɹ�
	 */
	public long inserOneBaseObject(BaseObject actor);

	/**
	 * ����/����/ɾ������
	 * @param sql�в������
	 * @param obj����ֵ����
	 */
	public int operateBaseObject(String sql, Object[] obj);

	/**
	 * ����SQL��ѯ��¼����
	 * 
	 * @param sql
	 * @return
	 */
	public int findRowCountBySQL(String sql);

	/**
	 * ����Id����ָ������
	 * 
	 * @param id
	 * @return
	 */
	public BaseObject findBaseObjectById(long id);

	/**
	 * ����Id����ָ������
	 * 
	 * @param id
	 * @return
	 */
	public BaseObject findBaseObjectByIdSimple(long id);

	/**
	 * �������ж���
	 * 
	 * @return
	 */
	public List findAllBaseObjects();


}

