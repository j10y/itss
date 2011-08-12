package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaAuthpbccitem extends BaseModel {
	protected long id;
	protected long apbcId;
	protected long akpiItemId;
	protected double result;
	
	public HrPaAuthpbccitem(){}

	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getApbcId() {
		return apbcId;
	}

	public void setApbcId(long apbcId) {
		this.apbcId = apbcId;
	}

	public long getAkpiItemId() {
		return akpiItemId;
	}

	public void setAkpiItemId(long akpiItemId) {
		this.akpiItemId = akpiItemId;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}
}
