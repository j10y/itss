package com.xpsoft.oa.model.danpin;

import com.xpsoft.core.model.BaseModel;

public class SupplyInfo extends BaseModel{
	private Long sid;
	private String supplyId;
	private String companyName;
	private String legalPerson;
	private String linker;
	private String linkerPhone;
	private String supplyAddress;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getLinker() {
		return linker;
	}
	public void setLinker(String linker) {
		this.linker = linker;
	}
	public String getLinkerPhone() {
		return linkerPhone;
	}
	public void setLinkerPhone(String linkerPhone) {
		this.linkerPhone = linkerPhone;
	}
	public String getSupplyAddress() {
		return supplyAddress;
	}
	public void setSupplyAddress(String supplyAddress) {
		this.supplyAddress = supplyAddress;
	}
}
