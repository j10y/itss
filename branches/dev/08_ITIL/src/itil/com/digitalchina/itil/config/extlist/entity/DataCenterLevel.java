package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class DataCenterLevel extends BaseObject {
   private java.lang.Long id;
   private java.lang.String level;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setLevel(java.lang.String level){
	     this.level=level;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getLevel(){
	     return this.level;
   }
} 
