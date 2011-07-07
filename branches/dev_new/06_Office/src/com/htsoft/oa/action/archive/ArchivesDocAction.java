/*     */ package com.htsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.archive.ArchivesDoc;
/*     */ import com.htsoft.oa.model.archive.DocHistory;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.service.archive.ArchivesDocService;
/*     */ import com.htsoft.oa.service.archive.DocHistoryService;
/*     */ import com.htsoft.oa.service.system.FileAttachService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ArchivesDocAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ArchivesDocService archivesDocService;
/*     */ 
/*     */   @Resource
/*     */   private DocHistoryService docHistoryService;
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */   private ArchivesDoc archivesDoc;
/*     */   private Long docId;
/*     */ 
/*     */   public Long getDocId()
/*     */   {
/*  47 */     return this.docId;
/*     */   }
/*     */ 
/*     */   public void setDocId(Long docId) {
/*  51 */     this.docId = docId;
/*     */   }
/*     */ 
/*     */   public ArchivesDoc getArchivesDoc() {
/*  55 */     return this.archivesDoc;
/*     */   }
/*     */ 
/*     */   public void setArchivesDoc(ArchivesDoc archivesDoc) {
/*  59 */     this.archivesDoc = archivesDoc;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  66 */     QueryFilter filter = new QueryFilter(getRequest());
/*  67 */     String archivesId = getRequest().getParameter("archivesId");
/*  68 */     if ((archivesId != null) && (!"".equals(archivesId))) {
/*  69 */       filter.addFilter("Q_archives.archivesId_L_EQ", archivesId);
/*     */     }
/*  71 */     List<ArchivesDoc> list = this.archivesDocService.getAll(filter);
/*     */ 
/*  73 */     Type type = new TypeToken<List<ArchivesDoc>>() {  }
/*  73 */     .getType();
/*  74 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  75 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  77 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*  78 */     buff.append(gson.toJson(list, type));
/*  79 */     buff.append("}");
/*     */ 
/*  81 */     this.jsonString = buff.toString();
/*     */ 
/*  83 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  91 */     String[] ids = getRequest().getParameterValues("ids");
/*  92 */     if (ids != null) {
/*  93 */       for (String id : ids) {
/*  94 */         this.archivesDocService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  98 */     this.jsonString = "{success:true}";
/*     */ 
/* 100 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 108 */     ArchivesDoc archivesDoc = (ArchivesDoc)this.archivesDocService.get(this.docId);
/*     */ 
/* 110 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*     */ 
/* 112 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 113 */     sb.append(gson.toJson(archivesDoc));
/* 114 */     sb.append("}");
/* 115 */     setJsonString(sb.toString());
/*     */ 
/* 117 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 123 */     AppUser curUser = ContextUtil.getCurrentUser();
/* 124 */     if (this.archivesDoc.getDocId() == null)
/*     */     {
/* 126 */       this.archivesDoc.initUsers(curUser);
/* 127 */       this.archivesDoc.setDocStatus(Short.valueOf(ArchivesDoc.STATUS_MODIFY));
/* 128 */       this.archivesDoc.setUpdatetime(new Date());
/* 129 */       this.archivesDoc.setCreatetime(new Date());
/* 130 */       this.archivesDoc.setCurVersion(Integer.valueOf(ArchivesDoc.ORI_VERSION));
/* 131 */       this.archivesDoc.setFileAttach(this.fileAttachService.getByPath(this.archivesDoc.getDocPath()));
/* 132 */       this.archivesDocService.save(this.archivesDoc);
/*     */     }
/*     */     else {
/* 135 */       ArchivesDoc oldVersion = (ArchivesDoc)this.archivesDocService.get(this.archivesDoc.getDocId());
/* 136 */       this.archivesDoc.setCreatetime(oldVersion.getCreatetime());
/* 137 */       this.archivesDoc.setArchives(oldVersion.getArchives());
/* 138 */       this.archivesDoc.setCreatorId(oldVersion.getCreatorId());
/* 139 */       this.archivesDoc.setFileAttach(this.fileAttachService.getByPath(this.archivesDoc.getDocPath()));
/* 140 */       this.archivesDoc.setCreator(oldVersion.getCreator());
/* 141 */       this.archivesDoc.setDocStatus(Short.valueOf(ArchivesDoc.STATUS_MODIFY));
/* 142 */       this.archivesDoc.setUpdatetime(new Date());
/* 143 */       this.archivesDoc.setCurVersion(Integer.valueOf(oldVersion.getCurVersion().intValue() + 1));
/* 144 */       this.archivesDoc.setMender(curUser.getFullname());
/* 145 */       this.archivesDoc.setMenderId(curUser.getUserId());
/* 146 */       this.archivesDoc.setDocHistorys(oldVersion.getDocHistorys());
/* 147 */       this.archivesDoc.setFileAttach(this.fileAttachService.getByPath(this.archivesDoc.getDocPath()));
/* 148 */       this.archivesDocService.merge(this.archivesDoc);
/*     */     }
/*     */ 
/* 152 */     DocHistory docHistory = new DocHistory();
/* 153 */     docHistory.setArchivesDoc(this.archivesDoc);
/* 154 */     docHistory.setFileAttach(this.fileAttachService.getByPath(this.archivesDoc.getDocPath()));
/* 155 */     docHistory.setDocName(this.archivesDoc.getDocName());
/* 156 */     docHistory.setPath(this.archivesDoc.getDocPath());
/* 157 */     docHistory.setVersion(this.archivesDoc.getCurVersion());
/* 158 */     docHistory.setUpdatetime(new Date());
/* 159 */     docHistory.setMender(curUser.getFullname());
/* 160 */     this.docHistoryService.save(docHistory);
/*     */ 
/* 165 */     StringBuffer buff = new StringBuffer("{success:true,data:");
/*     */ 
/* 167 */     JSONSerializer json = new JSONSerializer();
/* 168 */     buff.append(json.exclude(new String[] { "class", "docHistorys" }).serialize(this.archivesDoc));
/*     */ 
/* 170 */     buff.append("}");
/*     */ 
/* 172 */     setJsonString(buff.toString());
/* 173 */     return "success";
/*     */   }
/*     */ 
/*     */   public String copy() {
/* 177 */     String historyId = getRequest().getParameter("historyId");
/* 178 */     DocHistory docHistory = (DocHistory)this.docHistoryService.get(new Long(historyId));
/* 179 */     DocHistory newHistory = new DocHistory();
/*     */ 
/* 181 */     this.archivesDoc = docHistory.getArchivesDoc();
/*     */ 
/* 183 */     newHistory.setDocName(docHistory.getDocName());
/* 184 */     newHistory.setFileAttach(docHistory.getFileAttach());
/*     */ 
/* 186 */     newHistory.setMender(ContextUtil.getCurrentUser().getFullname());
/* 187 */     newHistory.setPath(docHistory.getPath());
/* 188 */     newHistory.setUpdatetime(new Date());
/* 189 */     newHistory.setVersion(Integer.valueOf(this.archivesDoc.getCurVersion().intValue() + 1));
/* 190 */     newHistory.setArchivesDoc(this.archivesDoc);
/* 191 */     this.docHistoryService.save(newHistory);
/*     */ 
/* 193 */     this.archivesDoc.setCurVersion(newHistory.getVersion());
/* 194 */     this.archivesDoc.setDocPath(newHistory.getPath());
/* 195 */     this.archivesDoc.setFileAttach(newHistory.getFileAttach());
/*     */ 
/* 197 */     this.archivesDocService.save(this.archivesDoc);
/*     */ 
/* 199 */     StringBuffer buff = new StringBuffer("{success:true,data:");
/* 200 */     JSONSerializer json = new JSONSerializer();
/* 201 */     buff.append(json.exclude(new String[] { "class", "docHistorys" }).serialize(this.archivesDoc));
/* 202 */     buff.append("}");
/*     */ 
/* 204 */     setJsonString(buff.toString());
/* 205 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.archive.ArchivesDocAction
 * JD-Core Version:    0.6.0
 */