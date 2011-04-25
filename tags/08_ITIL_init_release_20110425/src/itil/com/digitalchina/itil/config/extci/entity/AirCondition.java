package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class AirCondition extends BaseObject {
   private java.lang.Long id;
   private java.lang.String model;
   private com.digitalchina.itil.config.extlist.entity.AirConditionType type;
   private java.lang.String powerConsumption;
   private java.lang.String brand;
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;
   private java.lang.String sn;
   private java.lang.String capacity;
   private java.lang.String cisn;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setModel(java.lang.String model){
	     this.model=model;
   }
   public void setType(com.digitalchina.itil.config.extlist.entity.AirConditionType type){
	     this.type=type;
   }
   public void setPowerConsumption(java.lang.String powerConsumption){
	     this.powerConsumption=powerConsumption;
   }
   public void setBrand(java.lang.String brand){
	     this.brand=brand;
   }
   public void setCreateUser(com.digitalchina.info.framework.security.entity.UserInfo createUser){
	     this.createUser=createUser;
   }
   public void setCreateDate(java.util.Date createDate){
	     this.createDate=createDate;
   }
   public void setModifyUser(com.digitalchina.info.framework.security.entity.UserInfo modifyUser){
	     this.modifyUser=modifyUser;
   }
   public void setModifyDate(java.util.Date modifyDate){
	     this.modifyDate=modifyDate;
   }
   public void setSn(java.lang.String sn){
	     this.sn=sn;
   }
   public void setCapacity(java.lang.String capacity){
	     this.capacity=capacity;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getModel(){
	     return this.model;
   }
   public com.digitalchina.itil.config.extlist.entity.AirConditionType getType(){
	     return this.type;
   }
   public java.lang.String getPowerConsumption(){
	     return this.powerConsumption;
   }
   public java.lang.String getBrand(){
	     return this.brand;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getCreateUser(){
	     return this.createUser;
   }
   public java.util.Date getCreateDate(){
	     return this.createDate;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getModifyUser(){
	     return this.modifyUser;
   }
   public java.util.Date getModifyDate(){
	     return this.modifyDate;
   }
   public java.lang.String getSn(){
	     return this.sn;
   }
   public java.lang.String getCapacity(){
	     return this.capacity;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
   }
} 
