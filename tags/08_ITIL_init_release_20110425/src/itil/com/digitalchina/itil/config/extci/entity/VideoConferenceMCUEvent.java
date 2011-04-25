package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class VideoConferenceMCUEvent extends BaseObject {
   private java.lang.Long id;
   private java.lang.String brand;
   private java.lang.String model;
   private java.lang.String sn;
   private java.lang.String ipAddress;

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
   public void setIpAddress(java.lang.String ipAddress){
	     this.ipAddress=ipAddress;
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
   public java.lang.String getIpAddress(){
	     return this.ipAddress;
   }
} 
