package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAssessmenttasksassigned extends BaseModel {
	protected Long id;
	protected Long acId;
	protected Long category;
	protected Double target;
	protected Date publishDate;
	protected Long publishPerson;
	protected Long userId;
	protected String templateId;
	protected Long deptId;
	
	public HrPaAssessmenttasksassigned(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAcId() {
		return acId;
	}

	public void setAcId(Long acId) {
		this.acId = acId;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Double getTarget() {
		return target;
	}

	public void setTarget(Double target) {
		this.target = target;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Long getPublishPerson() {
		return publishPerson;
	}

	public void setPublishPerson(Long publishPerson) {
		this.publishPerson = publishPerson;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
