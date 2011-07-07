/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class SysConfig extends BaseModel
/*     */ {
/*  23 */   public static String CODE_OPEN = "1";
/*     */ 
/*  27 */   public static String CODE_COLSE = "2";
/*     */   protected Long configId;
/*     */   protected String configKey;
/*     */   protected String configName;
/*     */   protected String configDesc;
/*     */   protected String typeName;
/*     */   protected Short dataType;
/*     */   protected String dataValue;
/*     */ 
/*     */   public String getDataValue()
/*     */   {
/*  39 */     return this.dataValue;
/*     */   }
/*     */ 
/*     */   public void setDataValue(String dataValue) {
/*  43 */     this.dataValue = dataValue;
/*     */   }
/*     */ 
/*     */   public SysConfig()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SysConfig(Long in_configId)
/*     */   {
/*  59 */     setConfigId(in_configId);
/*     */   }
/*     */ 
/*     */   public Long getConfigId()
/*     */   {
/*  69 */     return this.configId;
/*     */   }
/*     */ 
/*     */   public void setConfigId(Long aValue)
/*     */   {
/*  76 */     this.configId = aValue;
/*     */   }
/*     */ 
/*     */   public String getConfigKey()
/*     */   {
/*  84 */     return this.configKey;
/*     */   }
/*     */ 
/*     */   public void setConfigKey(String aValue)
/*     */   {
/*  92 */     this.configKey = aValue;
/*     */   }
/*     */ 
/*     */   public String getConfigName()
/*     */   {
/* 100 */     return this.configName;
/*     */   }
/*     */ 
/*     */   public void setConfigName(String aValue)
/*     */   {
/* 108 */     this.configName = aValue;
/*     */   }
/*     */ 
/*     */   public String getConfigDesc()
/*     */   {
/* 116 */     return this.configDesc;
/*     */   }
/*     */ 
/*     */   public void setConfigDesc(String aValue)
/*     */   {
/* 123 */     this.configDesc = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/* 131 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String aValue)
/*     */   {
/* 139 */     this.typeName = aValue;
/*     */   }
/*     */ 
/*     */   public Short getDataType()
/*     */   {
/* 153 */     return this.dataType;
/*     */   }
/*     */ 
/*     */   public void setDataType(Short aValue)
/*     */   {
/* 161 */     this.dataType = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 168 */     if (!(object instanceof SysConfig)) {
/* 169 */       return false;
/*     */     }
/* 171 */     SysConfig rhs = (SysConfig)object;
/* 172 */     return new EqualsBuilder()
/* 173 */       .append(this.configId, rhs.configId)
/* 174 */       .append(this.configKey, rhs.configKey)
/* 175 */       .append(this.configName, rhs.configName)
/* 176 */       .append(this.configDesc, rhs.configDesc)
/* 177 */       .append(this.typeName, rhs.typeName)
/* 178 */       .append(this.dataType, rhs.dataType)
/* 179 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 186 */     return new HashCodeBuilder(-82280557, -700257973)
/* 187 */       .append(this.configId)
/* 188 */       .append(this.configKey)
/* 189 */       .append(this.configName)
/* 190 */       .append(this.configDesc)
/* 191 */       .append(this.typeName)
/* 192 */       .append(this.dataType)
/* 193 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 200 */     return new ToStringBuilder(this)
/* 201 */       .append("configId", this.configId)
/* 202 */       .append("configKey", this.configKey)
/* 203 */       .append("configName", this.configName)
/* 204 */       .append("configDesc", this.configDesc)
/* 205 */       .append("typeName", this.typeName)
/* 206 */       .append("dataType", this.dataType)
/* 207 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.SysConfig
 * JD-Core Version:    0.6.0
 */