package com.digitalchina.itil.config.extci.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class AppAdministratorEvent extends BaseObject {
   private java.lang.Long id;
   private com.digitalchina.info.framework.security.entity.Department deptInfo;
   private com.digitalchina.info.framework.security.entity.UserInfo userInfo;
   private java.lang.String employeeCode;
   private java.lang.String email;
   private java.lang.String telephone;
   private java.lang.String mobilePhone;
   private java.lang.String otherInfo;
   private com.digitalchina.info.framework.security.entity.UserInfo createUser;
   private java.util.Date createDate;
   private com.digitalchina.info.framework.security.entity.UserInfo modifyUser;
   private java.util.Date modifyDate;
   private java.lang.String cisn;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setDeptInfo(com.digitalchina.info.framework.security.entity.Department deptInfo){
	     this.deptInfo=deptInfo;
   }
   public void setUserInfo(com.digitalchina.info.framework.security.entity.UserInfo userInfo){
	     this.userInfo=userInfo;
   }
   public void setEmployeeCode(java.lang.String employeeCode){
	     this.employeeCode=employeeCode;
   }
   public void setEmail(java.lang.String email){
	     this.email=email;
   }
   public void setTelephone(java.lang.String telephone){
	     this.telephone=telephone;
   }
   public void setMobilePhone(java.lang.String mobilePhone){
	     this.mobilePhone=mobilePhone;
   }
   public void setOtherInfo(java.lang.String otherInfo){
	     this.otherInfo=otherInfo;
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
   public com.digitalchina.info.framework.security.entity.Department getDeptInfo(){
	     return this.deptInfo;
   }
   public com.digitalchina.info.framework.security.entity.UserInfo getUserInfo(){
	     return this.userInfo;
   }
   public java.lang.String getEmployeeCode(){
	     return this.employeeCode;
   }
   public java.lang.String getEmail(){
	     return this.email;
   }
   public java.lang.String getTelephone(){
	     return this.telephone;
   }
   public java.lang.String getMobilePhone(){
	     return this.mobilePhone;
   }
   public java.lang.String getOtherInfo(){
	     return this.otherInfo;
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
   	final com.digitalchina.itil.config.extci.entity.AppAdministratorEvent other = (com.digitalchina.itil.config.extci.entity.AppAdministratorEvent) obj;
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
public java.lang.String getCisn() {
	return cisn;
}
public void setCisn(java.lang.String cisn) {
	this.cisn = cisn;
}
} 
