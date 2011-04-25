/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.info.framework.security.serviceMenuService.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 ����03:03:04
 * TODO
 */
package com.digitalchina.info.appframework.template.service;

import java.util.List;

import com.digitalchina.info.appframework.template.entity.UserMenuSetting;

/**
 * �ṩ�˵���ĸ������ 
 * @deprecated
 * @Class Name MenuService
 * @Author zhangpeng
 * @Create In 2008-7-17
 */
public interface UserMenuSettingService {
	
	/**
	 * ���ݸ��˵���ȡ���е��Ӳ˵�
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-25 By sa
	 * @param parentMenu
	 * @return List<Menu>
	 */
	public List<UserMenuSetting> findChildenByParent(String parentMenuId);
	/**
	 * ����˵���
	 * @Methods Name save
	 * @Create In 2008-7-17 By zhangpeng
	 * @param menu
	 * @return Menu
	 */
	public UserMenuSetting saveUserMenuSetting(UserMenuSetting menu);
	
	/**
	 * �������в˵���
	 * @Methods Name findAll
	 * @Create In 2008-7-17 By zhangpeng
	 * @return List<Menu>
	 */
	public List<UserMenuSetting> findAllUserMenuSetting();
	
	/**
	 * �޸Ĳ˵�����
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-25 By sa
	 * @param id
	 * @param menuName
	 * @return Boolean
	 */
	//public UserMenuSetting modifyMenuName(String menuId, String menuName);
	
	/**
	 * ɾ���˵��ڵ�
	 * @Methods Name ajaxRemoveNode
	 * @Create In 2008-8-25 By sa
	 * @param id void
	 */
	public void removeNode(String menuId);
	
	/**
	 * ����˵��ڵ��˳���ƶ�
	 * @Methods Name saveNodeMove
	 * @Create In 2008-8-25 By sa
	 * @param menuId
	 * @param oldParentId
	 * @param newParentId
	 * @param nodeIndex void
	 */
	public void saveNodeMove(String userMenuSettingId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * ����ID�����˵�
	 * @Methods Name findMenuByID
	 * @Create In 2008-7-17 By zhangpeng
	 * @param Id
	 * @return Menu
	 */
	public UserMenuSetting findUserMenuSettingById(String Id);
	
	/**
	 * �������Ƽ����˵���
	 * @Methods Name findMenusByName
	 * @Create In 2008-7-17 By zhangpeng
	 * @param name
	 * @return List<Menu>
	 */
	public List<UserMenuSetting> findUserMenuSettingByMenuName(String menuName);
	
	/**
	 * �Ƴ��˵����ײ�ֻ�Ǽ򵥵�ɾ���˵������ƶ�����
	 * @Methods Name remove
	 * @Create In 2008-7-17 By zhangpeng void
	 */
	public void removeUserMenuSetting(String id);

}
