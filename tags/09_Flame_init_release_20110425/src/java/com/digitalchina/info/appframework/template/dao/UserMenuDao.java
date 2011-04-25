package com.digitalchina.info.appframework.template.dao;

import java.util.List;

import com.digitalchina.info.appframework.template.entity.UserMenuSetting;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 用户菜单设定Dao
 * @Class Name UserMenuDao
 * @Author peixf
 * @Create In 2008-4-3
 */
public interface UserMenuDao {
	
	/**
	 * 保存用户菜单设定。当修改UserMenuSetting列表的排序或可见性后，提交表单到
	 * action。action通过List selectUserMenuSettingsByUser(UserInfo userInfo)方法获取
	 * 所有老UserMenuSetting列表，对比request中参数逐一修改
	 * @Methods Name insertOrUpdateUserMenuSetting
	 * @Create In 2008-4-3 By peixf
	 * @param ums
	 * @return UserMenuSetting
	 */
	UserMenuSetting insertOrUpdateUserMenuSetting(UserMenuSetting ums);
	
	/**
	 * 获取用户所有的菜单设定
	 * @Methods Name selectUserMenuSettingsByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List selectUserMenuSettingsByUser(UserInfo userInfo);
	
	/**
	 * 当修改用户菜单设定的order或可用性后，将所有UserMenuSetting集合设置到userinfo里
	 * 保存userInfo，级联保存UserMenuSetting集合
	 * @Methods Name updateUserMenuSettings
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List updateUserMenuSettings(UserInfo userInfo);
	
	/**
	 * 为所有菜单初始化用户菜单设定给所有用户
	 * @Methods Name insertAllModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void insertAllModulesToUserMenuSetting();
	
	/**
	 * 为所有菜单初始化用户菜单设定给指定用户
	 * @Methods Name insertAllModulesToUserMenuSettingByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo void
	 */
	void insertAllModulesToUserMenuSettingByUser(UserInfo userInfo);
	
	/**
	 * 当新增模块时，为所有用户初始化用户菜单设定数据
	 * @Methods Name insertNewModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void insertNewModulesToUserMenuSetting(Module module);
	
	/**
	 * 删除指定模块的所有UserMenuSetting，调用时机：模块删除时
	 * @Methods Name deleteUserMenuSettingByModule
	 * @Create In 2008-4-3 By peixf
	 * @param module void
	 */
	void deleteUserMenuSettingByModule(Module module);
	
	
}
