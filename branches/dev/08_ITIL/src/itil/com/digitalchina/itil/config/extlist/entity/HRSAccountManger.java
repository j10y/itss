package com.digitalchina.itil.config.extlist.entity;
import com.digitalchina.info.framework.dao.BaseObject;

public class HRSAccountManger extends BaseObject {
   private java.lang.Long id;
   private java.lang.String itcode;
   private java.lang.String userName;
   private java.lang.String dept;
   
   public String getRealNameAndDept() {
		String result = itcode+"/"+userName+"/"+dept;
		return result;
	}
   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setItcode(java.lang.String itcode){
	     this.itcode=itcode;
   }
   public void setUserName(java.lang.String userName){
	     this.userName=userName;
   }
   public void setDept(java.lang.String dept){
	     this.dept=dept;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getItcode(){
	     return this.itcode;
   }
   public java.lang.String getUserName(){
	     return this.userName;
   }
   public java.lang.String getDept(){
	     return this.dept;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.HRSAccountManger other = (com.digitalchina.itil.config.extlist.entity.HRSAccountManger) obj;
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
