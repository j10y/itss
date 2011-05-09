package com.zsgj.info.appframework.template.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.template.entity.Template;
import com.zsgj.info.appframework.template.entity.TemplateItem;
import com.zsgj.info.framework.security.entity.UserInfo;

public interface TemplateService {
	
	Template findTemplateByClass(Class clazz);
	/**
	 * 获取很模板项目
	 * @Methods Name findRootTemplateItemWithChild
	 * @Create In 2008-9-11 By sa
	 * @param template
	 * @return TemplateItem
	 */
	String findTemplateItemWithChild(Template template);
	
	/**
	 * 获取使用了模板功能的系统主表
	 * @Methods Name findSystemMainTableHasTemplate
	 * @Create In 2008-9-2 By sa
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findTemplateTable();
	
	/**
	 * 获取具有模板功能的系统主表的所有字段（包括扩展字段）
	 * @Methods Name findColumnByTemplateTable
	 * @Create In 2008-9-2 By sa
	 * @param smt
	 * @return List<Column>
	 */
	List<Column> findColumnByTemplateTable(SystemMainTable smt);
	
	/**
	 * 保存模板
	 * @Methods Name saveTemplate
	 * @Create In 2008-9-2 By sa
	 * @param template
	 * @return Template
	 */
	Template saveTemplate(Template template);
	
	/**
	 * 删除多个模板
	 * @Methods Name removeTemplate
	 * @Create In 2008-9-2 By sa
	 * @param tmpIds void
	 */
	void removeTemplate(String[] tmpIds);
	
	/**
	 * 获取所有的模板
	 * @Methods Name findAllTemplates
	 * @Create In 2008-9-2 By sa
	 * @return List
	 */
	List findAllTemplates();
	
	/**
	 * 根据上级模板条目获取子的模板条目
	 * @Methods Name findChildenByParent
	 * @Create In 2008-9-3 By sa
	 * @param parentTemplateItemId
	 * @return List<TemplateItem>
	 */
	List<TemplateItem> findChildenByParent(String parentTemplateItemId);
	
	/**
	 * 保存菜单
	 * @Methods Name saveTemplateItem
	 * @Create In 2008-9-3 By sa
	 * @param TemplateItem
	 * @return TemplateItem
	 */
	TemplateItem saveTemplateItem(TemplateItem TemplateItem);
	
	/**
	 * 获取所有的菜单
	 * @Methods Name findAllTemplateItem
	 * @Create In 2008-9-3 By sa
	 * @return List<TemplateItem>
	 */
	List<TemplateItem> findAllTemplateItem();
	
	/**
	 * 修改模板项目的名称
	 * @Methods Name modifyTemplateItemName
	 * @Create In 2008-9-3 By sa
	 * @param TemplateItemId
	 * @param TemplateItemName
	 * @return TemplateItem
	 */
	TemplateItem modifyTemplateItemName(String TemplateItemId, String TemplateItemName);
	
	/**
	 * 删除模板菜单项
	 * @Methods Name removeNode
	 * @Create In 2008-8-29 By sa
	 * @param TemplateItemId void
	 */
	void removeNode(String TemplateItemId);
	
	/**
	 * 保存模板菜单项
	 * @Methods Name saveNodeMove
	 * @Create In 2008-8-29 By sa
	 * @param TemplateItemId
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	void saveNodeMove(String TemplateItemId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * 通过编号获取模板项
	 * @Methods Name findTemplateItemById
	 * @Create In 2008-8-29 By sa
	 * @param Id
	 * @return DeptTemplateItemTemplateItem
	 */
	TemplateItem findTemplateItemById(String Id);
	
	/**
	 * 根据名称检索模板项
	 * @Methods Name findTemplateItemsByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<DeptTemplateItemTemplateItem>
	 */
	List<TemplateItem> findTemplateItemsByName(String name);
	
	/**
	 * 移除菜单；底层只是简单地删除项目，不移动排序
	 * @Methods Name removeTemplateItem
	 * @Create In 2008-8-29 By sa
	 * @param id void
	 */
	void removeTemplateItem(String id);
	
	/**
	 * 根据模板ID查找没有父节点的模板项
	 * TODO
	 * Sep 9, 2008 By hp
	 * @return TODO
	 */
	List<TemplateItem> findTemplateItemNoParent(String templateId);
	/**
	 * 根据模板ID查找指定结点的孩子结点
	 * TODO
	 * Sep 9, 2008 By hp
	 * @param parentId
	 * @param templateId
	 * @return TODO
	 */
	List<TemplateItem> findChildenByParentAndTemplate(String parentId,String templateId);
	
	/**
	 * 查找所有的用户信息
	 * TODO
	 * Sep 11, 2008 By hp
	 * @return TODO
	 */
	List<UserInfo>  findAllUsers();
	
	/**
	 * 通过Id查找模板
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param templateId
	 * @return TODO
	 */
	public Template findTemplateById(String templateId);
	
	/**
	 * 通过模板Id查找没有父节点的模板项，即位于模板项最顶层的结点
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param templateId
	 * @return TODO
	 */
	List<TemplateItem> findTemplateItemsNoParent(String templateId);
	
	/**
	 * 通过模板ID和父结点ID查找指定模板项的孩子结点
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param parentId
	 * @param templateId
	 * @return TODO
	 */
	List<TemplateItem> findTemplateItemsByParentAndTemplate(String parentId, String templateId);
}
