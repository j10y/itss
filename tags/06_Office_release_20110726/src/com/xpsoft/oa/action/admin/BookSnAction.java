/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.BookSn;
/*     */ import com.xpsoft.oa.service.admin.BookSnService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class BookSnAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private BookSnService bookSnService;
/*     */   private BookSn bookSn;
/*     */   private Long bookSnId;
/*     */ 
/*     */   public Long getBookSnId()
/*     */   {
/*  32 */     return this.bookSnId;
/*     */   }
/*     */ 
/*     */   public void setBookSnId(Long bookSnId) {
/*  36 */     this.bookSnId = bookSnId;
/*     */   }
/*     */ 
/*     */   public BookSn getBookSn() {
/*  40 */     return this.bookSn;
/*     */   }
/*     */ 
/*     */   public void setBookSn(BookSn bookSn) {
/*  44 */     this.bookSn = bookSn;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
/*  53 */     List list = this.bookSnService.getAll(filter);
/*  54 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  55 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  56 */     JSONSerializer serializer = new JSONSerializer();
/*  57 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  58 */     buff.append("}");
/*  59 */     this.jsonString = buff.toString();
/*  60 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  67 */     String[] ids = getRequest().getParameterValues("ids");
/*  68 */     if (ids != null) {
/*  69 */       for (String id : ids) {
/*  70 */         this.bookSnService.remove(new Long(id));
/*     */       }
/*     */     }
/*  73 */     this.jsonString = "{success:true}";
/*  74 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  82 */     BookSn bookSn = (BookSn)this.bookSnService.get(this.bookSnId);
/*  83 */     Gson gson = new Gson();
/*     */ 
/*  85 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  86 */     sb.append(gson.toJson(bookSn));
/*  87 */     sb.append("}");
/*  88 */     setJsonString(sb.toString());
/*  89 */     return "success";
/*     */   }
/*     */ 
/*     */   public String getSn()
/*     */   {
/*  96 */     List<BookSn> list = null;
/*  97 */     this.bookSn = new BookSn();
/*  98 */     Long bookId = Long.valueOf(0L);
/*  99 */     bookId = Long.valueOf(getRequest().getParameter("bookId"));
/* 100 */     list = this.bookSnService.findByBookId(bookId);
/* 101 */     StringBuffer buff = new StringBuffer("[");
/* 102 */     for (BookSn bookSn : list) {
/* 103 */       buff.append("['" + bookSn.getBookSnId() + "','" + bookSn.getBookSN() + "'],");
/*     */     }
/* 105 */     if (list.size() != 0) {
/* 106 */       buff.deleteCharAt(buff.length() - 1);
/*     */     }
/* 108 */     buff.append("]");
/* 109 */     setJsonString(buff.toString());
/* 110 */     return "success";
/*     */   }
/*     */ 
/*     */   public String getBorrowSn()
/*     */   {
/* 117 */     List<BookSn> list = null;
/* 118 */     this.bookSn = new BookSn();
/* 119 */     Long bookId = Long.valueOf(0L);
/* 120 */     bookId = Long.valueOf(getRequest().getParameter("bookId"));
/* 121 */     list = this.bookSnService.findBorrowByBookId(bookId);
/* 122 */     StringBuffer buff = new StringBuffer("[");
/* 123 */     for (BookSn bookSn : list) {
/* 124 */       buff.append("['" + bookSn.getBookSnId() + "','" + bookSn.getBookSN() + "'],");
/*     */     }
/* 126 */     if (list.size() != 0) {
/* 127 */       buff.deleteCharAt(buff.length() - 1);
/*     */     }
/* 129 */     buff.append("]");
/* 130 */     setJsonString(buff.toString());
/* 131 */     return "success";
/*     */   }
/*     */ 
/*     */   public String getReturnSn()
/*     */   {
/* 138 */     List<BookSn> list = null;
/* 139 */     this.bookSn = new BookSn();
/* 140 */     Long bookId = Long.valueOf(0L);
/* 141 */     bookId = Long.valueOf(getRequest().getParameter("bookId"));
/* 142 */     list = this.bookSnService.findReturnByBookId(bookId);
/* 143 */     StringBuffer buff = new StringBuffer("[");
/* 144 */     for (BookSn bookSn : list) {
/* 145 */       buff.append("['" + bookSn.getBookSnId() + "','" + bookSn.getBookSN() + "'],");
/*     */     }
/* 147 */     if (list.size() != 0) {
/* 148 */       buff.deleteCharAt(buff.length() - 1);
/*     */     }
/* 150 */     buff.append("]");
/* 151 */     setJsonString(buff.toString());
/* 152 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 158 */     this.bookSnService.save(this.bookSn);
/* 159 */     setJsonString("{success:true}");
/* 160 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.BookSnAction
 * JD-Core Version:    0.6.0
 */