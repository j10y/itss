package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class GatewayEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String model;
   private com.digitalchina.itil.config.extlist.entity.GatewayType type;
   private java.lang.String sn;
   private java.lang.String ipAddress1;
   private java.lang.String iosVersion;
   private java.lang.Integer powerConsumption;
   private java.lang.String ipAddress2;
   private java.lang.String cisn;
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
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
   public void setType(com.digitalchina.itil.config.extlist.entity.GatewayType type){
	     this.type=type;
   }
   public void setSn(java.lang.String sn){
	     this.sn=sn;
   }
   public void setIpAddress1(java.lang.String ipAddress1){
	     this.ipAddress1=ipAddress1;
   }
   public void setIosVersion(java.lang.String iosVersion){
	     this.iosVersion=iosVersion;
   }
   public void setPowerConsumption(java.lang.Integer powerConsumption){
	     this.powerConsumption=powerConsumption;
   }
   public void setIpAddress2(java.lang.String ipAddress2){
	     this.ipAddress2=ipAddress2;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
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

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getBrand(){
	     return this.brand;
   }
   public java.lang.String getModel(){
	     return this.model;
   }
   public com.digitalchina.itil.config.extlist.entity.GatewayType getType(){
	     return this.type;
   }
   public java.lang.String getSn(){
	     return this.sn;
   }
   public java.lang.String getIpAddress1(){
	     return this.ipAddress1;
   }
   public java.lang.String getIosVersion(){
	     return this.iosVersion;
   }
   public java.lang.Integer getPowerConsumption(){
	     return this.powerConsumption;
   }
   public java.lang.String getIpAddress2(){
	     return this.ipAddress2;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
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
} 
