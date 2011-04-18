package com.digitalchina.itil.account.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

/**
 * 新的应用管理员（用于替换员工离职时管理员）
 * @Class Name AccountNewAppAdmin
 * @Author lee
 * @Create In Oct 14, 2009
 */
public class AccountNewAppAdmin extends BaseObject{
	private Long id;
	private AccountApplyMainTable amt;
	private UserInfo newUser;
	private ConfigItem configItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserInfo getNewUser() {
		return newUser;
	}
	public void setNewUser(UserInfo newUser) {
		this.newUser = newUser;
	}
	public AccountApplyMainTable getAmt() {
		return amt;
	}
	public void setAmt(AccountApplyMainTable amt) {
		this.amt = amt;
	}
	public ConfigItem getConfigItem() {
		return configItem;
	}
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}
}
