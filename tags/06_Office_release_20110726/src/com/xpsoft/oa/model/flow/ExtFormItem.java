/*     */ package com.xpsoft.oa.model.flow;
/*     */ 
/*     */ public class ExtFormItem
/*     */ {
/*     */   private String name;
/*     */   private String xtype;
/*     */   private boolean allowBlank;
/*     */   private String fieldLabel;
/*     */   private String format;
/*     */   private int maxLength;
/*     */   private boolean hidden;
/*     */ 
/*     */   public String getIsShowed()
/*     */   {
/*  21 */     if (this.hidden) {
/*  22 */       return "false";
/*     */     }
/*  24 */     return "true";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  29 */     if (("textfield".equals(this.xtype)) || ("textarea".equals(this.xtype)))
/*  30 */       return "varchar";
/*  31 */     if ("datefield".equals(this.xtype))
/*  32 */       return "date";
/*  33 */     if ("radio".equals(this.xtype))
/*  34 */       return "varchar";
/*  35 */     if ("checkbox".equals(this.xtype))
/*  36 */       return "varchar";
/*  37 */     if ("numberfield".equals(this.xtype)) {
/*  38 */       return "decimal";
/*     */     }
/*     */ 
/*  41 */     return "varchar";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  51 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  56 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getXtype()
/*     */   {
/*  61 */     return this.xtype;
/*     */   }
/*     */ 
/*     */   public void setXtype(String xtype)
/*     */   {
/*  66 */     this.xtype = xtype;
/*     */   }
/*     */ 
/*     */   public boolean isAllowBlank()
/*     */   {
/*  71 */     return this.allowBlank;
/*     */   }
/*     */ 
/*     */   public void setAllowBlank(boolean allowBlank)
/*     */   {
/*  76 */     this.allowBlank = allowBlank;
/*     */   }
/*     */ 
/*     */   public String getFieldLabel()
/*     */   {
/*  81 */     return this.fieldLabel;
/*     */   }
/*     */ 
/*     */   public void setFieldLabel(String fieldLabel)
/*     */   {
/*  86 */     this.fieldLabel = fieldLabel;
/*     */   }
/*     */ 
/*     */   public String getFormat()
/*     */   {
/*  91 */     return this.format;
/*     */   }
/*     */ 
/*     */   public void setFormat(String format)
/*     */   {
/*  96 */     this.format = format;
/*     */   }
/*     */ 
/*     */   public int getMaxLength()
/*     */   {
/* 101 */     return this.maxLength;
/*     */   }
/*     */ 
/*     */   public void setMaxLength(int maxLength)
/*     */   {
/* 106 */     this.maxLength = maxLength;
/*     */   }
/*     */ 
/*     */   public boolean isHidden()
/*     */   {
/* 111 */     return this.hidden;
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean hidden)
/*     */   {
/* 116 */     this.hidden = hidden;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.flow.ExtFormItem
 * JD-Core Version:    0.6.0
 */