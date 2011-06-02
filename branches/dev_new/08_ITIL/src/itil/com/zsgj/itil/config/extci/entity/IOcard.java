package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class IOcard extends BaseObject {
   private java.lang.Long id;
   private com.zsgj.itil.config.extlist.entity.IOType type;
   private java.lang.String cisn;
   private java.lang.String desc1;
   private java.lang.String desc2;
   private com.zsgj.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;
   private java.lang.String brand;
   private java.lang.String model;
   private String assetsNum;
   public String getAssetsNum() {
	return assetsNum;
	}
	public void setAssetsNum(String assetsNum) {
		this.assetsNum = assetsNum;
	}
	public java.lang.String getBrand() {
		return brand;
	}
	public void setBrand(java.lang.String brand) {
		this.brand = brand;
	}
	public java.lang.String getModel() {
		return model;
	}
	public void setModel(java.lang.String model) {
		this.model = model;
	}
	public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setType(com.zsgj.itil.config.extlist.entity.IOType type){
	     this.type=type;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
   }
   public void setDesc1(java.lang.String desc1){
	     this.desc1=desc1;
   }
   public void setDesc2(java.lang.String desc2){
	     this.desc2=desc2;
   }
   public void setCreateUser(com.zsgj.info.framework.security.entity.UserInfo createUser){
	     this.createUser=createUser;
   }
   public void setCreateDate(java.util.Date createDate){
	     this.createDate=createDate;
   }
   public void setModifyUser(com.zsgj.info.framework.security.entity.UserInfo modifyUser){
	     this.modifyUser=modifyUser;
   }
   public void setModifyDate(java.util.Date modifyDate){
	     this.modifyDate=modifyDate;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.zsgj.itil.config.extlist.entity.IOType getType(){
	     return this.type;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
   }
   public java.lang.String getDesc1(){
	     return this.desc1;
   }
   public java.lang.String getDesc2(){
	     return this.desc2;
   }
   public com.zsgj.info.framework.security.entity.UserInfo getCreateUser(){
	     return this.createUser;
   }
   public java.util.Date getCreateDate(){
	     return this.createDate;
   }
   public com.zsgj.info.framework.security.entity.UserInfo getModifyUser(){
	     return this.modifyUser;
   }
   public java.util.Date getModifyDate(){
	     return this.modifyDate;
   }
} 
