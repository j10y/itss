/*    */ package com.htsoft.test.admin;
/*    */ 
/*    */ import com.htsoft.oa.dao.admin.FixedAssetsDao;
/*    */ import com.htsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.GregorianCalendar;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ 
/*    */ public class FixedAssetsDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private FixedAssetsDao fixedAssetsDao;
/*    */ 
/*    */   @Test
/*    */   public void text()
/*    */   {
/* 37 */     GregorianCalendar calendar2 = new GregorianCalendar(2009, 2, 5);
/*    */ 
/* 40 */     System.out.println(getMonth(new Date(), calendar2.getTime()));
/*    */   }
/*    */ 
/*    */   public int getMonth(Date s, Date e)
/*    */   {
/* 51 */     if (s.after(e)) {
/* 52 */       Date t = s;
/* 53 */       s = e;
/* 54 */       e = t;
/*    */     }
/* 56 */     Calendar start = Calendar.getInstance();
/* 57 */     start.setTime(s);
/* 58 */     Calendar end = Calendar.getInstance();
/* 59 */     end.setTime(e);
/* 60 */     Calendar temp = Calendar.getInstance();
/* 61 */     temp.setTime(e);
/* 62 */     temp.add(5, 1);
/*    */ 
/* 64 */     int y = end.get(1) - start.get(1);
/* 65 */     int m = end.get(2) - start.get(2);
/*    */ 
/* 67 */     if ((start.get(5) == 1) && (temp.get(5) == 1))
/* 68 */       return y * 12 + m + 1;
/* 69 */     if ((start.get(5) != 1) && 
/* 70 */       (temp.get(5) == 1))
/* 71 */       return y * 12 + m;
/* 72 */     if ((start.get(5) == 1) && 
/* 73 */       (temp.get(5) != 1)) {
/* 74 */       return y * 12 + m;
/*    */     }
/* 76 */     return y * 12 + m - 1 < 0 ? 0 : y * 12 + m - 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.admin.FixedAssetsDaoTestCase
 * JD-Core Version:    0.6.0
 */