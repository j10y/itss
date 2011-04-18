package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class PlatFormHRCountSign extends BaseObject {
   private java.lang.Long id;
   private java.lang.String realName;
   private java.lang.String itcode;
   private java.lang.String department;
   private java.lang.String telephone;
   private java.lang.String mobilePhone;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setRealName(java.lang.String realName){
	     this.realName=realName;
   }
   public void setItcode(java.lang.String itcode){
	     this.itcode=itcode;
   }
   public void setDepartment(java.lang.String department){
	     this.department=department;
   }
   public void setTelephone(java.lang.String telephone){
	     this.telephone=telephone;
   }
   public void setMobilePhone(java.lang.String mobilePhone){
	     this.mobilePhone=mobilePhone;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getRealName(){
	     return this.realName;
   }
   public java.lang.String getItcode(){
	     return this.itcode;
   }
   public java.lang.String getDepartment(){
	     return this.department;
   }
   public java.lang.String getTelephone(){
	     return this.telephone;
   }
   public java.lang.String getMobilePhone(){
	     return this.mobilePhone;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.PlatFormHRCountSign other = (com.digitalchina.itil.config.extlist.entity.PlatFormHRCountSign) obj;
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
