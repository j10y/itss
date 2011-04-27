package com.zsgj.info.appframework.menu.service;

import java.util.List;
import java.util.Set;

import com.zsgj.info.appframework.menu.entity.DeptMenu;
import com.zsgj.info.appframework.menu.entity.DeptMenuItem;
import com.zsgj.info.appframework.menu.entity.TempMenuItem;
import com.zsgj.info.appframework.menu.entity.TemplateMenuItem;
import com.zsgj.info.appframework.menu.entity.UserExtraMenuItem;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 菜单服务层
 * @Class Name MenuService
 * @Author lee
 * @Create In Aug 12, 2010
 */
public interface MenuService {

	/**
	 * 获取模板菜单项
	 * @Methods Name findTemplateMenuItemById
	 * @Create In Aug 13, 2010 By lee
	 * @return TemplateMenuItem
	 */
	TemplateMenuItem findTemplateMenuItemById(String id);
	/**
	 * 获取所有顶层模板菜单项
	 * @Methods Name findTemplateMenuItemNoParent
	 * @Create In Aug 12, 2010 By lee
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemNoParent();

	/**
	 * 获取子模板菜项
	 * @Methods Name findTemplateMenuItemByParent
	 * @Create In Aug 12, 2010 By lee
	 * @param parentId
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemByParent(String parentId);

	/**
	 * 保存菜单移动
	 * @Methods Name saveMenuItemMove
	 * @Create In Aug 14, 2010 By lee
	 * @param id
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	void saveMenuItemMove(String id, String oldParentId, String newParentId,
			Integer nodeIndex);
	/**
	 * 获取所有部门菜单
	 * @Methods Name findAllDeptMenus
	 * @Create In Aug 17, 2010 By lee
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findAllDeptMenus();
	/**
	 * 保存部门菜单
	 * @Methods Name saveDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu
	 * @return DeptMenu
	 */
	DeptMenu saveDeptMenu(DeptMenu deptMenu);
	/**
	 * 获取部门菜单
	 * @Methods Name findDeptMenuById
	 * @Create In Aug 17, 2010 By lee
	 * @param id
	 * @return DeptMenu
	 */
	DeptMenu findDeptMenuById(String id);
	/**
	 * 删除部门菜单
	 * @Methods Name removeDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu
	 * @return void
	 */
	void removeDeptMenu(DeptMenu deptMenu);
	/**
	 * 获取部门菜单顶层菜单项
	 * @Methods Name findDeptMenuItemNoParent
	 * @Create In Aug 17, 2010 By lee
	 * @param deptmenu
	 * @return List<TempMenuItem>
	 */
	List<TempMenuItem> findDeptMenuItemNoParent(DeptMenu deptmenu);
	/**
	 * 获取部门菜单菜单项
	 * @Methods Name findDeptMenuItemByParent
	 * @Create In Aug 17, 2010 By lee
	 * @param parentId
	 * @param deptmenu
	 * @return List<TempMenuItem>
	 */
	List<TempMenuItem> findDeptMenuItemByParent(String parentId, DeptMenu deptmenu);
	/**
	 * 保存部门菜单项
	 * @Methods Name saveDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param deptMenuItem
	 * @return DeptMenuItem
	 */
	DeptMenuItem saveDeptMenuItem(DeptMenuItem deptMenuItem);
	/**
	 * 删除部门菜单项
	 * @Methods Name removeDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param deptMenuItem void
	 */
	void removeDeptMenuItem(DeptMenuItem deptMenuItem);
	/**
	 * 获取部门菜单项
	 * @Methods Name findDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param deptMenu
	 * @param templateMenuItem
	 * @return DeptMenuItem
	 */
	DeptMenuItem findDeptMenuItem(DeptMenu deptMenu,
			TemplateMenuItem templateMenuItem);
	/**
	 * 通过父ID获取用户额外菜单项展示信息
	 * @Methods Name findUserExtraMenuItemByParent
	 * @Create In Aug 18, 2010 By lee
	 * @param parentId
	 * @param user
	 * @return List<TempMenuItem>
	 */
	List<TempMenuItem> findUserExtraMenuItemByParent(String parentId, UserInfo user);
	/**
	 * 保存用户额外菜单项
	 * @Methods Name saveUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param userExtraMenuItem
	 * @return UserExtraMenuItem
	 */
	UserExtraMenuItem saveUserExtraMenuItem(UserExtraMenuItem userExtraMenuItem);
	/**
	 * 删除用户额外菜单项
	 * @Methods Name removeUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param userExtraMenuItem void
	 */
	void removeUserExtraMenuItem(UserExtraMenuItem userExtraMenuItem);
	/**
	 * 获取用户额外菜单项
	 * @Methods Name findUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @param templateMenuItem
	 * @return UserExtraMenuItem
	 */
	UserExtraMenuItem findUserExtraMenuItem(UserInfo user,
			TemplateMenuItem templateMenuItem);
	/**
	 * 获取用户顶层菜单项
	 * @Methods Name findUserMenuNoParent
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findUserMenuNoParent(UserInfo user);
	/**
	 * 获取用户菜单项
	 * @Methods Name findUserMenuByParent
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @param parentId
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findUserMenuByParent(UserInfo user, String parentId);
	/**
	 * 获取部门菜单
	 * @Methods Name findDeptMenuByDept
	 * @Create In Aug 18, 2010 By lee
	 * @param dept
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findDeptMenuByDept(Department dept);
	/**
	 * 删除模板菜单项
	 * @Methods Name removeTemplateMenuItem
	 * @Create In Aug 19, 2010 By lee
	 * @param curItem void
	 */
	void removeTemplateMenuItem(TemplateMenuItem curItem);
	
}
