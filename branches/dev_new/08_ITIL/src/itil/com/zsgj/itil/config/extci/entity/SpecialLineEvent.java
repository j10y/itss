package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class SpecialLineEvent extends BaseObject {
   private java.lang.Long id;
   private com.zsgj.itil.config.extci.entity.OfficeLocation startLocation;
   private com.zsgj.itil.config.extci.entity.OfficeLocation endLocation;
   private java.lang.String lineServiceProviderA;
   private com.zsgj.itil.config.extlist.entity.LineCostRecoverType lineCostRecoverType;
   private java.lang.String lineServiceProviderB;
   private java.lang.String cisn;
   private java.lang.String serviceProviderATel;
   private java.lang.String serviceProviderBTel;
   private java.lang.String lineNumberA;
   private java.lang.String lineNumberB;
   private com.zsgj.itil.config.extlist.entity.SpecialLineType type;
   private com.zsgj.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.zsgj.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;
   private String assetsNum;
   
   public String getAssetsNum() {
		return assetsNum;
	}
	public void setAssetsNum(String assetsNum) {
		this.assetsNum = assetsNum;
	}
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setStartLocation(com.zsgj.itil.config.extci.entity.OfficeLocation startLocation){
	     this.startLocation=startLocation;
   }
   public void setEndLocation(com.zsgj.itil.config.extci.entity.OfficeLocation endLocation){
	     this.endLocation=endLocation;
   }
   public void setLineServiceProviderA(java.lang.String lineServiceProviderA){
	     this.lineServiceProviderA=lineServiceProviderA;
   }
   public void setLineCostRecoverType(com.zsgj.itil.config.extlist.entity.LineCostRecoverType lineCostRecoverType){
	     this.lineCostRecoverType=lineCostRecoverType;
   }
   public void setLineServiceProviderB(java.lang.String lineServiceProviderB){
	     this.lineServiceProviderB=lineServiceProviderB;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
   }
   public void setServiceProviderATel(java.lang.String serviceProviderATel){
	     this.serviceProviderATel=serviceProviderATel;
   }
   public void setServiceProviderBTel(java.lang.String serviceProviderBTel){
	     this.serviceProviderBTel=serviceProviderBTel;
   }
   public void setLineNumberA(java.lang.String lineNumberA){
	     this.lineNumberA=lineNumberA;
   }
   public void setLineNumberB(java.lang.String lineNumberB){
	     this.lineNumberB=lineNumberB;
   }
   public void setType(com.zsgj.itil.config.extlist.entity.SpecialLineType type){
	     this.type=type;
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
   public com.zsgj.itil.config.extci.entity.OfficeLocation getStartLocation(){
	     return this.startLocation;
   }
   public com.zsgj.itil.config.extci.entity.OfficeLocation getEndLocation(){
	     return this.endLocation;
   }
   public java.lang.String getLineServiceProviderA(){
	     return this.lineServiceProviderA;
   }
   public com.zsgj.itil.config.extlist.entity.LineCostRecoverType getLineCostRecoverType(){
	     return this.lineCostRecoverType;
   }
   public java.lang.String getLineServiceProviderB(){
	     return this.lineServiceProviderB;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
   }
   public java.lang.String getServiceProviderATel(){
	     return this.serviceProviderATel;
   }
   public java.lang.String getServiceProviderBTel(){
	     return this.serviceProviderBTel;
   }
   public java.lang.String getLineNumberA(){
	     return this.lineNumberA;
   }
   public java.lang.String getLineNumberB(){
	     return this.lineNumberB;
   }
   public com.zsgj.itil.config.extlist.entity.SpecialLineType getType(){
	     return this.type;
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
