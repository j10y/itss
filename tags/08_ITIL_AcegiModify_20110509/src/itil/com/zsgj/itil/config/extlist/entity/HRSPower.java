package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class HRSPower extends BaseObject {
   private java.lang.Long id;
   private java.lang.String name;
   private java.lang.String keyWord;
   private java.lang.String descn;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setName(java.lang.String name){
	     this.name=name;
   }
   public void setKeyWord(java.lang.String keyWord){
	     this.keyWord=keyWord;
   }
   public void setDescn(java.lang.String descn){
	     this.descn=descn;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getName(){
	     return this.name;
   }
   public java.lang.String getKeyWord(){
	     return this.keyWord;
   }
   public java.lang.String getDescn(){
	     return this.descn;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extlist.entity.HRSPower other = (com.zsgj.itil.config.extlist.entity.HRSPower) obj;
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
