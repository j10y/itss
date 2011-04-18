package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

public class CCSkillGWorkSum extends BaseObject{
	private Long id;
	private String skillg;
	private Integer glevel;
	private String dn;
	private String result;
	private String routeValue;
	private String origid;
	private Date enterTime;
	private Date assignTime;
	private Date leaveTime;
	private Integer tag;
	private String millisec;
	private String callid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRouteValue() {
		return routeValue;
	}
	public void setRouteValue(String routeValue) {
		this.routeValue = routeValue;
	}
	public String getOrigid() {
		return origid;
	}
	public void setOrigid(String origid) {
		this.origid = origid;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public String getMillisec() {
		return millisec;
	}
	public void setMillisec(String millisec) {
		this.millisec = millisec;
	}
	public String getCallid() {
		return callid;
	}
	public void setCallid(String callid) {
		this.callid = callid;
	}
}
//[id] [bigint] IDENTITY (1, 1) NOT NULL ,
//[SKILLG] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
//[GLEVEL] [numeric](18, 0) NULL ,
//[DN] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
//[RESULT] [varchar] (1) COLLATE Chinese_PRC_CI_AS NULL ,
//[ROUTEVALUE] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
//[ORIGID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
//[ENTERTIME] [datetime] NULL ,
//[ASSIGNTIME] [datetime] NULL ,
//[LEAVETIME] [datetime] NULL ,
//[TAG] [numeric](18, 0) NULL ,
//[MILLISEC] [varchar] (9) COLLATE Chinese_PRC_CI_AS NULL ,
//[CALLID] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL 