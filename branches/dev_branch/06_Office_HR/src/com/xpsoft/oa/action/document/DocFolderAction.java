 package com.xpsoft.oa.action.document;
 
 import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.document.DocFolder;
import com.xpsoft.oa.model.document.DocPrivilege;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.document.DocFolderService;
import com.xpsoft.oa.service.document.DocPrivilegeService;
import com.xpsoft.oa.service.document.DocumentService;
 
 public class DocFolderAction extends BaseAction
 {
 
   @Resource
   private DocFolderService docFolderService;
 
   @Resource
   private DocPrivilegeService docPrivilegeService;
 
   @Resource
   private DocumentService documentService;
   private DocFolder docFolder;
   private Long folderId;
/*  45 */   private static Integer ALL_RIGHT = Integer.valueOf(7);
/*  46 */   private static Integer NOT_RIGHT = Integer.valueOf(0);
/*  47 */   private static Long ISPARENT = Long.valueOf(0L);
 
   public Long getFolderId() {
/*  50 */     return this.folderId;
   }
 
   public void setFolderId(Long folderId) {
/*  54 */     this.folderId = folderId;
   }
 
   public DocFolder getDocFolder() {
/*  58 */     return this.docFolder;
   }
 
   public void setDocFolder(DocFolder docFolder) {
/*  62 */     this.docFolder = docFolder;
   }
 
   public String list()
   {
/*  69 */     String method = getRequest().getParameter("method");
/*  70 */     StringBuffer buff = new StringBuffer();
/*  71 */     boolean flag = false;
/*  72 */     if (StringUtils.isNotEmpty(method)) {
/*  73 */       buff.append("[");
/*  74 */       flag = true;
     } else {
/*  76 */       buff.append("[{id:'0',text:'我的文件夹',expanded:true,children:[");
     }
/*  78 */     Long curUserId = ContextUtil.getCurrentUserId();
/*  79 */     List<DocFolder> docList = this.docFolderService.getUserFolderByParentId(curUserId, Long.valueOf(0L));
/*  80 */     for (DocFolder folder : docList) {
/*  81 */       buff.append("{id:'" + folder.getFolderId()).append("',text:'" + folder.getFolderName()).append("',");
/*  82 */       buff.append(findChildsFolder(curUserId, folder.getFolderId()));
     }
/*  84 */     if (!docList.isEmpty()) {
/*  85 */       buff.deleteCharAt(buff.length() - 1);
     }
/*  87 */     if (flag)
/*  88 */       buff.append("]");
     else {
/*  90 */       buff.append("]}]");
     }
/*  92 */     setJsonString(buff.toString());
/*  93 */     this.logger.info("tree json:" + buff.toString());
/*  94 */     return "success";
   }
 
   public String tree()
   {
/* 104 */     StringBuffer buff = new StringBuffer("[{id:'0',text:'公共文件夹',expanded:true,children:[");
/* 105 */     List<DocFolder> docList = this.docFolderService.getPublicFolderByParentId(Long.valueOf(0L));
/* 106 */     for (DocFolder folder : docList) {
/* 107 */       buff.append("{id:'" + folder.getFolderId()).append("',text:'" + folder.getFolderName()).append("',");
/* 108 */       buff.append(findChildsFolder(folder.getFolderId()));
     }
/* 110 */     if (!docList.isEmpty()) {
/* 111 */       buff.deleteCharAt(buff.length() - 1);
     }
/* 113 */     buff.append("]}]");
/* 114 */     setJsonString(buff.toString());
 
/* 116 */     this.logger.info("tree json:" + buff.toString());
/* 117 */     return "success";
   }
 
   public String select()
   {
/* 126 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 127 */     StringBuffer buff = new StringBuffer("[{id:'0',text:'公共文件夹',expanded:true,children:[");
/* 128 */     List<DocFolder> docList = this.docFolderService.getPublicFolderByParentId(Long.valueOf(0L));
/* 129 */     for (DocFolder docFolder : docList) {
/* 130 */       List<Integer> rights = this.docPrivilegeService.getRightsByFolder(appUser, docFolder.getFolderId());
/* 131 */       Integer right = NOT_RIGHT;
/* 132 */       for (Integer in : rights) {
/* 133 */         right = Integer.valueOf(right.intValue() | in.intValue());
       }
/* 135 */       Set roleRight = appUser.getRights();
/* 136 */       if (roleRight.contains("__ALL")) {
/* 137 */         right = ALL_RIGHT;
       }
/* 139 */       if (right == NOT_RIGHT) {
/* 140 */         buff.append("{id:'" + docFolder.getFolderId()).append("',disabled:true,text:'" + docFolder.getFolderName()).append("',");
/* 141 */         buff.append(findChildsFolderByRight(docFolder.getFolderId(), right, false));
       } else {
/* 143 */         buff.append("{id:'" + docFolder.getFolderId()).append("',text:'" + docFolder.getFolderName()).append("',");
/* 144 */         if (right == ALL_RIGHT)
/* 145 */           buff.append(findChildsFolderByRight(docFolder.getFolderId(), right, true));
         else {
/* 147 */           buff.append(findChildsFolderByRight(docFolder.getFolderId(), right, false));
         }
       }
     }
/* 151 */     if (!docList.isEmpty()) {
/* 152 */       buff.deleteCharAt(buff.length() - 1);
     }
/* 154 */     buff.append("]}]");
/* 155 */     setJsonString(buff.toString());
/* 156 */     return "success";
   }
 
   public String share()
   {
/* 165 */     QueryFilter filter = new QueryFilter(getRequest());
/* 166 */     filter.addFilter("Q_isShared_SN_EQ", "1");
/* 167 */     List<DocFolder> list = this.docFolderService.getAll(filter);
/* 168 */     Type type = new TypeToken<List<DocFolder>>() {  }
/* 168 */     .getType();
/* 169 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 170 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/* 171 */     Gson gson = new Gson();
/* 172 */     buff.append(gson.toJson(list, type));
/* 173 */     buff.append("}");
/* 174 */     this.jsonString = buff.toString();
/* 175 */     return "success";
   }
 
   public String findChildsFolder(Long userId, Long parentId)
   {
/* 185 */     StringBuffer sb = new StringBuffer();
/* 186 */     List<DocFolder> list = this.docFolderService.getUserFolderByParentId(userId, parentId);
/* 187 */     if (list.size() == 0) {
/* 188 */       sb.append("leaf:true,expanded:true},");
/* 189 */       return sb.toString();
     }
/* 191 */     sb.append("children:[");
/* 192 */     for (DocFolder folder : list) {
/* 193 */       sb.append("{id:'" + folder.getFolderId() + "',text:'" + folder.getFolderName() + "',");
/* 194 */       sb.append(findChildsFolder(userId, folder.getFolderId()));
     }
/* 196 */     sb.deleteCharAt(sb.length() - 1);
/* 197 */     sb.append("]},");
/* 198 */     return sb.toString();
   }
 
   public String findChildsFolder(Long parentId)
   {
/* 211 */     StringBuffer sb = new StringBuffer();
/* 212 */     List<DocFolder> list = this.docFolderService.getPublicFolderByParentId(parentId);
/* 213 */     if (list.size() == 0) {
/* 214 */       sb.append("leaf:true,expanded:true},");
/* 215 */       return sb.toString();
     }
/* 217 */     sb.append("children:[");
/* 218 */     for (DocFolder folder : list) {
/* 219 */       sb.append("{id:'" + folder.getFolderId() + "',text:'" + folder.getFolderName() + "',");
/* 220 */       sb.append(findChildsFolder(folder.getFolderId()));
     }
/* 222 */     sb.deleteCharAt(sb.length() - 1);
/* 223 */     sb.append("]},");
/* 224 */     return sb.toString();
   }
 
   public String findChildsFolderByRight(Long parentId, Integer right, boolean isAllRight)
   {
/* 237 */     StringBuffer sb = new StringBuffer();
/* 238 */     List<DocFolder> list = this.docFolderService.getPublicFolderByParentId(parentId);
/* 239 */     if (list.size() == 0) {
/* 240 */       sb.append("leaf:true,expanded:true},");
/* 241 */       return sb.toString();
     }
/* 243 */     sb.append("children:[");
/* 244 */     for (DocFolder folder : list) {
/* 245 */       Integer in = right;
/* 246 */       if (isAllRight) {
/* 247 */         in = ALL_RIGHT;
       }
/* 249 */       else if (in != NOT_RIGHT) {
/* 250 */         in = NOT_RIGHT;
/* 251 */         AppUser appUser = ContextUtil.getCurrentUser();
/* 252 */         List<Integer> rights = this.docPrivilegeService.getRightsByFolder(appUser, folder.getFolderId());
/* 253 */         for (Integer inte : rights) {
/* 254 */           in = Integer.valueOf(in.intValue() | inte.intValue());
         }
       }
 
/* 258 */       if (in == NOT_RIGHT) {
/* 259 */         sb.append("{id:'" + folder.getFolderId() + "',disabled:true,text:'" + folder.getFolderName() + "',");
/* 260 */         sb.append(findChildsFolderByRight(folder.getFolderId(), in, isAllRight));
       } else {
/* 262 */         sb.append("{id:'" + folder.getFolderId() + "',text:'" + folder.getFolderName() + "',");
/* 263 */         sb.append(findChildsFolderByRight(folder.getFolderId(), in, isAllRight));
       }
     }
/* 266 */     sb.deleteCharAt(sb.length() - 1);
/* 267 */     sb.append("]},");
/* 268 */     return sb.toString();
   }
 
   public String multiDel()
   {
/* 279 */     String[] ids = getRequest().getParameterValues("ids");
/* 280 */     if (ids != null) {
/* 281 */       for (String id : ids) {
/* 282 */         this.docFolderService.remove(new Long(id));
       }
     }
 
/* 286 */     this.jsonString = "{success:true}";
 
/* 288 */     return "success";
   }
 
   public String remove()
   {
/* 296 */     String folderId = getRequest().getParameter("folderId");
/* 297 */     if (StringUtils.isNotEmpty(folderId)) {
/* 298 */       DocFolder tmpFolder = (DocFolder)this.docFolderService.get(new Long(folderId));
/* 299 */       List<DocFolder> docFolderList = this.docFolderService.getFolderLikePath(tmpFolder.getPath());
 
/* 301 */       for (DocFolder folder : docFolderList) {
/* 302 */         List list = this.documentService.findByFolder(folder.getPath());
/* 303 */         if (list.size() > 0) {
/* 304 */           this.jsonString = "{success:false,message:'该目录下还有文档，请把文件删除后删除该目录'}";
/* 305 */           return "success";
         }
/* 307 */         QueryFilter filter = new QueryFilter(getRequest());
/* 308 */         filter.addFilter("Q_docFolder.folderId_L_EQ", folder.getFolderId().toString());
/* 309 */         List<DocPrivilege> priList = this.docPrivilegeService.getAll(filter);
/* 310 */         for (DocPrivilege dp : priList) {
/* 311 */           this.docPrivilegeService.remove(dp);
         }
/* 313 */         this.docFolderService.remove(folder.getFolderId());
       }
     }
 
/* 317 */     this.jsonString = "{success:true}";
/* 318 */     return "success";
   }
 
   public String get()
   {
/* 326 */     DocFolder docFolder = (DocFolder)this.docFolderService.get(this.folderId);
 
/* 328 */     Gson gson = new Gson();
 
/* 330 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 331 */     sb.append(gson.toJson(docFolder));
/* 332 */     sb.append("}");
/* 333 */     setJsonString(sb.toString());
 
/* 335 */     return "success";
   }
 
   public String save()
   {
/* 342 */     if (this.docFolder.getFolderId() == null) {
/* 343 */       if (this.docFolder.getIsShared() != null) {
/* 344 */         this.docFolder.setIsShared(DocFolder.IS_SHARED);
       } else {
/* 346 */         this.docFolder.setAppUser(ContextUtil.getCurrentUser());
/* 347 */         this.docFolder.setIsShared(DocFolder.IS_NOT_SHARED);
       }
/* 349 */       this.docFolderService.save(this.docFolder);
 
/* 352 */       if ((this.docFolder.getParentId() == null) || (this.docFolder.getParentId().longValue() == 0L)) {
/* 353 */         this.docFolder.setPath(this.docFolder.getFolderId() + ".");
       } else {
/* 355 */         DocFolder pFolder = (DocFolder)this.docFolderService.get(this.docFolder.getParentId());
/* 356 */         if (pFolder != null) {
/* 357 */           this.docFolder.setPath(pFolder.getPath() + this.docFolder.getFolderId() + ".");
         }
       }
 
/* 361 */       this.docFolderService.save(this.docFolder);
     } else {
/* 363 */       DocFolder df = (DocFolder)this.docFolderService.get(this.docFolder.getFolderId());
 
/* 365 */       df.setFolderName(this.docFolder.getFolderName());
/* 366 */       this.docFolderService.save(df);
     }
 
/* 369 */     setJsonString("{success:true}");
/* 370 */     return "success";
   }
 
   public String move()
   {
/* 378 */     String strFolderIdOld = getRequest().getParameter("folderIdOld");
/* 379 */     String strFolderIdNew = getRequest().getParameter("folderIdNew");
/* 380 */     if ((StringUtils.isNotEmpty(strFolderIdOld)) && (StringUtils.isNotEmpty(strFolderIdNew))) {
/* 381 */       Long folderIdOld = new Long(strFolderIdOld);
/* 382 */       Long folderIdNew = new Long(strFolderIdNew);
/* 383 */       String newPath = null;
/* 384 */       DocFolder folderOld = (DocFolder)this.docFolderService.get(folderIdOld);
/* 385 */       DocFolder folderNew = new DocFolder();
/* 386 */       if (folderIdNew.longValue() > 0L) {
/* 387 */         folderNew = (DocFolder)this.docFolderService.get(folderIdNew);
/* 388 */         newPath = folderNew.getPath() + folderIdOld.toString() + ".";
/* 389 */         boolean flag = Pattern.compile(folderOld.getPath()).matcher(folderNew.getPath()).find();
/* 390 */         if (flag) {
/* 391 */           setJsonString("{success:false,msg:'不能移到子文件夹下！'}");
/* 392 */           return "success";
         }
       } else {
/* 395 */         folderIdNew = ISPARENT;
/* 396 */         newPath = folderIdOld.toString() + ".";
       }
/* 398 */       String oldPath = folderOld.getPath();
/* 399 */       folderOld.setParentId(folderIdNew);
/* 400 */       folderOld.setPath(newPath);
/* 401 */       List<DocFolder> list = this.docFolderService.getFolderLikePath(oldPath);
/* 402 */       for (DocFolder folder : list) {
/* 403 */         folder.setPath(folder.getPath().replaceFirst(oldPath, newPath));
/* 404 */         this.docFolderService.save(folder);
       }
/* 406 */       this.docFolderService.save(folderOld);
/* 407 */       setJsonString("{success:true}");
     } else {
/* 409 */       setJsonString("{success:false,msg:'请联系系统管理员！'}");
     }
/* 411 */     return "success";
   }
 }
