package com.digitalchina.itil.require.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.extci.entity.DeliveryTeam;
import com.digitalchina.itil.config.extci.entity.ServiceEngineer;

/**
 * ���������Ӧ��ϵͳά����
 * @Class Name RequireAppSystem
 * @Author lee
 * @Create In Nov 24, 2009
 */
public class RequireAppSystem extends BaseObject{
	private Long id;	//�Զ����
	private ConfigItem appConfigItem;	//Ӧ��ϵͳ���� �ĳ�appConfigItem
	private UserInfo appManager;	//Ӧ��ϵͳ����Ա
	private DeliveryTeam deliveryTeam;	//�����Ŷ�
	private ServiceEngineer engineer;	//��������
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
