package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class Application extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String model;
   private java.lang.String appPackage;
   private com.zsgj.itil.config.extlist.entity.Apptype type;
   private java.lang.Integer supportUserMax;
   private java.lang.Integer supportUserStd;
   private java.lang.String cisn;
   private com.zsgj.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setBrand(java.lang.String brand){
	     this.brand=brand;
   }
   public void setModel(java.lang.String model){
	     this.model=model;
   }
   public void setAppPackage(java.lang.String appPackage){
	     this.appPackage=appPackage;
   }
   public void setType(com.zsgj.itil.config.extlist.entity.Apptype type){
	     this.type=type;
   }
   public void setSupportUserMax(java.lang.Integer supportUserMax){
	     this.supportUserMax=supportUserMax;
   }
   public void setSupportUserStd(java.lang.Integer supportUserStd){
	     this.supportUserStd=supportUserStd;
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
   public java.lang.String getModel(){
	     return this.model;
   }
   public java.lang.String getAppPackage(){
	     return this.appPackage;
   }
   public com.zsgj.itil.config.extlist.entity.Apptype getType(){
	     return this.type;
   }
   public java.lang.Integer getSupportUserMax(){
	     return this.supportUserMax;
   }
   public java.lang.Integer getSupportUserStd(){
	     return this.supportUserStd;
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
