/*     */ package com.htsoft.oa.model.flow;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class FormDef extends BaseModel
/*     */ {
/*  23 */   public static final Integer DEFAULT_COLUMNS = Integer.valueOf(1);
/*     */ 
/*     */   @Expose
/*     */   protected Long formDefId;
/*     */ 
/*     */   @Expose
/*     */   protected String formName;
/*     */ 
/*     */   @Expose
/*     */   protected Integer columns;
/*     */ 
/*     */   @Expose
/*     */   protected Short isEnabled;
/*     */ 
/*     */   @Expose
/*     */   protected String activityName;
/*     */ 
/*     */   @Expose
/*     */   protected String extDef;
/*     */ 
/*     */   @Expose
/*     */   protected String deployId;
/*     */ 
/*     */   public FormDef() {  } 
/*  55 */   public FormDef(Long in_formDefId) { setFormDefId(in_formDefId);
/*     */   }
/*     */ 
/*     */   public String getExtDef()
/*     */   {
/*  60 */     return this.extDef;
/*     */   }
/*     */ 
/*     */   public void setExtDef(String extDef) {
/*  64 */     this.extDef = extDef;
/*     */   }
/*     */ 
/*     */   public Long getFormDefId()
/*     */   {
/*  72 */     return this.formDefId;
/*     */   }
/*     */ 
/*     */   public void setFormDefId(Long aValue)
/*     */   {
/*  79 */     this.formDefId = aValue;
/*     */   }
/*     */ 
/*     */   public String getFormName()
/*     */   {
/*  87 */     return this.formName;
/*     */   }
/*     */ 
/*     */   public void setFormName(String aValue)
/*     */   {
/*  95 */     this.formName = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getColumns()
/*     */   {
/* 103 */     return this.columns;
/*     */   }
/*     */ 
/*     */   public void setColumns(Integer aValue)
/*     */   {
/* 111 */     this.columns = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsEnabled()
/*     */   {
/* 119 */     return this.isEnabled;
/*     */   }
/*     */ 
/*     */   public void setIsEnabled(Short aValue)
/*     */   {
/* 127 */     this.isEnabled = aValue;
/*     */   }
/*     */ 
/*     */   public String getActivityName()
/*     */   {
/* 135 */     return this.activityName;
/*     */   }
/*     */ 
/*     */   public void setActivityName(String aValue)
/*     */   {
/* 143 */     this.activityName = aValue;
/*     */   }
/*     */ 
/*     */   public String getDeployId()
/*     */   {
/* 151 */     return this.deployId;
/*     */   }
/*     */ 
/*     */   public void setDeployId(String aValue)
/*     */   {
/* 159 */     this.deployId = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 166 */     if (!(object instanceof FormDef)) {
/* 167 */       return false;
/*     */     }
/* 169 */     FormDef rhs = (FormDef)object;
/* 170 */     return new EqualsBuilder()
/* 171 */       .append(this.formDefId, rhs.formDefId)
/* 172 */       .append(this.formName, rhs.formName)
/* 173 */       .append(this.columns, rhs.columns)
/* 174 */       .append(this.isEnabled, rhs.isEnabled)
/* 175 */       .append(this.activityName, rhs.activityName)
/* 176 */       .append(this.deployId, rhs.deployId)
/* 177 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 184 */     return new HashCodeBuilder(-82280557, -700257973)
/* 185 */       .append(this.formDefId)
/* 186 */       .append(this.formName)
/* 187 */       .append(this.columns)
/* 188 */       .append(this.isEnabled)
/* 189 */       .append(this.activityName)
/* 190 */       .append(this.deployId)
/* 191 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 198 */     return new ToStringBuilder(this)
/* 199 */       .append("formDefId", this.formDefId)
/* 200 */       .append("formName", this.formName)
/* 201 */       .append("columns", this.columns)
/* 202 */       .append("isEnabled", this.isEnabled)
/* 203 */       .append("activityName", this.activityName)
/* 204 */       .append("deployId", this.deployId)
/* 205 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.flow.FormDef
 * JD-Core Version:    0.6.0
 */