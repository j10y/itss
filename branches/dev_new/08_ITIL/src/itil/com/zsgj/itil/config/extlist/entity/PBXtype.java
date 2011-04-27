package com.zsgj.itil.config.extlist.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class PBXtype extends BaseObject {
   private java.lang.Long id;
   private java.lang.String type;

   public void setId(java.lang.Long id){
	     this.id=id;
   }
   public void setType(java.lang.String type){
	     this.type=type;
   }

   public java.lang.Long getId(){
	     return this.id;
   }
   public java.lang.String getType(){
	     return this.type;
   }
} 
