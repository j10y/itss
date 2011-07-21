/*     */ package com.xpsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.archive.Archives;
/*     */ import com.xpsoft.oa.model.archive.ArchivesHandle;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.archive.ArchivesHandleService;
/*     */ import com.xpsoft.oa.service.archive.ArchivesService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ArchivesHandleAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ArchivesHandleService archivesHandleService;
/*     */   private ArchivesHandle archivesHandle;
/*     */ 
/*     */   @Resource
/*     */   private ArchivesService archivesService;
/*     */   private Long handleId;
/*     */   private String handleOpinion;
/*     */   private Short isPass;
/*     */   private Long archiveId;
/*     */ 
/*     */   public Long getArchiveId()
/*     */   {
/*  47 */     return this.archiveId;
/*     */   }
/*     */ 
/*     */   public void setArchiveId(Long archiveId) {
/*  51 */     this.archiveId = archiveId;
/*     */   }
/*     */ 
/*     */   public String getHandleOpinion() {
/*  55 */     return this.handleOpinion;
/*     */   }
/*     */ 
/*     */   public void setHandleOpinion(String handleOpinion) {
/*  59 */     this.handleOpinion = handleOpinion;
/*     */   }
/*     */ 
/*     */   public Short getIsPass() {
/*  63 */     return this.isPass;
/*     */   }
/*     */ 
/*     */   public void setIsPass(Short isPass) {
/*  67 */     this.isPass = isPass;
/*     */   }
/*     */ 
/*     */   public Long getHandleId() {
/*  71 */     return this.handleId;
/*     */   }
/*     */ 
/*     */   public void setHandleId(Long handleId) {
/*  75 */     this.handleId = handleId;
/*     */   }
/*     */ 
/*     */   public ArchivesHandle getArchivesHandle() {
/*  79 */     return this.archivesHandle;
/*     */   }
/*     */ 
/*     */   public void setArchivesHandle(ArchivesHandle archivesHandle) {
/*  83 */     this.archivesHandle = archivesHandle;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  91 */     QueryFilter filter = new QueryFilter(getRequest());
/*  92 */     filter.addFilter("Q_userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/*     */ 
/*  95 */     List list = this.archivesHandleService.getAll(filter);
/*     */ 
/*  97 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  98 */       .append(filter.getPagingBean().getTotalItems()).append(
/*  99 */       ",result:");
/* 100 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "createtime", "archives.issueDate", "archives.createtime" });
/* 101 */     buff.append(serializer.exclude(new String[] { "archives.archRecType", "class" }).serialize(list));
/* 102 */     buff.append("}");
/*     */ 
/* 104 */     this.jsonString = buff.toString();
/*     */ 
/* 106 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 116 */     String[] ids = getRequest().getParameterValues("ids");
/* 117 */     if (ids != null) {
/* 118 */       for (String id : ids) {
/* 119 */         this.archivesHandleService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/* 123 */     this.jsonString = "{success:true}";
/*     */ 
/* 125 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 134 */     ArchivesHandle archivesHandle = (ArchivesHandle)this.archivesHandleService.get(this.handleId);
/*     */ 
/* 136 */     Gson gson = new Gson();
/*     */ 
/* 138 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 139 */     sb.append(gson.toJson(archivesHandle));
/* 140 */     sb.append("}");
/* 141 */     setJsonString(sb.toString());
/*     */ 
/* 143 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 150 */     ArchivesHandle arh = new ArchivesHandle();
/* 151 */     AppUser user = ContextUtil.getCurrentUser();
/* 152 */     Archives archives = (Archives)this.archivesService.get(this.archiveId);
/* 153 */     arh.setArchives(archives);
/* 154 */     arh.setCreatetime(new Date());
/* 155 */     arh.setFillTime(new Date());
/* 156 */     arh.setHandleOpinion(this.handleOpinion);
/* 157 */     arh.setIsPass(this.isPass);
/* 158 */     arh.setUserId(user.getUserId());
/* 159 */     arh.setUserFullname(user.getFullname());
/* 160 */     this.archivesHandleService.save(arh);
/* 161 */     String signIds = getRequest().getParameter("handlerUserIds");
/* 162 */     if (StringUtils.isNotEmpty(signIds)) {
/* 163 */       String[] signId = signIds.split(",");
/* 164 */       int size = signId.length;
/* 165 */       if (size < 2) {
/* 166 */         archives.setStatus(Archives.STATUS_LEADERREAD);
/*     */       } else {
/* 168 */         int handlerSize = this.archivesHandleService.countHandler(archives.getArchivesId());
/* 169 */         if (size == handlerSize)
/* 170 */           archives.setStatus(Archives.STATUS_LEADERREAD);
/*     */         else {
/* 172 */           archives.setStatus(Archives.STATUS_HANDLEING);
/*     */         }
/*     */       }
/* 175 */       this.archivesService.save(archives);
/*     */     }
/* 177 */     setJsonString("{success:true}");
/* 178 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.archive.ArchivesHandleAction
 * JD-Core Version:    0.6.0
 */