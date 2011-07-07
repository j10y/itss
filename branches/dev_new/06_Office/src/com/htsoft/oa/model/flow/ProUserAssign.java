/*     */ package com.htsoft.oa.model.flow;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ProUserAssign extends BaseModel
/*     */ {
/*     */   protected Long assignId;
/*     */   protected String deployId;
/*     */   protected String activityName;
/*     */   protected String roleId;
/*     */   protected String roleName;
/*     */   protected String userId;
/*     */   protected String username;
/*     */   protected Short isSigned;
/*     */ 
/*     */   public ProUserAssign()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ProUserAssign(Long in_assignId)
/*     */   {
/*  43 */     setAssignId(in_assignId);
/*     */   }
/*     */ 
/*     */   public Short getIsSigned() {
/*  47 */     return this.isSigned;
/*     */   }
/*     */ 
/*     */   public void setIsSigned(Short isSigned) {
/*  51 */     this.isSigned = isSigned;
/*     */   }
/*     */ 
/*     */   public Long getAssignId()
/*     */   {
/*  59 */     return this.assignId;
/*     */   }
/*     */ 
/*     */   public void setAssignId(Long aValue)
/*     */   {
/*  66 */     this.assignId = aValue;
/*     */   }
/*     */ 
/*     */   public String getDeployId()
/*     */   {
/*  74 */     return this.deployId;
/*     */   }
/*     */ 
/*     */   public void setDeployId(String aValue)
/*     */   {
/*  82 */     this.deployId = aValue;
/*     */   }
/*     */ 
/*     */   public String getActivityName()
/*     */   {
/*  90 */     return this.activityName;
/*     */   }
/*     */ 
/*     */   public void setActivityName(String aValue)
/*     */   {
/*  98 */     this.activityName = aValue;
/*     */   }
/*     */ 
/*     */   public String getRoleId()
/*     */   {
/* 106 */     return this.roleId;
/*     */   }
/*     */ 
/*     */   public void setRoleId(String aValue)
/*     */   {
/* 113 */     this.roleId = aValue;
/*     */   }
/*     */ 
/*     */   public String getUserId()
/*     */   {
/* 121 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(String aValue)
/*     */   {
/* 128 */     this.userId = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 135 */     if (!(object instanceof ProUserAssign)) {
/* 136 */       return false;
/*     */     }
/* 138 */     ProUserAssign rhs = (ProUserAssign)object;
/* 139 */     return new EqualsBuilder()
/* 140 */       .append(this.assignId, rhs.assignId)
/* 141 */       .append(this.deployId, rhs.deployId)
/* 142 */       .append(this.activityName, rhs.activityName)
/* 143 */       .append(this.roleId, rhs.roleId)
/* 144 */       .append(this.userId, rhs.userId)
/* 145 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 152 */     return new HashCodeBuilder(-82280557, -700257973)
/* 153 */       .append(this.assignId)
/* 154 */       .append(this.deployId)
/* 155 */       .append(this.activityName)
/* 156 */       .append(this.roleId)
/* 157 */       .append(this.userId)
/* 158 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 165 */     return new ToStringBuilder(this)
/* 166 */       .append("assignId", this.assignId)
/* 167 */       .append("deployId", this.deployId)
/* 168 */       .append("activityName", this.activityName)
/* 169 */       .append("roleId", this.roleId)
/* 170 */       .append("userId", this.userId)
/* 171 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 178 */     return "assignId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 185 */     return this.assignId;
/*     */   }
/*     */ 
/*     */   public String getRoleName() {
/* 189 */     return this.roleName;
/*     */   }
/*     */ 
/*     */   public void setRoleName(String roleName) {
/* 193 */     this.roleName = roleName;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 197 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 201 */     this.username = username;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.flow.ProUserAssign
 * JD-Core Version:    0.6.0
 */