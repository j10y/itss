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
 * Hibernate Dao�ķ��ͻ���.
 * <p/>
 * �̳���Spring��<code>HibernateDaoSupport</code>,�ṩ��ҳ���������ɱ�ݲ�ѯ���������Է���ֵ���˷�������ת��.
 * ע�⣬���в�������Session��StatelessSession�ķ������粻�˽����ڲ�ԭ������.
 * @author xiaofeng
 * @see HibernateDaoSupport
 * @see BaseDao
 */
@SuppressWarnings("unchecked")
public class HibernateGenericDao extends HibernateDaoSupport {
	
	/**
	 * �������ô˷���
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
	 * ��ȡ�־û�����Ϣ
	 * @Methods Name getClassMapping
	 * @Create In Jul 17, 2009 By sa
	 * @param entityClass
	 * @return PersistentClass
	 */
	public PersistentClass getClassMapping(Class entityClass) {
		return getConfiguration().getClassMapping(entityClass.getName());
	}
	
	/**
	 * ֱ�ӻ�ȡspring�����SessionFactory
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
	 * ��ȡspring�����Configuration
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
	 * ��ȡ��״̬Session, �˷�������
	 * @return StatelessSession
	 */
	public StatelessSession getStatelessSession(){
		return this.getSessionFactory().openStatelessSession();
	}
	
	/**
	 * ����ID��ȡ�־û�ʵ��. ʵ�ʵ���Hibernate��session.load()��������ʵ�����proxy����. ������󲻴��ڣ��׳��쳣.
	 */
	public <T> T load(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * ����ID��ȡ�־û�ʵ��. ʵ�ʵ���Hibernate��session.get()��������ʵ��. ������󲻴��ڣ�����null.
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}
	/**
	 * ��ȡȫ���־û�ʵ������getAll��������ʹ��getHibernateTemplate()��loadAll
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
	 * ��ȡָ��������г־û�ʵ����ʹ��getCriteria(entityClass).list()
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
	 * ��ȡָ�����ȫ���־û�ʵ��,�������ֶ������������.
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
	 * ��ȡָ�����ȫ���־û�ʵ��,��2�������ֶ������������.
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2) {
		Assert.hasText(orderBy1);
		Assert.hasText(orderBy2);
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.add(Restrictions.isNotNull("id"));
		if(isAsc1){ //���ֶ�1����
			dc.addOrder(Order.asc(orderBy1));
		}else{
			dc.addOrder(Order.desc(orderBy1));
		}
		if(isAsc2){ //���ֶ�2����
			dc.addOrder(Order.asc(orderBy2));
		}else{
			dc.addOrder(Order.desc(orderBy2));
		}
		List list = getHibernateTemplate().findByCriteria(dc);
		return list;
		
	}

	/**
	 * �������������߳־û������൱insert��update/merge�����ĺα�
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
			throw new RuntimeException("�����쳣,ʵ��"+obj.getClass().getName()+"û�м̳�BaseObject");
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
	 * ɾ���־û�����,���ڼ���ɾ������Ч
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * ����IDɾ���־û�����.
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		remove(get(entityClass, id));
	}

	/**
	 * ˢ�»��棬ͬ������
	 * @Methods Name flush
	 * @Create In 2008-5-29 By xiaofeng void
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * ��տ����������г־û�����
	 * @Methods Name clear
	 * @Create In 2008-5-29 By xiaofeng void
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}
	
	/**
	 * ��ջ�����ָ���ĳ־û�������������ʱ�������˶����Ժ�Ӧ���������ô˷���������ӻ��������
	 * @Methods Name evict
	 * @Create In 2008-5-29 By xiaofeng
	 * @param object void
	 */
	public void evict(Object object) {
		getHibernateTemplate().evict(object);
	}

	/**
	 * ����Query����. ������Ҫfirst,max,fetchsize,cache,cacheRegion��������õĺ���,�����ڷ���Query����������.
	 * ���������������,���£�
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * ���÷�ʽ���£�
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * @param values �ɱ����.
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
	 * ����Query����. ������Ҫfirst,max,fetchsize,cache,cacheRegion��������õĺ���,�����ڷ���Query����������.
	 * @param values �ɱ����.
	 * @param types �ɱ����.
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
	 * ʹ��ָ������״̬session��������������״̬��ѯ���˷�������
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
	 * ʹ��ָ������״̬session��������������״̬��ѯ���˷�������
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
	 * ʹ��HSQL���У���״̬��ѯ�����ں������ݲ�ѯʹ�á�
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
	 * ����Criteria����.
	 * @param criterions �ɱ��Restrictions�����б�,��{@link #createQuery(String,Object...)}
	 */
	protected <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * ��ȡָ�����Criteria����ͬcreateCriteria.
	 * @param criterions 
	 */
	public Criteria getCriteria(Class entityClass/*, Criterion... criterions*/) {
		return getSession().createCriteria(entityClass);
	}
	
	/**
	 * ����Criteria����.
	 * @param criterions 
	 */
	public Criteria createCriteria(Class entityClass/*, Criterion... criterions*/) {
		return getSession().createCriteria(entityClass);
	}

	
	/**
	 * ����Criteria���󣬴������ֶ����������ֶ�.
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
	 * ����hql��ѯ,ֱ��ʹ��HibernateTemplate��find����.
	 * @param values �ɱ����,��{@link #createQuery(String,Object...)}
	 */
	public List find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * ����������������ֵ��ѯ����.
	 * @return ���������Ķ����б�
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * ����������������ֵ��ѯ����,���������.
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName, Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, orderBy, isAsc).add(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * ����������������ֵ��ѯΨһ����.
	 * @return ����������Ψһ���� or null if not found.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * ͨ��hql�Ͳ�ѯ������ҳ��С���ؼ�¼��ҳ��
	 * @Methods Name getPageCount
	 * @param hql
	 * @param pageSize
	 * @param values
	 * @return long
	 */
	public long pageCount(String hql, int pageSize, Object... values) {
		Assert.hasText(hql);
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}
	
	
	/**
	 * ��ȡHQL��ѯ���ܼ�¼��
	 * @Methods Name rowCount
	 * @Create In 2008-10-23 By sa
	 * @param hql
	 * @param pageSize
	 * @param values
	 * @return long
	 */
	public long totalCount(String hql, Object... values) {
		Assert.hasText(hql);
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);
		return totalCount;
	}
	
	/**
	 * ��ҳ��ѯ������ʹ��hql.
	 * @param pageNo ҳ��,��1��ʼ.
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// ʵ�ʲ�ѯ���ط�ҳ����
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * ʹ��ָ������״̬session�����������з�ҳ��ѯ�������˷�������
	 * @param session ��״̬session
	 */
	protected Page pagedQueryBySession(StatelessSession session,String hql, 
			int pageNo, int pageSize, Object[] values, Type[] paramTypes) {
			 
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		for (int i = 0; i < values.length; i++) {
			qc.setParameter(i, values[i], paramTypes[i]);
		}
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// ʵ�ʲ�ѯ���ط�ҳ����
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i], paramTypes[i]);
		}
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * ʹ��ָ����session�����������з�ҳ��ѯ�������˷�������
	 * @param session ��״̬session
	 */
	protected Page pagedQueryBySession(Session session,String hql, 
			int pageNo, int pageSize, Object[] values, Type[] paramTypes) {
			 
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		for (int i = 0; i < values.length; i++) {
			qc.setParameter(i, values[i], paramTypes[i]);
		}
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// ʵ�ʲ�ѯ���ط�ҳ����
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i], paramTypes[i]);
		}
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * ʹ��ָ����session�����������з�ҳ��ѯ�������˷�������
	 * @param session ��״̬session
	 */
	protected Page pagedQueryBySession(Session session,String hql, 
			int pageNo, int pageSize) {
			 
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// ʵ�ʲ�ѯ���ط�ҳ����
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * ʹ�ñ������ݿ����ӵ���״̬session�����������з�ҳ��ѯ����
	 * @param session ��״̬session
	 */
	public Page pagedStatelessQuery(String hql, int pageNo, int pageSize, 
			Object[] values, Type[] paramTypes) {
			 
		StatelessSession session = this.getStatelessSession();
		// Count��ѯ
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		Query qc = session.createQuery(countQueryString);
		for (int i = 0; i < values.length; i++) {
			qc.setParameter(i, values[i], paramTypes[i]);
		}
		List countlist = qc.list();
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1)
			return new Page();
		// ʵ�ʲ�ѯ���ط�ҳ����
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i], paramTypes[i]);
		}
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	/**
	 * ��ҳ��ѯ������ʹ������ò�ѯ�����������<code>Criteria</code>.
	 * @param pageNo ҳ��,��1��ʼ.
	 * @return ���ܼ�¼���͵�ǰҳ���ݵ�Page����.
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.notNull(criteria);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		CriteriaImpl impl = (CriteriaImpl) criteria;
		ResultTransformer resultTransformer = impl.getResultTransformer();
		// �Ȱ�Projection��OrderBy����ȡ����,���������ִ��Count����
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) BeanUtils.forceGetProperty(impl, "orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
			BeanUtils.forceSetProperty(impl, "resultTransformer", resultTransformer); //���������bug
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// ִ�в�ѯ
		Object uniqueObject = criteria.setProjection(Projections.rowCount()).uniqueResult();
		if(uniqueObject==null){
			throw new RuntimeException(impl.getClass().getName()+"�ڳ־û����������޷���ȡ������ʵ���Ƿ�ӳ����ȷ.");
		}
		long totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();

		// ��֮ǰ��Projection��OrderBy�����������ȥ
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

		// ���ط�ҳ����
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * ��ҳ��ѯ����������entityClass�Ͳ�ѯ������������Ĭ�ϵ�<code>Criteria</code>.
	 * @param pageNo ҳ��,��1��ʼ.
	 * @return ���ܼ�¼���͵�ǰҳ���ݵ�Page����.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize/*, Criterion... criterions*/) {
		Criteria criteria = getCriteria(entityClass/*, criterions*/);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * ��ҳ��ѯ����������entityClass�Ͳ�ѯ��������,�����������Ĭ�ϵ�<code>Criteria</code>.
	 * @param pageNo ҳ��,��1��ʼ.
	 * @return ���ܼ�¼���͵�ǰҳ���ݵ�Page����.
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
	 * �ж϶���ĳЩ���Ե�ֵ�����ݿ����Ƿ�Ψһ.
	 * @param uniquePropertyNames ��POJO�ﲻ���ظ��������б�,�Զ��ŷָ� ��"name,loginid,password"
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria(entityClass).setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// ѭ������Ψһ��
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}

			// ���´���Ϊ�������update�����,�ų�entity����.

			String idName = getIdName(entityClass);

			// ȡ��entity������ֵ
			Serializable id = getId(entityClass, entity);

			// ���id!=null,˵�������Ѵ���,�ò���Ϊupdate,�����ų�������ж�
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}

	/**
	 * ȡ�ö��������ֵ,��������.
	 */
	protected Serializable getId(Class entityClass, Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(entityClass);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(entityClass));
	}

	/**
	 * ȡ�ö����������,��������,����ʹ�õ�����ʵ��OID����id���ʴ˷���ͨ����ʹ�á�
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
	 * ִ������HSQL�������������޸Ļ�ɾ����
	 * @Methods Name executeUpdate
	 * @Create In 2008-5-29 By xiaofeng
	 * @param updateHql HQL���
	 * @param values ����ֵ����
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
	 * ִ������HSQL����, 
	 * ͨ��ʹ��executeUpdate(final String updateHql, final Object... values)���ɣ�
	 * �˷������޷��Զ����ֲ�������ʱʹ�á�
	 * @Methods Name executeUpdate
	 * @Create In 2008-5-29 By xiaofeng
	 * @param updateHql  HQL���
	 * @param values ����ֵ����
	 * @param types ��������
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
	 * ȥ��hql��select �Ӿ䣬δ����union�����,����pagedQuery.
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
	 * ȥ��hql��orderby �Ӿ䣬����pagedQuery.
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