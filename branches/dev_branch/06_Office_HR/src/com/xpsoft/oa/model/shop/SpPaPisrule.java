package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaPisrule extends BaseModel {
	protected Long id;
	protected SpPaPerformanceindexscore pis;
	protected Long pisAC;
	protected String formula;
	
	public SpPaPisrule(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpPaPerformanceindexscore getPis() {
		return pis;
	}

	public void setPis(SpPaPerformanceindexscore pis) {
		this.pis = pis;
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
