package com.xpsoft.oa.model.kpi;

import java.math.BigDecimal;

import com.xpsoft.core.model.BaseModel;

public class HrPaPerformanceindexscore extends BaseModel {
	protected long id;
	protected long piId;
	protected BigDecimal pisScore;
	protected long pisType;
	protected String pisDesc;
	
	public HrPaPerformanceindexscore(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPiId() {
		return piId;
	}

	public void setPiId(long piId) {
		this.piId = piId;
	}

	public BigDecimal getPisScore() {
		return pisScore;
	}

	public void setPisScore(BigDecimal pisScore) {
		this.pisScore = pisScore;
	}

	public long getPisType() {
		return pisType;
	}

	public void setPisType(long pisType) {
		this.pisType = pisType;
	}

	public String getPisDesc() {
		return pisDesc;
	}

	public void setPisDesc(String pisDesc) {
		this.pisDesc = pisDesc;
	}
}
