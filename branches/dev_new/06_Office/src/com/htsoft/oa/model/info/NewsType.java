/*    */ package com.htsoft.oa.model.info;
/*    */ 
/*    */ import com.google.gson.annotations.Expose;
/*    */ import com.htsoft.core.model.BaseModel;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class NewsType extends BaseModel
/*    */ {
/*    */ 
/*    */   @Expose
/*    */   private Long typeId;
/*    */ 
/*    */   @Expose
/*    */   private String typeName;
/*    */ 
/*    */   @Expose
/*    */   private Short sn;
/*    */   private Set<News> news;
/*    */ 
/*    */   public Long getTypeId()
/*    */   {
/* 27 */     return this.typeId;
/*    */   }
/*    */   public void setTypeId(Long typeId) {
/* 30 */     this.typeId = typeId;
/*    */   }
/*    */   public String getTypeName() {
/* 33 */     return this.typeName;
/*    */   }
/*    */   public void setTypeName(String typeName) {
/* 36 */     this.typeName = typeName;
/*    */   }
/*    */   public Short getSn() {
/* 39 */     return this.sn;
/*    */   }
/*    */   public void setSn(Short sn) {
/* 42 */     this.sn = sn;
/*    */   }
/*    */   public Set<News> getNews() {
/* 45 */     return this.news;
/*    */   }
/*    */   public void setNews(Set<News> news) {
/* 48 */     this.news = news;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.info.NewsType
 * JD-Core Version:    0.6.0
 */