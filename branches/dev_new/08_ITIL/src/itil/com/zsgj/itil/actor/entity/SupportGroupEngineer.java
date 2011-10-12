package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * ֧���飨support group����Ա��
 * �˹��ܶ�����������ά��
 * @Class Name SupportGroupUser
 * @Author sa
 * @Create In 2008-11-10
 */
public class SupportGroupEngineer extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -6361585980992856142L;
	private Long id;	
	private SupportGroup supportGroup; 
	private UserInfo userInfo;
	private Integer level;
	private String nowtime;
	private SupportGroupEngineerStatus busyStatus;//����ʦ״̬
	//��ǰ֧����Ĺ���ʦ�����ķ�����
//	private ServiceProviderType serviceProviderType;
//	private Long serviceProvider;

	/**
	 * @Return the SupportGroupEngineerStatus busyStatus
	 */
	public SupportGroupEngineerStatus getBusyStatus() {
		return busyStatus;
	}
	/**
	 * @Param SupportGroupEngineerStatus busyStatus to set
	 */
	public void setBusyStatus(SupportGroupEngineerStatus busyStatus) {
		this.busyStatus = busyStatus;
	}
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