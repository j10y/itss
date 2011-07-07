/*    */ package com.htsoft.core.util;
/*    */ 
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.beanutils.BeanUtilsBean;
/*    */ import org.apache.commons.beanutils.DynaBean;
/*    */ import org.apache.commons.beanutils.DynaClass;
/*    */ import org.apache.commons.beanutils.DynaProperty;
/*    */ import org.apache.commons.beanutils.PropertyUtilsBean;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class BeanUtil
/*    */ {
/* 18 */   private static Log log = LogFactory.getLog(BeanUtil.class);
/*    */ 
/*    */   public static void copyNotNullProperties(Object dest, Object orig)
/*    */     throws IllegalAccessException, InvocationTargetException
/*    */   {
/* 30 */     BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
/*    */ 
/* 32 */     if (dest == null) {
/* 33 */       throw new IllegalArgumentException("No destination bean specified");
/*    */     }
/* 35 */     if (orig == null) {
/* 36 */       throw new IllegalArgumentException("No origin bean specified");
/*    */     }
/* 38 */     if (log.isDebugEnabled()) {
/* 39 */       log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
/*    */     }
/*    */ 
/* 43 */     if ((orig instanceof DynaBean)) {
/* 44 */       DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass()
/* 45 */         .getDynaProperties();
/* 46 */       for (int i = 0; i < origDescriptors.length; i++) {
/* 47 */         String name = origDescriptors[i].getName();
/*    */ 
/* 50 */         if ((!beanUtils.getPropertyUtils().isReadable(orig, name)) || 
/* 51 */           (!beanUtils.getPropertyUtils().isWriteable(dest, name))) continue;
/* 52 */         Object value = ((DynaBean)orig).get(name);
/* 53 */         beanUtils.copyProperty(dest, name, value);
/*    */       }
/*    */     }
/* 56 */     else if ((orig instanceof Map)) {
/* 57 */       Iterator entries = ((Map)orig).entrySet().iterator();
/* 58 */       while (entries.hasNext()) {
/* 59 */         Map.Entry entry = (Map.Entry)entries.next();
/* 60 */         String name = (String)entry.getKey();
/* 61 */         if (beanUtils.getPropertyUtils().isWriteable(dest, name))
/* 62 */           beanUtils.copyProperty(dest, name, entry.getValue());
/*    */       }
/*    */     }
/*    */     else {
/* 66 */       PropertyDescriptor[] origDescriptors = beanUtils.getPropertyUtils()
/* 67 */         .getPropertyDescriptors(orig);
/* 68 */       for (int i = 0; i < origDescriptors.length; i++) {
/* 69 */         String name = origDescriptors[i].getName();
/* 70 */         if ("class".equals(name)) {
/*    */           continue;
/*    */         }
/* 73 */         if ((!beanUtils.getPropertyUtils().isReadable(orig, name)) || 
/* 74 */           (!beanUtils.getPropertyUtils().isWriteable(dest, name))) continue;
/*    */         try {
/* 76 */           Object value = beanUtils.getPropertyUtils().getSimpleProperty(orig, name);
/* 77 */           if (value != null)
/* 78 */             beanUtils.copyProperty(dest, name, value);
/*    */         }
/*    */         catch (NoSuchMethodException localNoSuchMethodException)
/*    */         {
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.util.BeanUtil
 * JD-Core Version:    0.6.0
 */