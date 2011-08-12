package com.xpsoft.oa.model.kpi;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;

public class HrPaAcreached extends BaseModel {
	protected long id;
	protected long acId;
	protected long category;
	protected double result;
	protected Date inputDate;
	protected long inputPerson;
	
	public HrPaAcreached(){}

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

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputPerson() {
		return inputPerson;
	}

	public void setInputPerson(long inputPerson) {
		this.inputPerson = inputPerson;
	}
}
