/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.Book;
/*     */ import com.xpsoft.oa.model.admin.BookBorRet;
/*     */ import com.xpsoft.oa.model.admin.BookSn;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.admin.BookBorRetService;
/*     */ import com.xpsoft.oa.service.admin.BookService;
/*     */ import com.xpsoft.oa.service.admin.BookSnService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class BookBorRetAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private BookBorRetService bookBorRetService;
/*     */   private BookBorRet bookBorRet;
/*     */ 
/*     */   @Resource
/*     */   private BookSnService bookSnService;
/*     */ 
/*     */   @Resource
/*     */   private BookService bookService;
/*     */   private Long recordId;
/*     */   private Long bookSnId;
/*     */ 
/*     */   public Long getBookSnId()
/*     */   {
/*  50 */     return this.bookSnId;
/*     */   }
/*     */ 
/*     */   public void setBookSnId(Long bookSnId) {
/*  54 */     this.bookSnId = bookSnId;
/*     */   }
/*     */ 
/*     */   public Long getRecordId() {
/*  58 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long recordId) {
/*  62 */     this.recordId = recordId;
/*     */   }
/*     */ 
/*     */   public BookBorRet getBookBorRet() {
/*  66 */     return this.bookBorRet;
/*     */   }
/*     */ 
/*     */   public void setBookBorRet(BookBorRet bookBorRet) {
/*  70 */     this.bookBorRet = bookBorRet;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  78 */     QueryFilter filter = new QueryFilter(getRequest());
/*  79 */     List list = this.bookBorRetService.getAll(filter);
/*  80 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  81 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  82 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "borrowTime", "returnTime", "lastReturnTime" });
/*  83 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  84 */     buff.append("}");
/*  85 */     this.jsonString = buff.toString();
/*  86 */     return "success";
/*     */   }
/*     */ 
/*     */   public String listBorrow()
/*     */   {
/*  92 */     PagingBean pb = getInitPagingBean();
/*  93 */     List list = this.bookBorRetService.getBorrowInfo(pb);
/*  94 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  95 */       .append(pb.getTotalItems()).append(",result:");
/*  96 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "borrowTime", "returnTime", "lastReturnTime" });
/*  97 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  98 */     buff.append("}");
/*  99 */     this.jsonString = buff.toString();
/* 100 */     return "success";
/*     */   }
/*     */ 
/*     */   public String listReturn()
/*     */   {
/* 106 */     PagingBean pb = getInitPagingBean();
/* 107 */     List list = this.bookBorRetService.getReturnInfo(pb);
/* 108 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 109 */       .append(pb.getTotalItems()).append(",result:");
/* 110 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "borrowTime", "returnTime", "lastReturnTime" });
/* 111 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/* 112 */     buff.append("}");
/* 113 */     this.jsonString = buff.toString();
/* 114 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 121 */     String[] ids = getRequest().getParameterValues("ids");
/* 122 */     if (ids != null) {
/* 123 */       for (String id : ids) {
/* 124 */         this.bookBorRetService.remove(new Long(id));
/*     */       }
/*     */     }
/* 127 */     this.jsonString = "{success:true}";
/* 128 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 136 */     BookBorRet bookBorRet = (BookBorRet)this.bookBorRetService.get(this.recordId);
/* 137 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "borrowTime", "returnTime", "lastReturnTime" });
/* 138 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 139 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(bookBorRet));
/* 140 */     sb.append("}");
/* 141 */     setJsonString(sb.toString());
/* 142 */     return "success";
/*     */   }
/*     */ 
/*     */   public String saveBorrow()
/*     */   {
/* 150 */     Long snId = this.bookBorRet.getBookSn().getBookSnId();
/* 151 */     Long recordId = this.bookBorRetService.getBookBorRetId(snId);
/* 152 */     if (recordId != null) {
/* 153 */       this.bookBorRetService.remove(recordId);
/*     */     }
/* 155 */     this.bookBorRet.setBorrowTime(new Date());
/* 156 */     AppUser user = ContextUtil.getCurrentUser();
/* 157 */     this.bookBorRet.setRegisterName(user.getFullname());
/* 158 */     this.bookBorRetService.save(this.bookBorRet);
/* 159 */     BookSn bookSn = (BookSn)this.bookSnService.get(this.bookBorRet.getBookSnId());
/*     */ 
/* 161 */     bookSn.setStatus((short)1);
/* 162 */     this.bookSnService.save(bookSn);
/* 163 */     Book book = (Book)this.bookService.get(bookSn.getBookId());
/*     */ 
/* 165 */     book.setLeftAmount(Integer.valueOf(book.getLeftAmount().intValue() - 1));
/* 166 */     this.bookService.save(book);
/* 167 */     setJsonString("{success:true}");
/* 168 */     return "success";
/*     */   }
/*     */ 
/*     */   public String saveReturn()
/*     */   {
/* 175 */     this.bookBorRet.setLastReturnTime(new Date());
/* 176 */     AppUser user = ContextUtil.getCurrentUser();
/* 177 */     this.bookBorRet.setRegisterName(user.getFullname());
/* 178 */     this.bookBorRetService.save(this.bookBorRet);
/* 179 */     BookSn bookSn = (BookSn)this.bookSnService.get(this.bookBorRet.getBookSnId());
/*     */ 
/* 181 */     bookSn.setStatus((short)0);
/* 182 */     this.bookSnService.save(bookSn);
/* 183 */     Book book = (Book)this.bookService.get(bookSn.getBookId());
/*     */ 
/* 185 */     book.setLeftAmount(Integer.valueOf(book.getLeftAmount().intValue() + 1));
/* 186 */     this.bookService.save(book);
/* 187 */     setJsonString("{success:true}");
/* 188 */     return "success";
/*     */   }
/*     */ 
/*     */   public String getBorRetTime()
/*     */   {
/* 195 */     Long bookSnId = Long.valueOf(getRequest().getParameter("bookSnId"));
/* 196 */     BookBorRet bookBorRet = this.bookBorRetService.getByBookSnId(bookSnId);
/* 197 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "borrowTime", "returnTime" });
/* 198 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 199 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(bookBorRet));
/* 200 */     sb.append("}");
/* 201 */     setJsonString(sb.toString());
/* 202 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.BookBorRetAction
 * JD-Core Version:    0.6.0
 */