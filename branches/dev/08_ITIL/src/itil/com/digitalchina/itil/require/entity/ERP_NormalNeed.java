package com.digitalchina.itil.require.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.sun.tools.javac.resources.javac;

public class ERP_NormalNeed extends BaseObject {
   private java.lang.Long id;
   private java.lang.String name;
   private com.digitalchina.itil.require.entity.ERP_NormalNeed oldApply;
   private java.lang.Integer processType;
   private java.lang.Integer status;
   private java.lang.Integer deleteFlag;
   private java.lang.Long serviceItem;
   private java.lang.String applyNum;
   private java.util.Date applyDate;
   private com.digitalchina.info.framework.security.entity.Department applyDept;
   private java.lang.String costConter;
   private com.digitalchina.info.framework.security.entity.UserInfo applyUser;
   private java.lang.String tel;
   private com.digitalchina.itil.config.extlist.entity.RequirementLevel requireLevel;
   private java.lang.String reason;
   private java.lang.String oldSystemLink;
   private java.lang.String actualStatus;
   private java.lang.String useStation;
   private java.lang.String includeAndExpend;
   private java.lang.String otherInfo;
   private java.lang.String attachment;
   private java.util.Set scopes = new java.util.HashSet();
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;
   private java.lang.Integer isWM;
   private java.lang.Integer isStore;
   private com.digitalchina.itil.require.entity.RequireApplyDefaultAudit flat;
   private java.lang.String weightAccount ; //加权账期
   private java.lang.Integer isOverseasSale ; //是否海外销售

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setName(java.lang.String name){
	     this.name=name;
   }
   public void setOldApply(com.digitalchina.itil.require.entity.ERP_NormalNeed oldApply){
	     this.oldApply=oldApply;
   }
   public void setProcessType(java.lang.Integer processType){
	     this.processType=processType;
   }
   public void setStatus(java.lang.Integer status){
	     this.status=status;
   }
   public void setDeleteFlag(java.lang.Integer deleteFlag){
	     this.deleteFlag=deleteFlag;
   }
   public void setServiceItem(java.lang.Long serviceItem){
	     this.serviceItem=serviceItem;
   }
   public void setApplyNum(java.lang.String applyNum){
	     this.applyNum=applyNum;
   }
   public void setApplyDate(java.util.Date applyDate){
	     this.applyDate=applyDate;
   }
   public void setApplyDept(com.digitalchina.info.framework.security.entity.Department applyDept){
	     this.applyDept=applyDept;
   }
   public void setCostConter(java.lang.String costConter){
	     this.costConter=costConter;
   }
   public void setApplyUser(com.digitalchina.info.framework.security.entity.UserInfo applyUser){
	     this.applyUser=applyUser;
   }
   public void setTel(java.lang.String tel){
	     this.tel=tel;
   }
   public void setRequireLevel(com.digitalchina.itil.config.extlist.entity.RequirementLevel requireLevel){
	     this.requireLevel=requireLevel;
   }
   public void setReason(java.lang.String reason){
	     this.reason=reason;
   }
   public void setOldSystemLink(java.lang.String oldSystemLink){
	     this.oldSystemLink=oldSystemLink;
   }
   public void setActualStatus(java.lang.String actualStatus){
	     this.actualStatus=actualStatus;
   }
   public void setUseStation(java.lang.String useStation){
	     this.useStation=useStation;
   }
   public void setIncludeAndExpend(java.lang.String includeAndExpend){
	     this.includeAndExpend=includeAndExpend;
   }
   public void setOtherInfo(java.lang.String otherInfo){
	     this.otherInfo=otherInfo;
   }
   public void setAttachment(java.lang.String attachment){
	     this.attachment=attachment;
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
   public java.lang.String getName(){
	     return this.name;
   }
   public com.digitalchina.itil.require.entity.ERP_NormalNeed getOldApply(){
	     return this.oldApply;
   }
   public java.lang.Integer getProcessType(){
	     return this.processType;
   }
   public java.lang.Integer getStatus(){
	     return this.status;
   }
   public java.lang.Integer getDeleteFlag(){
	     return this.deleteFlag;
   }
   public java.lang.Long getServiceItem(){
	     return this.serviceItem;
   }
   public java.lang.String getApplyNum(){
	     return this.applyNum;
   }
   public java.util.Date getApplyDate(){
	     return this.applyDate;
   }
   public com.digitalchina.info.framework.security.entity.Department getApplyDept(){
	     return this.applyDept;
   }
   public java.lang.String getCostConter(){
	     return this.costConter;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getApplyUser(){
	     return this.applyUser;
   }
   public java.lang.String getTel(){
	     return this.tel;
   }
   public com.digitalchina.itil.config.extlist.entity.RequirementLevel getRequireLevel(){
	     return this.requireLevel;
   }
   public java.lang.String getReason(){
	     return this.reason;
   }
   public java.lang.String getOldSystemLink(){
	     return this.oldSystemLink;
   }
   public java.lang.String getActualStatus(){
	     return this.actualStatus;
   }
   public java.lang.String getUseStation(){
	     return this.useStation;
   }
   public java.lang.String getIncludeAndExpend(){
	     return this.includeAndExpend;
   }
   public java.lang.String getOtherInfo(){
	     return this.otherInfo;
   }
   public java.lang.String getAttachment(){
	     return this.attachment;
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
public java.lang.Integer getIsWM() {
	return isWM;
}
public void setIsWM(java.lang.Integer isWM) {
	this.isWM = isWM;
}
public java.lang.Integer getIsStore() {
	return isStore;
}
public void setIsStore(java.lang.Integer isStore) {
	this.isStore = isStore;
}
public java.util.Set getScopes() {
	return scopes;
}
public void setScopes(java.util.Set scopes) {
	this.scopes = scopes;
}
public com.digitalchina.itil.require.entity.RequireApplyDefaultAudit getFlat() {
	return flat;
}
public void setFlat(
		com.digitalchina.itil.require.entity.RequireApplyDefaultAudit flat) {
	this.flat = flat;
	
}
public java.lang.String getWeightAccount() {
	return weightAccount;
}
public void setWeightAccount(java.lang.String weightAccount) {
	this.weightAccount = weightAccount;
}
public java.lang.Integer getIsOverseasSale() {
	return isOverseasSale;
}
public void setIsOverseasSale(java.lang.Integer isOverseasSale) {
	this.isOverseasSale = isOverseasSale;
}


} 
