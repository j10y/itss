package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class TelephoneType extends BaseObject {
   private java.lang.Long id;
   private java.lang.String type;

   
   
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setType(java.lang.String type){
	     this.type=type;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getType(){
	     return this.type;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.TelephoneType other = (com.zsgj.itil.config.extlist.entity.TelephoneType) obj;
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
