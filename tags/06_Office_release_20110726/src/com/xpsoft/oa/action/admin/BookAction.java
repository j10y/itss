/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.Book;
/*     */ import com.xpsoft.oa.model.admin.BookSn;
/*     */ import com.xpsoft.oa.model.admin.BookType;
/*     */ import com.xpsoft.oa.service.admin.BookService;
/*     */ import com.xpsoft.oa.service.admin.BookSnService;
/*     */ import com.xpsoft.oa.service.admin.BookTypeService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class BookAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private BookService bookService;
/*     */ 
/*     */   @Resource
/*     */   private BookTypeService bookTypeService;
/*     */ 
/*     */   @Resource
/*     */   private BookSnService bookSnService;
/*     */   private Book book;
/*     */   private Long bookId;
/*     */   private Long typeId;
/*     */   private BookType bookType;
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  41 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long typeId) {
/*  45 */     this.typeId = typeId;
/*     */   }
/*     */ 
/*     */   public BookType getBookType() {
/*  49 */     return this.bookType;
/*     */   }
/*     */ 
/*     */   public void setBookType(BookType bookType) {
/*  53 */     this.bookType = bookType;
/*     */   }
/*     */ 
/*     */   public Long getBookId() {
/*  57 */     return this.bookId;
/*     */   }
/*     */ 
/*     */   public void setBookId(Long bookId) {
/*  61 */     this.bookId = bookId;
/*     */   }
/*     */ 
/*     */   public Book getBook() {
/*  65 */     return this.book;
/*     */   }
/*     */ 
/*     */   public void setBook(Book book) {
/*  69 */     this.book = book;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  77 */     QueryFilter filter = new QueryFilter(getRequest());
/*  78 */     List list = this.bookService.getAll(filter);
/*  79 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  80 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  81 */     JSONSerializer serializer = new JSONSerializer();
/*  82 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  83 */     buff.append("}");
/*  84 */     this.jsonString = buff.toString();
/*  85 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  92 */     String[] ids = getRequest().getParameterValues("ids");
/*  93 */     if (ids != null) {
/*  94 */       for (String id : ids) {
/*  95 */         this.bookService.remove(new Long(id));
/*     */       }
/*     */     }
/*  98 */     this.jsonString = "{success:true}";
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 107 */     Book book = (Book)this.bookService.get(this.bookId);
/* 108 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 109 */     JSONSerializer serializer = new JSONSerializer();
/* 110 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(book));
/* 111 */     sb.append("}");
/* 112 */     setJsonString(sb.toString());
/* 113 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 119 */     String bookSnNumber = "";
/*     */ 
/* 121 */     if (this.book.getBookId() == null)
/*     */     {
/* 123 */       this.book.setLeftAmount(this.book.getAmount());
/* 124 */       this.bookService.save(this.book);
/* 125 */       for (int i = 1; i <= this.book.getAmount().intValue(); i++)
/*     */       {
/* 127 */         BookSn bookSn = new BookSn();
/*     */ 
/* 130 */         bookSnNumber = this.book.getIsbn() + "-" + i;
/* 131 */         bookSn.setBookId(this.book.getBookId());
/* 132 */         bookSn.setBookSN(bookSnNumber);
/*     */ 
/* 134 */         bookSn.setStatus((short) 0);
/*     */ 
/* 136 */         this.bookSnService.save(bookSn);
/*     */       }
/*     */     } else {
/* 139 */       this.bookService.save(this.book);
/*     */     }
/* 141 */     setJsonString("{success:true,bookSnNumber:'" + bookSnNumber + "'}");
/* 142 */     return "success";
/*     */   }
/*     */ 
/*     */   public String updateAmount()
/*     */   {
/* 148 */     Long bookId = Long.valueOf(getRequest().getParameter("bookId"));
/* 149 */     this.book = ((Book)this.bookService.get(bookId));
/* 150 */     int addAmount = Integer.parseInt(getRequest().getParameter("addAmount"));
/* 151 */     int amount = this.book.getAmount().intValue() + addAmount;
/* 152 */     BookSn bookSn = null;
/* 153 */     String bookSnNumber = "";
/* 154 */     for (int i = this.book.getAmount().intValue() + 1; i <= this.book.getAmount().intValue() + addAmount; i++)
/*     */     {
/* 156 */       bookSn = new BookSn();
/*     */ 
/* 159 */       bookSnNumber = this.book.getIsbn() + "-" + i;
/* 160 */       bookSn.setBookId(this.book.getBookId());
/* 161 */       bookSn.setBookSN(bookSnNumber);
/*     */ 
/* 163 */       bookSn.setStatus((short)0);
/*     */ 
/* 165 */       this.bookSnService.save(bookSn);
/*     */     }
/* 167 */     this.book.setAmount(Integer.valueOf(amount));
/* 168 */     this.book.setLeftAmount(Integer.valueOf(this.book.getLeftAmount().intValue() + addAmount));
/* 169 */     this.bookService.save(this.book);
/* 170 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 171 */     JSONSerializer serializer = new JSONSerializer();
/* 172 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(this.book));
/* 173 */     sb.append("}");
/* 174 */     setJsonString(sb.toString());
/* 175 */     return "success";
/*     */   }
/*     */ 
/*     */   public String updateAmountAndLeftAmount()
/*     */   {
/* 181 */     Long bookId = Long.valueOf(getRequest().getParameter("bookId"));
/* 182 */     this.book = ((Book)this.bookService.get(bookId));
/* 183 */     int amount = this.book.getAmount().intValue() - 1;
/* 184 */     int leftAmount = this.book.getLeftAmount().intValue() - 1;
/* 185 */     this.book.setAmount(Integer.valueOf(amount));
/* 186 */     this.book.setLeftAmount(Integer.valueOf(leftAmount));
/* 187 */     this.bookService.save(this.book);
/* 188 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 189 */     JSONSerializer serializer = new JSONSerializer();
/* 190 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(this.book));
/* 191 */     sb.append("}");
/* 192 */     setJsonString(sb.toString());
/* 193 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.BookAction
 * JD-Core Version:    0.6.0
 */