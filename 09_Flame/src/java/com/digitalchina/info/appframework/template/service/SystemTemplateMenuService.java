package com.digitalchina.info.appframework.template.service;

import java.util.List;

import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.entity.SystemMenuTemplateItem;

/**
 * ϵͳ�˵�ģ�弰ϵͳ�˵�ģ�������
 * @Class Name SystemMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface SystemTemplateMenuService {
	
	/**
	 * ����ϵͳ�˵�ģ��
	 * @Methods Name saveSystemTemplate4Copy
	 * @Create In 2008-9-15 By sa
	 * @param smt void
	 */
	SystemMenuTemplate saveSystemTemplate4Copy(SystemMenuTemplate targetSmt, String sourceTmpId);
	/**
	 * ��ȡ���е�ϵͳ�˵�ģ��
	 * @Methods Name findSystemMenuTemplates
	 * @Create In 2008-8-29 By sa
	 * @return List
	 */
	List findSystemMenuTemplates();
	
	/**
	 * ����ID��ȡϵͳ�˵�ģ��
	 * TODO
	 * Aug 31, 2008 By hp
	 * @return TODO
	 */
	SystemMenuTemplate findSystemMenuTemplateById(String smtId);
	
	/**
	 * ����name��ȡϵͳ�˵�ģ��
	 * TODO
	 * Aug 31, 2008 By hp
	 * @return TODO
	 */
	List findSystemMenuTemplateByName(String smtName);
	
	/**
	 * ����ָ���˵�ģ����û�и��ڵ�Ĳ˵���Ŀ
	 * TODO
	 * Sep 2, 2008 By hp
	 * @param smt
	 * @return TODO
	 */
	List findSystemMenuTemplateItemNoParent(String smtId);
	
	/**
	 * ����ָ���˵�ģ��͸��ڵ���Һ��ӽ�� 
	 * TODO
	 * Sep 2, 2008 By hp
	 * @param parentMenuId
	 * @param smtId
	 * @return TODO
	 */
	List findChildenByParentAndSystemMenuTemplate(String parentMenuId, String smtId);
	
	/**
	 * ����ϵͳ�˵�ģ��
	 * @Methods Name saveSystemMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smt
	 * @return SystemMenuTemplate
	 */
	SystemMenuTemplate saveSystemMenuTemplate(SystemMenuTemplate smt);
	
	/**
	 * ɾ��ϵͳ�˵�ģ��
	 * @Methods Name removeSystemMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smsId void
	 */
	void removeSystemMenuTemplate(String smsId);
	
	/**
	 * ɾ��ϵͳ�˵�ģ��
	 * @Methods Name removeSystemMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param dmtIds void
	 */
	void removeSystemMenuTemplate(String[] dmtIds);
	/**
	 * �ṩ��ϵͳ�˵�ģ����ı�Ż�ȡ��������ϵͳ�˵�ģ����
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-29 By sa
	 * @param parentMenuId
	 * @return List<SystemMenuTemplateItem>
	 */
	List<SystemMenuTemplateItem> findChildenByParent(String parentMenuId);
	
	/**
	 * ����ϵͳ�˵�ģ����
	 * @Methods Name saveMenu
	 * @Create In 2008-8-29 By sa
	 * @param menu
	 * @return SystemMenuTemplateItem
	 */
	SystemMenuTemplateItem saveMenu(SystemMenuTemplateItem menu);
	
	/**
	 * ��ȡ���е�ϵͳ�˵�ģ����
	 * @Methods Name findAllMenu
	 * @Create In 2008-8-29 By sa
	 * @return List<SystemMenuTemplateItem>
	 */
	List<SystemMenuTemplateItem> findAllMenu();
	
	/**
	 * �޸�ϵͳģ��˵��������
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param menuName
	 * @return SystemMenuTemplateItem
	 */
	SystemMenuTemplateItem modifyMenuName(String menuId, String menuName);
	
	/**
	 * ɾ��ϵͳģ��˵���
	 * @Methods Name removeNode
	 * @Create In 2008-8-29 By sa
	 * @param menuId void
	 */
	void removeNode(String menuId);
	
	/**
	 * ����ϵͳģ��˵���
	 * @Methods Name saveNodeMove
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	void saveNodeMove(String menuId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * ͨ����Ż�ȡϵͳģ��˵���
	 * @Methods Name findMenuById
	 * @Create In 2008-8-29 By sa
	 * @param Id
	 * @return SystemMenuTemplateItem
	 */
	SystemMenuTemplateItem findMenuById(String Id);
	
	/**
	 * �������Ƽ���ϵͳģ��˵���
	 * @Methods Name findMenusByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<SystemMenuTemplateItem>
	 */
	List<SystemMenuTemplateItem> findMenusByName(String name);
	
	/**
	 * �Ƴ��˵����ײ�ֻ�Ǽ򵥵�ɾ���˵������ƶ�����
	 * @Methods Name removeMenu
	 * @Create In 2008-8-29 By sa
	 * @param id void
	 */
	void removeMenu(String id);
	
	
}
