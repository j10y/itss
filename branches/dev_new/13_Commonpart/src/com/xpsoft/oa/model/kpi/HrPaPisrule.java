package com.xpsoft.oa.model.kpi;


import com.xpsoft.core.model.BaseModel;

public class HrPaPisrule extends BaseModel {
	protected Long id;
	protected Long pisId;
	protected Long pisAC;
	protected String formula;
	
	public HrPaPisrule(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPisId() {
		return pisId;
	}

	public void setPisId(Long pisId) {
		this.pisId = pisId;
	}

	public Long getPisAC() {
		return pisAC;
	}

	public void setPisAC(Long pisAC) {
		this.pisAC = pisAC;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	
}
