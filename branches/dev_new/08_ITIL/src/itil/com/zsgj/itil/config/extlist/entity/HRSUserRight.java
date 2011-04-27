package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class HRSUserRight extends BaseObject {
   private java.lang.Long id;
   private java.lang.String name;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setName(java.lang.String name){
	     this.name=name;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getName(){
	     return this.name;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.HRSUserRight other = (com.zsgj.itil.config.extlist.entity.HRSUserRight) obj;
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
