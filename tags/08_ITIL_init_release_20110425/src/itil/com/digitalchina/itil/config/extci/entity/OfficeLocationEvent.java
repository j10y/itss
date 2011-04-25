package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class OfficeLocationEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String address;
   private String principalName;
   private java.lang.String contactPersonTel;
   private java.lang.String contactPersonMobile;
   private java.lang.String contactPersonEmail;
   private java.lang.String locationName;
   private com.digitalchina.itil.config.extlist.entity.OfficeLocationType type;
   private java.lang.String cisn;
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setAddress(java.lang.String address){
	     this.address=address;
   }
   public void setContactPersonTel(java.lang.String contactPersonTel){
	     this.contactPersonTel=contactPersonTel;
   }
   public void setContactPersonMobile(java.lang.String contactPersonMobile){
	     this.contactPersonMobile=contactPersonMobile;
   }
   public void setContactPersonEmail(java.lang.String contactPersonEmail){
	     this.contactPersonEmail=contactPersonEmail;
   }
   public void setLocationName(java.lang.String locationName){
	     this.locationName=locationName;
   }
   public void setType(com.digitalchina.itil.config.extlist.entity.OfficeLocationType type){
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
   public java.lang.String getAddress(){
	     return this.address;
   }
   
   public String getPrincipalName() {
	return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public java.lang.String getContactPersonTel(){
	     return this.contactPersonTel;
   }
   public java.lang.String getContactPersonMobile(){
	     return this.contactPersonMobile;
   }
   public java.lang.String getContactPersonEmail(){
	     return this.contactPersonEmail;
   }
   public java.lang.String getLocationName(){
	     return this.locationName;
   }
   public com.digitalchina.itil.config.extlist.entity.OfficeLocationType getType(){
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
