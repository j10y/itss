package com.xpsoft.oa.model.system;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class MrbsRepeat extends BaseModel{
	private Long id;
	private String startHour;
	private String endHour;
	private String startMini;
	private String endMini;
	private MrbsRoom room;
	private AppUser createBy;
	private String orderman;
	private Integer repOpt;
	private String description;
	private Date startDate;
	private Date endDate;
	private Integer allday;
	private Integer repeatWeekDay;
	private Integer weekSpan;
	private Integer repeatDay;
	private String ordermanEmail;
	private Integer projector;
	private Integer conferenceCall;
	private Integer num;
	private Date createDate;
	private Date modifyDate;
	private AppUser modifyBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getStartMini() {
		return startMini;
	}
	public void setStartMini(String startMini) {
		this.startMini = startMini;
	}
	public String getEndMini() {
		return endMini;
	}
	public void setEndMini(String endMini) {
		this.endMini = endMini;
	}
	public MrbsRoom getRoom() {
		return room;
	}
	public void setRoom(MrbsRoom room) {
		this.room = room;
	}
	public AppUser getCreateBy() {
		return createBy;
	}
	public void setCreateBy(AppUser createBy) {
		this.createBy = createBy;
	}
	public String getOrderman() {
		return orderman;
	}
	public void setOrderman(String orderman) {
		this.orderman = orderman;
	}
	public Integer getRepOpt() {
		return repOpt;
	}
	public void setRepOpt(Integer repOpt) {
		this.repOpt = repOpt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getAllday() {
		return allday;
	}
	public void setAllday(Integer allday) {
		this.allday = allday;
	}
	public Integer getRepeatWeekDay() {
		return repeatWeekDay;
	}
	public void setRepeatWeekDay(Integer repeatWeekDay) {
		this.repeatWeekDay = repeatWeekDay;
	}
	public Integer getWeekSpan() {
		return weekSpan;
	}
	public void setWeekSpan(Integer weekSpan) {
		this.weekSpan = weekSpan;
	}
	public Integer getRepeatDay() {
		return repeatDay;
	}
	public void setRepeatDay(Integer repeatDay) {
		this.repeatDay = repeatDay;
	}
	public String getOrdermanEmail() {
		return ordermanEmail;
	}
	public void setOrdermanEmail(String ordermanEmail) {
		this.ordermanEmail = ordermanEmail;
	}
	public Integer getProjector() {
		return projector;
	}
	public void setProjector(Integer projector) {
		this.projector = projector;
	}
	public Integer getConferenceCall() {
		return conferenceCall;
	}
	public void setConferenceCall(Integer conferenceCall) {
		this.conferenceCall = conferenceCall;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public AppUser getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(AppUser modifyBy) {
		this.modifyBy = modifyBy;
	}
}
