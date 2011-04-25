package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class MobileTelAllowance extends BaseObject {
   private java.lang.Long id;
   private java.lang.String postCode;
   private java.lang.String postName;
   private java.lang.Double allowance;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setPostCode(java.lang.String postCode){
	     this.postCode=postCode;
   }
   public void setPostName(java.lang.String postName){
	     this.postName=postName;
   }
   public void setAllowance(java.lang.Double allowance){
	     this.allowance=allowance;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getPostCode(){
	     return this.postCode;
   }
   public java.lang.String getPostName(){
	     return this.postName;
   }
   public java.lang.Double getAllowance(){
	     return this.allowance;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.MobileTelAllowance other = (com.digitalchina.itil.config.extlist.entity.MobileTelAllowance) obj;
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
