/*    */ package com.htsoft.core.log;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class AnnotationDemo
/*    */ {
/*    */   @Action(description="TEST SAVE")
/*    */   public void save()
/*    */   {
/* 10 */     System.out.println("save method");
/*    */   }
/*    */ 
/*    */   public void get() {
/* 14 */     System.out.println("get method");
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */     throws SecurityException, NoSuchMethodException
/*    */   {
/* 22 */     AnnotationDemo demo = new AnnotationDemo();
/*    */ 
/* 24 */     System.out.println("get Annotation");
/*    */ 
/* 26 */     Method method = demo.getClass().getMethod("save", new Class[0]);
/*    */ 
/* 28 */     Action action = (Action)method.getAnnotation(Action.class);
/*    */ 
/* 30 */     System.out.println("action:" + action);
/*    */ 
/* 32 */     Annotation[] ans = method.getAnnotations();
/*    */ 
/* 34 */     for (Annotation an : ans)
/* 35 */       System.out.println("an:" + ans.getClass().getName());
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.log.AnnotationDemo
 * JD-Core Version:    0.6.0
 */