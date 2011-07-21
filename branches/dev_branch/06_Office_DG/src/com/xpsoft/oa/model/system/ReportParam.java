/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ReportParam extends BaseModel
/*     */ {
/*     */   protected Long paramId;
/*     */   protected String paramName;
/*     */   protected String paramKey;
/*     */   protected String defaultVal;
/*     */   protected String paramType;
/*     */   protected Integer sn;
/*     */   protected ReportTemplate reportTemplate;
/*     */ 
/*     */   public ReportParam()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReportParam(Long in_paramId)
/*     */   {
/*  41 */     setParamId(in_paramId);
/*     */   }
/*     */ 
/*     */   public ReportTemplate getReportTemplate()
/*     */   {
/*  46 */     return this.reportTemplate;
/*     */   }
/*     */ 
/*     */   public void setReportTemplate(ReportTemplate in_reportTemplate) {
/*  50 */     this.reportTemplate = in_reportTemplate;
/*     */   }
/*     */ 
/*     */   public Long getParamId()
/*     */   {
/*  59 */     return this.paramId;
/*     */   }
/*     */ 
/*     */   public void setParamId(Long aValue)
/*     */   {
/*  66 */     this.paramId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getReportId()
/*     */   {
/*  73 */     return getReportTemplate() == null ? null : getReportTemplate().getReportId();
/*     */   }
/*     */ 
/*     */   public void setReportId(Long aValue)
/*     */   {
/*  80 */     if (aValue == null) {
/*  81 */       this.reportTemplate = null;
/*  82 */     } else if (this.reportTemplate == null) {
/*  83 */       this.reportTemplate = new ReportTemplate(aValue);
/*  84 */       this.reportTemplate.setVersion(new Integer(0));
/*     */     } else {
/*  86 */       this.reportTemplate.setReportId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getParamName()
/*     */   {
/*  95 */     return this.paramName;
/*     */   }
/*     */ 
/*     */   public void setParamName(String aValue)
/*     */   {
/* 103 */     this.paramName = aValue;
/*     */   }
/*     */ 
/*     */   public String getParamKey()
/*     */   {
/* 111 */     return this.paramKey;
/*     */   }
/*     */ 
/*     */   public void setParamKey(String aValue)
/*     */   {
/* 119 */     this.paramKey = aValue;
/*     */   }
/*     */ 
/*     */   public String getDefaultVal()
/*     */   {
/* 127 */     return this.defaultVal;
/*     */   }
/*     */ 
/*     */   public void setDefaultVal(String aValue)
/*     */   {
/* 135 */     this.defaultVal = aValue;
/*     */   }
/*     */ 
/*     */   public String getParamType()
/*     */   {
/* 149 */     return this.paramType;
/*     */   }
/*     */ 
/*     */   public void setParamType(String aValue)
/*     */   {
/* 157 */     this.paramType = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getSn()
/*     */   {
/* 165 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(Integer aValue)
/*     */   {
/* 173 */     this.sn = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 180 */     if (!(object instanceof ReportParam)) {
/* 181 */       return false;
/*     */     }
/* 183 */     ReportParam rhs = (ReportParam)object;
/* 184 */     return new EqualsBuilder()
/* 185 */       .append(this.paramId, rhs.paramId)
/* 186 */       .append(this.paramName, rhs.paramName)
/* 187 */       .append(this.paramKey, rhs.paramKey)
/* 188 */       .append(this.defaultVal, rhs.defaultVal)
/* 189 */       .append(this.paramType, rhs.paramType)
/* 190 */       .append(this.sn, rhs.sn)
/* 191 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 198 */     return new HashCodeBuilder(-82280557, -700257973)
/* 199 */       .append(this.paramId)
/* 200 */       .append(this.paramName)
/* 201 */       .append(this.paramKey)
/* 202 */       .append(this.defaultVal)
/* 203 */       .append(this.paramType)
/* 204 */       .append(this.sn)
/* 205 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 212 */     return new ToStringBuilder(this)
/* 213 */       .append("paramId", this.paramId)
/* 214 */       .append("paramName", this.paramName)
/* 215 */       .append("paramKey", this.paramKey)
/* 216 */       .append("defaultVal", this.defaultVal)
/* 217 */       .append("paramType", this.paramType)
/* 218 */       .append("sn", this.sn)
/* 219 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.ReportParam
 * JD-Core Version:    0.6.0
 */