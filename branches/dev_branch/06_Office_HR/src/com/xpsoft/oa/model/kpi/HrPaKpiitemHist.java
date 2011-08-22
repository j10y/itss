package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitemHist extends BaseModel {
	protected long id;
	protected HrPaKpipbcHist pbc;
	protected long piId;
	protected Double weight;
	protected Double result;
	
	public HrPaKpiitemHist() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HrPaKpipbcHist getPbc() {
		return pbc;
	}

	public void setPbc(HrPaKpipbcHist pbc) {
		this.pbc = pbc;
	}

	public long getPiId() {
		return piId;
	}

	public void setPiId(long piId) {
		this.piId = piId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}
	
}
