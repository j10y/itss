package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.entity.ConfigItem;

/**
 * ��������פ�������������������ʵ��
 * @Class Name ServerManageConfigItem
 * @Author lee
 * @Create In Jul 31, 2009
 */
public class ServerManageConfigItem extends BaseObject{
	private Long id;
	private ServerManage serverManage;	//��������פ����ʵ��
	private ConfigItem configItem;		//������������ʵ��
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ServerManage getServerManage() {
		return serverManage;
	}
	public void setServerManage(ServerManage serverManage) {
		this.serverManage = serverManage;
	}
	public ConfigItem getConfigItem() {
		return configItem;
	}
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}
}
