/*    */ package com.htsoft.oa.model.flow;
/*    */ 
/*    */ import org.jbpm.pvm.internal.model.Activity;
/*    */ import org.jbpm.pvm.internal.model.Transition;
/*    */ 
/*    */ public class Transform
/*    */ {
/*    */   private String name;
/*    */   private String destination;
/*    */   private String source;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 31 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 35 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getDestination() {
/* 39 */     return this.destination;
/*    */   }
/*    */ 
/*    */   public void setDestination(String destination) {
/* 43 */     this.destination = destination;
/*    */   }
/*    */ 
/*    */   public String getSource() {
/* 47 */     return this.source;
/*    */   }
/*    */ 
/*    */   public void setSource(String source) {
/* 51 */     this.source = source;
/*    */   }
/*    */ 
/*    */   public Transform()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Transform(Transition jbpmtran)
/*    */   {
/* 60 */     this.name = jbpmtran.getName();
/* 61 */     this.source = jbpmtran.getSource().getName();
/* 62 */     this.destination = jbpmtran.getDestination().getName();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.flow.Transform
 * JD-Core Version:    0.6.0
 */