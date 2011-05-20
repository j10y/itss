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
	 * ��ȡ��ģ����Ŀ
	 * @Methods Name findRootTemplateItemWithChild
	 * @Create In 2008-9-11 By sa
	 * @param template
	 * @return TemplateItem
	 */
	String findTemplateItemWithChild(Template template);
	
	/**
	 * ��ȡʹ����ģ�幦�ܵ�ϵͳ����
	 * @Methods Name findSystemMainTableHasTemplate
	 * @Create In 2008-9-2 By sa
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findTemplateTable();
	
	/**
	 * ��ȡ����ģ�幦�ܵ�ϵͳ����������ֶΣ�������չ�ֶΣ�
	 * @Methods Name findColumnByTemplateTable
	 * @Create In 2008-9-2 By sa
	 * @param smt
	 * @return List<Column>
	 */
	List<Column> findColumnByTemplateTable(SystemMainTable smt);
	
	/**
	 * ����ģ��
	 * @Methods Name saveTemplate
	 * @Create In 2008-9-2 By sa
	 * @param template
	 * @return Template
	 */
	Template saveTemplate(Template template);
	
	/**
	 * ɾ�����ģ��
	 * @Methods Name removeTemplate
	 * @Create In 2008-9-2 By sa
	 * @param tmpIds void
	 */
	void removeTemplate(String[] tmpIds);
	
	/**
	 * ��ȡ���е�ģ��
	 * @Methods Name findAllTemplates
	 * @Create In 2008-9-2 By sa
	 * @return List
	 */
	List findAllTemplates();
	
	/**
	 * �����ϼ�ģ����Ŀ��ȡ�ӵ�ģ����Ŀ
	 * @Methods Name findChildenByParent
	 * @Create In 2008-9-3 By sa
	 * @param parentTemplateItemId
	 * @return List<TemplateItem>
	 */
	List<TemplateItem> findChildenByParent(String parentTemplateItemId);
	
	/**
	 * ����˵�
	 * @Methods Name saveTemplateItem
	 * @Create In 2008-9-3 By sa
	 * @param TemplateItem
	 * @return TemplateItem
	 */
	TemplateItem saveTemplateItem(TemplateItem TemplateItem);
	
	/**
	 * ��ȡ���еĲ˵�
	 * @Methods Name findAllTemplateItem
	 * @Create In 2008-9-3 By sa
	 * @return List<TemplateItem>
	 */
	List<TemplateItem> findAllTemplateItem();
	
	/**
	 * �޸�ģ����Ŀ������
	 * @Methods Name modifyTemplateItemName
	 * @Create In 2008-9-3 By sa
	 * @param TemplateItemId
	 * @param TemplateItemName
	 * @return TemplateItem
	 */
	TemplateItem modifyTemplateItemName(String TemplateItemId, String TemplateItemName);
	
	/**
	 * ɾ��ģ��˵���
	 * @Methods Name removeNode
	 * @Create In 2008-8-29 By sa
	 * @param TemplateItemId void
	 */
	void removeNode(String TemplateItemId);
	
	/**
	 * ����ģ��˵���
	 * @Methods Name saveNodeMove
	 * @Create In 2008-8-29 By sa
	 * @param TemplateItemId
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	void saveNodeMove(String TemplateItemId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * ͨ����Ż�ȡģ����
	 * @Methods Name findTemplateItemById
	 * @Create In 2008-8-29 By sa
	 * @param Id
	 * @return DeptTemplateItemTemplateItem
	 */
	TemplateItem findTemplateItemById(String Id);
	
	/**
	 * �������Ƽ���ģ����
	 * @Methods Name findTemplateItemsByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<DeptTemplateItemTemplateItem>
	 */
	List<TemplateItem> findTemplateItemsByName(String name);
	
	/**
	 * �Ƴ��˵����ײ�ֻ�Ǽ򵥵�ɾ����Ŀ�����ƶ�����
	 * @Methods Name removeTemplateItem
	 * @Create In 2008-8-29 By sa
	 * @param id void
	 */
	void removeTemplateItem(String id);
	
	/**
	 * ����ģ��ID����û�и��ڵ��ģ����
	 * TODO
	 * Sep 9, 2008 By hp
	 * @return TODO
	 */
	List<TemplateItem> findTemplateItemNoParent(String templateId);
	/**
	 * ����ģ��ID����ָ�����ĺ��ӽ��
	 * TODO
	 * Sep 9, 2008 By hp
	 * @param parentId
	 * @param templateId
	 * @return TODO
	 */
	List<TemplateItem> findChildenByParentAndTemplate(String parentId,String templateId);
	
	/**
	 * �������е��û���Ϣ
	 * TODO
	 * Sep 11, 2008 By hp
	 * @return TODO
	 */
	List<UserInfo>  findAllUsers();
	
	/**
	 * ͨ��Id����ģ��
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param templateId
	 * @return TODO
	 */
	public Template findTemplateById(String templateId);
	
	/**
	 * ͨ��ģ��Id����û�и��ڵ��ģ�����λ��ģ�������Ľ��
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param templateId
	 * @return TODO
	 */
	List<TemplateItem> findTemplateItemsNoParent(String templateId);
	
	/**
	 * ͨ��ģ��ID�͸����ID����ָ��ģ����ĺ��ӽ��
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param parentId
	 * @param templateId
	 * @return TODO
	 */
	List<TemplateItem> findTemplateItemsByParentAndTemplate(String parentId, String templateId);
}
