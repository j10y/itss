package com.xp.commonpart.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;
import com.xp.commonpart.dao.BaseDao;
import com.xp.commonpart.dao.MainTableDao;

public class MainTableDaoImpl extends HibernateDaoSupport implements MainTableDao {
	
	private BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List selectMainTable() {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(MainTable.class);
		c.addOrder(Order.asc("tableid"));
		return c.list();
	}
	
	public List selectMainTableColumn(MainTable mainTable) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(MainTableColumn.class);
		c.add(Restrictions.eq("maintableid", mainTable.getTableid()));	
		c.addOrder(Order.asc("columnid"));
		return c.list();
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
	/**
	 * �������Ϣ
	 */
	public MainTable saveMainTable(MainTable mainTable) {
		// TODO Auto-generated method stub
		if(mainTable.getTableid()==null){
			Long id=this.selectMaxIdFromTable(MainTable.class, "tableid")+1;
			mainTable.setTableid(id);
			this.getHibernateTemplate().save(mainTable);
			this.getHibernateTemplate().flush();
		}else{
			getHibernateTemplate().merge(mainTable);
			getHibernateTemplate().flush();
		}
		
		return mainTable;
	}
	
	public void removeObject(Object obj){
		this.getHibernateTemplate().delete(obj);
	}
	
	public void removeMainTable(MainTable mainTable) {
		// TODO Auto-generated method stub
		this.removeObject(mainTable);
		List<MainTableColumn> list=this.selectMainTableColumn(mainTable);
		for(MainTableColumn mt:list){
			this.removeObject(mt);
		}
	}
	public MainTable selectMainTableByMainId(Long maintableid) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(MainTable.class);
		c.add(Restrictions.eq("tableid",maintableid));	
		MainTable maintable=(MainTable) c.uniqueResult();
		return maintable;
	}

	public List selectMainTableColumnList(){
		Criteria c=this.getSession().createCriteria(MainTableColumn.class);
		c.addOrder(Order.asc("columnid"));
		return c.list();
	}

	public List selectMainTableColumnById(Long mainTableId) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(MainTableColumn.class);
		c.add(Restrictions.eq("maintableid", mainTableId));		
		c.addOrder(Order.asc("columnid"));
		return c.list();
	}

	public MainTableColumn selectMainTableColumnByColumnId(Long columnid) {
		// TODO Auto-generated method stub
		Criteria c=this.getSession().createCriteria(MainTableColumn.class);
		c.add(Restrictions.eq("columnid", columnid));
		MainTableColumn mc=(MainTableColumn) c.uniqueResult();
		return mc;
	}

	public MainTableColumn saveMainTableColumn(MainTableColumn mainTableColumn) {
		// TODO Auto-generated method stub
		if(mainTableColumn.getColumnid()==null){
			Long id=this.selectMaxIdFromTable(MainTableColumn.class, "columnid")+1;
			mainTableColumn.setColumnid(id);
			this.getHibernateTemplate().save(mainTableColumn);
			this.getHibernateTemplate().flush();
		}else{
			getHibernateTemplate().merge(mainTableColumn);
			getHibernateTemplate().flush();
		}
		return mainTableColumn;
	}
	
}
