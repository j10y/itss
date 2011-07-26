/*     */ package com.xpsoft.oa.model.flow;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ProcessForm extends BaseModel
/*     */ {
/*     */   protected Long formId;
/*     */   protected String activityName;
/*  24 */   protected Integer sn = Integer.valueOf(1);
/*     */   protected Date createtime;
/*     */   protected Long creatorId;
/*     */   protected String creatorName;
/*     */   protected ProcessRun processRun;
/*  32 */   protected Set formDatas = new HashSet();
/*  33 */   protected Set formFiles = new HashSet();
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/*  37 */     if (this.createtime == null) {
/*  38 */       this.createtime = new Date();
/*     */     }
/*  40 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date createtime) {
/*  44 */     this.createtime = createtime;
/*     */   }
/*     */ 
/*     */   public ProcessForm()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ProcessForm(Long in_formId)
/*     */   {
/*  60 */     setFormId(in_formId);
/*     */   }
/*     */ 
/*     */   public ProcessRun getProcessRun()
/*     */   {
/*  65 */     return this.processRun;
/*     */   }
/*     */ 
/*     */   public void setProcessRun(ProcessRun in_processRun) {
/*  69 */     this.processRun = in_processRun;
/*     */   }
/*     */ 
/*     */   public Set getFormDatas() {
/*  73 */     return this.formDatas;
/*     */   }
/*     */ 
/*     */   public void setFormDatas(Set in_formDatas) {
/*  77 */     this.formDatas = in_formDatas;
/*     */   }
/*     */ 
/*     */   public Set getFormFiles() {
/*  81 */     return this.formFiles;
/*     */   }
/*     */ 
/*     */   public void setFormFiles(Set in_formFiles) {
/*  85 */     this.formFiles = in_formFiles;
/*     */   }
/*     */ 
/*     */   public Long getFormId()
/*     */   {
/*  94 */     return this.formId;
/*     */   }
/*     */ 
/*     */   public void setFormId(Long aValue)
/*     */   {
/* 101 */     this.formId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getRunId()
/*     */   {
/* 108 */     return getProcessRun() == null ? null : getProcessRun().getRunId();
/*     */   }
/*     */ 
/*     */   public void setRunId(Long aValue)
/*     */   {
/* 115 */     if (aValue == null) {
/* 116 */       this.processRun = null;
/* 117 */     } else if (this.processRun == null) {
/* 118 */       this.processRun = new ProcessRun(aValue);
/* 119 */       this.processRun.setVersion(new Integer(0));
/*     */     } else {
/* 121 */       this.processRun.setRunId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getActivityName()
/*     */   {
/* 130 */     return this.activityName;
/*     */   }
/*     */ 
/*     */   public void setActivityName(String aValue)
/*     */   {
/* 138 */     this.activityName = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 145 */     if (!(object instanceof ProcessForm)) {
/* 146 */       return false;
/*     */     }
/* 148 */     ProcessForm rhs = (ProcessForm)object;
/* 149 */     return new EqualsBuilder()
/* 150 */       .append(this.formId, rhs.formId)
/* 151 */       .append(this.activityName, rhs.activityName)
/* 152 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 159 */     return new HashCodeBuilder(-82280557, -700257973)
/* 160 */       .append(this.formId)
/* 161 */       .append(this.activityName)
/* 162 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 169 */     return new ToStringBuilder(this)
/* 170 */       .append("formId", this.formId)
/* 171 */       .append("activityName", this.activityName)
/* 172 */       .toString();
/*     */   }
/*     */ 
/*     */   public Integer getSn() {
/* 176 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(Integer sn) {
/* 180 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public Long getCreatorId() {
/* 184 */     return this.creatorId;
/*     */   }
/*     */ 
/*     */   public void setCreatorId(Long creatorId) {
/* 188 */     this.creatorId = creatorId;
/*     */   }
/*     */ 
/*     */   public String getCreatorName() {
/* 192 */     return this.creatorName;
/*     */   }
/*     */ 
/*     */   public void setCreatorName(String creatorName) {
/* 196 */     this.creatorName = creatorName;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.flow.ProcessForm
 * JD-Core Version:    0.6.0
 */