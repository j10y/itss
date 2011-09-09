package com.xpsoft.oa.model.kpi;


import java.math.BigDecimal;

import com.xpsoft.core.model.BaseModel;

public class HrPaPerformanceindexscore extends BaseModel {
	protected Long id;
	protected Long piId;
	protected BigDecimal pisScore;
	protected Long pisType;
	protected String pisDesc;
    protected Double coefficient;
	
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}
	public HrPaPerformanceindexscore(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPiId() {
		return piId;
	}

	public void setPiId(Long piId) {
		this.piId = piId;
	}

	public BigDecimal getPisScore() {
		return pisScore;
	}

	public void setPisScore(BigDecimal pisScore) {
		this.pisScore = pisScore;
	}

	public Long getPisType() {
		return pisType;
	}

	public void setPisType(Long pisType) {
		this.pisType = pisType;
	}

	public String getPisDesc() {
		return pisDesc;
	}

	public void setPisDesc(String pisDesc) {
		this.pisDesc = pisDesc;
	}

	
}
