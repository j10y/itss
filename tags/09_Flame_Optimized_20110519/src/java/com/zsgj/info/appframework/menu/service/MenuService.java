package com.zsgj.info.appframework.menu.service;

import java.util.List;
import java.util.Set;

import com.zsgj.info.appframework.menu.entity.DeptMenu;
import com.zsgj.info.appframework.menu.entity.DeptMenuItem;
import com.zsgj.info.appframework.menu.entity.TempMenuItem;
import com.zsgj.info.appframework.menu.entity.TemplateMenuItem;
import com.zsgj.info.appframework.menu.entity.UserExtraMenuItem;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * �˵������
 * @Class Name MenuService
 * @Author lee
 * @Create In Aug 12, 2010
 */
public interface MenuService {

	/**
	 * ��ȡģ��˵���
	 * @Methods Name findTemplateMenuItemById
	 * @Create In Aug 13, 2010 By lee
	 * @return TemplateMenuItem
	 */
	TemplateMenuItem findTemplateMenuItemById(String id);
	/**
	 * ��ȡ���ж���ģ��˵���
	 * @Methods Name findTemplateMenuItemNoParent
	 * @Create In Aug 12, 2010 By lee
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemNoParent();

	/**
	 * ��ȡ��ģ�����
	 * @Methods Name findTemplateMenuItemByParent
	 * @Create In Aug 12, 2010 By lee
	 * @param parentId
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findTemplateMenuItemByParent(String parentId);

	/**
	 * ����˵��ƶ�
	 * @Methods Name saveMenuItemMove
	 * @Create In Aug 14, 2010 By lee
	 * @param id
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	void saveMenuItemMove(String id, String oldParentId, String newParentId,
			Integer nodeIndex);
	/**
	 * ��ȡ���в��Ų˵�
	 * @Methods Name findAllDeptMenus
	 * @Create In Aug 17, 2010 By lee
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findAllDeptMenus();
	/**
	 * ���沿�Ų˵�
	 * @Methods Name saveDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu
	 * @return DeptMenu
	 */
	DeptMenu saveDeptMenu(DeptMenu deptMenu);
	/**
	 * ��ȡ���Ų˵�
	 * @Methods Name findDeptMenuById
	 * @Create In Aug 17, 2010 By lee
	 * @param id
	 * @return DeptMenu
	 */
	DeptMenu findDeptMenuById(String id);
	/**
	 * ɾ�����Ų˵�
	 * @Methods Name removeDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @param deptMenu
	 * @return void
	 */
	void removeDeptMenu(DeptMenu deptMenu);
	/**
	 * ��ȡ���Ų˵�����˵���
	 * @Methods Name findDeptMenuItemNoParent
	 * @Create In Aug 17, 2010 By lee
	 * @param deptmenu
	 * @return List<TempMenuItem>
	 */
	List<TempMenuItem> findDeptMenuItemNoParent(DeptMenu deptmenu);
	/**
	 * ��ȡ���Ų˵��˵���
	 * @Methods Name findDeptMenuItemByParent
	 * @Create In Aug 17, 2010 By lee
	 * @param parentId
	 * @param deptmenu
	 * @return List<TempMenuItem>
	 */
	List<TempMenuItem> findDeptMenuItemByParent(String parentId, DeptMenu deptmenu);
	/**
	 * ���沿�Ų˵���
	 * @Methods Name saveDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param deptMenuItem
	 * @return DeptMenuItem
	 */
	DeptMenuItem saveDeptMenuItem(DeptMenuItem deptMenuItem);
	/**
	 * ɾ�����Ų˵���
	 * @Methods Name removeDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param deptMenuItem void
	 */
	void removeDeptMenuItem(DeptMenuItem deptMenuItem);
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
	 * ͨ����ID��ȡ�û�����˵���չʾ��Ϣ
	 * @Methods Name findUserExtraMenuItemByParent
	 * @Create In Aug 18, 2010 By lee
	 * @param parentId
	 * @param user
	 * @return List<TempMenuItem>
	 */
	List<TempMenuItem> findUserExtraMenuItemByParent(String parentId, UserInfo user);
	/**
	 * �����û�����˵���
	 * @Methods Name saveUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param userExtraMenuItem
	 * @return UserExtraMenuItem
	 */
	UserExtraMenuItem saveUserExtraMenuItem(UserExtraMenuItem userExtraMenuItem);
	/**
	 * ɾ���û�����˵���
	 * @Methods Name removeUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @param userExtraMenuItem void
	 */
	void removeUserExtraMenuItem(UserExtraMenuItem userExtraMenuItem);
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
	 * ��ȡ�û�����˵���
	 * @Methods Name findUserMenuNoParent
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findUserMenuNoParent(UserInfo user);
	/**
	 * ��ȡ�û��˵���
	 * @Methods Name findUserMenuByParent
	 * @Create In Aug 18, 2010 By lee
	 * @param user
	 * @param parentId
	 * @return List<TemplateMenuItem>
	 */
	List<TemplateMenuItem> findUserMenuByParent(UserInfo user, String parentId);
	/**
	 * ��ȡ���Ų˵�
	 * @Methods Name findDeptMenuByDept
	 * @Create In Aug 18, 2010 By lee
	 * @param dept
	 * @return List<DeptMenu>
	 */
	List<DeptMenu> findDeptMenuByDept(Department dept);
	/**
	 * ɾ��ģ��˵���
	 * @Methods Name removeTemplateMenuItem
	 * @Create In Aug 19, 2010 By lee
	 * @param curItem void
	 */
	void removeTemplateMenuItem(TemplateMenuItem curItem);
	
}
