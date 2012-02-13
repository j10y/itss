/*    */ package com.xpsoft.oa.model.info;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Notice
/*    */ {
/*    */   private long noticeId;
/*    */   private String postName;
/*    */   private String noticeTitle;
/*    */   private String noticeContent;
/*    */   private Date effectiveDate;
/*    */   private Date expirationDate;
/*    */   private short state;
/*    */ 
/*    */   public String getPostName()
/*    */   {
/* 23 */     return this.postName;
/*    */   }
/*    */   public long getNoticeId() {
/* 26 */     return this.noticeId;
/*    */   }
/*    */   public void setNoticeId(long noticeId) {
/* 29 */     this.noticeId = noticeId;
/*    */   }
/*    */   public void setPostName(String postName) {
/* 32 */     this.postName = postName;
/*    */   }
/*    */   public String getNoticeTitle() {
/* 35 */     return this.noticeTitle;
/*    */   }
/*    */   public void setNoticeTitle(String noticeTitle) {
/* 38 */     this.noticeTitle = noticeTitle;
/*    */   }
/*    */ 
/*    */   public String getNoticeContent() {
/* 42 */     return this.noticeContent;
/*    */   }
/*    */   public void setNoticeContent(String noticeContent) {
/* 45 */     this.noticeContent = noticeContent;
/*    */   }
/*    */   public Date getEffectiveDate() {
/* 48 */     return this.effectiveDate;
/*    */   }
/*    */   public void setEffectiveDate(Date effectiveDate) {
/* 51 */     this.effectiveDate = effectiveDate;
/*    */   }
/*    */   public Date getExpirationDate() {
/* 54 */     return this.expirationDate;
/*    */   }
/*    */   public void setExpirationDate(Date expirationDate) {
/* 57 */     this.expirationDate = expirationDate;
/*    */   }
/*    */   public short getState() {
/* 60 */     return this.state;
/*    */   }
/*    */   public void setState(short state) {
/* 63 */     this.state = state;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.info.Notice
 * JD-Core Version:    0.6.0
 */