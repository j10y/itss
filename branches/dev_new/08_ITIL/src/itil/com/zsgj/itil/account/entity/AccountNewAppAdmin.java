package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.require.entity.AccountApplyMainTable;

/**
 * �µ�Ӧ�ù���Ա�������滻Ա����ְʱ����Ա��
 * @Class Name AccountNewAppAdmin
 * @Author lee
 * @Create In Oct 14, 2009
 */
public class AccountNewAppAdmin extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 7163032517877560957L;
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
