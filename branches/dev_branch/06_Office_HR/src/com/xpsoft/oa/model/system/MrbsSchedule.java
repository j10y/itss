package com.xpsoft.oa.model.system;

import java.util.Date;
import java.util.Set;

import com.xpsoft.core.model.BaseModel;

public class MrbsSchedule extends BaseModel{
	private Long id;
	private Date startTime;
	private Date endTime;
	private MrbsRoom room;
	private AppUser createBy;
	private String preside;
	private MrbsRepeat repeat;
	private String description;
	private Integer num;
	private String title;
	private String presideEmail;
	private Integer projector;
	private Integer conferenceCall;
	private Date createDate;
	private Date modifyDate;
	private AppUser modifyBy;
	
	public MrbsSchedule(Long id){
		this.id = id;
	}
	public MrbsSchedule(){
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPreside() {
		return preside;
	}
	public void setPreside(String preside) {
		this.preside = preside;
	}
	public MrbsRepeat getRepeat() {
		return repeat;
	}
	public void setRepeat(MrbsRepeat repeat) {
		this.repeat = repeat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPresideEmail() {
		return presideEmail;
	}
	public void setPresideEmail(String presideEmail) {
		this.presideEmail = presideEmail;
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
