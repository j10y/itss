/*    */ package com.htsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.archive.ArchTemplateDao;
/*    */ import com.htsoft.oa.model.archive.ArchTemplate;
/*    */ import com.htsoft.oa.service.archive.ArchTemplateService;
/*    */ import com.htsoft.oa.service.system.FileAttachService;
/*    */ import javax.annotation.Resource;
/*    */ 
/*    */ public class ArchTemplateServiceImpl extends BaseServiceImpl<ArchTemplate>
/*    */   implements ArchTemplateService
/*    */ {
/*    */   private ArchTemplateDao dao;
/*    */ 
/*    */   @Resource
/*    */   FileAttachService fileAttachService;
/*    */ 
/*    */   public ArchTemplateServiceImpl(ArchTemplateDao dao)
/*    */   {
/* 21 */     super(dao);
/* 22 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public void remove(Long id)
/*    */   {
/* 29 */     ArchTemplate template = (ArchTemplate)this.dao.get(id);
/* 30 */     remove(template);
/* 31 */     this.fileAttachService.removeByPath(template.getTempPath());
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.archive.impl.ArchTemplateServiceImpl
 * JD-Core Version:    0.6.0
 */