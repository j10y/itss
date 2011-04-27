package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class LanProjectEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String projectConstractor;
   private java.lang.Integer phonePortNumber;
   private java.lang.Integer dataPortNumber;
   private java.lang.String projectContactPerson;
   private java.lang.String projectContactPersonTel;
   private java.lang.Integer lanAPNumber;
   private java.lang.String projectContactPersonMobile;
   private java.lang.String projectContactPersonEmail;
   private com.zsgj.itil.config.extlist.entity.LanProjectType type;
   private java.lang.String cisn;
   private com.zsgj.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setProjectConstractor(java.lang.String projectConstractor){
	     this.projectConstractor=projectConstractor;
   }
   public void setPhonePortNumber(java.lang.Integer phonePortNumber){
	     this.phonePortNumber=phonePortNumber;
   }
   public void setDataPortNumber(java.lang.Integer dataPortNumber){
	     this.dataPortNumber=dataPortNumber;
   }
   public void setProjectContactPerson(java.lang.String projectContactPerson){
	     this.projectContactPerson=projectContactPerson;
   }
   public void setProjectContactPersonTel(java.lang.String projectContactPersonTel){
	     this.projectContactPersonTel=projectContactPersonTel;
   }
   public void setLanAPNumber(java.lang.Integer lanAPNumber){
	     this.lanAPNumber=lanAPNumber;
   }
   public void setProjectContactPersonMobile(java.lang.String projectContactPersonMobile){
	     this.projectContactPersonMobile=projectContactPersonMobile;
   }
   public void setProjectContactPersonEmail(java.lang.String projectContactPersonEmail){
	     this.projectContactPersonEmail=projectContactPersonEmail;
   }
   public void setType(com.zsgj.itil.config.extlist.entity.LanProjectType type){
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
   public java.lang.String getProjectConstractor(){
	     return this.projectConstractor;
   }
   public java.lang.Integer getPhonePortNumber(){
	     return this.phonePortNumber;
   }
   public java.lang.Integer getDataPortNumber(){
	     return this.dataPortNumber;
   }
   public java.lang.String getProjectContactPerson(){
	     return this.projectContactPerson;
   }
   public java.lang.String getProjectContactPersonTel(){
	     return this.projectContactPersonTel;
   }
   public java.lang.Integer getLanAPNumber(){
	     return this.lanAPNumber;
   }
   public java.lang.String getProjectContactPersonMobile(){
	     return this.projectContactPersonMobile;
   }
   public java.lang.String getProjectContactPersonEmail(){
	     return this.projectContactPersonEmail;
   }
   public com.zsgj.itil.config.extlist.entity.LanProjectType getType(){
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
