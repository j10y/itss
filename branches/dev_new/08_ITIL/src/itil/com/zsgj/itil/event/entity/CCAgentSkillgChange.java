package com.zsgj.itil.event.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * CCAgentStatusChange
 * @Class Name CCAgentStatusChange
 * @Author sa
 * @Create In Aug 7, 2009
 */
public class CCAgentSkillgChange extends BaseObject {
	private Long id;
	private String agentId;
	private String dn;
	private Date loginTime;
	private String skilllg;
	private String millisec;
	private String logtag;
	
//	1	id	bigint	8	0
//	0	AGENTID	varchar	8	1
//	0	DN	varchar	6	1
//	0	LOGINTIME	datetime	8	1
//	0	SKILLG	varchar	255	1
//	0	MILLISEC	varchar	9	1
//	0	LOGTAG	varchar	64	1
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getSkilllg() {
		return skilllg;
	}
	public void setSkilllg(String skilllg) {
		this.skilllg = skilllg;
	}
	public String getMillisec() {
		return millisec;
	}
	public void setMillisec(String millisec) {
		this.millisec = millisec;
	}
	public String getLogtag() {
		return logtag;
	}
	public void setLogtag(String logtag) {
		this.logtag = logtag;
	}
	
}
