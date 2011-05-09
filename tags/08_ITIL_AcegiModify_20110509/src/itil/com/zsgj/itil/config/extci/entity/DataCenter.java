package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class DataCenter extends BaseObject {
   private java.lang.Integer floor;
   private com.zsgj.itil.config.extlist.entity.DataCenterType type;
   private java.lang.Long id;
   private java.lang.String nameAbb;
   private java.lang.Integer roomSize;
   private java.lang.String cisn;
   private com.zsgj.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;


   public void setFloor(java.lang.Integer floor){
	     this.floor=floor;
   }
   public void setType(com.zsgj.itil.config.extlist.entity.DataCenterType type){
	     this.type=type;
   }
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setNameAbb(java.lang.String nameAbb){
	     this.nameAbb=nameAbb;
   }
   public void setRoomSize(java.lang.Integer roomSize){
	     this.roomSize=roomSize;
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
   public java.lang.Integer getFloor(){
	     return this.floor;
   }
   public com.zsgj.itil.config.extlist.entity.DataCenterType getType(){
	     return this.type;
   }
   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getNameAbb(){
	     return this.nameAbb;
   }
   public java.lang.Integer getRoomSize(){
	     return this.roomSize;
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
