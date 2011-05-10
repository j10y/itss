/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.zsgj.info.framework.security.serviceMenuService.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 下午03:03:04
 * TODO
 */
package com.zsgj.info.appframework.template.service;

import java.util.List;

import com.zsgj.info.appframework.template.entity.Menu;

/**
 * 提供菜单项的各项服务
 * @deprecated
 * @Class Name MenuService
 * @Author zhangpeng
 * @Create In 2008-7-17
 */
public interface MenuService {
	
	/**
	 * 根据父菜单获取所有的子菜单
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-25 By sa
	 * @param parentMenu
	 * @return List<Menu>
	 */
	public List<Menu> findChildenByParent(String parentMenuId);
	/**
	 * 保存菜单项
	 * @Methods Name save
	 * @Create In 2008-7-17 By zhangpeng
	 * @param menu
	 * @return Menu
	 */
	public Menu saveMenu(Menu menu);
	
	/**
	 * 检索所有菜单项
	 * @Methods Name findAll
	 * @Create In 2008-7-17 By zhangpeng
	 * @return List<Menu>
	 */
	public List<Menu> findAllMenu();
	
	/**
	 * 修改菜单名称
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-25 By sa
	 * @param id
	 * @param menuName
	 * @return Boolean
	 */
	public Menu modifyMenuName(String menuId, String menuName);
	
	/**
	 * 删除菜单节点
	 * @Methods Name ajaxRemoveNode
	 * @Create In 2008-8-25 By sa
	 * @param id void
	 */
	public void removeNode(String menuId);
	
	/**
	 * 保存菜单节点的顺序移动
	 * @Methods Name saveNodeMove
	 * @Create In 2008-8-25 By sa
	 * @param menuId
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	public void saveNodeMove(String menuId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * 根据ID检索菜单
	 * @Methods Name findMenuByID
	 * @Create In 2008-7-17 By zhangpeng
	 * @param Id
	 * @return Menu
	 */
	public Menu findMenuById(String Id);
	
	/**
	 * 根据名称检索菜单项
	 * @Methods Name findMenusByName
	 * @Create In 2008-7-17 By zhangpeng
	 * @param name
	 * @return List<Menu>
	 */
	public List<Menu> findMenusByName(String name);
	
	/**
	 * 移除菜单；底层只是简单地删除菜单，不移动排序
	 * @Methods Name remove
	 * @Create In 2008-7-17 By zhangpeng void
	 */
	public void removeMenu(String id);

}
