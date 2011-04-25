package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class IOcard extends BaseObject {
   private java.lang.Long id;
   private com.digitalchina.itil.config.extlist.entity.IOType type;
   private java.lang.String cisn;
   private java.lang.String desc1;
   private java.lang.String desc2;
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setType(com.digitalchina.itil.config.extlist.entity.IOType type){
	     this.type=type;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
   }
   public void setDesc1(java.lang.String desc1){
	     this.desc1=desc1;
   }
   public void setDesc2(java.lang.String desc2){
	     this.desc2=desc2;
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
   public com.digitalchina.itil.config.extlist.entity.IOType getType(){
	     return this.type;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
   }
   public java.lang.String getDesc1(){
	     return this.desc1;
   }
   public java.lang.String getDesc2(){
	     return this.desc2;
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
