package com.zsgj.info.appframework.template.service;

import java.util.List;

import com.zsgj.info.appframework.template.entity.UserMenuSetting;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 *  @deprecated
 * @Class Name UserMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface UserMenuService {


	/**
	 * 保存用户菜单设定。当修改UserMenuSetting列表的排序或可见性后，提交表单到
	 * action。action通过List selectUserMenuSettingsByUser(UserInfo userInfo)方法获取
	 * 所有老UserMenuSetting列表，对比request中参数逐一修改
	 * @Methods Name insertOrUpdateUserMenuSetting
	 * @Create In 2008-4-3 By peixf
	 * @param ums
	 * @return UserMenuSetting
	 */
	UserMenuSetting saveUserMenuSetting(UserMenuSetting ums);
	
	/**
	 * 获取用户所有的菜单设定
	 * @Methods Name findUserMenuSettingsByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List findUserMenuSettingsByUser(UserInfo userInfo);
	
	
	/**
	 * 当修改用户菜单设定的order或可用性后，将所有UserMenuSetting集合设置到userinfo里
	 * 保存userInfo，级联保存UserMenuSetting集合，此方法设计不好
	 * @Methods Name updateUserMenuSettings
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List saveUserMenuSettings(UserInfo userInfo);
	
	/**
	 * 为所有菜单初始化用户菜单设定给所有用户，？？
	 * @Methods Name insertAllModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void saveAllModulesToUserMenuSetting();
	
	/**
	 * 为所有菜单初始化用户菜单设定给指定用户
	 * @Methods Name insertAllModulesToUserMenuSettingByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo void
	 */
	void saveAllModulesToUserMenuSettingByUser(UserInfo userInfo);
	
	/**
	 * 当新增模块时，为所有用户初始化用户菜单设定数据
	 * @Methods Name insertNewModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void saveNewModulesToUserMenuSetting(Module module);
	
	/**
	 * 删除指定模块的所有UserMenuSetting，调用时机：模块删除时
	 * @Methods Name deleteUserMenuSettingByModule
	 * @Create In 2008-4-3 By peixf
	 * @param module void
	 */
	void removeUserMenuSettingByModule(Module module);
	
}
