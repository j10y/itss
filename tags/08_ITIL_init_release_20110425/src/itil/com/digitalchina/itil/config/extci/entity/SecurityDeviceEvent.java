package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class SecurityDeviceEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String model;
   private java.lang.String sn;
   private java.lang.String ipAddress1;
   private java.lang.String ipAddress2;
   private java.lang.String ipAddress3;
   private java.lang.String version;
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
   public void setSn(java.lang.String sn){
	     this.sn=sn;
   }
   public void setIpAddress1(java.lang.String ipAddress1){
	     this.ipAddress1=ipAddress1;
   }
   public void setIpAddress2(java.lang.String ipAddress2){
	     this.ipAddress2=ipAddress2;
   }
   public void setIpAddress3(java.lang.String ipAddress3){
	     this.ipAddress3=ipAddress3;
   }
   public void setVersion(java.lang.String version){
	     this.version=version;
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
   public java.lang.String getSn(){
	     return this.sn;
   }
   public java.lang.String getIpAddress1(){
	     return this.ipAddress1;
   }
   public java.lang.String getIpAddress2(){
	     return this.ipAddress2;
   }
   public java.lang.String getIpAddress3(){
	     return this.ipAddress3;
   }
   public java.lang.String getVersion(){
	     return this.version;
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

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extci.entity.SecurityDeviceEvent other = (com.digitalchina.itil.config.extci.entity.SecurityDeviceEvent) obj;
   	if (id == null) {
   		if (other.id != null)
   			return false;
   	} else if (!id.equals(other.id))
   		return false;
   	return true; 
   }


   public int hashCode() {
		final int prime = 31;
   	int result = super.hashCode();
   	result = prime * result + ((id == null) ? 0 : id.hashCode());
   	return result;
  	}
} 
