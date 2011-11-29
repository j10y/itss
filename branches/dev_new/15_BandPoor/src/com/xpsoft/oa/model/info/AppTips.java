/*     */ package com.xpsoft.oa.model.info;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class AppTips extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long tipsId;
/*     */ 
/*     */   @Expose
/*     */   protected String tipsName;
/*     */ 
/*     */   @Expose
/*     */   protected String content;
/*     */ 
/*     */   @Expose
/*     */   protected Integer disheight;
/*     */ 
/*     */   @Expose
/*     */   protected Integer diswidth;
/*     */ 
/*     */   @Expose
/*     */   protected Integer disleft;
/*     */ 
/*     */   @Expose
/*     */   protected Integer distop;
/*     */ 
/*     */   @Expose
/*     */   protected Integer dislevel;
/*     */ 
/*     */   @Expose
/*     */   protected Date createTime;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public AppTips()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AppTips(Long in_tipsId)
/*     */   {
/*  55 */     setTipsId(in_tipsId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  60 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  64 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getTipsId()
/*     */   {
/*  73 */     return this.tipsId;
/*     */   }
/*     */ 
/*     */   public void setTipsId(Long aValue)
/*     */   {
/*  80 */     this.tipsId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  87 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/*  94 */     if (aValue == null) {
/*  95 */       this.appUser = null;
/*  96 */     } else if (this.appUser == null) {
/*  97 */       this.appUser = new AppUser(aValue);
/*  98 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 100 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTipsName()
/*     */   {
/* 109 */     return this.tipsName;
/*     */   }
/*     */ 
/*     */   public void setTipsName(String aValue)
/*     */   {
/* 116 */     this.tipsName = aValue;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 124 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 131 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDisheight()
/*     */   {
/* 139 */     return this.disheight;
/*     */   }
/*     */ 
/*     */   public void setDisheight(Integer aValue)
/*     */   {
/* 146 */     this.disheight = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDiswidth()
/*     */   {
/* 154 */     return this.diswidth;
/*     */   }
/*     */ 
/*     */   public void setDiswidth(Integer aValue)
/*     */   {
/* 161 */     this.diswidth = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDisleft()
/*     */   {
/* 169 */     return this.disleft;
/*     */   }
/*     */ 
/*     */   public void setDisleft(Integer aValue)
/*     */   {
/* 176 */     this.disleft = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDistop()
/*     */   {
/* 184 */     return this.distop;
/*     */   }
/*     */ 
/*     */   public void setDistop(Integer aValue)
/*     */   {
/* 191 */     this.distop = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDislevel()
/*     */   {
/* 199 */     return this.dislevel;
/*     */   }
/*     */ 
/*     */   public void setDislevel(Integer aValue)
/*     */   {
/* 206 */     this.dislevel = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime()
/*     */   {
/* 214 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date aValue)
/*     */   {
/* 221 */     this.createTime = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 228 */     if (!(object instanceof AppTips)) {
/* 229 */       return false;
/*     */     }
/* 231 */     AppTips rhs = (AppTips)object;
/* 232 */     return new EqualsBuilder()
/* 233 */       .append(this.tipsId, rhs.tipsId)
/* 234 */       .append(this.tipsName, rhs.tipsName)
/* 235 */       .append(this.content, rhs.content)
/* 236 */       .append(this.disheight, rhs.disheight)
/* 237 */       .append(this.diswidth, rhs.diswidth)
/* 238 */       .append(this.disleft, rhs.disleft)
/* 239 */       .append(this.distop, rhs.distop)
/* 240 */       .append(this.dislevel, rhs.dislevel)
/* 241 */       .append(this.createTime, rhs.createTime)
/* 242 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 249 */     return new HashCodeBuilder(-82280557, -700257973)
/* 250 */       .append(this.tipsId)
/* 251 */       .append(this.tipsName)
/* 252 */       .append(this.content)
/* 253 */       .append(this.disheight)
/* 254 */       .append(this.diswidth)
/* 255 */       .append(this.disleft)
/* 256 */       .append(this.distop)
/* 257 */       .append(this.dislevel)
/* 258 */       .append(this.createTime)
/* 259 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 266 */     return new ToStringBuilder(this)
/* 267 */       .append("tipsId", this.tipsId)
/* 268 */       .append("tipsName", this.tipsName)
/* 269 */       .append("content", this.content)
/* 270 */       .append("disheight", this.disheight)
/* 271 */       .append("diswidth", this.diswidth)
/* 272 */       .append("disleft", this.disleft)
/* 273 */       .append("distop", this.distop)
/* 274 */       .append("dislevel", this.dislevel)
/* 275 */       .append("createTime", this.createTime)
/* 276 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.info.AppTips
 * JD-Core Version:    0.6.0
 */