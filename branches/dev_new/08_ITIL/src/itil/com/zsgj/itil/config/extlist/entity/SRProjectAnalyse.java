package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.require.entity.SpecialRequirement;

public class SRProjectAnalyse extends BaseObject {
   private Long id;
   private SpecialRequirement specialRequire;
   private String projectName;
   private String summary;
   private String content;
   private Date startDate;
   private Date testBeginDate;
   private Date testEndDate;
   private Date finishDate;
   private String attachFile;

   public void setId(Long id){
	     this.id=id;
   }
   public void setSpecialRequire(SpecialRequirement specialRequire){
	     this.specialRequire=specialRequire;
   }
   public void setProjectName(String projectName){
	     this.projectName=projectName;
   }
   public void setSummary(String summary){
	     this.summary=summary;
   }
   public void setContent(String content){
	     this.content=content;
   }
   public void setStartDate(Date startDate){
	     this.startDate=startDate;
   }
   public void setTestBeginDate(Date testBeginDate){
	     this.testBeginDate=testBeginDate;
   }
   public void setTestEndDate(Date testEndDate){
	     this.testEndDate=testEndDate;
   }
   public void setFinishDate(Date finishDate){
	     this.finishDate=finishDate;
   }
   public void setAttachFile(String attachFile){
	     this.attachFile=attachFile;
   }

   public Long getId(){
	     return this.id;
   }
   public SpecialRequirement getSpecialRequire(){
	     return this.specialRequire;
   }
   public String getProjectName(){
	     return this.projectName;
   }
   public String getSummary(){
	     return this.summary;
   }
   public String getContent(){
	     return this.content;
   }
   public Date getStartDate(){
	     return this.startDate;
   }
   public Date getTestBeginDate(){
	     return this.testBeginDate;
   }
   public Date getTestEndDate(){
	     return this.testEndDate;
   }
   public Date getFinishDate(){
	     return this.finishDate;
   }
   public String getAttachFile(){
	     return this.attachFile;
   }

   public boolean equals(Object obj) {
		if (this == obj)
   		return true;
   	if (!super.equals(obj))
   		return false;
   	if (getClass() != obj.getClass())
   		return false;
   	final SRProjectAnalyse other = (SRProjectAnalyse) obj;
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
