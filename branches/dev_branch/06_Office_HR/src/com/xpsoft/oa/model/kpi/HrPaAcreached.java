package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAcreached extends BaseModel {
	protected Long id;
	protected Long acId;
	protected Long category;
	protected Double result;
	protected Date inputDate;
	protected Long inputPerson;
	protected Long userId;
	protected String templateId;
	protected Long deptId;
	
	public HrPaAcreached(){}

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

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Long getInputPerson() {
		return inputPerson;
	}

	public void setInputPerson(Long inputPerson) {
		this.inputPerson = inputPerson;
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
