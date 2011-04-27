package com.zsgj.itil.event.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.knowledge.entity.Knowledge;

/**
 * 解决方案使用历史记录
 * @Class Name KnowledgeUseLog
 * @Author sa
 * @Create In Mar 31, 2009
 */
public class KnowledgeUseLog extends BaseObject {
	private static final long serialVersionUID = 8066703329295773739L;
	
	private Knowledge knowledge;
	private UserInfo userInfo;
	private Date useTime;
	
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
}
