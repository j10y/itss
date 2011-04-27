package com.zsgj.info.framework.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.AbstractSessionFactoryBean;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springside.core.utils.BeanUtils;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;


/**
 * Hibernate Dao的泛型基类.
 * <p/>
 * 继承于Spring的<code>HibernateDaoSupport</code>,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 * 注意，所有参数带有Session或StatelessSession的方法，如不了解其内部原理慎用.
 * @author xiaofeng
 * @see HibernateDaoSupport
 * @see BaseDao
 */
@SuppressWarnings("unchecked")
public class HibernateGenericDao extends HibernateDaoSupport {
	
	/**
	 * 谨慎调用此方法
	 * @Methods Name reset
	 * @Create In Jul 17, 2009 By sa void
	 */
	public void reset() {
		Session session = super.getSessionFactory().getCurrentSession();
		if (session != null) {
			session.flush();
			if (session.isOpen()) {
				System.out.print("closing session ... ");
				//session.close();
				System.out.println("ok");
			}
		}
		SessionFactory sf = getSessionFactory();
		if (sf != null) {
			System.out.print("closing session factory ... ");
			sf.close();
			System.out.println("ok");
		}
	}

	/**
	 * 获取持久化类信息
	 * @Methods Name getClassMapping
	 * @Create In Jul 17, 2009 By sa
	 * @param entityClass
	 * @return PersistentClass
	 */
	public PersistentClass getClassMapping(Class entityClass) {
		return getConfiguration().getClassMapping(entityClass.getName());
	}
	
	/**
	 * 直接获取spring管理的SessionFactory
	 * @Methods Name getSessionFactoryDirectly
	 * @Create In 2008-11-26 By peixf
	 * @return SessionFactory
	 */
	@SuppressWarnings("static-access")
	protected SessionFactory getHibernateSessionFactory(){
		Proxy  proxy = (Proxy) ContextHolder.getBean("pmcSessionFactory");
		InvocationHandler handler = proxy.getInvocationHandler(proxy);
		AbstractSessionFactoryBean.TransactionAwareInvocationHandler asfbTransHandler = 
			(AbstractSessionFactoryBean.TransactionAwareInvocationHandler) handler;
		SessionFactory sf = asfbTransHandler.getObject();
		return sf;
	}
	
	/**
	 * 获取spring管理的Configuration
	 * @Methods Name getConfiguration
	 * @Create In 2008-11-26 By peixf
	 * @return Configuration
	 */
	protected Configuration getConfiguration(){
		LocalSessionFactoryBean sessionFactoryBean = (LocalSessionFactoryBean) ContextHolder.getBean("&pmcSessionFactory");
		Configuration config = sessionFactoryBean.getConfiguration();
		return config;
	}
	
	/**
	 * 获取无状态Session, 此方法慎用
	 * @return StatelessSession
	 */
	public StatelessSession getStatelessSession(){
		return this.getSessionFactory().openStatelessSession();
	}
	
	/**
	 * 根据ID获取持久化实例. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 */
	public <T> T load(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 根据ID获取持久化实例. 实际调用Hibernate的session.get()方法返回实体. 如果对象不存在，返回null.
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}
	/**
	 * 获取全部持久化实例，与getAll的区别是使用getHibernateTemplate()的loadAll
	 */
	public <T> List<T> loadAll(Class<T> entityClass) {
		if(entityClass.getName().equalsIgnoreCase("com.digitalchina.info.framework.security.entity.UserInfo")){
			logger.info("userInfo loadAll");
		}else if(entityClass.getName().equalsIgnoreCase("com.digitalchina.info.framework.security.entity.Department")){
			logger.info("department loadAll");
		}
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	/**
	 * 获取指定类的所有持久化实例，使用getCriteria(entityClass).list()
	 */
	public <T> List<T> getAll(Class<T> entityClass) {
		if(entityClass.getName().equalsIgnoreCase("com.digitalchina.info.framework.security.entity.UserInfo")){
			logger.info("userInfo loadAll");
		}else if(entityClass.getName().equalsIgnoreCase("com.digitalchina.info.framework.security.entity.Department")){
			logger.info("department loadAll");
		}
		Criteria c = getCriteria(entityClass);
		return c.list();
	}

	/**
	 * 获取指定类的全部持久化实例,带排序字段与升降序参数.
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
	}
	
	/**
	 * 获取指定类的全部持久化实例,带2个排序字段与升降序参数.
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2) {
		Assert.hasText(orderBy1);
		Assert.hasText(orderBy2);
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.add(Restrictions.isNotNull("id"));
		if(isAsc1){ //按字段1排序
			dc.addOrder(Order.asc(orderBy1));
		}else{
			dc.addOrder(Order.desc(orderBy1));
		}
		if(isAsc2){ //按字段2排序
			dc.addOrder(Order.asc(orderBy2));
		}else{
			dc.addOrder(Order.desc(orderBy2));
		}
		List list = getHibernateTemplate().findByCriteria(dc);
		return list;
		
	}

	/**
	 * 保存游离对象或者持久化对象，相当insert和update/merge方法的何必
	 */
	public Object save(Object obj) {
//		modify by peixf begin
	/*	Class clazz = obj.getClass();
		String idName = this.getIdName(clazz);
		BeanWrapper bw = new BeanWrapperImpl(obj); 
		Object idValue = bw.getPropertyValue(idName);*/
		if(obj instanceof BaseObject){
			BaseObject baseObject = (BaseObject) obj;
			if(baseObject.getId()==null){
				getHibernateTemplate().save(obj);
				getHibernateTemplate().flush();
			}else{
				getHibernateTemplate().merge(obj);
				getHibernateTemplate().flush();
			}
		}else{
			throw new RuntimeException("发生异常,实体"+obj.getClass().getName()+"没有继承BaseObject");
//			BeanWrapper bw = new BeanWrapperImpl(obj); 
//			if(bw.getPropertyValue("id")==null){
//				getHibernateTemplate().save(obj);
//				getHibernateTemplate().flush();
//			}else{
//				getHibernateTemplate().merge(obj);
//				getHibernateTemplate().flush();
//			}
		}
		
		return obj; 
//		modify by peixf end
	}

	/**
	 * 删除持久化对象,对于级联删除将生效
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 根据ID删除持久化对象.
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		remove(get(entityClass, id));
	}

	/**
	 * 刷新缓存，同步数据
	 * @Methods Name flush
	 * @Create In 2008-5-29 By xiaofeng void
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 清空况缓存中所有持久化对象
	 * @Methods Name clear
	 * @Create In 2008-5-29 By xiaofeng void
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}
	
	/**
	 * 清空缓存中指定的持久化对象，批量操作时，加载了对象以后应该立即调用此方法将对象从缓冲中清除
	 * @Methods Name evict
	 * @Create In 2008-5-29 By xiaofeng
	 * @param object void
	 */
	public void evict(Object object) {
		getHibernateTemplate().evict(object);
	}

	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 调用方式如下：
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * @param values 可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		return query;
	}
	
	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * @param values 可变参数.
	 * @param types 可变参数.
	 */
	public Query createQuery(String hql, Object[] values, Type[] types) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i], types[i]); 
			}
		}
		
		return query;
	}
	
	/**
	 * 使用指定的无状态session做参数，进行无状态查询，此方法慎用
	 * @param hql
	 * @param values
	 * @param types
	 */
	protected Query createQuery(StatelessSession session, String hql, 
			Object[] values, Type[] types) {
		Assert.hasText(hql);
		Query query = session.createQuery(hql);
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i], types[i]);
			}
		}
		
		return query;
	}
	
	protected Query createQuery(Session session, String hql, Object[] values) {
		Assert.hasText(hql);
		Query query = session.createQuery(hql);
		//System.out.println("###-- createQuery by session ----"+ session.hashCode());
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 使用指定的无状态session做参数，进行无状态查询，此方法慎用
	 * @param hql
	 * @param values
	 */
	protected Query createQuery(StatelessSession session, String hql, 
			Object... values) {
		Assert.hasText(hql);
		Query query = session.createQuery(hql);
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		return query;
	}
	
	/**
	 * 使用HSQL进行，无状态查询，对于海量数据查询使用。
	 * @param hql
	 * @param values
	 */
	public List findByStatelessQuery(String hql, Object... values) {
		Assert.hasText(hql);
		StatelessSession session = this.getStatelessSession();
		Query query = session.createQuery(hql);
		if(values!=null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		return query.list();
	}

	/**
	 * 创建Criteria对象.
	 * @param criterions 可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	protected <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 获取指定类的Criteria对象，同createCriteria.
	 * @param criterions 
	 */
	public Criteria getCriteria(Class entityClass/*, Criterion... criterions*/) {
		return getSession().createCriteria(entityClass);
	}
	
	/**
	 * 创建Criteria对象.
	 * @param criterions 
	 */
	public Criteria createCriteria(Class entityClass/*, Criterion... criterions*/) {
		return getSession().createCriteria(entityClass);
	}

	
	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * @see #createCriteria(Class,Criterion[])
	 */
	public <T> Criteria createCriteria(Class<T> entityClass, String orderBy, boolean isAsc/*, Criterion... criterions*/) {
		Assert.hasText(orderBy);

		Criteria criteria = this.getCriteria(entityClass);/*createCriteria(entityClass, criterions);*/

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 */
	public List find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName, Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, orderBy, isAsc).add(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 通过hql和查询参数与页大小返回记录的页数
	 * @Methods Name getPageCount
	 * @param hql
	 * @param pageSize
	 * @param values
	 * @return long
	 */
	public long pageCount(String hql, int pageSize, Object... values) {
		Assert.hasText(hql);
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}
	
	
	/**
	 * 获取HQL查询的总记录数
	 * @Methods Name rowCount
	 * @Create In 2008-10-23 By sa
	 * @param hql
	 * @param pageSize
	 * @param values
	 * @return long
	 */
	public long totalCount(String hql, Object... values) {
		Assert.hasText(hql);
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);
		return totalCount;
	}
	
	/**
	 * 分页查询函数，使用hql.
	 * @param pageNo 页号,从1开始.
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * 使用指定的无状态session做参数，进行分页查询函数，此方法慎用
	 * @param session 无状态session
	 */
	protected Page pagedQueryBySession(StatelessSession session,String hql, 
			int pageNo, int pageSize, Object[] values, Type[] paramTypes) {
			 
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		for (int i = 0; i < values.length; i++) {
			qc.setParameter(i, values[i], paramTypes[i]);
		}
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i], paramTypes[i]);
		}
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * 使用指定的session做参数，进行分页查询函数，此方法慎用
	 * @param session 无状态session
	 */
	protected Page pagedQueryBySession(Session session,String hql, 
			int pageNo, int pageSize, Object[] values, Type[] paramTypes) {
			 
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		for (int i = 0; i < values.length; i++) {
			qc.setParameter(i, values[i], paramTypes[i]);
		}
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i], paramTypes[i]);
		}
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * 使用指定的session做参数，进行分页查询函数，此方法慎用
	 * @param session 无状态session
	 */
	protected Page pagedQueryBySession(Session session,String hql, 
			int pageNo, int pageSize) {
			 
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 使用本地数据库连接的无状态session做参数，进行分页查询函数
	 * @param session 无状态session
	 */
	public Page pagedStatelessQuery(String hql, int pageNo, int pageSize, 
			Object[] values, Type[] paramTypes) {
			 
		StatelessSession session = this.getStatelessSession();
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		for (int i = 0; i < values.length; i++) {
			qc.setParameter(i, values[i], paramTypes[i]);
		}
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i], paramTypes[i]);
		}
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.notNull(criteria);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		CriteriaImpl impl = (CriteriaImpl) criteria;
		ResultTransformer resultTransformer = impl.getResultTransformer();
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) BeanUtils.forceGetProperty(impl, "orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
			BeanUtils.forceSetProperty(impl, "resultTransformer", resultTransformer); //新增，解决bug
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 执行查询
		Object uniqueObject = criteria.setProjection(Projections.rowCount()).uniqueResult();
		if(uniqueObject==null){
			throw new RuntimeException(impl.getClass().getName()+"在持久化上下文中无法获取，请检查实体是否映射正确.");
		}
		long totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
			BeanUtils.forceSetProperty(impl, "resultTransformer", resultTransformer);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize/*, Criterion... criterions*/) {
		Criteria criteria = getCriteria(entityClass/*, criterions*/);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 * @param pageNo 页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize, String orderBy, boolean isAsc
						  /* Criterion... criterions*/) {
		Criteria criteria = createCriteria(entityClass, orderBy, isAsc/*, criterions*/);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/*public Page pagedQuery(Class entityClass, int pageNo, int pageSize, String orderBy, boolean isAsc, List criterions) {
		Criterion[] criterion2 = (Criterion[]) criterions.toArray(new Criterion[0]);
		return this.pagedQuery(entityClass, pageNo, pageSize, criterion2);
	}*/
	
	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria(entityClass).setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entityClass, entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	protected Serializable getId(Class entityClass, Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(entityClass);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(entityClass));
	}

	/**
	 * 取得对象的主键名,辅助函数,因框架使用的所有实体OID都是id，故此方法通常不使用。
	 */
	public String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
//		Map allMetas = getSessionFactory().getAllCollectionMetadata();
		
		Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		return idName;
	}
	
	/**
	 * 执行批量HSQL操作，如批量修改或删除。
	 * @Methods Name executeUpdate
	 * @Create In 2008-5-29 By xiaofeng
	 * @param updateHql HQL语句
	 * @param values 参数值数组
	 * @return int
	 */
	public int executeUpdate(final String updateHql, final Object... values) {
        HibernateCallback updateCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(updateHql);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                return new Integer(query.executeUpdate());
            }
        };
        return ((Integer)getHibernateTemplate().execute(updateCallback)).intValue();
    }
	
	/**
	 * 执行批量HSQL操作, 
	 * 通常使用executeUpdate(final String updateHql, final Object... values)即可；
	 * 此方法在无法自动区分参数类型时使用。
	 * @Methods Name executeUpdate
	 * @Create In 2008-5-29 By xiaofeng
	 * @param updateHql  HQL语句
	 * @param values 参数值数组
	 * @param types 参数类型
	 * @return int
	 */
	public int executeUpdate(final String updateHql, 
			final Object[] values, final Type[] types) {
        HibernateCallback updateCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(updateHql);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i], types[i]);
                }
                return new Integer(query.executeUpdate());
            }
        };
        return ((Integer)getHibernateTemplate().execute(updateCallback)).intValue();
    }

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	protected static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}