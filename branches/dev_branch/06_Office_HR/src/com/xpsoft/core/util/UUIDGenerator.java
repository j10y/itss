/*    */ package com.xpsoft.core.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UUIDGenerator
/*    */ {
/*    */   public static String getUUID()
/*    */   {
/* 17 */     String s = UUID.randomUUID().toString();
/*    */ 
/* 19 */     return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
/*    */   }
/*    */ 
/*    */   public static String[] getUUID(int number)
/*    */   {
/* 27 */     if (number < 1) {
/* 28 */       return null;
/*    */     }
/* 30 */     String[] ss = new String[number];
/* 31 */     for (int i = 0; i < number; i++) {
/* 32 */       ss[i] = getUUID();
/*    */     }
/* 34 */     return ss;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 43 */     String[] vars = UUID.randomUUID().toString().split("-");
System.out.println("ok:" + UUID.randomUUID().toString());
/* 44 */     for (int i = 0; i < vars.length; i++) {
/* 45 */       System.out.println("ok:" + vars[i]);
/* 46 */       long var = Long.valueOf(vars[i], 16).longValue();
/* 47 */       System.out.println("ok:===" + var);
/*    */     }
/*    */   }
/*    */ }