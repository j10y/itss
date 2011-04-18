package com.digitalchina.info.appframework.template.service;

import java.util.List;

import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.entity.SystemMenuTemplateItem;

/**
 * 系统菜单模板及系统菜单模板项服务
 * @Class Name SystemMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface SystemTemplateMenuService {
	
	/**
	 * 拷贝系统菜单模板
	 * @Methods Name saveSystemTemplate4Copy
	 * @Create In 2008-9-15 By sa
	 * @param smt void
	 */
	SystemMenuTemplate saveSystemTemplate4Copy(SystemMenuTemplate targetSmt, String sourceTmpId);
	/**
	 * 获取所有的系统菜单模板
	 * @Methods Name findSystemMenuTemplates
	 * @Create In 2008-8-29 By sa
	 * @return List
	 */
	List findSystemMenuTemplates();
	
	/**
	 * 根据ID获取系统菜单模板
	 * TODO
	 * Aug 31, 2008 By hp
	 * @return TODO
	 */
	SystemMenuTemplate findSystemMenuTemplateById(String smtId);
	
	/**
	 * 根据name获取系统菜单模板
	 * TODO
	 * Aug 31, 2008 By hp
	 * @return TODO
	 */
	List findSystemMenuTemplateByName(String smtName);
	
	/**
	 * 查找指定菜单模板中没有父节点的菜单条目
	 * TODO
	 * Sep 2, 2008 By hp
	 * @param smt
	 * @return TODO
	 */
	List findSystemMenuTemplateItemNoParent(String smtId);
	
	/**
	 * 根据指定菜单模板和父节点查找孩子结点 
	 * TODO
	 * Sep 2, 2008 By hp
	 * @param parentMenuId
	 * @param smtId
	 * @return TODO
	 */
	List findChildenByParentAndSystemMenuTemplate(String parentMenuId, String smtId);
	
	/**
	 * 保存系统菜单模板
	 * @Methods Name saveSystemMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smt
	 * @return SystemMenuTemplate
	 */
	SystemMenuTemplate saveSystemMenuTemplate(SystemMenuTemplate smt);
	
	/**
	 * 删除系统菜单模板
	 * @Methods Name removeSystemMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smsId void
	 */
	void removeSystemMenuTemplate(String smsId);
	
	/**
	 * 删除系统菜单模板
	 * @Methods Name removeSystemMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param dmtIds void
	 */
	void removeSystemMenuTemplate(String[] dmtIds);
	/**
	 * 提供父系统菜单模板项的编号获取其所有子系统菜单模板项
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-29 By sa
	 * @param parentMenuId
	 * @return List<SystemMenuTemplateItem>
	 */
	List<SystemMenuTemplateItem> findChildenByParent(String parentMenuId);
	
	/**
	 * 保存系统菜单模板项
	 * @Methods Name saveMenu
	 * @Create In 2008-8-29 By sa
	 * @param menu
	 * @return SystemMenuTemplateItem
	 */
	SystemMenuTemplateItem saveMenu(SystemMenuTemplateItem menu);
	
	/**
	 * 获取所有的系统菜单模板项
	 * @Methods Name findAllMenu
	 * @Create In 2008-8-29 By sa
	 * @return List<SystemMenuTemplateItem>
	 */
	List<SystemMenuTemplateItem> findAllMenu();
	
	/**
	 * 修改系统模板菜单项的名称
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param menuName
	 * @return SystemMenuTemplateItem
	 */
	SystemMenuTemplateItem modifyMenuName(String menuId, String menuName);
	
	/**
	 * 删除系统模板菜单项
	 * @Methods Name removeNode
	 * @Create In 2008-8-29 By sa
	 * @param menuId void
	 */
	void removeNode(String menuId);
	
	/**
	 * 保存系统模板菜单项
	 * @Methods Name saveNodeMove
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	void saveNodeMove(String menuId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * 通过编号获取系统模板菜单项
	 * @Methods Name findMenuById
	 * @Create In 2008-8-29 By sa
	 * @param Id
	 * @return SystemMenuTemplateItem
	 */
	SystemMenuTemplateItem findMenuById(String Id);
	
	/**
	 * 根据名称检索系统模板菜单项
	 * @Methods Name findMenusByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<SystemMenuTemplateItem>
	 */
	List<SystemMenuTemplateItem> findMenusByName(String name);
	
	/**
	 * 移除菜单；底层只是简单地删除菜单，不移动排序
	 * @Methods Name removeMenu
	 * @Create In 2008-8-29 By sa
	 * @param id void
	 */
	void removeMenu(String id);
	
	
}
