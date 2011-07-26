/*     */ package com.xpsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.archive.Archives;
/*     */ import com.xpsoft.oa.model.archive.LeaderRead;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.archive.ArchivesService;
/*     */ import com.xpsoft.oa.service.archive.LeaderReadService;
/*     */ import com.xpsoft.oa.service.system.AppUserService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class LeaderReadAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private LeaderReadService leaderReadService;
/*     */   private LeaderRead leaderRead;
/*     */ 
/*     */   @Resource
/*     */   private ArchivesService archivesService;
/*     */ 
/*     */   @Resource
/*     */   private AppUserService appUserService;
/*     */   private Long readId;
/*     */   private String leaderOpinion;
/*     */   private Short isPass;
/*     */   private Archives archives;
/*     */ 
/*     */   public Archives getArchives()
/*     */   {
/*  55 */     return this.archives;
/*     */   }
/*     */ 
/*     */   public void setArchives(Archives archives) {
/*  59 */     this.archives = archives;
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
/*     */   public String getLeaderOpinion() {
/*  71 */     return this.leaderOpinion;
/*     */   }
/*     */ 
/*     */   public void setLeaderOpinion(String leaderOpinion) {
/*  75 */     this.leaderOpinion = leaderOpinion;
/*     */   }
/*     */ 
/*     */   public Long getReadId() {
/*  79 */     return this.readId;
/*     */   }
/*     */ 
/*     */   public void setReadId(Long readId) {
/*  83 */     this.readId = readId;
/*     */   }
/*     */ 
/*     */   public LeaderRead getLeaderRead() {
/*  87 */     return this.leaderRead;
/*     */   }
/*     */ 
/*     */   public void setLeaderRead(LeaderRead leaderRead) {
/*  91 */     this.leaderRead = leaderRead;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  99 */     QueryFilter filter = new QueryFilter(getRequest());
/* 100 */     filter.addFilter("Q_userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/* 101 */     filter.addFilter("Q_archives.archType_SN_EQ", Archives.ARCHIVE_TYPE_RECEIVE.toString());
/* 102 */     List list = this.leaderReadService.getAll(filter);
/* 103 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 104 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/* 105 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "createtime", "archives.issueDate", "archives.createtime" });
/* 106 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/* 107 */     buff.append("}");
/*     */ 
/* 109 */     this.jsonString = buff.toString();
/*     */ 
/* 111 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 119 */     String[] ids = getRequest().getParameterValues("ids");
/* 120 */     if (ids != null) {
/* 121 */       for (String id : ids) {
/* 122 */         this.leaderReadService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/* 126 */     this.jsonString = "{success:true}";
/*     */ 
/* 128 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 136 */     LeaderRead leaderRead = (LeaderRead)this.leaderReadService.get(this.readId);
/*     */ 
/* 138 */     Gson gson = new Gson();
/*     */ 
/* 140 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 141 */     sb.append(gson.toJson(leaderRead));
/* 142 */     sb.append("}");
/* 143 */     setJsonString(sb.toString());
/*     */ 
/* 145 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 151 */     String strArchivesId = getRequest().getParameter("archivesId");
/* 152 */     if (StringUtils.isNotEmpty(strArchivesId)) {
/* 153 */       LeaderRead leader = new LeaderRead();
/* 154 */       Archives archives = (Archives)this.archivesService.get(new Long(strArchivesId));
/* 155 */       AppUser user = ContextUtil.getCurrentUser();
/* 156 */       leader.setArchives(archives);
/* 157 */       leader.setLeaderOpinion(this.leaderOpinion);
/* 158 */       leader.setIsPass(this.isPass);
/* 159 */       leader.setUserId(user.getUserId());
/* 160 */       leader.setLeaderName(user.getFullname());
/* 161 */       leader.setCreatetime(new Date());
/* 162 */       this.leaderReadService.save(leader);
/* 163 */       archives.setStatus(Archives.STATUS_DISPATCH);
/* 164 */       this.archivesService.save(archives);
/*     */     }
/* 166 */     setJsonString("{success:true}");
/* 167 */     return "success";
/*     */   }
/*     */ 
/*     */   public String saveDep()
/*     */   {
/* 174 */     this.archives = ((Archives)this.archivesService.get(this.archives.getArchivesId()));
/* 175 */     String archivesStatus = getRequest().getParameter("archivesStatus");
/* 176 */     if (StringUtils.isNotEmpty(archivesStatus)) {
/* 177 */       this.archives.setStatus(Short.valueOf(Short.parseShort(archivesStatus)));
/*     */     }
/* 179 */     this.archivesService.save(this.archives);
/*     */ 
/* 181 */     this.leaderRead.setLeaderName(ContextUtil.getCurrentUser().getFullname());
/* 182 */     this.leaderRead.setUserId(ContextUtil.getCurrentUserId());
/* 183 */     this.leaderRead.setArchives(this.archives);
/* 184 */     this.leaderRead.setCreatetime(new Date());
/* 185 */     this.leaderRead.setIsPass(LeaderRead.IS_PASS);
/* 186 */     this.leaderReadService.save(this.leaderRead);
/*     */ 
/* 188 */     setJsonString("{success:true,readId:" + this.leaderRead.getReadId() + "}");
/* 189 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.archive.LeaderReadAction
 * JD-Core Version:    0.6.0
 */