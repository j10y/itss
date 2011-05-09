package com.zsgj.info.appframework.template.service;

import java.util.List;

import com.zsgj.info.appframework.template.entity.DeptMenuTemplate;
import com.zsgj.info.appframework.template.entity.DeptMenuTemplateItem;
import com.zsgj.info.appframework.template.entity.SystemMenuTemplate;
import com.zsgj.info.framework.security.entity.Department;

/**
 * 部门菜单模板及部门菜单模板项服务
 * @Class Name SystemMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface DeptTemplateMenuService {
	
	/**
	 * 保存部门菜单模板的所属系统菜单模板变更
	 * @Methods Name saveDeptSystemTemplateChange
	 * @Create In 2008-9-18 By sa
	 * @param dmt
	 * @param smtNew void
	 */
	void saveDeptSystemTemplateChange(DeptMenuTemplate dmt, SystemMenuTemplate smtNew);
	/**
	 * 获取部门的菜单模板
	 * @Methods Name findDeptMenuTemplate
	 * @Create In 2008-9-16 By sa
	 * @param dept
	 * @return List
	 */
	List findDeptMenuTemplate(Department dept);
	/**
	 * 获取所有的部门菜单模板
	 * @Methods Name findSystemMenuTemplates
	 * @Create In 2008-8-29 By sa
	 * @return List
	 */
	List findDeptMenuTemplates();
	
	/**
	 * 根据ID查找部门菜单模板
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param dmtId
	 * @return TODO
	 */
	DeptMenuTemplate findDeptMenuTemplateById(String dmtId);
	
	/**
	 * 保存部门菜单模板
	 * @Methods Name saveDeptMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smt
	 * @return DeptMenuTemplate
	 */
	DeptMenuTemplate saveDeptMenuTemplate(DeptMenuTemplate smt);
	
	/**
	 * 删除部门菜单模板
	 * @Methods Name removeDeptMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smsId void
	 */
	void removeDeptMenuTemplate(String smsId);
	
	/**
	 * 删除所选的部门菜单模板
	 * @Methods Name removeDeptMenuTemplate
	 * @Create In 2008-9-6 By sa
	 * @param dmtIds void
	 */
	void removeDeptMenuTemplate(String[] dmtIds);
	
	/**
	 * 提供父部门菜单模板项的编号获取其所有子部门菜单模板项
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-29 By sa
	 * @param parentMenuId
	 * @return List<DeptMenuTemplateItem>
	 */
	List<DeptMenuTemplateItem> findChildenByParent(String parentMenuId);
	
	/**
	 * 获取部门菜单模板中父节点为空的部门菜单条目
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param parentMenuId
	 * @return TODO
	 */
	List<DeptMenuTemplateItem> findDeptMenuTemplateItemNoParent(String parentMenuId);
	
	/**
	 * 根据指定部门菜单模板和父节点查找孩子结点 
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param parentMenuId
	 * @param smtId
	 * @return TODO
	 */
	List<DeptMenuTemplateItem> findChildenByParentAndDeptMenuTemplate(String parentMenuId, String dmtId);
	
	/**
	 * 保存部门菜单模板项
	 * @Methods Name saveMenu
	 * @Create In 2008-8-29 By sa
	 * @param menu
	 * @return DeptMenuTemplateItem
	 */
	DeptMenuTemplateItem saveMenu(DeptMenuTemplateItem menu);
	
	/**
	 * 获取所有的部门菜单模板项
	 * @Methods Name findAllMenu
	 * @Create In 2008-8-29 By sa
	 * @return List<DeptMenuTemplateItem>
	 */
	List<DeptMenuTemplateItem> findAllMenu();
	
	/**
	 * 修改系统模板菜单项的名称
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param menuName
	 * @return DeptMenuTemplateItem
	 */
	DeptMenuTemplateItem modifyMenuName(String menuId, String menuName);
	
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
	 * @return DeptMenuTemplateItem
	 */
	DeptMenuTemplateItem findMenuById(String Id);
	
	/**
	 * 根据名称检索系统模板菜单项
	 * @Methods Name findMenusByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<DeptMenuTemplateItem>
	 */
	List<DeptMenuTemplateItem> findMenusByName(String name);
	
	/**
	 * 移除菜单；底层只是简单地删除菜单，不移动排序
	 * @Methods Name removeMenu
	 * @Create In 2008-8-29 By sa
	 * @param id void
	 */
	void removeMenu(String id);
	
	/**
	 * 设置结点是否可见
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param nodeId  指定结点Id
	 * @param enabled 是否可见：1表示可见，0表示隐藏
	 * @return TODO
	 */
	DeptMenuTemplateItem saveNodeEnabled(String nodeId, String enabled);
	
	/**
	 * 根据部门Id查找部门菜单模板
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param deptId
	 * @return TODO
	 */
	List<DeptMenuTemplate> findDeptMenuTemplateByDeptCode(String deptCode);
}
