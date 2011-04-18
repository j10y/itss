package com.digitalchina.info.appframework.menu.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.digitalchina.info.appframework.menu.dao.MenuDao;
import com.digitalchina.info.appframework.menu.entity.DeptMenu;
import com.digitalchina.info.appframework.menu.entity.DeptMenuItem;
import com.digitalchina.info.appframework.menu.entity.TempMenuItem;
import com.digitalchina.info.appframework.menu.entity.TemplateMenuItem;
import com.digitalchina.info.appframework.menu.entity.UserExtraMenuItem;
import com.digitalchina.info.appframework.menu.service.MenuService;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.BaseService;

public class MenuServiceImpl extends BaseService implements MenuService{

	private MenuDao menuDao;
	
	public List<TemplateMenuItem> findTemplateMenuItemByParent(String parentId) {
		if("0".equals(parentId)){
			return menuDao.findTemplateMenuItemNoParent();
		}else{
			return menuDao.findTemplateMenuItemByParent(parentId);
		}
	}

	public List<TemplateMenuItem> findTemplateMenuItemNoParent() {
		// TODO Auto-generated method stub
		return menuDao.findTemplateMenuItemNoParent();
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public TemplateMenuItem findTemplateMenuItemById(String id) {
		return menuDao.findTemplateMenuItemById(id);
	}

	public void saveMenuItemMove(String id, String oldParentId,
			String newParentId, Integer nodeIndex) {
		TemplateMenuItem curMenuItem = menuDao.findTemplateMenuItemById(id);
		List<TemplateMenuItem> oldSameParentMenuItems = findTemplateMenuItemByParent(oldParentId);
		List<TemplateMenuItem> newSameParentMenuItems = findTemplateMenuItemByParent(newParentId);
		Integer oldMenuOrder = curMenuItem.getMenuOrder();
//		if(){
			//处理原父菜单下子菜单的排序情况
			for(TemplateMenuItem oldSameParentMenuItem : oldSameParentMenuItems){
				if(oldSameParentMenuItem.getMenuOrder()>oldMenuOrder){
					oldSameParentMenuItem.setMenuOrder(oldSameParentMenuItem.getMenuOrder()-1);
					menuDao.save(oldSameParentMenuItem);
				}
			}
			//处理新菜单下子菜单的排序情况
			for(TemplateMenuItem newSameParentMenuItem : newSameParentMenuItems){
				if(newSameParentMenuItem.getMenuOrder()>=nodeIndex){
					newSameParentMenuItem.setMenuOrder(newSameParentMenuItem.getMenuOrder()+1);
					menuDao.save(newSameParentMenuItem);
				}
			}
//		}
		curMenuItem.setParentItem(menuDao.findTemplateMenuItemById(newParentId));
		curMenuItem.setMenuOrder(nodeIndex);
		menuDao.save(curMenuItem);
	}

	public List<DeptMenu> findAllDeptMenus() {
		// TODO Auto-generated method stub
		return menuDao.findAllDeptMenus();
	}

	public DeptMenu saveDeptMenu(DeptMenu deptMenu) {
		// TODO Auto-generated method stub
		return menuDao.saveDeptMenu(deptMenu);
	}

	public DeptMenu findDeptMenuById(String id) {
		// TODO Auto-generated method stub
		return menuDao.findDeptMenuById(id);
	}

	public void removeDeptMenu(DeptMenu deptMenu) {
		// TODO Auto-generated method stub
		menuDao.removeDeptMenu(deptMenu);
	}

	public List<TempMenuItem> findDeptMenuItemByParent(String parentId,
			DeptMenu deptmenu) {
		List<TemplateMenuItem> deptTemplateMenuItems = menuDao.findTemplateMenuItemInDeptMenu(deptmenu);
		List<TemplateMenuItem> templateMenuItems = null;
		if("0".equals(parentId)){
			templateMenuItems = menuDao.findTemplateMenuItemNoParent();
		}else{
			templateMenuItems = menuDao.findTemplateMenuItemByParent(parentId);
		}
		List<TempMenuItem> tempMenuItems = new ArrayList<TempMenuItem>();
		for(TemplateMenuItem templateMenuItem : templateMenuItems){
			if(deptTemplateMenuItems.contains(templateMenuItem)){
				tempMenuItems.add(new TempMenuItem(templateMenuItem,TempMenuItem.ENABLE));
			}else{
				tempMenuItems.add(new TempMenuItem(templateMenuItem,TempMenuItem.DISABLE));
			}
		}
		return tempMenuItems;
	}

	public List<TempMenuItem> findDeptMenuItemNoParent(DeptMenu deptmenu) {
		List<TemplateMenuItem> deptTemplateMenuItems = menuDao.findTemplateMenuItemInDeptMenu(deptmenu);
		List<TemplateMenuItem> templateMenuItems = menuDao.findTemplateMenuItemNoParent();
		List<TempMenuItem> tempMenuItems = new ArrayList<TempMenuItem>();
		for(TemplateMenuItem templateMenuItem : templateMenuItems){
			if(deptTemplateMenuItems.contains(templateMenuItem)){
				tempMenuItems.add(new TempMenuItem(templateMenuItem,TempMenuItem.ENABLE));
			}else{
				tempMenuItems.add(new TempMenuItem(templateMenuItem,TempMenuItem.DISABLE));
			}
		}
		return tempMenuItems;
	}

	public DeptMenuItem saveDeptMenuItem(DeptMenuItem deptMenuItem) {
		// TODO Auto-generated method stub
		return (DeptMenuItem) super.save(deptMenuItem);
	}

	public DeptMenuItem findDeptMenuItem(DeptMenu deptMenu,
			TemplateMenuItem templateMenuItem) {
		return menuDao.findDeptMenuItem(deptMenu,templateMenuItem);
	}

	public void removeDeptMenuItem(DeptMenuItem deptMenuItem) {
		// TODO Auto-generated method stub
		super.remove(deptMenuItem);
	}

	public List<TempMenuItem> findUserExtraMenuItemByParent(String parentId,
			UserInfo user) {
		List<TemplateMenuItem> userExtraTemplateMenuItems = menuDao.findTemplateMenuItemInUserExtraMenu(user);
		List<TemplateMenuItem> templateMenuItems = null;
		if("0".equals(parentId)){
			templateMenuItems = menuDao.findTemplateMenuItemNoParent();
		}else{
			templateMenuItems = menuDao.findTemplateMenuItemByParent(parentId);
		}
		List<TempMenuItem> tempMenuItems = new ArrayList<TempMenuItem>();
		for(TemplateMenuItem templateMenuItem : templateMenuItems){
			if(userExtraTemplateMenuItems.contains(templateMenuItem)){
				tempMenuItems.add(new TempMenuItem(templateMenuItem,TempMenuItem.ENABLE));
			}else{
				tempMenuItems.add(new TempMenuItem(templateMenuItem,TempMenuItem.DISABLE));
			}
		}
		return tempMenuItems;
	}

	public UserExtraMenuItem findUserExtraMenuItem(UserInfo user,
			TemplateMenuItem templateMenuItem) {
		return menuDao.findUserExtraMenuItem(user,templateMenuItem);
	}

	public void removeUserExtraMenuItem(UserExtraMenuItem userExtraMenuItem) {
		super.remove(userExtraMenuItem);
		
	}

	public UserExtraMenuItem saveUserExtraMenuItem(
			UserExtraMenuItem userExtraMenuItem) {
		return (UserExtraMenuItem) super.save(userExtraMenuItem);
	}

	public List<TemplateMenuItem> findUserMenuByParent(UserInfo user,
			String parentId) {
		Set<TemplateMenuItem> userTemplateMenuItem = new HashSet();
		//添加用户额外菜单项
		List<TemplateMenuItem> userExtraTemplateMenuItems = menuDao.findTemplateMenuItemInUserExtraMenu(user);
		userTemplateMenuItem.addAll(userExtraTemplateMenuItems);
		//获取用户对应角色菜单项
		Set<Role> roles = user.getRoles();
		Set<DeptMenu> deptMenus = new HashSet();
		for(Role role : roles){
			deptMenus.add(role.getDeptMenu());
		}
		for(DeptMenu DeptMenu : deptMenus){
			List<TemplateMenuItem> deptTemplateMenuItems = menuDao.findTemplateMenuItemInDeptMenu(DeptMenu);
			userTemplateMenuItem.addAll(deptTemplateMenuItems);
		}
		//收集模板菜单项
		List<TemplateMenuItem> templateMenuItems = null;
		if("0".equals(parentId)||StringUtils.isBlank(parentId)){
			templateMenuItems = menuDao.findTemplateMenuItemNoParent();
		}else{
			templateMenuItems = menuDao.findTemplateMenuItemByParent(parentId);
		}
		List<TemplateMenuItem> userMenuItem = new ArrayList();	
		for(TemplateMenuItem templateMenuItem : templateMenuItems){
			if(userTemplateMenuItem.contains(templateMenuItem)){
				userMenuItem.add(templateMenuItem);
			}
		}
		return userMenuItem;
	}

	public List<TemplateMenuItem> findUserMenuNoParent(UserInfo user) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DeptMenu> findDeptMenuByDept(Department dept) {
		return menuDao.findDeptMenuByDept(dept);
	}

	public void removeTemplateMenuItem(TemplateMenuItem templateMenuItem) {
		List<DeptMenuItem> deptMenuItems = menuDao.findDeptMenuItem(templateMenuItem);
		for(DeptMenuItem deptMenuItem : deptMenuItems){
			this.remove(deptMenuItem);
		}
		List<UserExtraMenuItem> userExtraMenuItems = menuDao.findUserExtraMenuItem(templateMenuItem);
		for(UserExtraMenuItem userExtraMenuItem : userExtraMenuItems){
			this.remove(userExtraMenuItem);
		}
		this.remove(templateMenuItem);
	}

}
