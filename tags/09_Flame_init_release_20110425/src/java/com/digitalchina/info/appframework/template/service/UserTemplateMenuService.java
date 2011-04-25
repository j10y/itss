package com.digitalchina.info.appframework.template.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.template.entity.DeptMenuTemplate;
import com.digitalchina.info.appframework.template.entity.UserMenu;
import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * �û��˵�ģ�弰�û��˵�ģ�������
 * @Class Name SystemMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface UserTemplateMenuService {
	
	/**
	 * �����û�����ģ����
	 * @Methods Name saveUserDeptTemplateChange
	 * @Create In 2008-9-18 By sa
	 * @param um
	 * @param dmtNew void
	 */
	void saveUserDeptTemplateChange(UserMenu um, DeptMenuTemplate dmtNew);
	/**
	 * ��ȡ���е��û��˵�ģ��
	 * @Methods Name findSystemMenuTemplates
	 * @Create In 2008-8-29 By sa
	 * @return List
	 */
	List findUserMenus();

	/**
	 * ��ȡ���в��Ź���Ա��ɫ���û��б�
	 * �����Ӳ��Ź���Ա������û��˵�ģ��ʹ��
	 * @Methods Name findUserAsUserAdminRight
	 * @Create In 2008-9-2 By sa
	 * @return List
	 */
	List findUserAsUserAdminRight();
	
	/**
	 * �ṩ������ȡ�û�
	 * @Methods Name findUserByRealName
	 * @Create In 2008-9-1 By sa
	 * @param realName
	 * @return UserInfo
	 */
	UserInfo findUserByItcode(String itcode);
	
	/**
	 * 
	 * @Methods Name findUsers
	 * @Create In 2008-9-1 By sa
	 * @return List
	 */
	List findUsers();
	
	/**
	 * ��ѯĳ���˵��û��˵�ģ��
	 * @Methods Name findUserMenus
	 * @Create In 2008-8-31 By sa
	 * @param params
	 * @return List
	 */
	Page findUserMenus(Map params, int pageNo, int pageSize);
	
	/**
	 * ��ѯ�û��˵�ģ��
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param userMenuId
	 * @return TODO
	 */
	UserMenu findUserMenuById(String userMenuId);
	
	/**
	 * �����û��˵�ģ��
	 * @Methods Name saveUserMenu
	 * @Create In 2008-8-29 By sa
	 * @param smt
	 * @return UserMenu
	 */
	UserMenu saveUserMenu(UserMenu smt);
	
	/**
	 * ɾ���û��˵�ģ��
	 * @Methods Name removeUserMenu
	 * @Create In 2008-8-29 By sa
	 * @param smsId void
	 */
	void removeUserMenu(String smsId);
	
	/**
	 * ɾ���û��˵�ģ��
	 * @Methods Name removeUserMenu
	 * @Create In 2008-9-2 By sa
	 * @param smsId void
	 */
	void removeUserMenu(String[] smsId);
	
	/**
	 * �ṩ���û��˵�ģ����ı�Ż�ȡ���������û��˵�ģ����
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-29 By sa
	 * @param parentMenuId
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findChildenByParent(String parentMenuId);
	
	/**
	 * ͨ���û��˵�ģ��ID�������и��ڵ�Ϊ�յĽ��
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param userMenuId
	 * @return TODO
	 */
	List<UserMenuItem> findUserMenuItemNoParent(String userMenuId);
	
	/**
	 * ͨ�����ڵ�ID���û��˵�ģ��ID�����û��˵���
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param parentId
	 * @param userMenuId
	 * @return TODO
	 */
	List<UserMenuItem> findChildenByParentAndUserMenu(String parentId, String userMenuId);
	
	/**
	 * �����û��˵�ģ����
	 * @Methods Name saveMenu
	 * @Create In 2008-8-29 By sa
	 * @param menu
	 * @return UserMenuItem
	 */
	UserMenuItem saveMenu(UserMenuItem menu);
	
	/**
	 * ��ȡ���е��û��˵�ģ����
	 * @Methods Name findAllMenu
	 * @Create In 2008-8-29 By sa
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findAllMenu();
	
	/**
	 * �޸�ϵͳģ��˵��������
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param menuName
	 * @return UserMenuItem
	 */
	UserMenuItem modifyMenuName(String menuId, String menuName);
	
	/**
	 * ɾ��ϵͳģ��˵���K246
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
	 * @return UserMenuItem
	 */
	UserMenuItem findMenuById(String Id);
	
	/**
	 * �������Ƽ���ϵͳģ��˵���
	 * @Methods Name findMenusByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findMenusByName(String name);
	
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
	 * @param enabled  �Ƿ�ɼ���1��ʾ�ɼ���0��ʾ����
	 * @return TODO
	 */
	UserMenuItem saveNodeEnabled(String nodeId, String enabled);
	
	/**
	 * ����ָ���û��˵�ģ����û�и��ڵ�Ľ����Ϣ��ֻ����enabled=true���õĲ˵�
	 * TODO
	 * Sep 8, 2008 By hp
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findUserMenuItemNoParentByUserId(String userId);
	
	/**
	 * ����ָ���û��˵�ģ����û�и��ڵ�Ľ����Ϣ, �������еĲ˵������������õ�
	 * @Methods Name findUserMenuItemAllNoParentByUserId
	 * @Create In 2008-10-20 By sa
	 * @param userId
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findUserMenuItemAllNoParentByUserId(String userId);
	
	/**
	 * ͨ���û�Id�͸��ڵ�������ָ������µ����к��ӽ��
	 * TODO
	 * Sep 8, 2008 By hp
	 * @param nodeId
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findChildenByParentAndUserId(String nodeId, String userId);
	
	/**
	 * ͨ���û�ID���ҵ���������
	 * TODO
	 * Sep 16, 2008 By hp
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findAllMenuTitleByUserId(String userId);
	
	/**
	 * �趨�û��˵�ʱ,ͨ���û�ID�͵�ǰ���ID����ָ�����ĺ��ӽ��
	 * TODO
	 * Sep 16, 2008 By hp
	 * @param nodeId
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findUserSettingMenuChildenByParentAndUserId(String nodeId, String userId);
}
