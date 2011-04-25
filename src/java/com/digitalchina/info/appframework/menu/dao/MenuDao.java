package com.digitalchina.info.appframework.menu.dao;

import java.util.List;

import com.digitalchina.info.appframework.menu.entity.DeptMenu;
import com.digitalchina.info.appframework.menu.entity.DeptMenuItem;
import com.digitalchina.info.appframework.menu.entity.TemplateMenuItem;
import com.digitalchina.info.appframework.menu.entity.UserExtraMenuItem;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * �˵����ݴ����
 * @Class Name MenuDao
 * @Author lee
 * @Create In Aug 12, 2010
 */
public interface MenuDao {

	/**
	 * ��ȡ����ģ��˵���
	 * @Methods Name findTemplateMenuItemNoParent
	 * @Create In Aug 12, 2010 By lee
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemNoParent();

	/**
	 * ��ȡ��ģ��˵���
	 * @Methods Name findTemplateMenuItemByParent
	 * @Create In Aug 12, 2010 By lee
	 * @param parentId
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemByParent(String parentId);

	/**
	 * ��ȡģ��˵���
	 * @Methods Name findTemplateMenuItemById
	 * @Create In Aug 13, 2010 By lee
	 * @param id
	 * @return TemplateMenuItem
	 */
	TemplateMenuItem findTemplateMenuItemById(String id);

	/**
	 * ����
	 * @Methods Name save
	 * @Create In Aug 14, 2010 By lee
	 * @param templateMenuItem
	 * @return TemplateMenuItem
	 */
	TemplateMenuItem save(TemplateMenuItem templateMenuItem);

	/**
	 * ��ȡ���в��Ų˵�
	 * @Methods Name findAllDeptMenus
	 * @Create In Aug 17, 2010 By lee
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findAllDeptMenus();

	/**
	 * ����ID��ȡ���Ų˵�
	 * @Methods Name findDeptMenuById
	 * @Create In Aug 17, 2010 By lee
	 * @param id
	 * @return DeptMenu
	 */
	DeptMenu findDeptMenuById(String id);

	/**
	 * ��ȡ���Ų˵��еĲ˵�ģ��
	 * @Methods Name findTemplateMenuItemInDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptmenu
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemInDeptMenu(DeptMenu deptmenu);

	/**
	 * ���沿�Ų˵�
	 * @Methods Name saveDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu
	 * @return DeptMenu
	 */
	DeptMenu saveDeptMenu(DeptMenu deptMenu);

	/**
	 * ɾ�����Ų˵�
	 * @Methods Name removeDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu void
	 */
	void removeDeptMenu(DeptMenu deptMenu);

	/**
	 * ��ȡ���Ų˵���
	 * @Methods Name findDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param deptMenu
	 * @param templateMenuItem
	 * @return DeptMenuItem
	 */
	DeptMenuItem findDeptMenuItem(DeptMenu deptMenu,
			TemplateMenuItem templateMenuItem);

	/**
	 * ��ȡ�û�����ӵ�е�ģ��˵���
	 * @Methods Name findTemplateMenuItemInUserExtraMenu
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemInUserExtraMenu(UserInfo user);

	/**
	 * ��ȡ�û�����˵���
	 * @Methods Name findUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @param templateMenuItem
	 * @return UserExtraMenuItem
	 */
	UserExtraMenuItem findUserExtraMenuItem(UserInfo user,
			TemplateMenuItem templateMenuItem);

	/**
	 * ��ȡ���Ų˵�
	 * @Methods Name findDeptMenuByDept
	 * @Create In Aug 18, 2010 By lee
	 * @param dept
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findDeptMenuByDept(Department dept);

	/**
	 * ��ȡ��ģ��˵�����صĲ��Ų˵���
	 * @Methods Name findDeptMenuItem
	 * @Create In Aug 19, 2010 By lee
	 * @param templateMenuItem
	 * @return List<DeptMenuItem>
	 */
	List<DeptMenuItem> findDeptMenuItem(TemplateMenuItem templateMenuItem);

	/**
	 * ��ȡ��ģ��ʵ�����ص��û�����˵���
	 * @Methods Name findUserExtraMenuItem
	 * @Create In Aug 19, 2010 By lee
	 * @param templateMenuItem
	 * @return List<UserExtraMenuItem>
	 */
	List<UserExtraMenuItem> findUserExtraMenuItem(
			TemplateMenuItem templateMenuItem);

}
