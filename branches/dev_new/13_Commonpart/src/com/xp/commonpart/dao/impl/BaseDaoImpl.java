package com.xp.commonpart.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springside.core.utils.BeanUtils;

import com.xp.commonpart.Page;
import com.xp.commonpart.dao.BaseDao;
import com.xp.commonpart.util.DateUtil;
import com.xp.commonpart.util.WebUtils;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	public List findObjectByPar(Class clazz, String propName, Object propValue) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
//		if(propName!=null){
//			c.add(Restrictions.eq(propName, propValue));
//		}
		if(propValue==null){
			c.add(Restrictions.isNull(propName));
		}else {
			c.add(Restrictions.eq(propName,propValue));
		}
		return c.list();
	}

	public List findObjectByPars(Class clazz, String[] propNames,
			Object[] propValues) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		for(int i=0;i<propNames.length;i++){
			c.add(Restrictions.eq(propNames[i], propValues[i]));
		}
		return c.list();
	}

	public void remove(Object obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}
	public Long selectMaxIdFromTable(Class clazz,String propertyName){
		Criteria c=this.getSession().createCriteria(clazz);
		c.setProjection(Projections.projectionList()
				.add(Projections.max(propertyName))
		);
		Object ob=c.uniqueResult();
		Long orderMax = 0l; 
		if(ob!=null){
			orderMax = Long.parseLong(ob.toString());
		}
		return orderMax;
	}
	private Long selectSequence(){
		Object object=this.getSession().createSQLQuery("select seq_number.nextval from dual").uniqueResult();
		return Long.parseLong(object.toString());
	}
	public Object save(Object obj,Class clazz,String key) {
		// TODO Auto-generated method stub
		BeanWrapper bw= new BeanWrapperImpl(obj); 
		Object provalue=bw.getPropertyValue(key);
		if(provalue==null||provalue.equals("")){ 
			Long id=this.selectMaxIdFromTable(clazz, key)+1;
			//selectSequence();
			//Long id=Long.parseLong(DateUtil.getDateToStringFull2(new Date())+selectSequence());
			//String id=DateUtil.getDateToStringFull2(new Date())+System.currentTimeMillis()+DateUtil.getRandom(1000,9999);
			bw.setPropertyValue(key, id.toString());
			this.getHibernateTemplate().save(obj);
			this.getHibernateTemplate().flush();
		}else{
			getHibernateTemplate().merge(obj);
			getHibernateTemplate().flush();
		}
		return obj;
	}

	public List findObjectByParOrder(Class clazz, String propName,
			Object propValue, String orderName, String ascoOrdesc) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		c.add(Restrictions.eq(propName, propValue));
		if(orderName!=null&&ascoOrdesc.equals("desc")){
			c.addOrder(Order.desc(orderName));
		}else if(orderName!=null&&ascoOrdesc.equals("asc")){
			c.addOrder(Order.asc(orderName));
		}
		return c.list();
	}	

	public List findObjectByLikeParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String[] orderNames, String[] ascoOrdescs) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		for(int i=0;i<propNames.length;i++){
			if( propValues[i].toString().length()>0){
				c.add(Restrictions.ilike(propNames[i], propValues[i].toString(),MatchMode.ANYWHERE));
			}
			
		}
		for(int i=0;i<orderNames.length;i++){
			String orderName=orderNames[i];
			String ascoOrdesc=ascoOrdescs[i];
			if(orderName!=null&&ascoOrdesc.equals("desc")){
				c.addOrder(Order.desc(orderName));
			}else if(orderName!=null&&ascoOrdesc.equals("asc")){
				c.addOrder(Order.asc(orderName));
			}
		}
		return c.list();
	}

	public List findObjectByParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String orderName, String ascoOrdesc) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		for(int i=0;i<propNames.length;i++){
			c.add(Restrictions.eq(propNames[i], propValues[i]));
		}
		if(orderName!=null&&ascoOrdesc.equals("desc")){
			c.addOrder(Order.desc(orderName));
		}else if(orderName!=null&&ascoOrdesc.equals("asc")){
			c.addOrder(Order.asc(orderName));
		}
		return c.list();
	}
	public List findObjectListByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		if(proMap!=null){
			Set set=proMap.keySet();
			Iterator it=set.iterator();
			while(it.hasNext()){
				Object obkey=it.next();
				Object obvalue=proMap.get(obkey);
				c.add(Restrictions.eq(obkey.toString(),obvalue));
			}
		}
		if(orderMap!=null){
			Set set1=orderMap.keySet();
			Iterator it1=set1.iterator();
			while(it1.hasNext()){
				Object obkey=it1.next();
				Object obvalue=orderMap.get(obkey);
				if(obvalue!=null&&obvalue.equals("desc")){
					c.addOrder(Order.desc(obkey.toString()));
				}else if(obvalue!=null&&obvalue.equals("asc")){
					c.addOrder(Order.asc(obkey.toString()));
				}
			}
		}
		return c.list();
	}
	
	public Long getUniqueId(){
		Long id=Long.parseLong(DateUtil.getDateToStringFull2(new Date())+selectSequence());
		return id;
	}
	public Page findObjectPageByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap,int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		if(proMap!=null){
			Set set=proMap.keySet();
			Iterator it=set.iterator();
			while(it.hasNext()){
				Object obkey=it.next();
				Object obvalue=proMap.get(obkey);
				c.add(Restrictions.eq(obkey.toString(),obvalue));
			}
		}
		if(orderMap!=null){
			Set set1=orderMap.keySet();
			Iterator it1=set1.iterator();
			while(it1.hasNext()){
				Object obkey=it1.next();
				Object obvalue=orderMap.get(obkey);
				if(obvalue!=null&&obvalue.equals("desc")){
					c.addOrder(Order.desc(obkey.toString()));
				}else if(obvalue!=null&&obvalue.equals("asc")){
					c.addOrder(Order.asc(obkey.toString()));
				}
			}
		}
		return pagedQuery(c,pageNo,pageSize);
		//return c.list();
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
		int totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();

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
		return new Page(pageNo, totalCount, pageSize, list);
	}

	public Page findObjectPageByMultParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap proMultMap,
			LinkedHashMap orderMap, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		if(proMap!=null){
			Set set=proMap.keySet();
			Iterator it=set.iterator();
			while(it.hasNext()){
				Object obkey=it.next();
				Object obvalue=proMap.get(obkey);
				c.add(Restrictions.eq(obkey.toString(),obvalue));
			}
		}
		if(proMultMap!=null){
			Set set=proMultMap.keySet();
			Iterator it=set.iterator();
			while(it.hasNext()){
				Object obkey=it.next();
				Object obvalue=proMultMap.get(obkey);
				c.add(Restrictions.ilike(obkey.toString(),obvalue));
			}
		}
		if(orderMap!=null){
			Set set1=orderMap.keySet();
			Iterator it1=set1.iterator();
			while(it1.hasNext()){
				Object obkey=it1.next();
				Object obvalue=orderMap.get(obkey);
				if(obvalue!=null&&obvalue.equals("desc")){
					c.addOrder(Order.desc(obkey.toString()));
				}else if(obvalue!=null&&obvalue.equals("asc")){
					c.addOrder(Order.asc(obkey.toString()));
				}
			}
		}
		return pagedQuery(c,pageNo,pageSize);
	}

	public Object findObjectById(Class clazz, String key, Object id) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(clazz);
		c.add(Restrictions.eq(key,id));
		return c.uniqueResult();
	}
}
