/*     */ package com.xpsoft.oa.model.communicate;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class PhoneGroup extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long groupId;
/*     */ 
/*     */   @Expose
/*     */   protected String groupName;
/*     */ 
/*     */   @Expose
/*     */   protected Short isShared;
/*     */ 
/*     */   @Expose
/*     */   protected Integer sn;
/*     */   protected AppUser appUser;
/*  34 */   protected Set<PhoneGroup> phoneBooks = new HashSet();
/*     */ 
/*     */   public Set<PhoneGroup> getPhoneBooks() {
/*  37 */     return this.phoneBooks;
/*     */   }
/*     */ 
/*     */   public void setPhoneBooks(Set<PhoneGroup> phoneBooks)
/*     */   {
/*  42 */     this.phoneBooks = phoneBooks;
/*     */   }
/*     */ 
/*     */   public PhoneGroup()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PhoneGroup(Long in_groupId)
/*     */   {
/*  58 */     setGroupId(in_groupId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  63 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  67 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getGroupId()
/*     */   {
/*  77 */     return this.groupId;
/*     */   }
/*     */ 
/*     */   public void setGroupId(Long aValue)
/*     */   {
/*  84 */     this.groupId = aValue;
/*     */   }
/*     */ 
/*     */   public String getGroupName()
/*     */   {
/*  92 */     return this.groupName;
/*     */   }
/*     */ 
/*     */   public void setGroupName(String aValue)
/*     */   {
/* 100 */     this.groupName = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsShared()
/*     */   {
/* 109 */     return this.isShared;
/*     */   }
/*     */ 
/*     */   public void setIsShared(Short aValue)
/*     */   {
/* 117 */     this.isShared = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getSn()
/*     */   {
/* 125 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(Integer aValue)
/*     */   {
/* 133 */     this.sn = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 140 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 147 */     if (aValue == null) {
/* 148 */       this.appUser = null;
/* 149 */     } else if (this.appUser == null) {
/* 150 */       this.appUser = new AppUser(aValue);
/* 151 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 153 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 161 */     if (!(object instanceof PhoneGroup)) {
/* 162 */       return false;
/*     */     }
/* 164 */     PhoneGroup rhs = (PhoneGroup)object;
/* 165 */     return new EqualsBuilder()
/* 166 */       .append(this.groupId, rhs.groupId)
/* 167 */       .append(this.groupName, rhs.groupName)
/* 168 */       .append(this.isShared, rhs.isShared)
/* 169 */       .append(this.sn, rhs.sn)
/* 170 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 177 */     return new HashCodeBuilder(-82280557, -700257973)
/* 178 */       .append(this.groupId)
/* 179 */       .append(this.groupName)
/* 180 */       .append(this.isShared)
/* 181 */       .append(this.sn)
/* 182 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 189 */     return new ToStringBuilder(this)
/* 190 */       .append("groupId", this.groupId)
/* 191 */       .append("groupName", this.groupName)
/* 192 */       .append("isShared", this.isShared)
/* 193 */       .append("sn", this.sn)
/* 194 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 201 */     return "groupId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 208 */     return this.groupId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.communicate.PhoneGroup
 * JD-Core Version:    0.6.0
 */