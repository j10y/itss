/*    */ package com.htsoft.test.system;
/*    */ 
/*    */ import com.htsoft.core.util.BeanUtil;
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class BeanUtilTest
/*    */ {
/*    */   private String name;
/*    */   private Date createtime;
/*    */   private int age;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 20 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 24 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public Date getCreatetime()
/*    */   {
/* 29 */     return this.createtime;
/*    */   }
/*    */ 
/*    */   public void setCreatetime(Date createtime)
/*    */   {
/* 34 */     this.createtime = createtime;
/*    */   }
/*    */ 
/*    */   public int getAge()
/*    */   {
/* 39 */     return this.age;
/*    */   }
/*    */ 
/*    */   public void setAge(int age) {
/* 43 */     this.age = age;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 47 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 48 */     String date = this.createtime == null ? "null" : sdf.format(this.createtime);
/* 49 */     return "name:" + this.name + " createtime:" + date + " age:" + this.age;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
/* 53 */     BeanUtilTest test1 = new BeanUtilTest();
/*    */ 
/* 55 */     test1.setAge(10);
/* 56 */     test1.setCreatetime(new Date());
/* 57 */     test1.setName("john");
/*    */ 
/* 59 */     BeanUtilTest test2 = new BeanUtilTest();
/* 60 */     test2.setName("LingMing");
/* 61 */     test2.setAge(11);
/*    */ 
/* 66 */     BeanUtil.copyNotNullProperties(test1, test2);
/*    */ 
/* 68 */     System.out.println("test1:" + test1.toString());
/* 69 */     System.out.println("test2:" + test2.toString());
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.system.BeanUtilTest
 * JD-Core Version:    0.6.0
 */