/*    */ package com.htsoft.test.flow;
/*    */ 
/*    */ import com.htsoft.oa.dao.flow.ProDefinitionDao;
/*    */ import com.htsoft.oa.dao.flow.ProTypeDao;
/*    */ import com.htsoft.oa.model.flow.ProDefinition;
/*    */ import com.htsoft.oa.model.flow.ProType;
/*    */ import com.htsoft.test.BaseTestCase;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ProDefinitionDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProDefinitionDao proDefinitionDao;
/*    */ 
/*    */   @Resource
/*    */   private ProTypeDao proTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 26 */     ProDefinition pro = new ProDefinition();
/*    */ 
/* 28 */     pro.setDefXml("xml");
/* 29 */     pro.setDescription("descriptin");
/* 30 */     pro.setName("vtest");
/* 31 */     pro.setDrawDefXml("drawXml");
/* 32 */     pro.setCreatetime(new Date());
/*    */ 
/* 34 */     ProType proType = (ProType)this.proTypeDao.get(new Long(1L));
/*    */ 
/* 36 */     pro.setProType(proType);
/* 37 */     pro.setDeployId("1");
/* 38 */     this.proDefinitionDao.save(pro);
/*    */   }
/*    */ 
/*    */   public void get()
/*    */   {
/* 59 */     List list = this.proDefinitionDao.getAll();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.flow.ProDefinitionDaoTestCase
 * JD-Core Version:    0.6.0
 */