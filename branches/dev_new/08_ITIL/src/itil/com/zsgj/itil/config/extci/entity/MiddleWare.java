package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class MiddleWare extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String packages;
   private java.lang.String model;
   private com.zsgj.itil.config.extlist.entity.MiddleWareType type;
   private java.lang.String cisn;
   private com.zsgj.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;
   private String assetsNum;
   
   public String getAssetsNum() {
		return assetsNum;
	}
	public void setAssetsNum(String assetsNum) {
		this.assetsNum = assetsNum;
	}
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setBrand(java.lang.String brand){
	     this.brand=brand;
   }
   public void setPackages(java.lang.String packages){
	     this.packages=packages;
   }
   public void setModel(java.lang.String model){
	     this.model=model;
   }
   public void setType(com.zsgj.itil.config.extlist.entity.MiddleWareType type){
	     this.type=type;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
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
   public java.lang.String getBrand(){
	     return this.brand;
   }
   public java.lang.String getPackages(){
	     return this.packages;
   }
   public java.lang.String getModel(){
	     return this.model;
   }
   public com.zsgj.itil.config.extlist.entity.MiddleWareType getType(){
	     return this.type;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
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
