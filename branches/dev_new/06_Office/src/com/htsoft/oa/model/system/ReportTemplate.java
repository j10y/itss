/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ReportTemplate extends BaseModel
/*     */ {
/*     */   protected Long reportId;
/*     */   protected String title;
/*     */   protected String descp;
/*     */   protected String reportLocation;
/*     */   protected Date createtime;
/*     */   protected Date updatetime;
/*     */ 
/*     */   public ReportTemplate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReportTemplate(Long in_reportId)
/*     */   {
/*  41 */     setReportId(in_reportId);
/*     */   }
/*     */ 
/*     */   public Long getReportId()
/*     */   {
/*  51 */     return this.reportId;
/*     */   }
/*     */ 
/*     */   public void setReportId(Long aValue)
/*     */   {
/*  58 */     this.reportId = aValue;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  66 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String aValue)
/*     */   {
/*  74 */     this.title = aValue;
/*     */   }
/*     */ 
/*     */   public String getDescp()
/*     */   {
/*  82 */     return this.descp;
/*     */   }
/*     */ 
/*     */   public void setDescp(String aValue)
/*     */   {
/*  90 */     this.descp = aValue;
/*     */   }
/*     */ 
/*     */   public String getReportLocation()
/*     */   {
/*  98 */     return this.reportLocation;
/*     */   }
/*     */ 
/*     */   public void setReportLocation(String aValue)
/*     */   {
/* 106 */     this.reportLocation = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 114 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 122 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getUpdatetime()
/*     */   {
/* 130 */     return this.updatetime;
/*     */   }
/*     */ 
/*     */   public void setUpdatetime(Date aValue)
/*     */   {
/* 138 */     this.updatetime = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 145 */     if (!(object instanceof ReportTemplate)) {
/* 146 */       return false;
/*     */     }
/* 148 */     ReportTemplate rhs = (ReportTemplate)object;
/* 149 */     return new EqualsBuilder()
/* 150 */       .append(this.reportId, rhs.reportId)
/* 151 */       .append(this.title, rhs.title)
/* 152 */       .append(this.descp, rhs.descp)
/* 153 */       .append(this.reportLocation, rhs.reportLocation)
/* 154 */       .append(this.createtime, rhs.createtime)
/* 155 */       .append(this.updatetime, rhs.updatetime)
/* 156 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 163 */     return new HashCodeBuilder(-82280557, -700257973)
/* 164 */       .append(this.reportId)
/* 165 */       .append(this.title)
/* 166 */       .append(this.descp)
/* 167 */       .append(this.reportLocation)
/* 168 */       .append(this.createtime)
/* 169 */       .append(this.updatetime)
/* 170 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     return new ToStringBuilder(this)
/* 178 */       .append("reportId", this.reportId)
/* 179 */       .append("title", this.title)
/* 180 */       .append("descp", this.descp)
/* 181 */       .append("reportLocation", this.reportLocation)
/* 182 */       .append("createtime", this.createtime)
/* 183 */       .append("updatetime", this.updatetime)
/* 184 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 191 */     return "reportId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 198 */     return this.reportId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.ReportTemplate
 * JD-Core Version:    0.6.0
 */