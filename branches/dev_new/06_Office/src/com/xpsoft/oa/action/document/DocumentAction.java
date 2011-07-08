/*     */ package com.xpsoft.oa.action.document;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.document.DocFolder;
/*     */ import com.xpsoft.oa.model.document.Document;
/*     */ import com.xpsoft.oa.model.system.AppRole;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.model.system.Department;
/*     */ import com.xpsoft.oa.model.system.FileAttach;
/*     */ import com.xpsoft.oa.service.document.DocFolderService;
/*     */ import com.xpsoft.oa.service.document.DocPrivilegeService;
/*     */ import com.xpsoft.oa.service.document.DocumentService;
/*     */ import com.xpsoft.oa.service.system.FileAttachService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class DocumentAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DocumentService documentService;
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */ 
/*     */   @Resource
/*     */   private DocFolderService docFolderService;
/*     */ 
/*     */   @Resource
/*     */   private DocPrivilegeService docPrivilegeService;
/*     */   private AppUser appUser;
/*     */   private Document document;
/*     */   private Date from;
/*     */   private Date to;
/*     */   private Long docId;
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  62 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser appUser) {
/*  66 */     this.appUser = appUser;
/*     */   }
/*     */ 
/*     */   public Date getFrom() {
/*  70 */     return this.from;
/*     */   }
/*     */ 
/*     */   public void setFrom(Date from) {
/*  74 */     this.from = from;
/*     */   }
/*     */ 
/*     */   public Date getTo() {
/*  78 */     return this.to;
/*     */   }
/*     */ 
/*     */   public void setTo(Date to) {
/*  82 */     this.to = to;
/*     */   }
/*     */ 
/*     */   public Long getDocId()
/*     */   {
/*  89 */     return this.docId;
/*     */   }
/*     */ 
/*     */   public void setDocId(Long docId) {
/*  93 */     this.docId = docId;
/*     */   }
/*     */ 
/*     */   public Document getDocument() {
/*  97 */     return this.document;
/*     */   }
/*     */ 
/*     */   public void setDocument(Document document) {
/* 101 */     this.document = document;
/*     */   }
/*     */ 
/*     */   public String share()
/*     */   {
/* 108 */     HttpServletRequest request = getRequest();
/* 109 */     String userIds = request.getParameter("sharedUserIds");
/* 110 */     String depIds = request.getParameter("sharedDepIds");
/* 111 */     String roleIds = request.getParameter("sharedRoleIds");
/* 112 */     String docId = request.getParameter("docId");
/* 113 */     String userNames = request.getParameter("sharedUserNames");
/* 114 */     String depNames = request.getParameter("sharedDepNames");
/* 115 */     String roleNames = request.getParameter("sharedRoleNames");
/* 116 */     if ((StringUtils.isNotEmpty(userIds)) || (StringUtils.isNotEmpty(depIds)) || (StringUtils.isNotEmpty(roleIds))) {
/* 117 */       Document doc = (Document)this.documentService.get(new Long(docId));
/* 118 */       doc.setSharedUserIds(userIds);
/* 119 */       doc.setSharedRoleIds(roleIds);
/* 120 */       doc.setSharedDepIds(depIds);
/* 121 */       doc.setSharedUserNames(userNames);
/* 122 */       doc.setSharedDepNames(depNames);
/* 123 */       doc.setSharedRoleNames(roleNames);
/* 124 */       doc.setIsShared(Document.SHARED);
/* 125 */       this.documentService.save(doc);
/*     */     }
/*     */ 
/* 128 */     return "success";
/*     */   }
/*     */ 
/*     */   public String shareList()
/*     */   {
/* 136 */     PagingBean pb = getInitPagingBean();
/* 137 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 138 */     Set appRoles = appUser.getRoles();
/* 139 */     Long depId = null;
/* 140 */     if (!appUser.getUserId().equals(AppUser.SUPER_USER)) {
/* 141 */       Department dep = appUser.getDepartment();
/* 142 */       depId = dep.getDepId();
/*     */     }
/* 144 */     Iterator<AppRole> it = appRoles.iterator();
/* 145 */     ArrayList arrayList = new ArrayList();
/* 146 */     while (it.hasNext()) {
/* 147 */       arrayList.add(((AppRole)it.next()).getRoleId());
/*     */     }
/* 149 */     List<Document> list = this.documentService.findByIsShared(this.document, this.from, this.to, appUser.getUserId(), arrayList, depId, pb);
/* 150 */     Type type = new TypeToken<List<Document>>() {  }.getType();
/* 151 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 152 */       .append(pb.getTotalItems()).append(",result:");
/* 153 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 154 */     buff.append(gson.toJson(list, type));
/* 155 */     buff.append("}");
/* 156 */     setJsonString(buff.toString());
/* 157 */     return "success";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/* 163 */     QueryFilter filter = new QueryFilter(getRequest());
/* 164 */     filter.addFilter("Q_docFolder.appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/* 165 */     String folderId = getRequest().getParameter("folderId");
/* 166 */     String path = null;
/* 167 */     if ((StringUtils.isNotEmpty(folderId)) && (!"0".equals(folderId))) {
/* 168 */       path = ((DocFolder)this.docFolderService.get(new Long(folderId))).getPath();
/*     */     }
/* 170 */     if (path != null) {
/* 171 */       filter.addFilter("Q_docFolder.path_S_LK", path + "%");
/*     */     }
/* 173 */     List<Document> list = this.documentService.getAll(filter);
/* 174 */     Type type = new TypeToken<List<Document>>() {}.getType();
/* 175 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 176 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/* 177 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 178 */     buff.append(gson.toJson(list, type));
/* 179 */     buff.append("}");
/* 180 */     this.jsonString = buff.toString();
/* 181 */     return "success";
/*     */   }
/*     */ 
/*     */   public String publicList()
/*     */   {
/* 189 */     PagingBean pb = getInitPagingBean();
/* 190 */     String strFolderId = getRequest().getParameter("folderId");
/* 191 */     String path = null;
/* 192 */     if (StringUtils.isNotEmpty(strFolderId)) {
/* 193 */       Long folderId = new Long(strFolderId);
/* 194 */       if (folderId.longValue() > 0L) {
/* 195 */         path = ((DocFolder)this.docFolderService.get(new Long(strFolderId))).getPath();
/*     */       }
/*     */     }
/* 198 */     List<Document> list = this.documentService.findByPublic(path, this.document, this.from, this.to, ContextUtil.getCurrentUser(), pb);
/* 199 */     Type type = new TypeToken<List<Document>>() {  }
/* 199 */     .getType();
/* 200 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 201 */       .append(pb.getTotalItems()).append(",result:");
/* 202 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 203 */     buff.append(gson.toJson(list, type));
/* 204 */     buff.append("}");
/* 205 */     this.jsonString = buff.toString();
/* 206 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 215 */     String[] ids = getRequest().getParameterValues("ids");
/* 216 */     if (ids != null) {
/* 217 */       for (String id : ids)
/*     */       {
/* 219 */         this.documentService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/* 223 */     this.jsonString = "{success:true}";
/*     */ 
/* 225 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 233 */     Document document = (Document)this.documentService.get(this.docId);
/* 234 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*     */ 
/* 236 */     StringBuffer sb = new StringBuffer("{success:true,data:[");
/* 237 */     sb.append(gson.toJson(document));
/* 238 */     sb.append("]}");
/* 239 */     setJsonString(sb.toString());
/* 240 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 247 */     String fileIds = getRequest().getParameter("fileIds");
/* 248 */     String folderId = getRequest().getParameter("folderId");
/* 249 */     this.document.setSharedDepIds(this.document.getSharedDepIds());
/* 250 */     this.document.setSharedRoleIds(this.document.getSharedRoleIds());
/* 251 */     this.document.setSharedUserIds(this.document.getSharedUserIds());
/* 252 */     if (StringUtils.isNotEmpty(fileIds)) {
/* 253 */       this.document.getAttachFiles().clear();
/* 254 */       String[] fIds = fileIds.split(",");
/* 255 */       for (int i = 0; i < fIds.length; i++) {
/* 256 */         FileAttach fileAttach = (FileAttach)this.fileAttachService.get(new Long(fIds[i]));
/* 257 */         this.document.getAttachFiles().add(fileAttach);
/*     */       }
/*     */     }
/*     */ 
/* 261 */     if ((StringUtils.isNotEmpty(folderId)) && (!"0".equals(folderId))) {
/* 262 */       DocFolder folder = (DocFolder)this.docFolderService.get(new Long(folderId));
/* 263 */       this.document.setDocFolder(folder);
/*     */     }
/* 265 */     if (this.document.getDocId() == null) {
/* 266 */       AppUser appUser = ContextUtil.getCurrentUser();
/* 267 */       this.document.setAppUser(appUser);
/* 268 */       this.document.setFullname(appUser.getFullname());
/* 269 */       this.document.setCreatetime(new Date());
/* 270 */       this.document.setUpdatetime(new Date());
/* 271 */       this.document.setIsShared(Document.NOT_SHARED);
/*     */ 
/* 273 */       if (this.document.getAttachFiles().size() > 0)
/* 274 */         this.document.setHaveAttach(Document.HAVE_ATTACH);
/*     */       else {
/* 276 */         this.document.setHaveAttach(Document.NOT_HAVE_ATTACH);
/*     */       }
/* 278 */       this.documentService.save(this.document);
/*     */     }
/*     */     else {
/* 281 */       Document doc = (Document)this.documentService.get(this.document.getDocId());
/* 282 */       doc.setUpdatetime(new Date());
/* 283 */       doc.setDocName(this.document.getDocName());
/* 284 */       doc.setContent(this.document.getContent());
/* 285 */       doc.setAttachFiles(this.document.getAttachFiles());
/* 286 */       if (this.document.getAttachFiles().size() > 0)
/* 287 */         doc.setHaveAttach(Document.HAVE_ATTACH);
/*     */       else {
/* 289 */         doc.setHaveAttach(Document.NOT_HAVE_ATTACH);
/*     */       }
/* 291 */       this.documentService.save(doc);
/*     */     }
/* 293 */     setJsonString("{success:true}");
/* 294 */     return "success";
/*     */   }
/*     */ 
/*     */   public String detail()
/*     */   {
/* 304 */     String strDocId = getRequest().getParameter("docId");
/* 305 */     if (StringUtils.isNotEmpty(strDocId)) {
/* 306 */       Long docId = Long.valueOf(Long.parseLong(strDocId));
/* 307 */       this.document = ((Document)this.documentService.get(docId));
/*     */     }
/* 309 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String publicDetail() {
/* 313 */     String strDocId = getRequest().getParameter("docId");
/* 314 */     if (StringUtils.isNotEmpty(strDocId)) {
/* 315 */       Long docId = Long.valueOf(Long.parseLong(strDocId));
/* 316 */       this.document = ((Document)this.documentService.get(docId));
/*     */     }
/* 318 */     return "publicDetail";
/*     */   }
/*     */ 
/*     */   public String right()
/*     */   {
/* 326 */     String strDocId = getRequest().getParameter("docId");
/* 327 */     Integer right = Integer.valueOf(0);
/* 328 */     Document document = new Document();
/* 329 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 330 */     if (StringUtils.isNotEmpty(strDocId)) {
/* 331 */       Long docId = new Long(strDocId);
/* 332 */       right = this.docPrivilegeService.getRightsByDocument(appUser, docId);
/* 333 */       document = (Document)this.documentService.get(docId);
/*     */     }
/* 335 */     Integer rightD = Integer.valueOf(0);
/* 336 */     Integer rightM = Integer.valueOf(0);
/* 337 */     String strRight = Integer.toBinaryString(right.intValue());
/* 338 */     char[] cc = strRight.toCharArray();
/* 339 */     if ((cc.length == 2) && 
/* 340 */       (cc[0] == '1')) {
/* 341 */       rightM = Integer.valueOf(1);
/*     */     }
/*     */ 
/* 344 */     if (cc.length == 3) {
/* 345 */       if (cc[0] == '1') {
/* 346 */         rightD = Integer.valueOf(1);
/*     */       }
/* 348 */       if (cc[1] == '1') {
/* 349 */         rightM = Integer.valueOf(1);
/*     */       }
/*     */     }
/*     */ 
/* 353 */     setJsonString("{success:true,rightM:'" + rightM + "',rightD:'" + rightD + "',docName:'" + document.getDocName() + "'}");
/* 354 */     return "success";
/*     */   }
/*     */ 
/*     */   public String search() {
/* 358 */     PagingBean pb = getInitPagingBean();
/* 359 */     String content = getRequest().getParameter("content");
/* 360 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 361 */     List<Document> list = this.documentService.searchDocument(appUser, content, pb);
/* 362 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/* 363 */     Type type = new TypeToken<List<Document>>() {  }
/* 363 */     .getType();
/* 364 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 365 */       .append(pb.getTotalItems()).append(",result:");
/* 366 */     buff.append(gson.toJson(list, type));
/* 367 */     buff.append("}");
/* 368 */     this.jsonString = buff.toString();
/* 369 */     return "success";
/*     */   }
/*     */ 
/*     */   public String display()
/*     */   {
/* 377 */     QueryFilter filter = new QueryFilter(getRequest());
/* 378 */     filter.addFilter("Q_docFolder.appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/* 379 */     List list = this.documentService.getAll(filter);
/* 380 */     getRequest().setAttribute("documentList", list);
/* 381 */     return "display";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.document.DocumentAction
 * JD-Core Version:    0.6.0
 */