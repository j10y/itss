package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class DatabasesEvent extends BaseObject {
   private java.lang.String model;
   private java.lang.String dbPackage;
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String cisn;
   private java.lang.String name1;
   private java.lang.String name2;
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
   public void setModel(java.lang.String model){
	     this.model=model;
   }
   public void setDbPackage(java.lang.String dbPackage){
	     this.dbPackage=dbPackage;
   }
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setBrand(java.lang.String brand){
	     this.brand=brand;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
   }
   public void setName1(java.lang.String name1){
	     this.name1=name1;
   }
   public void setName2(java.lang.String name2){
	     this.name2=name2;
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

   public java.lang.String getModel(){
	     return this.model;
   }
   public java.lang.String getDbPackage(){
	     return this.dbPackage;
   }
   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getBrand(){
	     return this.brand;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
   }
   public java.lang.String getName1(){
	     return this.name1;
   }
   public java.lang.String getName2(){
	     return this.name2;
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
