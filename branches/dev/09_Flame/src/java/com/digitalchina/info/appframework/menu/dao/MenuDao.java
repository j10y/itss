package com.digitalchina.info.appframework.menu.dao;

import java.util.List;

import com.digitalchina.info.appframework.menu.entity.DeptMenu;
import com.digitalchina.info.appframework.menu.entity.DeptMenuItem;
import com.digitalchina.info.appframework.menu.entity.TemplateMenuItem;
import com.digitalchina.info.appframework.menu.entity.UserExtraMenuItem;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 菜单数据处理层
 * @Class Name MenuDao
 * @Author lee
 * @Create In Aug 12, 2010
 */
public interface MenuDao {

	/**
	 * 获取顶层模板菜单项
	 * @Methods Name findTemplateMenuItemNoParent
	 * @Create In Aug 12, 2010 By lee
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemNoParent();

	/**
	 * 获取子模板菜单项
	 * @Methods Name findTemplateMenuItemByParent
	 * @Create In Aug 12, 2010 By lee
	 * @param parentId
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemByParent(String parentId);

	/**
	 * 获取模板菜单项
	 * @Methods Name findTemplateMenuItemById
	 * @Create In Aug 13, 2010 By lee
	 * @param id
	 * @return TemplateMenuItem
	 */
	TemplateMenuItem findTemplateMenuItemById(String id);

	/**
	 * 保存
	 * @Methods Name save
	 * @Create In Aug 14, 2010 By lee
	 * @param templateMenuItem
	 * @return TemplateMenuItem
	 */
	TemplateMenuItem save(TemplateMenuItem templateMenuItem);

	/**
	 * 获取所有部门菜单
	 * @Methods Name findAllDeptMenus
	 * @Create In Aug 17, 2010 By lee
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findAllDeptMenus();

	/**
	 * 根据ID获取部门菜单
	 * @Methods Name findDeptMenuById
	 * @Create In Aug 17, 2010 By lee
	 * @param id
	 * @return DeptMenu
	 */
	DeptMenu findDeptMenuById(String id);

	/**
	 * 获取部门菜单中的菜单模板
	 * @Methods Name findTemplateMenuItemInDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptmenu
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemInDeptMenu(DeptMenu deptmenu);

	/**
	 * 保存部门菜单
	 * @Methods Name saveDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu
	 * @return DeptMenu
	 */
	DeptMenu saveDeptMenu(DeptMenu deptMenu);

	/**
	 * 删除部门菜单
	 * @Methods Name removeDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu void
	 */
	void removeDeptMenu(DeptMenu deptMenu);

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
	 * 获取用户额外拥有的模板菜单项
	 * @Methods Name findTemplateMenuItemInUserExtraMenu
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemInUserExtraMenu(UserInfo user);

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
	 * 获取部门菜单
	 * @Methods Name findDeptMenuByDept
	 * @Create In Aug 18, 2010 By lee
	 * @param dept
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findDeptMenuByDept(Department dept);

	/**
	 * 获取与模板菜单项相关的部门菜单项
	 * @Methods Name findDeptMenuItem
	 * @Create In Aug 19, 2010 By lee
	 * @param templateMenuItem
	 * @return List<DeptMenuItem>
	 */
	List<DeptMenuItem> findDeptMenuItem(TemplateMenuItem templateMenuItem);

	/**
	 * 获取与模板彩电项相关的用户额外菜单项
	 * @Methods Name findUserExtraMenuItem
	 * @Create In Aug 19, 2010 By lee
	 * @param templateMenuItem
	 * @return List<UserExtraMenuItem>
	 */
	List<UserExtraMenuItem> findUserExtraMenuItem(
			TemplateMenuItem templateMenuItem);

}
