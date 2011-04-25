package com.digitalchina.info.appframework.template.service.impl;

import java.util.List;

import com.digitalchina.info.appframework.template.dao.UserMenuDao;
import com.digitalchina.info.appframework.template.entity.UserMenuSetting;
import com.digitalchina.info.appframework.template.service.UserMenuService;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class UserMenuServiceImpl implements UserMenuService {
	private UserMenuDao userMenuDao = null;

	public void setUserMenuDao(UserMenuDao userMenuDao) {
		this.userMenuDao = userMenuDao;
	}

	public void removeUserMenuSettingByModule(Module module) {
		userMenuDao.deleteUserMenuSettingByModule(module);	
	}

	public void saveNewModulesToUserMenuSetting(Module module) {
		userMenuDao.insertNewModulesToUserMenuSetting(module);
	}

	public List findUserMenuSettingsByUser(UserInfo userInfo) {
		List list = null;
		list = userMenuDao.selectUserMenuSettingsByUser(userInfo);
		return list;
	}

	public void saveAllModulesToUserMenuSetting() {
		userMenuDao.insertAllModulesToUserMenuSetting();
	}

	public void saveAllModulesToUserMenuSettingByUser(UserInfo userInfo) {
		userMenuDao.insertAllModulesToUserMenuSettingByUser(userInfo);
	}

	public UserMenuSetting saveUserMenuSetting(UserMenuSetting ums) {
		UserMenuSetting result = null;
		result = userMenuDao.insertOrUpdateUserMenuSetting(ums);
		return result;
	}

	public List saveUserMenuSettings(UserInfo userInfo) {
		List list = null;
		list = userMenuDao.updateUserMenuSettings(userInfo);
		return list;
	}

}
