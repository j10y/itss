/*    */ package com.xpsoft.core.jbpm.pv;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ParamInfo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String label;
/*    */   private String name;
/*    */   private Object value;
/*    */   private boolean isShow;
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 41 */     return this.label;
/*    */   }
/*    */   public void setLabel(String label) {
/* 44 */     this.label = label;
/*    */   }
/*    */   public String getName() {
/* 47 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 50 */     this.name = name;
/*    */   }
/*    */   public Object getValue() {
/* 53 */     return this.value;
/*    */   }
/*    */   public void setValue(Object value) {
/* 56 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public boolean isShow() {
/* 60 */     return this.isShow;
/*    */   }
/*    */ 
/*    */   public void setShow(boolean isShow) {
/* 64 */     this.isShow = isShow;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.jbpm.pv.ParamInfo
 * JD-Core Version:    0.6.0
 */