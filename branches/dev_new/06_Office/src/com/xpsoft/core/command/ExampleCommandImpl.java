/*    */ package com.xpsoft.core.command;
/*    */ 
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.criterion.Example;
/*    */ 
/*    */ public class ExampleCommandImpl
/*    */   implements CriteriaCommand
/*    */ {
/* 19 */   private Object pojoExample = null;
/*    */ 
/*    */   public void setPojoExample(Object pojoEx) {
/* 22 */     this.pojoExample = pojoEx;
/*    */   }
/*    */ 
/*    */   public ExampleCommandImpl(Object pojoExample) {
/* 26 */     this.pojoExample = pojoExample;
/*    */   }
/*    */ 
/*    */   public Criteria execute(Criteria criteria) {
/* 30 */     if (this.pojoExample != null) {
/* 31 */       Example exampleRestriction = Example.create(this.pojoExample);
/* 32 */       criteria.add(exampleRestriction);
/*    */     }
/* 34 */     return criteria;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.command.ExampleCommandImpl
 * JD-Core Version:    0.6.0
 */