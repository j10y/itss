package com.digitalchina.info.appframework.menu.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.digitalchina.info.appframework.menu.dao.MenuDao;
import com.digitalchina.info.appframework.menu.entity.DeptMenu;
import com.digitalchina.info.appframework.menu.entity.DeptMenuItem;
import com.digitalchina.info.appframework.menu.entity.TemplateMenuItem;
import com.digitalchina.info.appframework.menu.entity.UserExtraMenuItem;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class MenuDaoImpl extends BaseDao implements MenuDao{

	public List<TemplateMenuItem> findTemplateMenuItemByParent(String parentId) {
		List<TemplateMenuItem> list = new ArrayList();
		if(StringUtils.isNotBlank(parentId)){
			Criteria c = super.getCriteria(TemplateMenuItem.class);
			c.add(Expression.eq("parentItem.id",Long.valueOf(parentId)));
			c.addOrder(Order.asc("menuOrder"));
			list = c.list();
		}
		return list;
	}

	public List<TemplateMenuItem> findTemplateMenuItemNoParent() {
		List<TemplateMenuItem> list = new ArrayList();
		Criteria c = super.createCriteria(TemplateMenuItem.class);
		c.add(Expression.isNull("parentItem"));
		c.addOrder(Order.asc("menuOrder"));
		list = c.list();
		return list;
	}

	public TemplateMenuItem findTemplateMenuItemById(String id) {
		return this.get(TemplateMenuItem.class, Long.valueOf(id));
	}

	public TemplateMenuItem save(TemplateMenuItem templateMenuItem) {
		TemplateMenuItem newTemplateMenuItem = (TemplateMenuItem) super.save(templateMenuItem);
		return newTemplateMenuItem;
	}

	public List<DeptMenu> findAllDeptMenus() {
		List<DeptMenu> list = new ArrayList();
		Criteria c = super.createCriteria(DeptMenu.class);
		c.setFetchMode("dept", FetchMode.JOIN);
		list = c.list();
		return list;
	}

	public DeptMenu findDeptMenuById(String id) {
		DeptMenu deptMenu = null;
		if(StringUtils.isNotBlank(id)){
			Criteria c = super.createCriteria(DeptMenu.class);
			c.add(Expression.eq("id",Long.valueOf(id)));
			deptMenu = (DeptMenu) c.uniqueResult();
		}
		return deptMenu; 
	}

	public List<TemplateMenuItem> findTemplateMenuItemInDeptMenu(
			DeptMenu deptmenu) {
		List<DeptMenuItem> deptMenuItems = new ArrayList();
		List<TemplateMenuItem> templateMenuItems = new ArrayList();
		Criteria c = super.createCriteria(DeptMenuItem.class);
		c.add(Expression.eq("deptMenu",deptmenu));
		c.setFetchMode("templateMenuItem", FetchMode.JOIN);
		deptMenuItems = c.list();
		for(DeptMenuItem deptMenuItem : deptMenuItems){
			templateMenuItems.add(deptMenuItem.getTemplateMenuItem());
		}
		return templateMenuItems;
	}

	public void removeDeptMenu(DeptMenu deptMenu) {
		super.remove(deptMenu);
		
	}

	public DeptMenu saveDeptMenu(DeptMenu deptMenu) {
		// TODO Auto-generated method stub
		return (DeptMenu) super.save(deptMenu);
	}

	public DeptMenuItem findDeptMenuItem(DeptMenu deptMenu,
			TemplateMenuItem templateMenuItem) {
		Criteria c = super.createCriteria(DeptMenuItem.class);
		c.add(Expression.eq("deptMenu",deptMenu));
		c.add(Expression.eq("templateMenuItem",templateMenuItem));
		return (DeptMenuItem) c.uniqueResult();
	}

	public List<TemplateMenuItem> findTemplateMenuItemInUserExtraMenu(
			UserInfo user) {
		List<UserExtraMenuItem> userExtraMenuItems = new ArrayList();
		List<TemplateMenuItem> templateMenuItems = new ArrayList();
		Criteria c = super.createCriteria(UserExtraMenuItem.class);
		c.add(Expression.eq("userInfo",user));
		c.setFetchMode("templateMenuItem", FetchMode.JOIN);
		userExtraMenuItems = c.list();
		for(UserExtraMenuItem userExtraMenuItem : userExtraMenuItems){
			templateMenuItems.add(userExtraMenuItem.getTemplateMenuItem());
		}
		return templateMenuItems;
	}

	public UserExtraMenuItem findUserExtraMenuItem(UserInfo user,
			TemplateMenuItem templateMenuItem) {
		Criteria c = super.createCriteria(UserExtraMenuItem.class);
		c.add(Expression.eq("userInfo",user));
		c.add(Expression.eq("templateMenuItem",templateMenuItem));
		return (UserExtraMenuItem) c.uniqueResult();
	}

	public List<DeptMenu> findDeptMenuByDept(Department dept) {
		Criteria c = super.createCriteria(DeptMenu.class);
		c.add(Expression.eq("dept",dept));
		return c.list();
	}

	public List<DeptMenuItem> findDeptMenuItem(TemplateMenuItem templateMenuItem) {
		Criteria c = super.createCriteria(DeptMenuItem.class);
		c.add(Expression.eq("templateMenuItem",templateMenuItem));
		return c.list();
	}

	public List<UserExtraMenuItem> findUserExtraMenuItem(
			TemplateMenuItem templateMenuItem) {
		Criteria c = super.createCriteria(UserExtraMenuItem.class);
		c.add(Expression.eq("templateMenuItem",templateMenuItem));
		return c.list();
	}
}
