/*    */ package com.htsoft.core.command;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*    */ import org.hibernate.Criteria;
/*    */ import org.hibernate.criterion.Order;
/*    */ 
/*    */ public class SortCommandImpl
/*    */   implements CriteriaCommand
/*    */ {
/*    */   private String sortName;
/*    */   private String ascDesc;
/*    */   private QueryFilter filter;
/*    */ 
/*    */   public Criteria execute(Criteria criteria)
/*    */   {
/* 18 */     String[] propertys = this.sortName.split("[.]");
/* 19 */     if ((propertys != null) && (propertys.length > 1)) {
/* 20 */       for (int i = 0; i < propertys.length - 1; i++)
/*    */       {
/* 22 */         if (!this.filter.getAliasSet().contains(propertys[i])) {
/* 23 */           criteria.createAlias(propertys[i], propertys[i]);
/* 24 */           this.filter.getAliasSet().add(propertys[i]);
/*    */         }
/*    */       }
/*    */     }
/* 28 */     if ("desc".equalsIgnoreCase(this.ascDesc))
/* 29 */       criteria.addOrder(Order.desc(this.sortName));
/* 30 */     else if ("asc".equalsIgnoreCase(this.ascDesc)) {
/* 31 */       criteria.addOrder(Order.asc(this.sortName));
/*    */     }
/* 33 */     return criteria;
/*    */   }
/*    */ 
/*    */   public SortCommandImpl(String sortName, String ascDesc, QueryFilter filter) {
/* 37 */     this.sortName = sortName;
/* 38 */     this.ascDesc = ascDesc;
/* 39 */     this.filter = filter;
/*    */   }
/*    */ 
/*    */   public String getSortName()
/*    */   {
/* 49 */     return this.sortName;
/*    */   }
/*    */ 
/*    */   public void setSortName(String sortName) {
/* 53 */     this.sortName = sortName;
/*    */   }
/*    */ 
/*    */   public String getAscDesc() {
/* 57 */     return this.ascDesc;
/*    */   }
/*    */ 
/*    */   public void setAscDesc(String ascDesc) {
/* 61 */     this.ascDesc = ascDesc;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 65 */     return new HashCodeBuilder(-82280557, -700257973)
/* 66 */       .append(this.sortName)
/* 67 */       .append(this.ascDesc).toHashCode();
/*    */   }
/*    */ 
/*    */   public String getPartHql() {
/* 71 */     return this.sortName + " " + this.ascDesc;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.command.SortCommandImpl
 * JD-Core Version:    0.6.0
 */