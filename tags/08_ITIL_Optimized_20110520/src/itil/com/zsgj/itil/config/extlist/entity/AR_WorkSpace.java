package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class AR_WorkSpace extends BaseObject {
   private java.lang.Long id;
   private java.lang.String space;
   private java.lang.String mailServer;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setSpace(java.lang.String space){
	     this.space=space;
   }
   public void setMailServer(java.lang.String mailServer){
	     this.mailServer=mailServer;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getSpace(){
	     return this.space;
   }
   public java.lang.String getMailServer(){
	     return this.mailServer;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.AR_WorkSpace other = (com.zsgj.itil.config.extlist.entity.AR_WorkSpace) obj;
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
