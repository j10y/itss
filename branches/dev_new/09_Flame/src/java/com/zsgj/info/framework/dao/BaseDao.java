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
 * ����Ϊ����Entity�����ṩCRUD������Hibernate DAO����.
 * ����ֻҪ���ඨ��ʱָ��������Entity��Class, ��ӵ�жԵ���Entity�����CRUD����.
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

	protected Class<T> entityClass;// DAO�������Entity����.

	/**
	 * �ڹ��캯���н�����T.class����entityClass.
	 */
	public BaseDao() {
		Class superClassGenricType = GenericsUtils.getSuperClassGenricType(getClass());
		entityClass = superClassGenricType;
	}

	/**
	 * ȡ��entityClass.JDK1.4��֧�ַ��͵���������׿�Class<T> entityClass,���ش˺����ﵽ��ͬЧ����
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * ����ID��ȡ����.
	 *
	 * @see HibernateGenericDao#getId(Class,Object)
	 */
/*	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}*/

	/**
	 * �����������ƺ�����ֵ��ȡ����
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
	 * �������ȫ����ID��ȡ����.
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
	 * ͨ��id��ȡ����
	 * @param clazz
	 * @param id
	 * @return hitDb �Ƿ�ֱ�ӷ������ݿ�
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
	 * ��ȡȫ������
	 *
	 * @see HibernateGenericDao#getAll(Class)
	 */
	public List<T> getAll() {
		return loadAll(getEntityClass());
	}

	/**
	 * ��ȡȫ������ָ��2������ʽ
	 */
	public List getObjectsBy(Class clazz, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2) {
		return getAll(clazz, orderBy1, isAsc1, orderBy2, isAsc2);
	}

	/**
	 * ��ȡָ��������г־û�ʵ����ָ��һ���������Ժ�������ʽ
	 */
	public List getObjectsBy(Class clazz, String orderBy, boolean isAsc) {
		return getAll(clazz, orderBy, isAsc);
	}


	/**
	 * ��ѯ���������ӹ����Ķ���
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
	 * ��ѯ���������ӹ����Ķ���
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
	 * ��ȡȫ������,���������.
	 *
	 * @see HibernateGenericDao#getAll(Class,String,boolean)
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		return getAll(getEntityClass(), orderBy, isAsc);
	}

	/**
	 * ����ID�Ƴ�����.
	 *
	 * @see HibernateGenericDao#removeById(Class,Serializable)
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass(), id);
	}

	/**
	 * ȡ��Entity��Criteria.
	 *
	 * @see HibernateGenericDao#createCriteria(Class,Criterion[])
	 */
	/*public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}*/

	/**
	 * ȡ��Entity��Criteria,���������.
	 *
	 * @see HibernateGenericDao#createCriteria(Class,String,boolean,Criterion[])
	 */
	/*public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		return createCriteria(getEntityClass(), orderBy, isAsc, criterions);
	}*/

	/**
	 * ����������������ֵ��ѯ����.
	 *
	 * @return ���������Ķ����б�
	 * @see HibernateGenericDao#findBy(Class,String,Object)
	 */
	/*public List<T> findBy(String propertyName, Object value) {
		return findBy(getEntityClass(), propertyName, value);
	}*/

	/**
	 * ����������������ֵ��ѯ����,���������.
	 *
	 * @return ���������Ķ����б�
	 * @see HibernateGenericDao#findBy(Class,String,Object,String,boolean)
	 */
	/*public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		return findBy(getEntityClass(), propertyName, value, orderBy, isAsc);
	}*/

	/**
	 * ��ҳ��ѯ����
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
	 * ����������������ֵ��ѯ��������.
	 *
	 * @return ����������Ψһ���� or null
	 * @see HibernateGenericDao#findUniqueBy(Class,String,Object)
	 */
	public T findUniqueBy(String propertyName, Object value) {
		return findUniqueBy(getEntityClass(), propertyName, value);
	}

	/**
	 * �ж϶���ĳЩ���Ե�ֵ�����ݿ���Ψһ.
	 *
	 * @param uniquePropertyNames ��POJO�ﲻ���ظ��������б�,�Զ��ŷָ� ��"name,loginid,password"
	 * @see HibernateGenericDao#isUnique(Class,Object,String)
	 */
	/*public boolean isUnique(Object entity, String uniquePropertyNames) {
		return isUnique(getEntityClass(), entity, uniquePropertyNames);
	}*/
	
	/**
	 * ͨ��ID��ȡ����
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
	 * ��ȡָ��������г־û�ʵ��
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
	 * ɾ��ָ��ID�Ķ���
	 */
	public void removeObject(Class clazz, Serializable id) {
		//this.removeById(clazz, id); //not cascade remove relate object
		Object object = getObject(clazz, id);
		remove(object); //cascade remove relate object
		
	}

}
