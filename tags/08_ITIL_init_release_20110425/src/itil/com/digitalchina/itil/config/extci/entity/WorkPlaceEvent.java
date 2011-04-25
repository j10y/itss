package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class WorkPlaceEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String telPortNo;
   private java.lang.String dataPortNo;
   private java.lang.String personCode;
   private com.digitalchina.itil.config.extlist.entity.WorkPlaceType type;
   private java.lang.String cisn;
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setTelPortNo(java.lang.String telPortNo){
	     this.telPortNo=telPortNo;
   }
   public void setDataPortNo(java.lang.String dataPortNo){
	     this.dataPortNo=dataPortNo;
   }
   public void setPersonCode(java.lang.String personCode){
	     this.personCode=personCode;
   }
   public void setType(com.digitalchina.itil.config.extlist.entity.WorkPlaceType type){
	     this.type=type;
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
   public java.lang.String getTelPortNo(){
	     return this.telPortNo;
   }
   public java.lang.String getDataPortNo(){
	     return this.dataPortNo;
   }
   public java.lang.String getPersonCode(){
	     return this.personCode;
   }
   public com.digitalchina.itil.config.extlist.entity.WorkPlaceType getType(){
	     return this.type;
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
