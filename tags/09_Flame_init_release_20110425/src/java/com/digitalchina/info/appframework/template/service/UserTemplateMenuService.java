package com.digitalchina.info.appframework.template.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.template.entity.DeptMenuTemplate;
import com.digitalchina.info.appframework.template.entity.UserMenu;
import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 用户菜单模板及用户菜单模板项服务
 * @Class Name SystemMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface UserTemplateMenuService {
	
	/**
	 * 保存用户部门模板变更
	 * @Methods Name saveUserDeptTemplateChange
	 * @Create In 2008-9-18 By sa
	 * @param um
	 * @param dmtNew void
	 */
	void saveUserDeptTemplateChange(UserMenu um, DeptMenuTemplate dmtNew);
	/**
	 * 获取所有的用户菜单模板
	 * @Methods Name findSystemMenuTemplates
	 * @Create In 2008-8-29 By sa
	 * @return List
	 */
	List findUserMenus();

	/**
	 * 获取具有部门管理员角色的用户列表；
	 * 给增加部门管理员级别的用户菜单模板使用
	 * @Methods Name findUserAsUserAdminRight
	 * @Create In 2008-9-2 By sa
	 * @return List
	 */
	List findUserAsUserAdminRight();
	
	/**
	 * 提供姓名获取用户
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
	 * 查询某个人的用户菜单模板
	 * @Methods Name findUserMenus
	 * @Create In 2008-8-31 By sa
	 * @param params
	 * @return List
	 */
	Page findUserMenus(Map params, int pageNo, int pageSize);
	
	/**
	 * 查询用户菜单模板
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param userMenuId
	 * @return TODO
	 */
	UserMenu findUserMenuById(String userMenuId);
	
	/**
	 * 保存用户菜单模板
	 * @Methods Name saveUserMenu
	 * @Create In 2008-8-29 By sa
	 * @param smt
	 * @return UserMenu
	 */
	UserMenu saveUserMenu(UserMenu smt);
	
	/**
	 * 删除用户菜单模板
	 * @Methods Name removeUserMenu
	 * @Create In 2008-8-29 By sa
	 * @param smsId void
	 */
	void removeUserMenu(String smsId);
	
	/**
	 * 删除用户菜单模板
	 * @Methods Name removeUserMenu
	 * @Create In 2008-9-2 By sa
	 * @param smsId void
	 */
	void removeUserMenu(String[] smsId);
	
	/**
	 * 提供父用户菜单模板项的编号获取其所有子用户菜单模板项
	 * @Methods Name findChildenByParent
	 * @Create In 2008-8-29 By sa
	 * @param parentMenuId
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findChildenByParent(String parentMenuId);
	
	/**
	 * 通过用户菜单模板ID查找所有父节点为空的结点
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param userMenuId
	 * @return TODO
	 */
	List<UserMenuItem> findUserMenuItemNoParent(String userMenuId);
	
	/**
	 * 通过父节点ID和用户菜单模板ID查找用户菜单项
	 * TODO
	 * Sep 3, 2008 By hp
	 * @param parentId
	 * @param userMenuId
	 * @return TODO
	 */
	List<UserMenuItem> findChildenByParentAndUserMenu(String parentId, String userMenuId);
	
	/**
	 * 保存用户菜单模板项
	 * @Methods Name saveMenu
	 * @Create In 2008-8-29 By sa
	 * @param menu
	 * @return UserMenuItem
	 */
	UserMenuItem saveMenu(UserMenuItem menu);
	
	/**
	 * 获取所有的用户菜单模板项
	 * @Methods Name findAllMenu
	 * @Create In 2008-8-29 By sa
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findAllMenu();
	
	/**
	 * 修改系统模板菜单项的名称
	 * @Methods Name modifyMenuName
	 * @Create In 2008-8-29 By sa
	 * @param menuId
	 * @param menuName
	 * @return UserMenuItem
	 */
	UserMenuItem modifyMenuName(String menuId, String menuName);
	
	/**
	 * 删除系统模板菜单项K246
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
	 * @return UserMenuItem
	 */
	UserMenuItem findMenuById(String Id);
	
	/**
	 * 根据名称检索系统模板菜单项
	 * @Methods Name findMenusByName
	 * @Create In 2008-8-29 By sa
	 * @param name
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findMenusByName(String name);
	
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
	 * @param enabled  是否可见：1表示可见，0表示隐藏
	 * @return TODO
	 */
	UserMenuItem saveNodeEnabled(String nodeId, String enabled);
	
	/**
	 * 查找指定用户菜单模板中没有父节点的结点信息，只返回enabled=true可用的菜单
	 * TODO
	 * Sep 8, 2008 By hp
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findUserMenuItemNoParentByUserId(String userId);
	
	/**
	 * 查找指定用户菜单模板中没有父节点的结点信息, 返回所有的菜单，包括不可用的
	 * @Methods Name findUserMenuItemAllNoParentByUserId
	 * @Create In 2008-10-20 By sa
	 * @param userId
	 * @return List<UserMenuItem>
	 */
	List<UserMenuItem> findUserMenuItemAllNoParentByUserId(String userId);
	
	/**
	 * 通过用户Id和父节点来查找指定结点下的所有孩子结点
	 * TODO
	 * Sep 8, 2008 By hp
	 * @param nodeId
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findChildenByParentAndUserId(String nodeId, String userId);
	
	/**
	 * 通过用户ID查找导航栏标题
	 * TODO
	 * Sep 16, 2008 By hp
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findAllMenuTitleByUserId(String userId);
	
	/**
	 * 设定用户菜单时,通过用户ID和当前结点ID查找指定结点的孩子结点
	 * TODO
	 * Sep 16, 2008 By hp
	 * @param nodeId
	 * @param userId
	 * @return TODO
	 */
	List<UserMenuItem> findUserSettingMenuChildenByParentAndUserId(String nodeId, String userId);
}
