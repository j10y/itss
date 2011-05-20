package com.zsgj.itil.config.extci.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class ERPinterface extends BaseObject {
   private java.lang.Long id;
   private java.lang.String name;
   private java.lang.String techName;
   private com.zsgj.itil.config.extlist.entity.ERPAccessMode accessMode;
   private java.lang.String webServiceName;
   private java.lang.String description;
   private java.lang.String notice;
   private java.util.Date goliveDate;
   private java.lang.String technicalDoc;
   private java.lang.String cisn;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setName(java.lang.String name){
	     this.name=name;
   }
   public void setTechName(java.lang.String techName){
	     this.techName=techName;
   }
   public void setAccessMode(com.zsgj.itil.config.extlist.entity.ERPAccessMode accessMode){
	     this.accessMode=accessMode;
   }
   public void setWebServiceName(java.lang.String webServiceName){
	     this.webServiceName=webServiceName;
   }
   public void setDescription(java.lang.String description){
	     this.description=description;
   }
   public void setNotice(java.lang.String notice){
	     this.notice=notice;
   }
   public void setGoliveDate(java.util.Date goliveDate){
	     this.goliveDate=goliveDate;
   }
   public void setTechnicalDoc(java.lang.String technicalDoc){
	     this.technicalDoc=technicalDoc;
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
   public java.lang.String getTechName(){
	     return this.techName;
   }
   public com.zsgj.itil.config.extlist.entity.ERPAccessMode getAccessMode(){
	     return this.accessMode;
   }
   public java.lang.String getWebServiceName(){
	     return this.webServiceName;
   }
   public java.lang.String getDescription(){
	     return this.description;
   }
   public java.lang.String getNotice(){
	     return this.notice;
   }
   public java.util.Date getGoliveDate(){
	     return this.goliveDate;
   }
   public java.lang.String getTechnicalDoc(){
	     return this.technicalDoc;
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
   	final com.zsgj.itil.config.extci.entity.ERPinterface other = (com.zsgj.itil.config.extci.entity.ERPinterface) obj;
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
