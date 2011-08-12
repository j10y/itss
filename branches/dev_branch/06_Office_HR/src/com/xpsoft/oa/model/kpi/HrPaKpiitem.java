package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaKpiitem extends BaseModel {
	protected long id;
	protected long pbcId;
	protected long piId;
	protected double weight;
	protected double result;
	
	public HrPaKpiitem(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPbcId() {
		return pbcId;
	}

	public void setPbcId(long pbcId) {
		this.pbcId = pbcId;
	}

	public long getPiId() {
		return piId;
	}

	public void setPiId(long piId) {
		this.piId = piId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}
}
