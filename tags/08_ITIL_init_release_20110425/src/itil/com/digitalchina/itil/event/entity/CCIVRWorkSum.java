package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 2¡¢	±íÃû£ºLOG1_IVRWORKSUM
 * 
 * @Class Name CCIVRWorkSum
 * @Author sa
 * @Create In Aug 10, 2009
 */
public class CCIVRWorkSum  extends BaseObject {

	private Long id;
	private String dn;
	private String actionIO;
	private String origId;
	private String calledrId;
	private String calleddId;
	private Date startTime;
	private Date endTime;
	private Integer timeSpan;
	private Integer tag;
	private String millisec;
	private String callId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActionIO() {
		return actionIO;
	}
	public void setActionIO(String actionIO) {
		this.actionIO = actionIO;
	}
	public String getOrigId() {
		return origId;
	}
	public void setOrigId(String origId) {
		this.origId = origId;
	}
	public String getCalledrId() {
		return calledrId;
	}
	public void setCalledrId(String calledrId) {
		this.calledrId = calledrId;
	}
	public String getCalleddId() {
		return calleddId;
	}
	public void setCalleddId(String calleddId) {
		this.calleddId = calleddId;
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
	public Integer getTimeSpan() {
		return timeSpan;
	}
	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
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
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	
}
