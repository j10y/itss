package com.zsgj.info.framework.dao;

import java.io.Serializable;
import java.util.List;

import com.zsgj.info.framework.dao.support.Page;

/**
 * 针对单个Entity对象的操作定义.不依赖于具体ORM实现方案.
 * @Class Name EntityDao
 * @Author xiaofeng
 * @Create In 2008-4-7
 * @param <T>
 */
public interface Dao<T> {
	
	/**
	 * 保存持久化或游离状态的对象
	 * @param o
	 * @return Object
	 */
	Object save(Object o);

	/**
	 * 删除持久化状态
	 * @param o void
	 */
	void remove(Object o);

	/**
     * 根据id获取对象，hitDb参数决定是否直接访问数据库，避免代理对象出现。
     * @param clazz
     * @param id
     * @param hitDb
     * @return Object
     */
    Object getObject(Class clazz, Serializable id, boolean hitDb);

    /**
	 * 提供对象id删除持久化对象
	 * @param id void
	 */
    void removeObject(Class clazz, Serializable id);


	/**
	 * 获取指定类的OID属性名称
	 * @param clazz
	 * @return String
	 */
	String getIdName(Class clazz);
	
	/**
	 * 提供指定类型，属性名称和属性值返回所有持久化对象
	 * @param clazz
	 * @param propName
	 * @param propValue
	 * @return List
	 */
	List getObjects(Class clazz, String propName, Object propValue);
	
	/**
	 * 获取指定类的所有持久化对象
	 * @param clazz
	 * @return List
	 */
	List getObjects(Class clazz);
	
	/**
	 * 获取指定类的所有持久化实例，指定一个属性的排序方式
	 * @Methods Name getObjectsBy
	 * @Create In 2008-10-20 By sa
	 * @param clazz
	 * @param orderBy
	 * @param isAsc
	 * @return List
	 */
	List getObjectsBy(Class clazz, String orderBy, boolean isAsc);
	
	/**
	 * 对于存在父子引用关联的实体，此方法返回顶层对象
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
	 * 对于存在父子引用关联的实体，此方法返回子对象，即有父类型的对象
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
	 * 获取指定类的所有持久化实例，指定两个属性的排序方式
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
     * 简易分页查询，不指定查询条件
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
     * 提供对象的id获取对象，默认返回代理对象
     * @Methods Name getObject
     * @Create In 2008-10-20 By sa
     * @param clazz
     * @param id
     * @return Object
     */
    Object getObject(Class clazz, Serializable id);
   
    
}
