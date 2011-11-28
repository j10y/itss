/*     */ package com.xpsoft.oa.action.info;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.info.Notice;
/*     */ import com.xpsoft.oa.service.info.NoticeService;
/*     */ import flexjson.DateTransformer;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class NoticeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private NoticeService noticeService;
/*     */   private Notice notice;
/*     */   private List<Notice> list;
/*     */   private Long noticeId;
/*     */ 
/*     */   public List<Notice> getList()
/*     */   {
/*  34 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List<Notice> list) {
/*  38 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public Long getNoticeId()
/*     */   {
/*  43 */     return this.noticeId;
/*     */   }
/*     */ 
/*     */   public void setNoticeId(Long noticeId) {
/*  47 */     this.noticeId = noticeId;
/*     */   }
/*     */ 
/*     */   public Notice getNotice() {
/*  51 */     return this.notice;
/*     */   }
/*     */ 
/*     */   public void setNotice(Notice notice) {
/*  55 */     this.notice = notice;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  63 */     QueryFilter filter = new QueryFilter(getRequest());
/*  64 */     List<Notice> list = this.noticeService.getAll(filter);
/*     */ 
/*  66 */     Type type = new TypeToken<List<Notice>>() {  }
/*  66 */     .getType();
/*  67 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  68 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  70 */     Gson gson = new Gson();
/*  71 */     buff.append(gson.toJson(list, type));
/*  72 */     buff.append("}");
/*     */ 
/*  74 */     this.jsonString = buff.toString();
/*  75 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  83 */     String[] ids = getRequest().getParameterValues("ids");
/*  84 */     if (ids != null) {
/*  85 */       for (String id : ids) {
/*  86 */         this.noticeService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  90 */     this.jsonString = "{success:true}";
/*     */ 
/*  92 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 100 */     Notice notice = (Notice)this.noticeService.get(this.noticeId);
/*     */ 
/* 102 */     JSONSerializer serializer = new JSONSerializer();
/* 103 */     serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), new String[] { "effectiveDate" });
/* 104 */     serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), new String[] { "expirationDate" });
/*     */ 
/* 106 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 107 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(notice));
/* 108 */     sb.append("}");
/* 109 */     setJsonString(sb.toString());
/*     */ 
/* 111 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 117 */     this.noticeService.save(this.notice);
/* 118 */     setJsonString("{success:true}");
/* 119 */     return "success";
/*     */   }
/*     */ 
/*     */   public String search()
/*     */   {
/* 127 */     PagingBean pb = getInitPagingBean();
/* 128 */     String searchContent = getRequest().getParameter("searchContent");
/* 129 */     List<Notice> list = this.noticeService.findBySearch(searchContent, pb);
/* 130 */     Type type = new TypeToken<List<Notice>>() {  }
/* 130 */     .getType();
/* 131 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 132 */       .append(pb.getTotalItems()).append(",result:");
/*     */ 
/* 134 */     Gson gson = new Gson();
/* 135 */     buff.append(gson.toJson(list, type));
/* 136 */     buff.append("}");
/*     */ 
/* 138 */     this.jsonString = buff.toString();
/*     */ 
/* 140 */     return "success";
/*     */   }
/*     */ 
/*     */   public String display()
/*     */   {
/* 148 */     PagingBean pb = new PagingBean(0, 8);
/* 149 */     List list = this.noticeService.findBySearch(null, pb);
/* 150 */     getRequest().setAttribute("noticeList", list);
/* 151 */     return "display";
/*     */   }
/*     */ 
/*     */   public String scroller()
/*     */   {
/* 156 */     PagingBean pb = new PagingBean(0, 8);
/* 157 */     List list = this.noticeService.findBySearch(null, pb);
/* 158 */     getRequest().setAttribute("noticeList", list);
/* 159 */     return "scroller";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.info.NoticeAction
 * JD-Core Version:    0.6.0
 */