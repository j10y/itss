/*    */ package com.htsoft.oa.action.document;
/*    */ 
/*    */ import com.htsoft.core.web.action.BaseAction;
/*    */ import com.htsoft.oa.model.system.FileAttach;
/*    */ import com.htsoft.oa.service.system.FileAttachService;
/*    */ import javax.annotation.Resource;
/*    */ 
/*    */ public class FileDetailAction extends BaseAction
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private FileAttachService fileAttachService;
/*    */   private FileAttach fileAttach;
/*    */   private Long fileId;
/*    */ 
/*    */   public Long getFileId()
/*    */   {
/* 20 */     return this.fileId;
/*    */   }
/*    */ 
/*    */   public void setFileId(Long fileId) {
/* 24 */     this.fileId = fileId;
/*    */   }
/*    */ 
/*    */   public FileAttach getFileAttach() {
/* 28 */     return this.fileAttach;
/*    */   }
/*    */ 
/*    */   public void setFileAttach(FileAttach fileAttach) {
/* 32 */     this.fileAttach = fileAttach;
/*    */   }
/*    */ 
/*    */   public String execute() throws Exception
/*    */   {
/* 37 */     this.fileAttach = ((FileAttach)this.fileAttachService.get(this.fileId));
/* 38 */     return "success";
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.document.FileDetailAction
 * JD-Core Version:    0.6.0
 */