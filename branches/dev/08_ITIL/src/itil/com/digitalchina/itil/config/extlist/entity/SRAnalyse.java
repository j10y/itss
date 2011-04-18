package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class SRAnalyse extends BaseObject {
   private java.lang.Long id;
   private com.digitalchina.itil.require.entity.SpecialRequirement specialRequire;
   private java.lang.String projectName;
   private java.lang.String summary;
   private java.lang.String content;
   private java.util.Date startDate;
   private java.util.Date testBeginDate;
   private java.util.Date testEndDate;
   private java.util.Date finishDate;
   private java.lang.String attachFile;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setSpecialRequire(com.digitalchina.itil.require.entity.SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setProjectName(java.lang.String projectName){
	     this.projectName=projectName;
   }
   public void setSummary(java.lang.String summary){
	     this.summary=summary;
   }
   public void setContent(java.lang.String content){
	     this.content=content;
   }
   public void setStartDate(java.util.Date startDate){
	     this.startDate=startDate;
   }
   public void setTestBeginDate(java.util.Date testBeginDate){
	     this.testBeginDate=testBeginDate;
   }
   public void setTestEndDate(java.util.Date testEndDate){
	     this.testEndDate=testEndDate;
   }
   public void setFinishDate(java.util.Date finishDate){
	     this.finishDate=finishDate;
   }
   public void setAttachFile(java.lang.String attachFile){
	     this.attachFile=attachFile;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public com.digitalchina.itil.require.entity.SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public java.lang.String getProjectName(){
	     return this.projectName;
   }
   public java.lang.String getSummary(){
	     return this.summary;
   }
   public java.lang.String getContent(){
	     return this.content;
   }
   public java.util.Date getStartDate(){
	     return this.startDate;
   }
   public java.util.Date getTestBeginDate(){
	     return this.testBeginDate;
   }
   public java.util.Date getTestEndDate(){
	     return this.testEndDate;
   }
   public java.util.Date getFinishDate(){
	     return this.finishDate;
   }
   public java.lang.String getAttachFile(){
	     return this.attachFile;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final com.digitalchina.itil.config.extlist.entity.SRAnalyse other = (com.digitalchina.itil.config.extlist.entity.SRAnalyse) obj;
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
