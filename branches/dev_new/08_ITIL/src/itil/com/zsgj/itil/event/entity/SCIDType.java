package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.config.entity.ConfigItem;

/**
 * �¼�ʹ�õ�SCID���ͱ���̨ӳ�䵽VIEW������CI��SCI���������
 * @Class Name SCIDType
 * @Author sa
 * @Create In 2009-3-5
 */
public class SCIDType extends BaseObject {
	private Long id;
	private String name;
	private Integer typeFlag;
	private ConfigItem configItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}
	/**
	 * @Param ConfigItem configItem to set
	 */
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}
	/**
	 * @Return the ConfigItem configItem
	 */
	public ConfigItem getConfigItem() {
		return configItem;
	}
	
}
