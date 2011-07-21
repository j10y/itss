package com.xpsoft.oa.model.dg;

import java.util.Date;

public class CopyFoxBill {
	private Long id;
	private Date createDate;
	private String brandName;
	private Long brandId;
	private Long saleCode; 
	private double price;
	private String receiptNum;
	private Integer faxNum;
	private Integer copyNum;
	private String createUser;
	private Integer shopId;
	private String shopName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getSaleCode() {
		return saleCode;
	}
	public void setSaleCode(Long saleCode) {
		this.saleCode = saleCode;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	public Integer getFaxNum() {
		return faxNum;
	}
	public void setFaxNum(Integer faxNum) {
		this.faxNum = faxNum;
	}
	public Integer getCopyNum() {
		return copyNum;
	}
	public void setCopyNum(Integer copyNum) {
		this.copyNum = copyNum;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	
}
