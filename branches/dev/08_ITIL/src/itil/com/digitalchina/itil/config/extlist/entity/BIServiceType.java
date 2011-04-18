package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class BIServiceType extends BaseObject {
   private java.lang.Long id;
   private java.lang.String serviceType;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setServiceType(java.lang.String serviceType){
	     this.serviceType=serviceType;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getServiceType(){
	     return this.serviceType;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.BIServiceType other = (com.digitalchina.itil.config.extlist.entity.BIServiceType) obj;
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
