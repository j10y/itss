package com.digitalchina.itil.config.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ��������չ��Ϣ��Ӧ��Ϣ��
 * @Class Name ConfigItemExtendInfo
 * @Author sa
 * @Create In 2008-11-24
 */
public class ConfigItemExtendInfo extends BaseObject {
	private Long id;
	private ConfigItem configItem;
	private SystemMainTable systemMainTable; //��Ӧϵͳ����id����ͻ���
	private Long extendDataId; //ʵ�ʵ���չ������id����ͻ�id
	
	private Integer levelFlag;
	
	public ConfigItem getConfigItem() {
		return configItem;
	}
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLevelFlag() {
		return levelFlag;
	}
	public void setLevelFlag(Integer levelFlag) {
		this.levelFlag = levelFlag;
	}
	public Long getExtendDataId() {
		return extendDataId;
	}
	public void setExtendDataId(Long extendDataId) {
		this.extendDataId = extendDataId;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
}