package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

public class CCAgentWorkSum  extends BaseObject {

	private Long id;
	private String agentId;
	private String skillg;
	private Integer glevel;
	private String actionType;
	private String origid;
	private String calledrId;
	private String calleddid;
	private Date ringTime;
	private Date connectTime;
	private Date endTime;
	private String millisec;
	private String callId;
	
//	1	AGENTID	varchar	8	1
//	0	SKILLG	varchar	6	1
//	0	GLEVEL	numeric	9	1
//	0	ACTIONTYPE	varchar	2	1
//	0	ORIGID	varchar	15	1
//	0	CALLERID	varchar	15	1
//	0	CALLEDID	varchar	15	1
//	0	RINGTIME	datetime	8	1
//	0	CONNECTTIME	datetime	8	1
//	0	ENDTIME	datetime	8	1
//	0	MILLISEC	varchar	9	1
//	0	CALLID	varchar	64	1
	
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
	public String getSkillg() {
		return skillg;
	}
	public void setSkillg(String skillg) {
		this.skillg = skillg;
	}
	public Integer getGlevel() {
		return glevel;
	}
	public void setGlevel(Integer glevel) {
		this.glevel = glevel;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getOrigid() {
		return origid;
	}
	public void setOrigid(String origid) {
		this.origid = origid;
	}
	public String getCalledrId() {
		return calledrId;
	}
	public void setCalledrId(String calledrId) {
		this.calledrId = calledrId;
	}
	public String getCalleddid() {
		return calleddid;
	}
	public void setCalleddid(String calleddid) {
		this.calleddid = calleddid;
	}
	public Date getRingTime() {
		return ringTime;
	}
	public void setRingTime(Date ringTime) {
		this.ringTime = ringTime;
	}
	public Date getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getMillisec() {
		return millisec;
	}
	public void setMillisec(String millisec) {
		this.millisec = millisec;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
}
