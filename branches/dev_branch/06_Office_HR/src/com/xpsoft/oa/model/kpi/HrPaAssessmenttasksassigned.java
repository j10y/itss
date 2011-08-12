package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAssessmenttasksassigned extends BaseModel {
	protected long id;
	protected long acId;
	protected long category;
	protected double target;
	protected Date publishDate;
	protected long publishPerson;
	
	public HrPaAssessmenttasksassigned(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAcId() {
		return acId;
	}

	public void setAcId(long acId) {
		this.acId = acId;
	}

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public long getPublishPerson() {
		return publishPerson;
	}

	public void setPublishPerson(long publishPerson) {
		this.publishPerson = publishPerson;
	}
}
