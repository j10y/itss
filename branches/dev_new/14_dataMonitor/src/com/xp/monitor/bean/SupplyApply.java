package com.xp.monitor.bean;

import java.util.Date;

public class SupplyApply {
	private  Long id;
	private String supplyName;//供应商名称
	
	private String brandRegistArea;//品牌注册地（国家/地区）
	
	private String brandBelong;//品牌持有（自有品牌/**级代理商）
	
	private String brandName;//经营品牌名称（中/英）
	
	private String brandPrice;//品牌价格带
	
	private String brandClass;//品牌所属类别
	
	private String brandStyle;//品牌风格（适合人群）
	
	private Integer storeNum;//拥有实体店/专柜数量
	
	private String storeAddress;//实体店区域分布（省市级）
	
	private String website;//品牌官方网站（网址）
	
	private String cooperateWebSite;//合作网站（5家以内）
	
	private String companyAddress;//公司地址
	
	private String companyTel;//公司电话
	
	private String companyEmail;//E-mail
	
	private String businessUser;//业务负责人/职位
	
	private String businessTel;//手机
	
	private String businessEmail;//E-mail
	
	private String eCommerceUser;//电商负责人/职位
	
	private String eCommerceTel;//手机
	
	private String eCommerceEmail;//E-mail
	
	private Date createDate;
	
	private UpLoadFile uploadFile;

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getBrandRegistArea() {
		return brandRegistArea;
	}

	public void setBrandRegistArea(String brandRegistArea) {
		this.brandRegistArea = brandRegistArea;
	}

	public String getBrandBelong() {
		return brandBelong;
	}

	public void setBrandBelong(String brandBelong) {
		this.brandBelong = brandBelong;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandPrice() {
		return brandPrice;
	}

	public void setBrandPrice(String brandPrice) {
		this.brandPrice = brandPrice;
	}

	public String getBrandClass() {
		return brandClass;
	}

	public void setBrandClass(String brandClass) {
		this.brandClass = brandClass;
	}

	public String getBrandStyle() {
		return brandStyle;
	}

	public void setBrandStyle(String brandStyle) {
		this.brandStyle = brandStyle;
	}

	public Integer getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Integer storeNum) {
		if(storeNum==null){
			storeNum=0;
		}
		this.storeNum = storeNum;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCooperateWebSite() {
		return cooperateWebSite;
	}

	public void setCooperateWebSite(String cooperateWebSite) {
		this.cooperateWebSite = cooperateWebSite;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getBusinessUser() {
		return businessUser;
	}

	public void setBusinessUser(String businessUser) {
		this.businessUser = businessUser;
	}

	public String getBusinessTel() {
		return businessTel;
	}

	public void setBusinessTel(String businessTel) {
		this.businessTel = businessTel;
	}

	public String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public String getECommerceUser() {
		return eCommerceUser;
	}

	public void setECommerceUser(String commerceUser) {
		eCommerceUser = commerceUser;
	}

	public String getECommerceTel() {
		return eCommerceTel;
	}

	public void setECommerceTel(String commerceTel) {
		eCommerceTel = commerceTel;
	}

	public String getECommerceEmail() {
		return eCommerceEmail;
	}

	public void setECommerceEmail(String commerceEmail) {
		eCommerceEmail = commerceEmail;
	}

	public UpLoadFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UpLoadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

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
	
}
