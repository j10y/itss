package com.zsgj.info.framework.orm.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.orm.DaoSupport;
import com.zsgj.info.framework.util.Page;

/**
 * DaoSupport接口的Hibernate3实现类。
 * 该类继承Hibernate3的HibernateDaoSupport，使用HibernateTemplate
 * 实现DaoSupport接口中定义的通用数据服务方法。
 * @Class Name DaoSupportHibernate3Imp
 * @author xiaofeng
 * @Create In 2007-10-30
 */
public class DaoSupportHibernate3Imp extends HibernateDaoSupport implements DaoSupport {
	


	public ClassMetadata getClassMetadata(String className) {
		return getSessionFactory().getClassMetadata(className);
	}

	public Map getAllCollectionMetadata() {
		SessionFactory sf = getSessionFactory();
		Map map = sf.getAllCollectionMetadata();
		return map;
	}

	public Map getAllClassMetadata() {
		SessionFactory sf = getSessionFactory();
		Map map = sf.getAllClassMetadata();
		return map;
	}

	public String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		Map allMetas = getSessionFactory().getAllCollectionMetadata();
		
		Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		return idName;
	}

	public void evict(BaseObject object) {
		getHibernateTemplate().evict(object);
		
	}

	public Object selectByKey(Class clazz, String keyName, Object keyValue) {
		try {
			List result = getHibernateTemplate().find(
					"from " + clazz.getName() + " where " + keyName + " = ?",
					keyValue);
			if (result != null && result.size() > 0) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (DataAccessException exception) {
			throw exception;
		}
	}

	public int selectCountByCriteria(final DetachedCriteria dc) {
//		Integer count = (Integer) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session session)
//							throws HibernateException {
//						Criteria criteria = dc
//								.getExecutableCriteria(session);
//						return criteria.setProjection(Projections.rowCount())
//								.uniqueResult();
//					}
//				}, true);
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					public Integer doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = dc
								.getExecutableCriteria(session);
						return (Integer)(criteria.setProjection(Projections.rowCount())
								.uniqueResult());
					}
				});
		return count.intValue();
	}

	public Object insertOrUpdate(BaseObject entity) {
		Object object = null;
		if(entity.getId()==null){
			getHibernateTemplate().save(entity);
			getHibernateTemplate().flush();
			object = entity;
		}else{
			object = getHibernateTemplate().merge(entity);
			getHibernateTemplate().flush();
		}
		return object;
	}

	public Object update(BaseObject object) {
		if(object.getId()==null) throw new DaoException("实体主键为null，更新失败");
		getHibernateTemplate().merge(object);
		getHibernateTemplate().flush();
		return object;
	}

	 public int executeUpdate(final String updateHql, final Object[] values) {
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
	 
	public void clear() {
		getHibernateTemplate().clear();
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public Iterator iterate(String queryString, Object value) {
		return getHibernateTemplate().iterate(queryString, value);
	}

	public Iterator iterate(String queryString, Object[] values) {
		return getHibernateTemplate().iterate(queryString, values);
	}

	public Iterator iterate(String queryString) {
		return getHibernateTemplate().iterate(queryString);
	}

	public Object load(Class entityClass, Serializable id) {
		return getHibernateTemplate().load(entityClass, id);
	}

	public List loadAll(Class entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public List selectByCriteria(DetachedCriteria dc) {
		return getHibernateTemplate().findByCriteria(dc);
	}

	public List selectByExample(Object exampleEntity, Page page) {
		return getHibernateTemplate().findByExample(exampleEntity, page.getFirstResult(), page.getPageCount());
	}

	public List selectByExample(Object exampleEntity) {
		return getHibernateTemplate().findByExample(exampleEntity);
	}

	public List selectByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
	}

	public List selectForList(String select) {
		return getHibernateTemplate().find(select);
	}

	public Object selectForObject(final String select) {
		HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                return query.uniqueResult();
            }
        };
        return getHibernateTemplate().execute(selectCallback);
	}

	public Object select(Class clazz, Serializable id) {
    	Object t = getHibernateTemplate().get(clazz, id);
        return t; 
    }
    
	public List selectAll(Class clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	public void update(List list) {	
		getHibernateTemplate().saveOrUpdateAll(list);
	}
	
    /**
     * 删除实体的默认实现
     */
    public void delete(BaseObject t) {
        getHibernateTemplate().delete(t);
    }
    
    /**
     * 根据OID删除实体
     */
    public void delete(Class clazz, Serializable id) {
    	Object entity = getHibernateTemplate().load(clazz, id);
    	getHibernateTemplate().delete(entity);
	}

	public void delete(List list) {
		getHibernateTemplate().deleteAll(list);
	}
    
    public List selectForList(final String select, final Object value) {
    	HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                query.setParameter(0, value);
                
                return query.list();
            }
        };
        return (List) getHibernateTemplate().executeFind(selectCallback);
	}

	public Object selectForObject(final String select, final Object value) {
		HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                if(value!=null){
                	query.setParameter(0, value);
                }
                return query.uniqueResult();
            }
        };
        return (Object) getHibernateTemplate().execute(selectCallback);
	}

//	public Object selectForObject(Class clazz, Object[] values) {
//		return null;
//	}
	
	public List selectForList(final String select, final Object[] values) {
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                List list = query.list();
                return list;
            }
        };
        return (List) getHibernateTemplate().executeFind(selectCallback);
	}
    
    /**
     * Prepared for sub-class for convenience. Query for list and also return 
     * total results' number.
     * 
     * @param selectCount HQL for "select count(*) from ..." and should return a Long.
     * @param select HQL for "select * from ..." and should return object list.
     * @param values For prepared statements.
     * @param page Page object for store page information.
     */
    public List selectForList(final String selectCount, final String select, final Object[] values, final Page page) {
        Long count = (Long)selectForObject(selectCount, values);
        page.setTotalCount(count.intValue());
        if(page.isEmpty())
            return Collections.EMPTY_LIST;
        return selectForList(select, values, page);
    }

    /**
     * Prepared for sub-class for convenience. Query for list but do not return 
     * total results' number.
     * 
     * @param select HQL for "select * from ..." and should return object list.
     * @param values For prepared statements.
     * @param page Page object for store page information.
     */
    public List selectForList(final String select, final Object[] values, final Page page) {
        // select:
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                return query.setFirstResult(page.getFirstResult())
                            .setMaxResults(page.getPageSize())
                            .list();
            }
        };
        return (List) getHibernateTemplate().executeFind(selectCallback);
    }

    /**
     * Prepared for sub-class for convenience. Query for unique result.
     * 
     * @param select HQL for "select * from ..." and should return unique object.
     * @param values For prepared statements.
     */
    public Object selectForObject(final String select, final Object[] values) {
    	
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                return query.uniqueResult();
            }
        };
        return getHibernateTemplate().execute(selectCallback);
    }

    public Object selectForObject(final DetachedCriteria dc) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                return dc.getExecutableCriteria(session).uniqueResult();
            }
        };
        return getHibernateTemplate().execute(callback);
    }

    /**
     * Prepared for sub-class for convenience.
     */
    public List selectForList(final DetachedCriteria dc, final Page page) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria c = dc.getExecutableCriteria(session); 
                if(page==null)
                    return c.list();
                return PaginationCriteria.query(c, page);
            }
        };
        return getHibernateTemplate().executeFind(callback);
    }

    /**
     * Prepared for sub-class for convenience.
     */
    public Object uniqueResult(final DetachedCriteria dc) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                return dc.getExecutableCriteria(session).uniqueResult();
            }
        };
        return getHibernateTemplate().execute(callback);
    }

	public List selectByCriteriaAndPage(DetachedCriteria dc, Page page) {
		Session session = getSession();
		Criteria c = dc.getExecutableCriteria(session);
		c.setFirstResult(page.getPageIndex()-1); //起始index为0，故减1
		c.setMaxResults(page.getPageSize()+1); //取pageSize+1条记录，最后一条用于判断是否有下一页
		return c.list();
	}

}


