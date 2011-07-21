/*    */ package com.xpsoft.core.util;
/*    */ 
/*    */ import flexjson.DateTransformer;
/*    */ import flexjson.JSONSerializer;
/*    */ 
/*    */ public class JsonUtil
/*    */ {
/*    */   public static JSONSerializer getJSONSerializer(String[] dateFields)
/*    */   {
/* 17 */     JSONSerializer serializer = new JSONSerializer();
/* 18 */     serializer.exclude(new String[] { "class" });
/* 19 */     serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), dateFields);
/* 20 */     return serializer;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.util.JsonUtil
 * JD-Core Version:    0.6.0
 */