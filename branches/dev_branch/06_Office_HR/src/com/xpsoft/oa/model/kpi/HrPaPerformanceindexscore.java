package com.xpsoft.oa.model.kpi;

import java.math.BigDecimal;

import com.xpsoft.core.model.BaseModel;

public class HrPaPerformanceindexscore extends BaseModel {
	protected Long id;
	protected HrPaPerformanceindex pi;
	protected BigDecimal pisScore;
	protected HrPaDatadictionary pisType;
	protected String pisDesc;
	
	public HrPaPerformanceindexscore(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HrPaPerformanceindex getPi() {
		return pi;
	}

	public void setPi(HrPaPerformanceindex pi) {
		this.pi = pi;
	}

	public BigDecimal getPisScore() {
		return pisScore;
	}

	public void setPisScore(BigDecimal pisScore) {
		this.pisScore = pisScore;
	}

	public HrPaDatadictionary getPisType() {
		return pisType;
	}

	public void setPisType(HrPaDatadictionary pisType) {
		this.pisType = pisType;
	}

	public String getPisDesc() {
		return pisDesc;
	}

	public void setPisDesc(String pisDesc) {
		this.pisDesc = pisDesc;
	}

}
