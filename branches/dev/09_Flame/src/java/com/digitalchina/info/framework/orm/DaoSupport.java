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
 * 实现数据访问的DaoSupport接口。
 * 其实现类可能是不同版本的Hibernate或Spring对Hibernate的封装。
 * @Class Name DaoSupport
 * @author xiaofeng
 * @Create In 2007-10-26 
 */
public interface DaoSupport {
	
	/**
	 * 保存或更新实体对象，返回持久化对象。
	 * @Methods Name insertOrUpdateForObject
	 * @Create In 2008-2-29 By peixf
	 * @param entity
	 * @return Object
	 */
	Object insertOrUpdate(BaseObject entity);
	/**
	 * 更新实体对象，对应底层的Hibernate实现是update方法。
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 */
	Object update(BaseObject object);

	/**
	 * 更新一组实体对象。
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 */
	void update(List list);

	/**
	 * 删除实体对象。
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 */
	void delete(BaseObject entity);

	/**
	 * 根据对象OID删除实体对象，如果欲实现级联删除功能，不可使用该方法。
	 * 2007-10-26 By xiaofeng
	 * @param entity
	 * @param id 对象标识符，通常是Integer或Long类型。
	 */
	void delete(Class clazz, Serializable id);

	/**
	 * 删除一组实体对象。
	 * 2007-10-26 By xiaofeng
	 * @param clazz
	 * @param id 
	 */
	void delete(List list);
	

	/**
	 * 使用HQL和位置参数更新实体，返回影响的记录数，此方法使用于批量修改操作。
	 * 2007-10-26 By xiaofeng
	 * @param updateHql
	 * @param values
	 * @return int
	 */
	int executeUpdate(final String updateHql, final Object[] values);
	

	/**
	 * 通过对象OID和实体类型获取实体对象。
	 * 2007-10-26 By xiaofeng
	 * @param clazz 实体类型
	 * @param id 对象标识符，通常是Integer或Long类型。 
	 * @return Object
	 */
	Object select(Class clazz, Serializable id); //相当session.get()
	
	/**
	 * 获取某一个类型实体的所有持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param clazz
	 * @return List 
	 */
	List selectAll(Class clazz);
	
	/**
	 * 通过对象OID加载实体对象，该方法会先在cache里查找持久化对象。
	 * 当确信某一个id对应的实体处于持久化状态，应优先使用该方法，以减少数据库的
	 * 访问次数，否则该方法可能抛出异常。<br>相当session.load(),会发挥session缓存的作用。
	 * 2007-10-26 By xiaofeng
	 * @param entityClass
	 * @param id
	 * @return Object
	 */
	Object load(Class clazz, Serializable id); 
	
	/**
	 * 加载某一个类型实体的所有持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param clazz
	 * @return List 
	 */
	List loadAll(Class clazz);
	
	/**
	 * 根据keyName，keyValue，查询得到list然后返回第一个值。<br>
	 * 底层实现如下：<br>
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
	 * 使用HQL和单个参数返回唯一实体对象。
	 * 2007-10-26 By xiaofeng
	 * @param select HQL语句
	 * @param value 位置参数
	 * @return Object
	 */
	Object selectForObject(final String select, final Object value); 
	
	/**
	 * 使用HQL和单个参数返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param select HQL语句
	 * @param value 位置参数
	 * @return List 
	 */
	List selectForList(final String select, final Object value); 
	
	/**
	 * 使用HQL和参数数组，返回唯一持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param select HQL语句
	 * @param values 多个位置参数
	 * @return Object
	 */
	Object selectForObject(final String select, final Object[] values); 
	
	/**
	 * 使用HQL和参数数组，返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param select HQL语句
	 * @param values 多个位置参数
	 * @return Object
	 */
	List selectForList(final String select, final Object[] values); 

	/**
	 * 使用HQL、参数数组和分页器返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param select HQL语句
	 * @param values 多个位置参数
	 * @return Object
	 */
	List selectForList(final String select, final Object[] values, final Page page);
	
	/**
	 * 使用DetachedCriteria和分页器返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param select HQL语句
	 * @param values 多个位置参数
	 * @return Object
	 */
	List selectForList(final DetachedCriteria dc, final Page page);
	
	/**
	 * 使用2个HQL语句，一组位置参数和分页器返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param selectCount 查询记录个数的HQL
	 * @param select HQL
	 * @param values 一组位置参数
	 * @param page 分页器
	 * @return List
	 */
	List selectForList(final String selectCount, final String select, final Object[] values, final Page page);
	
	/**
	 * 使用托管状态条件查询，返回唯一持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param criteria 托管状态条件查询
	 * @return Object
	 */
	Object uniqueResult(final DetachedCriteria dc);
	
	/**
	 * 使用托管状态条件查询，返回一组持久化对象，此方法尤其适合复杂的条件查询。
	 * 2007-10-26 By xiaofeng
	 * @param criteria 托管状态条件查询
	 * @return Object
	 */
	List selectByCriteria(DetachedCriteria dc);
	
	/**
	 * 使用托管状态条件查询，返回记录的个数。
	 * @Methods Name getCountByCriteria
	 * @Create In 2008-3-7 By peixf
	 * @param dc
	 * @return int
	 */
	int selectCountByCriteria(final DetachedCriteria dc);
	
	/**
	 * 使用托管状态条件查询，返回一组持久化对象，此方法使用分页器。
	 * @Methods Name selectByCriteriaAndPage
	 * @Create In 2008-2-29 By peixf
	 * @param dc
	 * @param page 分页器
	 * @return List
	 */
	List selectByCriteriaAndPage(DetachedCriteria dc, Page page);
	/**
	 * 使用样例查询，返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param exampleEntity
	 */
	List selectByExample(Object example);
	
	/**
	 * 使用样例查询和分页器，返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param exampleEntity 样例实体对象
	 * @param page 分页器
	 * @return Object
	 */
	List selectByExample(Object example, Page page);
	
	/**
	 * 使用HQL和一组命名参数，返回一组持久化对象。
	 * 2007-10-26 By xiaofeng
	 * @param queryString HQL语句
	 * @param paramName 命名参数名称
	 * @param value 命名参数值
	 * @return List
	 */
	List selectByNamedParam(String queryString, String[] paramNames, Object[] values);

	/**
	 * 刷新缓存，立刻与数据库同步。
	 * 2007-10-26 By xiaofeng
	 */
	void flush();
	
	/**
	 * 通过HQL和一个位置参数，返回Iterator。
	 * 2007-10-26 By xiaofeng
	 * @param select
	 * @param value
	 * @return Iterator
	 */
	Iterator iterate(String select, Object value);
	
	/**
	 * 通过HQL和一组位置参数，返回Iterator。
	 * 2007-10-26 By xiaofeng
	 * @param select
	 * @param value
	 * @return Iterator
	 */
	Iterator iterate(String select, Object[] values);
	
	/**
	 * 清空缓存
	 * @Methods Name clear
	 * @Create In 2008-3-28 By peixf
	 * @param entity void
	 */
	void clear();
	
	/**
	 * 从缓存中清空指定对象
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
