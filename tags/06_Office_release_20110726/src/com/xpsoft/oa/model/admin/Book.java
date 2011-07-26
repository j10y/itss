/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Book extends BaseModel
/*     */ {
/*     */   protected Long bookId;
/*     */   protected String bookName;
/*     */   protected String author;
/*     */   protected String isbn;
/*     */   protected String publisher;
/*     */   protected BigDecimal price;
/*     */   protected String location;
/*     */   protected String department;
/*     */   protected Integer amount;
/*     */   protected Integer leftAmount;
/*     */   protected BookType bookType;
/*  32 */   protected Set bookSns = new HashSet();
/*     */ 
/*     */   public Book()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Book(Long in_bookId)
/*     */   {
/*  47 */     setBookId(in_bookId);
/*     */   }
/*     */ 
/*     */   public BookType getBookType()
/*     */   {
/*  52 */     return this.bookType;
/*     */   }
/*     */ 
/*     */   public void setBookType(BookType in_bookType) {
/*  56 */     this.bookType = in_bookType;
/*     */   }
/*     */ 
/*     */   public Set getBookSns() {
/*  60 */     return this.bookSns;
/*     */   }
/*     */ 
/*     */   public void setBookSns(Set in_bookSns) {
/*  64 */     this.bookSns = in_bookSns;
/*     */   }
/*     */ 
/*     */   public Long getBookId()
/*     */   {
/*  73 */     return this.bookId;
/*     */   }
/*     */ 
/*     */   public void setBookId(Long aValue)
/*     */   {
/*  80 */     this.bookId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  87 */     return getBookType() == null ? null : getBookType().getTypeId();
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/*  94 */     if (aValue == null) {
/*  95 */       this.bookType = null;
/*  96 */     } else if (this.bookType == null) {
/*  97 */       this.bookType = new BookType(aValue);
/*  98 */       this.bookType.setVersion(new Integer(0));
/*     */     } else {
/* 100 */       this.bookType.setTypeId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getBookName()
/*     */   {
/* 109 */     return this.bookName;
/*     */   }
/*     */ 
/*     */   public void setBookName(String aValue)
/*     */   {
/* 117 */     this.bookName = aValue;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 125 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String aValue)
/*     */   {
/* 133 */     this.author = aValue;
/*     */   }
/*     */ 
/*     */   public String getIsbn()
/*     */   {
/* 141 */     return this.isbn;
/*     */   }
/*     */ 
/*     */   public void setIsbn(String aValue)
/*     */   {
/* 149 */     this.isbn = aValue;
/*     */   }
/*     */ 
/*     */   public String getPublisher()
/*     */   {
/* 157 */     return this.publisher;
/*     */   }
/*     */ 
/*     */   public void setPublisher(String aValue)
/*     */   {
/* 164 */     this.publisher = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getPrice()
/*     */   {
/* 172 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(BigDecimal aValue)
/*     */   {
/* 180 */     this.price = aValue;
/*     */   }
/*     */ 
/*     */   public String getLocation()
/*     */   {
/* 188 */     return this.location;
/*     */   }
/*     */ 
/*     */   public void setLocation(String aValue)
/*     */   {
/* 196 */     this.location = aValue;
/*     */   }
/*     */ 
/*     */   public String getDepartment()
/*     */   {
/* 204 */     return this.department;
/*     */   }
/*     */ 
/*     */   public void setDepartment(String aValue)
/*     */   {
/* 212 */     this.department = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getAmount()
/*     */   {
/* 220 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(Integer aValue)
/*     */   {
/* 228 */     this.amount = aValue;
/*     */   }
/*     */   public Integer getLeftAmount() {
/* 231 */     return this.leftAmount;
/*     */   }
/*     */ 
/*     */   public void setLeftAmount(Integer leftAmount) {
/* 235 */     this.leftAmount = leftAmount;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 241 */     if (!(object instanceof Book)) {
/* 242 */       return false;
/*     */     }
/* 244 */     Book rhs = (Book)object;
/* 245 */     return new EqualsBuilder()
/* 246 */       .append(this.bookId, rhs.bookId)
/* 247 */       .append(this.bookName, rhs.bookName)
/* 248 */       .append(this.author, rhs.author)
/* 249 */       .append(this.isbn, rhs.isbn)
/* 250 */       .append(this.publisher, rhs.publisher)
/* 251 */       .append(this.price, rhs.price)
/* 252 */       .append(this.location, rhs.location)
/* 253 */       .append(this.department, rhs.department)
/* 254 */       .append(this.amount, rhs.amount)
/* 255 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 262 */     return new HashCodeBuilder(-82280557, -700257973)
/* 263 */       .append(this.bookId)
/* 264 */       .append(this.bookName)
/* 265 */       .append(this.author)
/* 266 */       .append(this.isbn)
/* 267 */       .append(this.publisher)
/* 268 */       .append(this.price)
/* 269 */       .append(this.location)
/* 270 */       .append(this.department)
/* 271 */       .append(this.amount)
/* 272 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 279 */     return new ToStringBuilder(this)
/* 280 */       .append("bookId", this.bookId)
/* 281 */       .append("bookName", this.bookName)
/* 282 */       .append("author", this.author)
/* 283 */       .append("isbn", this.isbn)
/* 284 */       .append("publisher", this.publisher)
/* 285 */       .append("price", this.price)
/* 286 */       .append("location", this.location)
/* 287 */       .append("department", this.department)
/* 288 */       .append("amount", this.amount)
/* 289 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.Book
 * JD-Core Version:    0.6.0
 */