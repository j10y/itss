package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaPisrule extends BaseModel {
	protected long id;
	protected long pisId;
	protected long pisAC;
	protected String formula;
	
	public HrPaPisrule(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPisId() {
		return pisId;
	}

	public void setPisId(long pisId) {
		this.pisId = pisId;
	}

	public long getPisAC() {
		return pisAC;
	}

	public void setPisAC(long pisAC) {
		this.pisAC = pisAC;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
}
