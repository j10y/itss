package com.xpsoft.oa.model.shop;

import java.math.BigDecimal;

import com.xpsoft.core.model.BaseModel;

public class SpPaPerformanceindexscore extends BaseModel {
	protected Long id;
	protected SpPaPerformanceindex pi;
	protected BigDecimal pisScore;
	protected SpPaDatadictionary pisType;
	protected String pisDesc;
	protected Double coefficient;
	
	public SpPaPerformanceindexscore(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpPaPerformanceindex getPi() {
		return pi;
	}

	public void setPi(SpPaPerformanceindex pi) {
		this.pi = pi;
	}

	public BigDecimal getPisScore() {
		return pisScore;
	}

	public void setPisScore(BigDecimal pisScore) {
		this.pisScore = pisScore;
	}

	public SpPaDatadictionary getPisType() {
		return pisType;
	}

	public void setPisType(SpPaDatadictionary pisType) {
		this.pisType = pisType;
	}

	public String getPisDesc() {
		return pisDesc;
	}

	public void setPisDesc(String pisDesc) {
		this.pisDesc = pisDesc;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

}
