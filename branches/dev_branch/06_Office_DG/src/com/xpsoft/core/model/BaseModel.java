/*    */ package com.xpsoft.core.model;
/*    */ 
/*    */ import flexjson.JSON;
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class BaseModel
/*    */   implements Serializable
/*    */ {
/* 18 */   protected Log logger = LogFactory.getLog(BaseModel.class);
/*    */   private Integer version;
/*    */ 
/*    */   @JSON(include=false)
/*    */   public Integer getVersion()
/*    */   {
/* 22 */     return this.version;
/*    */   }
/*    */ 
/*    */   public void setVersion(Integer version) {
/* 26 */     this.version = version;
/*    */   }
/*    */ }