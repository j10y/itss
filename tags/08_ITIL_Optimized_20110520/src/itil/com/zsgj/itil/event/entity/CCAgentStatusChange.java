package com.zsgj.itil.event.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * CCAgentStatusChange
 * @Class Name CCAgentStatusChange
 * @Author sa
 * @Create In Aug 7, 2009
 */
public class CCAgentStatusChange extends BaseObject {
	private Long id;
	private String agentId;
	private String opertype;
	private Date startTime;
	private Date endTime;
	private Integer timespan;
	private String millisec;
	private String logtag;
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
	public String getOpertype() {
		return opertype;
	}
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getTimespan() {
		return timespan;
	}
	public void setTimespan(Integer timespan) {
		this.timespan = timespan;
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
