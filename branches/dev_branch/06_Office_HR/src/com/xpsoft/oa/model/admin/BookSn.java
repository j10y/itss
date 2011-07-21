/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class BookSn extends BaseModel
/*     */ {
/*     */   protected Long bookSnId;
/*     */   protected String bookSN;
/*     */   protected Short status;
/*     */   protected Book book;
/*  25 */   protected Set bookBorRets = new HashSet();
/*     */ 
/*     */   public BookSn()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BookSn(Long in_bookSnId)
/*     */   {
/*  40 */     setBookSnId(in_bookSnId);
/*     */   }
/*     */ 
/*     */   public Book getBook()
/*     */   {
/*  45 */     return this.book;
/*     */   }
/*     */ 
/*     */   public void setBook(Book in_book) {
/*  49 */     this.book = in_book;
/*     */   }
/*     */ 
/*     */   public Set getBookBorRets() {
/*  53 */     return this.bookBorRets;
/*     */   }
/*     */ 
/*     */   public void setBookBorRets(Set in_bookBorRets) {
/*  57 */     this.bookBorRets = in_bookBorRets;
/*     */   }
/*     */ 
/*     */   public Long getBookSnId()
/*     */   {
/*  66 */     return this.bookSnId;
/*     */   }
/*     */ 
/*     */   public void setBookSnId(Long aValue)
/*     */   {
/*  73 */     this.bookSnId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getBookId()
/*     */   {
/*  80 */     return getBook() == null ? null : getBook().getBookId();
/*     */   }
/*     */ 
/*     */   public void setBookId(Long aValue)
/*     */   {
/*  87 */     if (aValue == null) {
/*  88 */       this.book = null;
/*  89 */     } else if (this.book == null) {
/*  90 */       this.book = new Book(aValue);
/*  91 */       this.book.setVersion(new Integer(0));
/*     */     } else {
/*  93 */       this.book.setBookId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getBookSN()
/*     */   {
/* 102 */     return this.bookSN;
/*     */   }
/*     */ 
/*     */   public void setBookSN(String aValue)
/*     */   {
/* 110 */     this.bookSN = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 118 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 126 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 133 */     if (!(object instanceof BookSn)) {
/* 134 */       return false;
/*     */     }
/* 136 */     BookSn rhs = (BookSn)object;
/* 137 */     return new EqualsBuilder()
/* 138 */       .append(this.bookSnId, rhs.bookSnId)
/* 139 */       .append(this.bookSN, rhs.bookSN)
/* 140 */       .append(this.status, rhs.status)
/* 141 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 148 */     return new HashCodeBuilder(-82280557, -700257973)
/* 149 */       .append(this.bookSnId)
/* 150 */       .append(this.bookSN)
/* 151 */       .append(this.status)
/* 152 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 159 */     return new ToStringBuilder(this)
/* 160 */       .append("bookSnId", this.bookSnId)
/* 161 */       .append("bookSN", this.bookSN)
/* 162 */       .append("status", this.status)
/* 163 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.BookSn
 * JD-Core Version:    0.6.0
 */