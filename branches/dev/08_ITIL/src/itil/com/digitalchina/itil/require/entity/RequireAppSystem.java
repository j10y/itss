package com.digitalchina.itil.require.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.extci.entity.DeliveryTeam;
import com.digitalchina.itil.config.extci.entity.ServiceEngineer;

/**
 * 需求管理部分应用系统维护表
 * @Class Name RequireAppSystem
 * @Author lee
 * @Create In Nov 24, 2009
 */
public class RequireAppSystem extends BaseObject{
	private Long id;	//自动编号
	private ConfigItem appConfigItem;	//应用系统名称 改成appConfigItem
	private UserInfo appManager;	//应用系统管理员
	private DeliveryTeam deliveryTeam;	//交付团队
	private ServiceEngineer engineer;	//交付经理
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserInfo getAppManager() {
		return appManager;
	}
	public void setAppManager(UserInfo appManager) {
		this.appManager = appManager;
	}
	public ConfigItem getAppConfigItem() {
		return appConfigItem;
	}
	public void setAppConfigItem(ConfigItem appConfigItem) {
		this.appConfigItem = appConfigItem;
	}
	public DeliveryTeam getDeliveryTeam() {
		return deliveryTeam;
	}
	public void setDeliveryTeam(DeliveryTeam deliveryTeam) {
		this.deliveryTeam = deliveryTeam;
	}
	public ServiceEngineer getEngineer() {
		return engineer;
	}
	public void setEngineer(ServiceEngineer engineer) {
		this.engineer = engineer;
	}
	
}
