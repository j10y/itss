package com.zsgj.info.appframework.template.service;

import java.util.List;

import com.zsgj.info.appframework.template.entity.DeptMenuTemplate;
import com.zsgj.info.appframework.template.entity.DeptMenuTemplateItem;
import com.zsgj.info.appframework.template.entity.SystemMenuTemplate;
import com.zsgj.info.framework.security.entity.Department;

/**
 * ���Ų˵�ģ�弰���Ų˵�ģ�������
 * @Class Name SystemMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface DeptTemplateMenuService {
	
	/**
	 * ���沿�Ų˵�ģ�������ϵͳ�˵�ģ����
	 * @Methods Name saveDeptSystemTemplateChange
	 * @Create In 2008-9-18 By sa
	 * @param dmt
	 * @param smtNew void
	 */
	void saveDeptSystemTemplateChange(DeptMenuTemplate dmt, SystemMenuTemplate smtNew);
	/**
	 * ��ȡ���ŵĲ˵�ģ��
	 * @Methods Name findDeptMenuTemplate
	 * @Create In 2008-9-16 By sa
	 * @param dept
	 * @return List
	 */
	List findDeptMenuTemplate(Department dept);
	/**
	 * ��ȡ���еĲ��Ų˵�ģ��
	 * @Methods Name findSystemMenuTemplates
	 * @Create In 2008-8-29 By sa
	 * @return List
	 */
	List findDeptMenuTemplates();
	
	/**
	 * ����ID���Ҳ��Ų˵�ģ��
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param dmtId
	 * @return TODO
	 */
	DeptMenuTemplate findDeptMenuTemplateById(String dmtId);
	
	/**
	 * ���沿�Ų˵�ģ��
	 * @Methods Name saveDeptMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smt
	 * @return DeptMenuTemplate
	 */
	DeptMenuTemplate saveDeptMenuTemplate(DeptMenuTemplate smt);
	
	/**
	 * ɾ�����Ų˵�ģ��
	 * @Methods Name removeDeptMenuTemplate
	 * @Create In 2008-8-29 By sa
	 * @param smsId void
	 */
	void removeDeptMenuTemplate(String smsId);
	
	/**
	 * ɾ����ѡ�Ĳ��Ų˵�ģ��
	 * @Methods Name removeDeptMenuTemplate
	 * @Create In 2008-9-6 By sa
	 * @param dmtIds void
	 */
	void removeDeptMenuTemplate(String[] dmtIds);
	
	/**
	 * �ṩ�����Ų˵�ģ����ı�Ż�ȡ�������Ӳ��Ų˵�ģ����
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-29 By sa
	 * @param parentMenuId
	 * @return List<DeptMenuTemplateItem>
	 */
	List<DeptMenuTemplateItem> findChildenByParent(String parentMenuId);
	
	/**
	 * ��ȡ���Ų˵�ģ���и��ڵ�Ϊ�յĲ��Ų˵���Ŀ
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param parentMenuId
	 * @return TODO
	 */
	List<DeptMenuTemplateItem> findDeptMenuTemplateItemNoParent(String parentMenuId);
	
	/**
	 * ����ָ�����Ų˵�ģ��͸��ڵ���Һ��ӽ�� 
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param parentMenuId
	 * @param smtId
	 * @return TODO
	 */
	List<DeptMenuTemplateItem> findChildenByParentAndDeptMenuTemplate(String parentMenuId, String dmtId);
	
	/**
	 * ���沿�Ų˵�ģ����
	 * @Methods Name saveMenu
	 * @Create In 2008-8-29 By sa
	 * @param menu
	 * @return DeptMenuTemplateItem
	 */
	DeptMenuTemplateItem saveMenu(DeptMenuTemplateItem menu);
	
	/**
	 * ��ȡ���еĲ��Ų˵�ģ����
	 * @Methods Name findAllMenu
	 * @Create In 2008-8-29 By sa
	 * @return List<DeptMenuTemplateItem>
	 */
	List<DeptMenuTemplateItem> findAllMenu();
	
	/**
	 * �޸�ϵͳģ��˵��������
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param menuName
	 * @return DeptMenuTemplateItem
	 */
	DeptMenuTemplateItem modifyMenuName(String menuId, String menuName);
	
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
	 * @return DeptMenuTemplateItem
	 */
	DeptMenuTemplateItem findMenuById(String Id);
	
	/**
	 * �������Ƽ���ϵͳģ��˵���
	 * @Methods Name findMenusByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<DeptMenuTemplateItem>
	 */
	List<DeptMenuTemplateItem> findMenusByName(String name);
	
	/**
	 * �Ƴ��˵����ײ�ֻ�Ǽ򵥵�ɾ���˵������ƶ�����
	 * @Methods Name removeMenu
	 * @Create In 2008-8-29 By sa
	 * @param id void
	 */
	void removeMenu(String id);
	
	/**
	 * ���ý���Ƿ�ɼ�
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param nodeId  ָ�����Id
	 * @param enabled �Ƿ�ɼ���1��ʾ�ɼ���0��ʾ����
	 * @return TODO
	 */
	DeptMenuTemplateItem saveNodeEnabled(String nodeId, String enabled);
	
	/**
	 * ���ݲ���Id���Ҳ��Ų˵�ģ��
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param deptId
	 * @return TODO
	 */
	List<DeptMenuTemplate> findDeptMenuTemplateByDeptCode(String deptCode);
}
