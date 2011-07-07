/*    */ package com.htsoft.test;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class BastTest
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*  8 */     String s = "PF.CREATORID=? GROUP BY PR.RUNID";
/*  9 */     Pattern p = Pattern.compile(" GROUP BY [\\w|.]+");
/*    */ 
/* 11 */     Matcher m = p.matcher(s);
/* 12 */     boolean is = m.find();
/* 13 */     System.out.println("is:" + is);
/*    */ 
/* 16 */     int start = m.start();
/* 17 */     int end = m.end();
/*    */ 
/* 19 */     System.out.println("start:" + start + "end:" + end + s.substring(start, end));
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.BastTest
 * JD-Core Version:    0.6.0
 */