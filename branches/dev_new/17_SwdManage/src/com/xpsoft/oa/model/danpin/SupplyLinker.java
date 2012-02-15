package com.xpsoft.oa.model.danpin;

public class SupplyLinker {
	private Long sid;
	private Long supplyInfoSid;
	private String linker;
	private String linkerPhone;
	private String email;
	private Integer isMainLinker;
	private Integer status;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Long getSupplyInfoSid() {
		return supplyInfoSid;
	}
	public void setSupplyInfoSid(Long supplyInfoSid) {
		this.supplyInfoSid = supplyInfoSid;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsMainLinker() {
		return isMainLinker;
	}
	public void setIsMainLinker(Integer isMainLinker) {
		this.isMainLinker = isMainLinker;
	}
}
