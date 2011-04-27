package com.zsgj.info.framework.dao;

import java.io.Serializable;
import java.util.List;

import com.zsgj.info.framework.dao.support.Page;

/**
 * ��Ե���Entity����Ĳ�������.�������ھ���ORMʵ�ַ���.
 * @Class Name EntityDao
 * @Author xiaofeng
 * @Create In 2008-4-7
 * @param <T>
 */
public interface Dao<T> {
	
	/**
	 * ����־û�������״̬�Ķ���
	 * @param o
	 * @return Object
	 */
	Object save(Object o);

	/**
	 * ɾ���־û�״̬
	 * @param o void
	 */
	void remove(Object o);

	/**
     * ����id��ȡ����hitDb���������Ƿ�ֱ�ӷ������ݿ⣬������������֡�
     * @param clazz
     * @param id
     * @param hitDb
     * @return Object
     */
    Object getObject(Class clazz, Serializable id, boolean hitDb);

    /**
	 * �ṩ����idɾ���־û�����
	 * @param id void
	 */
    void removeObject(Class clazz, Serializable id);


	/**
	 * ��ȡָ�����OID��������
	 * @param clazz
	 * @return String
	 */
	String getIdName(Class clazz);
	
	/**
	 * �ṩָ�����ͣ��������ƺ�����ֵ�������г־û�����
	 * @param clazz
	 * @param propName
	 * @param propValue
	 * @return List
	 */
	List getObjects(Class clazz, String propName, Object propValue);
	
	/**
	 * ��ȡָ��������г־û�����
	 * @param clazz
	 * @return List
	 */
	List getObjects(Class clazz);
	
	/**
	 * ��ȡָ��������г־û�ʵ����ָ��һ�����Ե�����ʽ
	 * @Methods Name getObjectsBy
	 * @Create In 2008-10-20 By sa
	 * @param clazz
	 * @param orderBy
	 * @param isAsc
	 * @return List
	 */
	List getObjectsBy(Class clazz, String orderBy, boolean isAsc);
	
	/**
	 * ���ڴ��ڸ������ù�����ʵ�壬�˷������ض������
	 * @Methods Name getTopObjectsBy
	 * @Create In 2008-10-20 By sa
	 * @param clazz
	 * @param parentPropName
	 * @param orderBy
	 * @param isAsc
	 * @return List
	 */
	List getTopObjectsBy(Class clazz, String parentPropName, String orderBy, boolean isAsc);
	
	/**
	 * ���ڴ��ڸ������ù�����ʵ�壬�˷��������Ӷ��󣬼��и����͵Ķ���
	 * @Methods Name getChildObjectsBy
	 * @Create In 2008-10-20 By sa
	 * @param clazz
	 * @param parentPropName
	 * @param orderBy
	 * @param isAsc
	 * @return List
	 */
	List getChildObjectsBy(Class clazz, String parentPropName, String orderBy, boolean isAsc);
	    
	/**
	 * ��ȡָ��������г־û�ʵ����ָ���������Ե�����ʽ
	 * @Methods Name getObjectsBy
	 * @Create In 2008-10-20 By sa
	 * @param clazz
	 * @param orderBy1
	 * @param isAsc1
	 * @param orderBy2
	 * @param isAsc2
	 * @return List
	 */
    List getObjectsBy(Class clazz, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2);
   
    /**
     * ���׷�ҳ��ѯ����ָ����ѯ����
     * @Methods Name getByPagedQuery
     * @Create In 2008-10-20 By sa
     * @param clazz
     * @param orderBy
     * @param isAsc
     * @param pageNo
     * @param pageSize
     * @return Page
     */
    Page getByPagedQuery(Class clazz, String orderBy, boolean isAsc, int pageNo, int pageSize);
    
    /**
     * �ṩ�����id��ȡ����Ĭ�Ϸ��ش������
     * @Methods Name getObject
     * @Create In 2008-10-20 By sa
     * @param clazz
     * @param id
     * @return Object
     */
    Object getObject(Class clazz, Serializable id);
   
    
}
