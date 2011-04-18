package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class MailVolume extends BaseObject {
   private java.lang.Long id;
   private java.lang.String volume;
   private java.lang.Integer value;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setVolume(java.lang.String volume){
	     this.volume=volume;
   }
   public void setValue(java.lang.Integer value){
	     this.value=value;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getVolume(){
	     return this.volume;
   }
   public java.lang.Integer getValue(){
	     return this.value;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.MailVolume other = (com.digitalchina.itil.config.extlist.entity.MailVolume) obj;
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
