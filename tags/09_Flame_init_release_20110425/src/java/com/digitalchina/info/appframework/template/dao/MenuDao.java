/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.info.framework.security.daoMenuService.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 下午02:52:14
 * TODO
 */
package com.digitalchina.info.appframework.template.dao;

import java.io.Serializable;
import java.util.List;

import com.digitalchina.info.appframework.template.entity.Menu;

/**
 * 提供基础的菜单项维护服务
 * @Class Name MenuService
 * @Author zhangpeng
 * @Create In 2008-7-17
 */
public interface MenuDao {
	
	/**
	 * 保存菜单项
	 * @Methods Name save
	 * @Create In 2008-7-17 By zhangpeng
	 * @param menu
	 * @return Menu
	 */
	public Menu save(Menu menu);
	
	/**
	 * 检索所有菜单项
	 * @Methods Name findAll
	 * @Create In 2008-7-17 By zhangpeng
	 * @return List
	 */
	public List<Menu> selectAll();
	
	/**
	 * 根据Id检索菜单项
	 * @Methods Name findMenuByID
	 * @Create In 2008-7-17 By zhangpeng
	 * @param id
	 * @return Menu
	 */
	public Menu selectMenuByID(Long id);
	
	/**
	 * 根据名称检索菜单项
	 * @Methods Name selectMenusByName
	 * @Create In 2008-7-17 By zhangpeng
	 * @param name
	 * @return List<Menu>
	 */
	public List<Menu> selectMenusByName(String name);
	
	/**
	 * 删除菜单
	 * @Methods Name removeObject
	 * @Create In 2008-7-17 By zhangpeng
	 * @param clazz
	 * @param id void
	 */
	public void removeObject(Class clazz, Serializable id);
	
	/**
	 * 根据级别查询菜单项
	 * @Methods Name selectMenuByLevel
	 * @Create In 2008-7-22 By zhangpeng
	 * @param level
	 * @return List<Menu>
	 */
	public List<Menu> selectMenuByLevel(Integer level);
	
	/**
	 * 根据父菜单查询所有子菜单
	 * @Methods Name selectMenuByPrentMenu
	 * @Create In 2008-7-22 By zhangpeng
	 * @param parent
	 * @return List<Menu>
	 */
	public List<Menu> selectMenuByPrentMenu(Menu parent);
	
}
