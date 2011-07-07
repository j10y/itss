/*     */ package com.htsoft.oa.model.admin;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class BookBorRet extends BaseModel
/*     */ {
/*     */   protected Long recordId;
/*     */   protected Date borrowTime;
/*     */   protected Date returnTime;
/*     */   protected Date lastReturnTime;
/*     */   protected String borrowIsbn;
/*     */   protected String bookName;
/*     */   protected String registerName;
/*     */   protected String fullname;
/*     */   protected BookSn bookSn;
/*     */ 
/*     */   public BookBorRet()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BookBorRet(Long in_recordId)
/*     */   {
/*  47 */     setRecordId(in_recordId);
/*     */   }
/*     */ 
/*     */   public BookSn getBookSn()
/*     */   {
/*  52 */     return this.bookSn;
/*     */   }
/*     */ 
/*     */   public void setBookSn(BookSn in_bookSn) {
/*  56 */     this.bookSn = in_bookSn;
/*     */   }
/*     */ 
/*     */   public String getRegisterName() {
/*  60 */     return this.registerName;
/*     */   }
/*     */ 
/*     */   public void setRegisterName(String registerName) {
/*  64 */     this.registerName = registerName;
/*     */   }
/*     */ 
/*     */   public String getFullname() {
/*  68 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String fullname) {
/*  72 */     this.fullname = fullname;
/*     */   }
/*     */ 
/*     */   public Long getRecordId()
/*     */   {
/*  79 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long aValue)
/*     */   {
/*  86 */     this.recordId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getBookSnId()
/*     */   {
/*  93 */     return getBookSn() == null ? null : getBookSn().getBookSnId();
/*     */   }
/*     */ 
/*     */   public void setBookSnId(Long aValue)
/*     */   {
/* 100 */     if (aValue == null) {
/* 101 */       this.bookSn = null;
/* 102 */     } else if (this.bookSn == null) {
/* 103 */       this.bookSn = new BookSn(aValue);
/* 104 */       this.bookSn.setVersion(new Integer(0));
/*     */     } else {
/* 106 */       this.bookSn.setBookSnId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getBorrowTime()
/*     */   {
/* 115 */     return this.borrowTime;
/*     */   }
/*     */ 
/*     */   public void setBorrowTime(Date aValue)
/*     */   {
/* 123 */     this.borrowTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getReturnTime()
/*     */   {
/* 131 */     return this.returnTime;
/*     */   }
/*     */ 
/*     */   public void setReturnTime(Date aValue)
/*     */   {
/* 139 */     this.returnTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getLastReturnTime()
/*     */   {
/* 147 */     return this.lastReturnTime;
/*     */   }
/*     */ 
/*     */   public void setLastReturnTime(Date aValue)
/*     */   {
/* 154 */     this.lastReturnTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getBorrowIsbn()
/*     */   {
/* 162 */     return this.borrowIsbn;
/*     */   }
/*     */ 
/*     */   public void setBorrowIsbn(String aValue)
/*     */   {
/* 170 */     this.borrowIsbn = aValue;
/*     */   }
/*     */ 
/*     */   public String getBookName()
/*     */   {
/* 178 */     return this.bookName;
/*     */   }
/*     */ 
/*     */   public void setBookName(String aValue)
/*     */   {
/* 186 */     this.bookName = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 193 */     if (!(object instanceof BookBorRet)) {
/* 194 */       return false;
/*     */     }
/* 196 */     BookBorRet rhs = (BookBorRet)object;
/* 197 */     return new EqualsBuilder()
/* 198 */       .append(this.recordId, rhs.recordId)
/* 199 */       .append(this.borrowTime, rhs.borrowTime)
/* 200 */       .append(this.returnTime, rhs.returnTime)
/* 201 */       .append(this.lastReturnTime, rhs.lastReturnTime)
/* 202 */       .append(this.borrowIsbn, rhs.borrowIsbn)
/* 203 */       .append(this.bookName, rhs.bookName)
/* 204 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 211 */     return new HashCodeBuilder(-82280557, -700257973)
/* 212 */       .append(this.recordId)
/* 213 */       .append(this.borrowTime)
/* 214 */       .append(this.returnTime)
/* 215 */       .append(this.lastReturnTime)
/* 216 */       .append(this.borrowIsbn)
/* 217 */       .append(this.bookName)
/* 218 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 225 */     return new ToStringBuilder(this)
/* 226 */       .append("recordId", this.recordId)
/* 227 */       .append("borrowTime", this.borrowTime)
/* 228 */       .append("returnTime", this.returnTime)
/* 229 */       .append("lastReturnTime", this.lastReturnTime)
/* 230 */       .append("borrowIsbn", this.borrowIsbn)
/* 231 */       .append("bookName", this.bookName)
/* 232 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.admin.BookBorRet
 * JD-Core Version:    0.6.0
 */