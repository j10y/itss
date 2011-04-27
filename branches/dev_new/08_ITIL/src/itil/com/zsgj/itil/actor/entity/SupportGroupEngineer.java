package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 支持组（support group）组员。
 * 此功能独立于配置项维护
 * @Class Name SupportGroupUser
 * @Author sa
 * @Create In 2008-11-10
 */
public class SupportGroupEngineer extends BaseObject {
	private Long id;	
	private SupportGroup supportGroup; 
	private UserInfo userInfo;
	private Integer level;
	private String nowtime;
	private SupportGroupEngineerStatus busyStatus;//工程师状态
	//当前支持组的工程师所属的服务商
//	private ServiceProviderType serviceProviderType;
//	private Long serviceProvider;

	public String getNowtime() {
		return nowtime;
	}
	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SupportGroup getSupportGroup() {
		return supportGroup;
	}

	public void setSupportGroup(SupportGroup supportGroup) {
		this.supportGroup = supportGroup;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
