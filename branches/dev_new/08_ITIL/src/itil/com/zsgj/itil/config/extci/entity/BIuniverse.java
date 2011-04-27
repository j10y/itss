package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class BIuniverse extends BaseObject {
   private java.lang.Long id;
   private java.lang.String name;
   private java.lang.String biClass;
   private java.lang.String serviceObject;
   private java.util.Date goliveDate;
   private java.lang.String description;
   private java.lang.String cisn;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setName(java.lang.String name){
	     this.name=name;
   }
   public void setBiClass(java.lang.String biClass){
	     this.biClass=biClass;
   }
   public void setServiceObject(java.lang.String serviceObject){
	     this.serviceObject=serviceObject;
   }
   public void setGoliveDate(java.util.Date goliveDate){
	     this.goliveDate=goliveDate;
   }
   public void setDescription(java.lang.String description){
	     this.description=description;
   }
   public void setCisn(java.lang.String cisn){
	     this.cisn=cisn;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getName(){
	     return this.name;
   }
   public java.lang.String getBiClass(){
	     return this.biClass;
   }
   public java.lang.String getServiceObject(){
	     return this.serviceObject;
   }
   public java.util.Date getGoliveDate(){
	     return this.goliveDate;
   }
   public java.lang.String getDescription(){
	     return this.description;
   }
   public java.lang.String getCisn(){
	     return this.cisn;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.zsgj.itil.config.extci.entity.BIuniverse other = (com.zsgj.itil.config.extci.entity.BIuniverse) obj;
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
