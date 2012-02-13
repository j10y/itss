/*     */ package com.xpsoft.oa.model.info;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class NewsComment extends BaseModel
/*     */ {
/*     */   protected Long commentId;
/*     */   protected String content;
/*     */   protected Date createtime;
/*     */   protected String fullname;
/*     */   protected News news;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public NewsComment()
/*     */   {
/*     */   }
/*     */ 
/*     */   public NewsComment(Long in_commentId)
/*     */   {
/*  41 */     setCommentId(in_commentId);
/*     */   }
/*     */ 
/*     */   public News getNews()
/*     */   {
/*  46 */     return this.news;
/*     */   }
/*     */ 
/*     */   public void setNews(News in_news) {
/*  50 */     this.news = in_news;
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser() {
/*  54 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  58 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getCommentId()
/*     */   {
/*  67 */     return this.commentId;
/*     */   }
/*     */ 
/*     */   public void setCommentId(Long aValue)
/*     */   {
/*  74 */     this.commentId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getNewsId()
/*     */   {
/*  81 */     return getNews() == null ? null : getNews().getNewsId();
/*     */   }
/*     */ 
/*     */   public void setNewsId(Long aValue)
/*     */   {
/*  88 */     if (aValue == null) {
/*  89 */       this.news = null;
/*  90 */     } else if (this.news == null) {
/*  91 */       this.news = new News(aValue);
/*  92 */       this.news.setVersion(new Integer(0));
/*     */     } else {
/*  94 */       this.news.setNewsId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 103 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 111 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 119 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 127 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 135 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 143 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 150 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 157 */     if (aValue == null) {
/* 158 */       this.appUser = null;
/* 159 */     } else if (this.appUser == null) {
/* 160 */       this.appUser = new AppUser(aValue);
/* 161 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 163 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 171 */     if (!(object instanceof NewsComment)) {
/* 172 */       return false;
/*     */     }
/* 174 */     NewsComment rhs = (NewsComment)object;
/* 175 */     return new EqualsBuilder()
/* 176 */       .append(this.commentId, rhs.commentId)
/* 177 */       .append(this.content, rhs.content)
/* 178 */       .append(this.createtime, rhs.createtime)
/* 179 */       .append(this.fullname, rhs.fullname)
/* 180 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 187 */     return new HashCodeBuilder(-82280557, -700257973)
/* 188 */       .append(this.commentId)
/* 189 */       .append(this.content)
/* 190 */       .append(this.createtime)
/* 191 */       .append(this.fullname)
/* 192 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 199 */     return new ToStringBuilder(this)
/* 200 */       .append("commentId", this.commentId)
/* 201 */       .append("content", this.content)
/* 202 */       .append("createtime", this.createtime)
/* 203 */       .append("fullname", this.fullname)
/* 204 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.info.NewsComment
 * JD-Core Version:    0.6.0
 */