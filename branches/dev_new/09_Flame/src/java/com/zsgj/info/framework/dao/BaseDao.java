package com.zsgj.info.framework.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.GenericsUtils;

/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * <pre>
 * public class UserManager extends BaseDao<User> {
 * }
 * </pre>
 * @Class Name HibernateEntityDao
 * @Author peixf
 * @Create In 2008-4-7
 * @param <T>
 */
public class BaseDao<T> extends HibernateGenericDao implements Dao<T> {

	protected Class<T> entityClass;// DAO所管理的Entity类型.

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	public BaseDao() {
		Class superClassGenricType = GenericsUtils.getSuperClassGenricType(getClass());
		entityClass = superClassGenricType;
	}

	/**
	 * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 根据ID获取对象.
	 *
	 * @see HibernateGenericDao#getId(Class,Object)
	 */
/*	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}*/

	/**
	 * 根据属性名称和属性值获取对象
	 */
	public List getObjects(Class clazz, String propName, Object propValue) {
		if(propName.indexOf(".")!=-1){
			int dotIndex = propName.indexOf(".");
			String front = propName.substring(0, dotIndex);
			String end = propName.substring(dotIndex+1);
			//String[] propertyName = propName.split(".");
			Criteria c = super.getCriteria(clazz);
			c.createAlias(front, "_subProp");
			c.setFetchMode("_subProp", FetchMode.JOIN);
			c.add(Restrictions.eq("_subProp."+end, propValue));
			List list = c.list();
			return list;
		}
		return super.getCriteria(clazz).add(Restrictions.eq(propName, propValue)).list();
	}

	/**
	 * 根据类的全名和ID获取对象.
	 * @Methods Name get
	 * @Create In 2008-4-18 By xiaofeng
	 * @param className
	 * @param id
	 * @return Object
	 */
	public Object get(String className, Serializable id) {
		Object object = null;
		try {
			Class clazz = Class.forName(className);
			object = getObject(clazz, id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 通过id获取对象
	 * @param clazz
	 * @param id
	 * @return hitDb 是否直接访问数据库
	 */
	public Object getObject(Class clazz, Serializable id, boolean hitDb) {
		Object object = null;
		if(hitDb){
			object = super.findUniqueBy(clazz, "id", id);
		}else{
			object = this.load(clazz, id);
		}
		return object;
	}

	/**
	 * 获取全部对象
	 *
	 * @see HibernateGenericDao#getAll(Class)
	 */
	public List<T> getAll() {
		return loadAll(getEntityClass());
	}

	/**
	 * 获取全部对象，指定2个排序方式
	 */
	public List getObjectsBy(Class clazz, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2) {
		return getAll(clazz, orderBy1, isAsc1, orderBy2, isAsc2);
	}

	/**
	 * 获取指定类的所有持久化实例，指定一个排序属性和升降序方式
	 */
	public List getObjectsBy(Class clazz, String orderBy, boolean isAsc) {
		return getAll(clazz, orderBy, isAsc);
	}


	/**
	 * 查询带有自身父子关联的对象
	 */
	public List getChildObjectsBy(Class clazz, String parentPropName, String orderBy, boolean isAsc) {
		Criteria c = createCriteria(clazz);
		c.setFetchMode(parentPropName, FetchMode.JOIN);
		c.add(Restrictions.isNotNull(parentPropName));
		if(orderBy!=null){
			if(isAsc){
				c.addOrder(Order.asc(orderBy));
			}else{
				c.addOrder(Order.desc(orderBy));
			}
		}
		return c.list();
	}

	/**
	 * 查询带有自身父子关联的对象
	 */
	public List getTopObjectsBy(Class clazz, String parentPropName, String orderBy, boolean isAsc) {
		Criteria c = createCriteria(clazz);
		c.add(Restrictions.isNull(parentPropName));
		if(orderBy!=null){
			if(isAsc){
				c.addOrder(Order.asc(orderBy));
			}else{
				c.addOrder(Order.desc(orderBy));
			}
		}
		return c.list();
	}

	/**
	 * 获取全部对象,带排序参数.
	 *
	 * @see HibernateGenericDao#getAll(Class,String,boolean)
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		return getAll(getEntityClass(), orderBy, isAsc);
	}

	/**
	 * 根据ID移除对象.
	 *
	 * @see HibernateGenericDao#removeById(Class,Serializable)
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass(), id);
	}

	/**
	 * 取得Entity的Criteria.
	 *
	 * @see HibernateGenericDao#createCriteria(Class,Criterion[])
	 */
	/*public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}*/

	/**
	 * 取得Entity的Criteria,带排序参数.
	 *
	 * @see HibernateGenericDao#createCriteria(Class,String,boolean,Criterion[])
	 */
	/*public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		return createCriteria(getEntityClass(), orderBy, isAsc, criterions);
	}*/

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的对象列表
	 * @see HibernateGenericDao#findBy(Class,String,Object)
	 */
	/*public List<T> findBy(String propertyName, Object value) {
		return findBy(getEntityClass(), propertyName, value);
	}*/

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 *
	 * @return 符合条件的对象列表
	 * @see HibernateGenericDao#findBy(Class,String,Object,String,boolean)
	 */
	/*public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		return findBy(getEntityClass(), propertyName, value, orderBy, isAsc);
	}*/

	/**
	 * 分页查询数据
	 */
	public Page getByPagedQuery(Class clazz, String orderBy1, boolean isAsc1, int pageNo, int pageSize) {
		Page page = null;
		Criteria criteria = createCriteria(clazz);
		criteria.add(Restrictions.isNotNull("id"));
		if(orderBy1!=null){
			if(isAsc1){
				criteria.addOrder(Order.asc(orderBy1));
			}else{
				criteria.addOrder(Order.desc(orderBy1));
			}
		}
		if(pageNo!=0 && pageSize!=0){
			page = super.pagedQuery(criteria, pageNo, pageSize);
		}
		return page;
	}

	/**
	 * 根据属性名和属性值查询单个对象.
	 *
	 * @return 符合条件的唯一对象 or null
	 * @see HibernateGenericDao#findUniqueBy(Class,String,Object)
	 */
	public T findUniqueBy(String propertyName, Object value) {
		return findUniqueBy(getEntityClass(), propertyName, value);
	}

	/**
	 * 判断对象某些属性的值在数据库中唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @see HibernateGenericDao#isUnique(Class,Object,String)
	 */
	/*public boolean isUnique(Object entity, String uniquePropertyNames) {
		return isUnique(getEntityClass(), entity, uniquePropertyNames);
	}*/
	
	/**
	 * 通过ID获取对象
	 */
	public Object getObject(Class clazz, Serializable id) {
		Object object = null;
		try {
			//first load from current session, if not found, go to exception block
			object = this.load(clazz, id);
		} catch (RuntimeException e) {
			//if load failed, get
			object = this.get(clazz, id);
		}
		return object;
	}

	/**
	 * 获取指定类的所有持久化实例
	 */
	public List getObjects(Class clazz) {
		if(entityClass.getName().equalsIgnoreCase("com.zsgj.info.framework.security.entity.UserInfo")){
			logger.info("userInfo loadAll");
		}else if(entityClass.getName().equalsIgnoreCase("com.zsgj.info.framework.security.entity.Department")){
			logger.info("department loadAll");
		}
		return loadAll(clazz);
	}

	/*public List getObjects(Class clazz, boolean hitDB) {
		List list = null;
		if(hitDB){
			list = super.loadAll(clazz);
		}else{
			list = super.getAll(clazz);
		}
		return list;
	}*/

	/**
	 * 删除指定ID的对象
	 */
	public void removeObject(Class clazz, Serializable id) {
		//this.removeById(clazz, id); //not cascade remove relate object
		Object object = getObject(clazz, id);
		remove(object); //cascade remove relate object
		
	}

}
