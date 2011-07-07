/*    */ package com.htsoft.core.jbpm.pv;
/*    */ 
/*    */ public class ParamField
/*    */ {
/*    */   public static final String FIELD_TYPE_DATE = "date";
/*    */   public static final String FIELD_TYPE_DATETIME = "datetime";
/*    */   public static final String FIELD_TYPE_INT = "int";
/*    */   public static final String FIELD_TYPE_LONG = "long";
/*    */   public static final String FIELD_TYPE_DECIMAL = "decimal";
/*    */   public static final String FIELD_TYPE_VARCHAR = "varchar";
/*    */   public static final String FIELD_TYPE_BOOL = "bool";
/*    */   public static final String FIELD_TYPE_TEXT = "text";
/*    */   public static final String FIELD_TYPE_FILE = "file";
/*    */   private String name;
/*    */   private String type;
/*    */   private String label;
/*    */   private Integer length;
/* 28 */   private Short isShowed = 1;
/*    */   private String value;
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 34 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value)
/*    */   {
/* 39 */     if ("bool".equals(this.type)) {
/* 40 */       if (value != null)
/* 41 */         this.value = "1";
/*    */       else
/* 43 */         this.value = "0";
/*    */     }
/*    */     else
/* 46 */       this.value = value;
/*    */   }
/*    */ 
/*    */   public ParamField(String name, String type, String label, Integer length, Short isShowed)
/*    */   {
/* 51 */     this.name = name;
/* 52 */     this.type = type;
/* 53 */     this.label = label;
/* 54 */     this.length = length;
/* 55 */     this.isShowed = isShowed;
/*    */   }
/*    */ 
/*    */   public ParamField()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 63 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 66 */     this.name = name;
/*    */   }
/*    */   public String getType() {
/* 69 */     return this.type;
/*    */   }
/*    */   public void setType(String type) {
/* 72 */     this.type = type;
/*    */   }
/*    */   public String getLabel() {
/* 75 */     return this.label;
/*    */   }
/*    */   public void setLabel(String label) {
/* 78 */     this.label = label;
/*    */   }
/*    */   public Integer getLength() {
/* 81 */     return this.length;
/*    */   }
/*    */   public void setLength(Integer length) {
/* 84 */     this.length = length;
/*    */   }
/*    */ 
/*    */   public Short getIsShowed() {
/* 88 */     return this.isShowed;
/*    */   }
/*    */ 
/*    */   public void setIsShowed(Short isShowed) {
/* 92 */     this.isShowed = isShowed;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.pv.ParamField
 * JD-Core Version:    0.6.0
 */