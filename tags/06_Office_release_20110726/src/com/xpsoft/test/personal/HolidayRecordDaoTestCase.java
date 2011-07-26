/*    */ package com.xpsoft.test.personal;
/*    */ 
/*    */ import com.xpsoft.oa.dao.personal.HolidayRecordDao;
/*    */ import com.xpsoft.oa.model.personal.HolidayRecord;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class HolidayRecordDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private HolidayRecordDao holidayRecordDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     HolidayRecord holidayRecord = new HolidayRecord();
/*    */ 
/* 22 */     this.holidayRecordDao.save(holidayRecord);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.personal.HolidayRecordDaoTestCase
 * JD-Core Version:    0.6.0
 */