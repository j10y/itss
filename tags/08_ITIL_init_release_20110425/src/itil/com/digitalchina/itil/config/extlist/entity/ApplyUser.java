package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class ApplyUser extends BaseObject {
   private java.lang.Long id;
   private com.digitalchina.info.framework.security.entity.UserInfo accountApplyUser;
   private java.util.Date accountApplyDate;
   private java.lang.String applyUserNum;
   private java.lang.String costCenter;
   private java.lang.String applyDept;
   private java.lang.String applyUserTel;
   private java.lang.String sameEmailDept;
   private java.lang.String mailServer;
   private java.lang.String applyUserType;
   private java.lang.String personScope;
   private java.lang.String mailAddress;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setAccountApplyUser(com.digitalchina.info.framework.security.entity.UserInfo accountApplyUser){
	     this.accountApplyUser=accountApplyUser;
   }
   public void setAccountApplyDate(java.util.Date accountApplyDate){
	     this.accountApplyDate=accountApplyDate;
   }
   public void setApplyUserNum(java.lang.String applyUserNum){
	     this.applyUserNum=applyUserNum;
   }
   public void setCostCenter(java.lang.String costCenter){
	     this.costCenter=costCenter;
   }
   public void setApplyDept(java.lang.String applyDept){
	     this.applyDept=applyDept;
   }
   public void setApplyUserTel(java.lang.String applyUserTel){
	     this.applyUserTel=applyUserTel;
   }
   public void setSameEmailDept(java.lang.String sameEmailDept){
	     this.sameEmailDept=sameEmailDept;
   }
   public void setMailServer(java.lang.String mailServer){
	     this.mailServer=mailServer;
   }
   public void setApplyUserType(java.lang.String applyUserType){
	     this.applyUserType=applyUserType;
   }
   public void setPersonScope(java.lang.String personScope){
	     this.personScope=personScope;
   }
   public void setMailAddress(java.lang.String mailAddress){
	     this.mailAddress=mailAddress;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getAccountApplyUser(){
	     return this.accountApplyUser;
   }
   public java.util.Date getAccountApplyDate(){
	     return this.accountApplyDate;
   }
   public java.lang.String getApplyUserNum(){
	     return this.applyUserNum;
   }
   public java.lang.String getCostCenter(){
	     return this.costCenter;
   }
   public java.lang.String getApplyDept(){
	     return this.applyDept;
   }
   public java.lang.String getApplyUserTel(){
	     return this.applyUserTel;
   }
   public java.lang.String getSameEmailDept(){
	     return this.sameEmailDept;
   }
   public java.lang.String getMailServer(){
	     return this.mailServer;
   }
   public java.lang.String getApplyUserType(){
	     return this.applyUserType;
   }
   public java.lang.String getPersonScope(){
	     return this.personScope;
   }
   public java.lang.String getMailAddress(){
	     return this.mailAddress;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.ApplyUser other = (com.digitalchina.itil.config.extlist.entity.ApplyUser) obj;
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
