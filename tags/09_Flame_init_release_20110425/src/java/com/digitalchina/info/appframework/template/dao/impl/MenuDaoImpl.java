/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.info.framework.security.dao.implMenuServiceImpl.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 ÏÂÎç02:56:25
 * TODO
 */
package com.digitalchina.info.appframework.template.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.appframework.template.dao.MenuDao;
import com.digitalchina.info.appframework.template.entity.Menu;
import com.digitalchina.info.framework.dao.BaseDao;
/**
 * @Class Name MenuServiceImpl
 * @Author zhangpeng
 * @Create In 2008-7-17
 */
public class MenuDaoImpl extends BaseDao implements MenuDao {

	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.dao.MenuService#findAll()
	 */
	public List<Menu> selectAll() {
		Criteria c = super.getCriteria(Menu.class);
		List list = c.list(); 
		if(list.isEmpty()) return null;
		return list;
	}

	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.dao.MenuService#findMenuByID(java.lang.Long)
	 */
	public Menu selectMenuByID(Long id) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Menu.class);
		c.add(Expression.eq("id", id));
		List list = c.list(); 
		if(list.isEmpty()) return null;
		return (Menu) list.iterator().next();
	}

	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.dao.MenuService#save(com.digitalchina.info.framework.security.entity.Menu)
	 */
	public Menu save(Menu menu) {
		// TODO Auto-generated method stub
		return (Menu) super.save(menu);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.dao.MenuDao#selectMenusByName(java.lang.String)
	 */
	public List<Menu> selectMenusByName(String name) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Menu.class);
		c.add(Restrictions.like("menuName", name, MatchMode.ANYWHERE));
		List list = c.list(); 
		
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.dao.MenuDao#selectMenuByLevel(java.lang.Integer)
	 */
	public List<Menu> selectMenuByLevel(Integer level) {
		Criteria c = super.getCriteria(Menu.class);
		c.add(Expression.eq("level", level));
		c.addOrder(Order.asc("id"));
		
		List<Menu> list = c.list();
		
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.dao.MenuDao#selectMenuByPrentMenu(com.digitalchina.info.framework.security.entity.Menu)
	 */
	public List<Menu> selectMenuByPrentMenu(Menu parent) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(Menu.class);
		c.add(Expression.eq("parentMenu", parent));
		c.addOrder(Order.asc("id"));
		
		List<Menu> list = c.list();
		
		return list;
	}

}
