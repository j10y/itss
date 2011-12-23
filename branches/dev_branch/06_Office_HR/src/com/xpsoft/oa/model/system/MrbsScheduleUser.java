package com.xpsoft.oa.model.system;

import com.xpsoft.core.model.BaseModel;

public class MrbsScheduleUser extends BaseModel{
	private Long id;
	private MrbsSchedule schedule;
	private AppUser conferee;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MrbsSchedule getSchedule() {
		return schedule;
	}
	public void setSchedule(MrbsSchedule schedule) {
		this.schedule = schedule;
	}
	public AppUser getConferee() {
		return conferee;
	}
	public void setConferee(AppUser conferee) {
		this.conferee = conferee;
	}
}
