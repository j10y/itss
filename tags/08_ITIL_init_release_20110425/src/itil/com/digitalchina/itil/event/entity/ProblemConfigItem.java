package com.digitalchina.itil.event.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.entity.ConfigItemType;
/**
 * 问题配置项历史记录表
 * @Class Name ProblemConfigItem
 * @Author sa
 * @Create In 2009-3-4
 */
public class ProblemConfigItem extends BaseObject {
	private Long id;
	private Problem problem; //问题
	private ConfigItemType configItemType; //配置项类型
	private ConfigItem configItem; //配置项
	private String remark;//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	public ConfigItemType getConfigItemType() {
		return configItemType;
	}

	public void setConfigItemType(ConfigItemType configItemType) {
		this.configItemType = configItemType;
	}

	public ConfigItem getConfigItem() {
		return configItem;
	}
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
