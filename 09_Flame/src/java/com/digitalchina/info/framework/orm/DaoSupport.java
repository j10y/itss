package com.digitalchina.info.framework.orm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.util.Page;

/**
 * ʵ�����ݷ��ʵ�DaoSupport�ӿڡ�
 * ��ʵ��������ǲ�ͬ�汾��Hibernate��Spring��Hibernate�ķ�װ��
 * @Class Name DaoSupport
 * @author xiaofeng
 * @Create In 2007-10-26 
 */
public interface DaoSupport {
	
	/**
	 * ��������ʵ����󣬷��س־û�����
	 * @Methods Name insertOrUpdateForObject
	 * @Create In 2008-2-29 By peixf
	 * @param entity
	 * @return Object
	 */
	Object insertOrUpdate(BaseObject entity);
	/**
	 * ����ʵ����󣬶�Ӧ�ײ��Hibernateʵ����update������
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 */
	Object update(BaseObject object);

	/**
	 * ����һ��ʵ�����
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 */
	void update(List list);

	/**
	 * ɾ��ʵ�����
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 */
	void delete(BaseObject entity);

	/**
	 * ���ݶ���OIDɾ��ʵ����������ʵ�ּ���ɾ�����ܣ�����ʹ�ø÷�����
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 * @param id �����ʶ����ͨ����Integer��Long���͡�
	 */
	void delete(Class clazz, Serializable id);

	/**
	 * ɾ��һ��ʵ�����
	 * 2007-10-26 By xiaofeng
	 * @param clazz
	 * @param id 
	 */
	void delete(List list);
	

	/**
	 * ʹ��HQL��λ�ò�������ʵ�壬����Ӱ��ļ�¼�����˷���ʹ���������޸Ĳ�����
	 * 2007-10-26 By xiaofeng
	 * @param updateHql
	 * @param values
	 * @return int
	 */
	int executeUpdate(final String updateHql, final Object[] values);
	

	/**
	 * ͨ������OID��ʵ�����ͻ�ȡʵ�����
	 * 2007-10-26 By xiaofeng
	 * @param clazz ʵ������
	 * @param id �����ʶ����ͨ����Integer��Long���͡� 
	 * @return Object
	 */
	Object select(Class clazz, Serializable id); //�൱session.get()
	
	/**
	 * ��ȡĳһ������ʵ������г־û�����
	 * 2007-10-26 By xiaofeng
	 * @param clazz
	 * @return List 
	 */
	List selectAll(Class clazz);
	
	/**
	 * ͨ������OID����ʵ����󣬸÷���������cache����ҳ־û�����
	 * ��ȷ��ĳһ��id��Ӧ��ʵ�崦�ڳ־û�״̬��Ӧ����ʹ�ø÷������Լ������ݿ��
	 * ���ʴ���������÷��������׳��쳣��<br>�൱session.load(),�ᷢ��session��������á�
	 * 2007-10-26 By xiaofeng
	 * @param entityClass
	 * @param id
	 * @return Object
	 */
	Object load(Class clazz, Serializable id); 
	
	/**
	 * ����ĳһ������ʵ������г־û�����
	 * 2007-10-26 By xiaofeng
	 * @param clazz
	 * @return List 
	 */
	List loadAll(Class clazz);
	
	/**
	 * ����keyName��keyValue����ѯ�õ�listȻ�󷵻ص�һ��ֵ��<br>
	 * �ײ�ʵ�����£�<br>
	 * List result = getHibernateTemplate().find(
					"from " + entity.getName() + " where " + keyName + " = ?",
					keyValue);
	 * @param Class entity
	 * @param String keyName
	 * @param Object keyValue
	 * @return Object
	 * @throws DataAccessException
	 */
	Object selectByKey(Class clazz, String keyName, Object keyValue);
	
	/**
	 * ʹ��HQL�͵�����������Ψһʵ�����
	 * 2007-10-26 By xiaofeng
	 * @param select HQL���
	 * @param value λ�ò���
	 * @return Object
	 */
	Object selectForObject(final String select, final Object value); 
	
	/**
	 * ʹ��HQL�͵�����������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param select HQL���
	 * @param value λ�ò���
	 * @return List 
	 */
	List selectForList(final String select, final Object value); 
	
	/**
	 * ʹ��HQL�Ͳ������飬����Ψһ�־û�����
	 * 2007-10-26 By xiaofeng
	 * @param select HQL���
	 * @param values ���λ�ò���
	 * @return Object
	 */
	Object selectForObject(final String select, final Object[] values); 
	
	/**
	 * ʹ��HQL�Ͳ������飬����һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param select HQL���
	 * @param values ���λ�ò���
	 * @return Object
	 */
	List selectForList(final String select, final Object[] values); 

	/**
	 * ʹ��HQL����������ͷ�ҳ������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param select HQL���
	 * @param values ���λ�ò���
	 * @return Object
	 */
	List selectForList(final String select, final Object[] values, final Page page);
	
	/**
	 * ʹ��DetachedCriteria�ͷ�ҳ������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param select HQL���
	 * @param values ���λ�ò���
	 * @return Object
	 */
	List selectForList(final DetachedCriteria dc, final Page page);
	
	/**
	 * ʹ��2��HQL��䣬һ��λ�ò����ͷ�ҳ������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param selectCount ��ѯ��¼������HQL
	 * @param select HQL
	 * @param values һ��λ�ò���
	 * @param page ��ҳ��
	 * @return List
	 */
	List selectForList(final String selectCount, final String select, final Object[] values, final Page page);
	
	/**
	 * ʹ���й�״̬������ѯ������Ψһ�־û�����
	 * 2007-10-26 By xiaofeng
	 * @param criteria �й�״̬������ѯ
	 * @return Object
	 */
	Object uniqueResult(final DetachedCriteria dc);
	
	/**
	 * ʹ���й�״̬������ѯ������һ��־û����󣬴˷��������ʺϸ��ӵ�������ѯ��
	 * 2007-10-26 By xiaofeng
	 * @param criteria �й�״̬������ѯ
	 * @return Object
	 */
	List selectByCriteria(DetachedCriteria dc);
	
	/**
	 * ʹ���й�״̬������ѯ�����ؼ�¼�ĸ�����
	 * @Methods Name getCountByCriteria
	 * @Create In 2008-3-7 By peixf
	 * @param dc
	 * @return int
	 */
	int selectCountByCriteria(final DetachedCriteria dc);
	
	/**
	 * ʹ���й�״̬������ѯ������һ��־û����󣬴˷���ʹ�÷�ҳ����
	 * @Methods Name selectByCriteriaAndPage
	 * @Create In 2008-2-29 By peixf
	 * @param dc
	 * @param page ��ҳ��
	 * @return List
	 */
	List selectByCriteriaAndPage(DetachedCriteria dc, Page page);
	/**
	 * ʹ��������ѯ������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param exampleEntity
	 */
	List selectByExample(Object example);
	
	/**
	 * ʹ��������ѯ�ͷ�ҳ��������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param exampleEntity ����ʵ�����
	 * @param page ��ҳ��
	 * @return Object
	 */
	List selectByExample(Object example, Page page);
	
	/**
	 * ʹ��HQL��һ����������������һ��־û�����
	 * 2007-10-26 By xiaofeng
	 * @param queryString HQL���
	 * @param paramName ������������
	 * @param value ��������ֵ
	 * @return List
	 */
	List selectByNamedParam(String queryString, String[] paramNames, Object[] values);

	/**
	 * ˢ�»��棬���������ݿ�ͬ����
	 * 2007-10-26 By xiaofeng
	 */
	void flush();
	
	/**
	 * ͨ��HQL��һ��λ�ò���������Iterator��
	 * 2007-10-26 By xiaofeng
	 * @param select
	 * @param value
	 * @return Iterator
	 */
	Iterator iterate(String select, Object value);
	
	/**
	 * ͨ��HQL��һ��λ�ò���������Iterator��
	 * 2007-10-26 By xiaofeng
	 * @param select
	 * @param value
	 * @return Iterator
	 */
	Iterator iterate(String select, Object[] values);
	
	/**
	 * ��ջ���
	 * @Methods Name clear
	 * @Create In 2008-3-28 By peixf
	 * @param entity void
	 */
	void clear();
	
	/**
	 * �ӻ��������ָ������
	 * @Methods Name evict
	 * @Create In 2008-3-28 By peixf
	 * @param object void
	 */
	void evict(BaseObject object);
	
	String getIdName(Class clazz);
	
	Map getAllClassMetadata();
	
	Map getAllCollectionMetadata();
	
	ClassMetadata getClassMetadata(String className);
}
