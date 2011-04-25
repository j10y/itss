package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class VirtualMachine extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String model;
   private java.lang.Integer licenseQuantity;
   private java.lang.String cpuCapacity;
   private java.lang.Integer ramCapacity;
   private java.lang.String licenseType;
   private java.lang.String remoteMgtIP;
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
   public void setLicenseQuantity(java.lang.Integer licenseQuantity){
	     this.licenseQuantity=licenseQuantity;
   }
   public void setCpuCapacity(java.lang.String cpuCapacity){
	     this.cpuCapacity=cpuCapacity;
   }
   public void setRamCapacity(java.lang.Integer ramCapacity){
	     this.ramCapacity=ramCapacity;
   }
   public void setLicenseType(java.lang.String licenseType){
	     this.licenseType=licenseType;
   }
   public void setRemoteMgtIP(java.lang.String remoteMgtIP){
	     this.remoteMgtIP=remoteMgtIP;
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
   public java.lang.Integer getLicenseQuantity(){
	     return this.licenseQuantity;
   }
   public java.lang.String getCpuCapacity(){
	     return this.cpuCapacity;
   }
   public java.lang.Integer getRamCapacity(){
	     return this.ramCapacity;
   }
   public java.lang.String getLicenseType(){
	     return this.licenseType;
   }
   public java.lang.String getRemoteMgtIP(){
	     return this.remoteMgtIP;
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
