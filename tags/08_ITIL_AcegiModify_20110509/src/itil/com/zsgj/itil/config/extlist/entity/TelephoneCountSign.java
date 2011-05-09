package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class TelephoneCountSign extends BaseObject {
   private java.lang.Long id;
   private java.lang.String department;
   private java.lang.String countSignItcode;
   private java.lang.String countSignName;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setDepartment(java.lang.String department){
	     this.department=department;
   }
   public void setCountSignItcode(java.lang.String countSignItcode){
	     this.countSignItcode=countSignItcode;
   }
   public void setCountSignName(java.lang.String countSignName){
	     this.countSignName=countSignName;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getDepartment(){
	     return this.department;
   }
   public java.lang.String getCountSignItcode(){
	     return this.countSignItcode;
   }
   public java.lang.String getCountSignName(){
	     return this.countSignName;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.TelephoneCountSign other = (com.zsgj.itil.config.extlist.entity.TelephoneCountSign) obj;
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
