package com.digitalchina.itil.config.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 配置项扩展信息对应信息表
 * @Class Name ConfigItemExtendInfo
 * @Author sa
 * @Create In 2008-11-24
 */
public class ConfigItemExtendInfoEvent extends BaseObject {
	private Long id;
	private ConfigItemEvent configItemEvent;
	private SystemMainTable systemMainTable; //对应系统主表id，如客户表
	private Long extendDataId; //实际的扩展配置项id，如客户id
	
	private Integer levelFlag;
	
	public ConfigItemEvent getConfigItemEvent() {
		return configItemEvent;
	}
	public void setConfigItemEvent(ConfigItemEvent configItemEvent) {
		this.configItemEvent = configItemEvent;
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
