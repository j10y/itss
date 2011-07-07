/*    */ package com.htsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.core.util.AppUtil;
/*    */ import com.htsoft.oa.dao.system.FileAttachDao;
/*    */ import com.htsoft.oa.model.system.FileAttach;
/*    */ import com.htsoft.oa.service.system.FileAttachService;
/*    */ import java.io.File;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class FileAttachServiceImpl extends BaseServiceImpl<FileAttach>
/*    */   implements FileAttachService
/*    */ {
/*    */   private FileAttachDao dao;
/*    */ 
/*    */   public FileAttachServiceImpl(FileAttachDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public void removeByPath(String filePath)
/*    */   {
/* 26 */     FileAttach fileAttach = this.dao.getByPath(filePath);
/*    */ 
/* 28 */     String fullFilePath = AppUtil.getAppAbsolutePath() + "/attachFiles/" + filePath;
/*    */ 
/* 30 */     this.logger.info("file:" + fullFilePath);
/*    */ 
/* 32 */     File file = new File(fullFilePath);
/*    */ 
/* 34 */     if (file.exists()) {
/* 35 */       file.delete();
/*    */     }
/* 37 */     if (fileAttach != null)
/* 38 */       this.dao.remove(fileAttach);
/*    */   }
/*    */ 
/*    */   public FileAttach getByPath(String filePath)
/*    */   {
/* 44 */     return this.dao.getByPath(filePath);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.system.impl.FileAttachServiceImpl
 * JD-Core Version:    0.6.0
 */