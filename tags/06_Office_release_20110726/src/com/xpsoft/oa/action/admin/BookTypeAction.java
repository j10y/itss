/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.BookType;
/*     */ import com.xpsoft.oa.service.admin.BookService;
/*     */ import com.xpsoft.oa.service.admin.BookTypeService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class BookTypeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private BookTypeService bookTypeService;
/*     */   private BookType bookType;
/*     */ 
/*     */   @Resource
/*     */   private BookService bookService;
/*     */   private Long typeId;
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  37 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long typeId) {
/*  41 */     this.typeId = typeId;
/*     */   }
/*     */ 
/*     */   public BookType getBookType() {
/*  45 */     return this.bookType;
/*     */   }
/*     */ 
/*     */   public void setBookType(BookType bookType) {
/*  49 */     this.bookType = bookType;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  57 */     QueryFilter filter = new QueryFilter(getRequest());
/*  58 */     List<BookType> list = this.bookTypeService.getAll(filter);
/*  59 */     Type type = new TypeToken<List<BookType>>() {  }.getType();
/*  60 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  61 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  62 */     Gson gson = new Gson();
/*  63 */     buff.append(gson.toJson(list, type));
/*  64 */     buff.append("}");
/*  65 */     this.jsonString = buff.toString();
/*  66 */     return "success";
/*     */   }
/*     */ 
/*     */   public String tree()
/*     */   {
/*  73 */     String method = getRequest().getParameter("method");
/*  74 */     List<BookType> list = this.bookTypeService.getAll();
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     int i = 0;
/*  77 */     if (StringUtils.isNotEmpty(method)) {
/*  78 */       sb.append("[");
/*     */     } else {
/*  80 */       i++;
/*  81 */       sb.append("[{id:'0',text:'图书类别',expanded:true,children:[");
/*     */     }
/*  83 */     for (BookType bookType : list) {
/*  84 */       sb.append("{id:'" + bookType.getTypeId() + "',text:'" + bookType.getTypeName() + "',leaf:true},");
/*     */     }
/*  86 */     if (list.size() > 0) {
/*  87 */       sb.deleteCharAt(sb.length() - 1);
/*     */     }
/*  89 */     if (i == 0)
/*  90 */       sb.append("]");
/*     */     else {
/*  92 */       sb.append("]}]");
/*     */     }
/*  94 */     setJsonString(sb.toString());
/*  95 */     return "success";
/*     */   }
/*     */ 
/*     */   public String remove()
/*     */   {
/* 102 */     Long typeId = Long.valueOf(getRequest().getParameter("typeId"));
/* 103 */     setBookType((BookType)this.bookTypeService.get(typeId));
/* 104 */     if (this.bookType != null) {
/* 105 */       QueryFilter filter = new QueryFilter(getRequest());
/* 106 */       filter.addFilter("Q_bookType.typeId_L_EQ", typeId.toString());
/* 107 */       List list = this.bookService.getAll(filter);
/* 108 */       if (list.size() > 0) {
/* 109 */         this.jsonString = "{success:false,message:'该类型下还有图书，请将图书移走后再删除！'}";
/* 110 */         return "success";
/*     */       }
/* 112 */       this.bookTypeService.remove(typeId);
/*     */     }
/* 114 */     this.jsonString = "{success:true}";
/* 115 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 122 */     String[] ids = getRequest().getParameterValues("ids");
/* 123 */     if (ids != null) {
/* 124 */       for (String id : ids) {
/* 125 */         QueryFilter filter = new QueryFilter(getRequest());
/* 126 */         filter.addFilter("Q_bookType.typeId_L_EQ", id);
/* 127 */         List list = this.bookService.getAll(filter);
/* 128 */         if (list.size() > 0) {
/* 129 */           this.jsonString = "{success:false,message:'该类型下还有图书，请将图书移走后再删除！'}";
/* 130 */           return "success";
/*     */         }
/* 132 */         this.bookTypeService.remove(new Long(id));
/*     */       }
/*     */     }
/* 135 */     this.jsonString = "{success:true}";
/* 136 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 144 */     BookType bookType = (BookType)this.bookTypeService.get(this.typeId);
/* 145 */     Gson gson = new Gson();
/*     */ 
/* 147 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 148 */     sb.append(gson.toJson(bookType));
/* 149 */     sb.append("}");
/* 150 */     setJsonString(sb.toString());
/* 151 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 157 */     this.bookTypeService.save(this.bookType);
/* 158 */     setJsonString("{success:true}");
/* 159 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.BookTypeAction
 * JD-Core Version:    0.6.0
 */