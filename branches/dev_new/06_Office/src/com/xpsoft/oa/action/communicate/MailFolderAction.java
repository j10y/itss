 package com.xpsoft.oa.action.communicate;
 
 import com.google.gson.Gson;
 import com.xpsoft.core.util.ContextUtil;
 import com.xpsoft.core.web.action.BaseAction;
 import com.xpsoft.oa.model.communicate.MailBox;
 import com.xpsoft.oa.model.communicate.MailFolder;
 import com.xpsoft.oa.service.communicate.MailBoxService;
 import com.xpsoft.oa.service.communicate.MailFolderService;
 import java.util.Iterator;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.logging.Log;
 
 public class MailFolderAction extends BaseAction
 {
/*  23 */   static long FOLDER_ID_RECEIVE = 1L;
/*  24 */   static long FOLDER_ID_SEND = 2L;
/*  25 */   static long FOLDER_ID_DRAFT = 3L;
/*  26 */   static long FOLDER_ID_DELETE = 4L;
 
/*  28 */   static short OTHER_FOLDER_TYPE = 10;
/*  29 */   static int FIRST_LEVEL = 1;
/*  30 */   static long FIRST_LEVEL_PARENTID = 0L;
 
   @Resource
   private MailFolderService mailFolderService;
 
   @Resource
   private MailBoxService mailBoxService;
   private MailFolder mailFolder;
   private Long folderId;
 
/*  41 */   public Long getFolderId() { return this.folderId; }
 
   public void setFolderId(Long folderId)
   {
/*  45 */     this.folderId = folderId;
   }
 
   public MailFolder getMailFolder() {
/*  49 */     return this.mailFolder;
   }
 
   public void setMailFolder(MailFolder mailFolder) {
/*  53 */     this.mailFolder = mailFolder;
   }
 
   public String list()
   {
/*  61 */     StringBuffer buff = new StringBuffer("[{id:'0',text:'我的邮箱',iconCls:'menu-mail_box',expanded:true,children:[");
/*  62 */     Long curUserId = ContextUtil.getCurrentUserId();
/*  63 */     List<MailFolder> mailFolderList = this.mailFolderService.getAllUserFolderByParentId(curUserId, Long.valueOf(0L));
/*  64 */     for (MailFolder folder : mailFolderList) {
/*  65 */       buff.append("{id:'" + folder.getFolderId()).append("',text:'" + folder.getFolderName()).append("',");
/*  66 */       Long folderId = folder.getFolderId();
/*  67 */       if (folderId.longValue() == FOLDER_ID_RECEIVE)
/*  68 */         buff.append("iconCls:'menu-mail_inbox',");
/*  69 */       else if (folderId.longValue() == FOLDER_ID_SEND)
/*  70 */         buff.append("iconCls:'menu-mail_outbox',");
/*  71 */       else if (folderId.longValue() == FOLDER_ID_DRAFT)
/*  72 */         buff.append("iconCls:'menu-mail_drafts',");
/*  73 */       else if (folderId.longValue() == FOLDER_ID_DELETE)
/*  74 */         buff.append("iconCls:'menu-mail_trash',");
       else {
/*  76 */         buff.append("iconCls:'menu-mail_folder',");
       }
/*  78 */       buff.append(findChildsFolder(curUserId, folder.getFolderId()));
     }
/*  80 */     if (!mailFolderList.isEmpty()) {
/*  81 */       buff.deleteCharAt(buff.length() - 1);
     }
/*  83 */     buff.append("]}]");
/*  84 */     setJsonString(buff.toString());
 
/*  86 */     this.logger.info("tree json:" + buff.toString());
 
/*  88 */     return "success";
   }
 
   public String findChildsFolder(Long userId, Long parentId) {
/*  92 */     StringBuffer sb = new StringBuffer();
/*  93 */     List<MailFolder> folders = this.mailFolderService.getUserFolderByParentId(userId, parentId);
/*  94 */     if (folders.size() == 0) {
/*  95 */       sb.append("leaf:true,expanded:true},");
/*  96 */       return sb.toString();
     }
/*  98 */     sb.append("children:[");
/*  99 */     for (MailFolder folder : folders) {
/* 100 */       sb.append("{id:'" + folder.getFolderId() + "',text:'" + folder.getFolderName() + "',");
/* 101 */       sb.append("iconCls:'menu-mail_folder',");
/* 102 */       sb.append(findChildsFolder(userId, folder.getFolderId()));
     }
/* 104 */     sb.deleteCharAt(sb.length() - 1);
/* 105 */     sb.append("]},");
/* 106 */     return sb.toString();
   }
 
   public String multiDel()
   {
/* 115 */     String[] ids = getRequest().getParameterValues("ids");
/* 116 */     if (ids != null) {
/* 117 */       for (String id : ids) {
/* 118 */         this.mailFolderService.remove(new Long(id));
       }
     }
 
/* 122 */     this.jsonString = "{success:true}";
 
/* 124 */     return "success";
   }
 
   public String count()
   {
/* 131 */     MailFolder tmpFolder = (MailFolder)this.mailFolderService.get(new Long(this.folderId.longValue()));
/* 132 */     List<MailFolder> mailFolderList = this.mailFolderService.getFolderLikePath(tmpFolder.getPath());
 
/* 134 */     Long total = Long.valueOf(0L);
/* 135 */     for (MailFolder folder : mailFolderList) {
/* 136 */       Long count = this.mailBoxService.CountByFolderId(folder.getFolderId());
/* 137 */       total = Long.valueOf(total.longValue() + count.longValue());
     }
 
/* 140 */     setJsonString("{success:true,count:" + total + "}");
/* 141 */     return "success";
   }
 
   public String remove()
   {
/* 149 */     String count = getRequest().getParameter("count");
/* 150 */     if (this.folderId != null) {
/* 151 */       MailFolder tmpFolder = (MailFolder)this.mailFolderService.get(new Long(this.folderId.longValue()));
/* 152 */       List<MailFolder> mailFolderList = this.mailFolderService.getFolderLikePath(tmpFolder.getPath());
       MailFolder folder;
/* 155 */       if ((count != null) && (new Long(count).longValue() > 0L)) {
/* 156 */         MailFolder deleteFolder = (MailFolder)this.mailFolderService.get(Long.valueOf(4L));
/* 157 */         for (Iterator localIterator1 = mailFolderList.iterator(); localIterator1.hasNext(); ) { folder = (MailFolder)localIterator1.next();
/* 158 */           List<MailBox> mailBoxList = this.mailBoxService.findByFolderId(folder.getFolderId());
/* 159 */           for (MailBox mailBox : mailBoxList) {
/* 160 */             mailBox.setMailFolder(deleteFolder);
/* 161 */             this.mailBoxService.save(mailBox);
           }
         }
 
       }
 
/* 167 */       for (MailFolder fd : mailFolderList) {
/* 168 */         this.mailFolderService.remove(fd.getFolderId());
       }
     }
 
/* 172 */     this.jsonString = "{success:true}";
/* 173 */     return "success";
   }
 
   public String get()
   {
/* 181 */     MailFolder mailFolder = (MailFolder)this.mailFolderService.get(this.folderId);
 
/* 183 */     Gson gson = new Gson();
 
/* 185 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 186 */     sb.append(gson.toJson(mailFolder));
/* 187 */     sb.append("}");
/* 188 */     setJsonString(sb.toString());
/* 189 */     return "success";
   }
 
   public String save()
   {
/* 195 */     MailFolder parentFolder = null;
/* 196 */     Long parentId = this.mailFolder.getParentId();
/* 197 */     if ((parentId == null) || (parentId.longValue() == FIRST_LEVEL_PARENTID)) {
/* 198 */       this.mailFolder.setParentId(new Long(FIRST_LEVEL_PARENTID));
/* 199 */       this.mailFolder.setDepLevel(Integer.valueOf(FIRST_LEVEL));
     } else {
/* 201 */       parentFolder = (MailFolder)this.mailFolderService.get(parentId);
/* 202 */       this.mailFolder.setDepLevel(Integer.valueOf(parentFolder.getDepLevel().intValue() + 1));
     }
/* 204 */     this.mailFolder.setFolderType(Short.valueOf(OTHER_FOLDER_TYPE));
/* 205 */     this.mailFolder.setUserId(ContextUtil.getCurrentUserId());
/* 206 */     this.mailFolderService.save(this.mailFolder);
 
/* 209 */     if (this.mailFolder.getParentId().longValue() == FIRST_LEVEL_PARENTID)
/* 210 */       this.mailFolder.setPath("0." + this.mailFolder.getFolderId() + ".");
     else {
/* 212 */       this.mailFolder.setPath(parentFolder.getPath() + this.mailFolder.getFolderId() + ".");
     }
/* 214 */     this.mailFolderService.save(this.mailFolder);
/* 215 */     setJsonString("{success:true}");
/* 216 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.communicate.MailFolderAction
 * JD-Core Version:    0.6.0
 */