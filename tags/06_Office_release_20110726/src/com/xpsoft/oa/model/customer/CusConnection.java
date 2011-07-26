/*     */ package com.xpsoft.oa.model.customer;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class CusConnection extends BaseModel
/*     */ {
/*     */   protected Long connId;
/*     */   protected String contactor;
/*     */   protected Date startDate;
/*     */   protected Date endDate;
/*     */   protected String content;
/*     */   protected String notes;
/*     */   protected String creator;
/*     */   protected Customer customer;
/*     */ 
/*     */   public CusConnection()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CusConnection(Long in_connId)
/*     */   {
/*  43 */     setConnId(in_connId);
/*     */   }
/*     */ 
/*     */   public Customer getCustomer()
/*     */   {
/*  48 */     return this.customer;
/*     */   }
/*     */ 
/*     */   public void setCustomer(Customer in_customer) {
/*  52 */     this.customer = in_customer;
/*     */   }
/*     */ 
/*     */   public Long getConnId()
/*     */   {
/*  61 */     return this.connId;
/*     */   }
/*     */ 
/*     */   public void setConnId(Long aValue)
/*     */   {
/*  68 */     this.connId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getCustomerId()
/*     */   {
/*  75 */     return getCustomer() == null ? null : getCustomer().getCustomerId();
/*     */   }
/*     */ 
/*     */   public void setCustomerId(Long aValue)
/*     */   {
/*  82 */     if (aValue == null) {
/*  83 */       this.customer = null;
/*  84 */     } else if (this.customer == null) {
/*  85 */       this.customer = new Customer(aValue);
/*  86 */       this.customer.setVersion(new Integer(0));
/*     */     } else {
/*  88 */       this.customer.setCustomerId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getContactor()
/*     */   {
/*  97 */     return this.contactor;
/*     */   }
/*     */ 
/*     */   public void setContactor(String aValue)
/*     */   {
/* 105 */     this.contactor = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartDate()
/*     */   {
/* 113 */     return this.startDate;
/*     */   }
/*     */ 
/*     */   public void setStartDate(Date aValue)
/*     */   {
/* 121 */     this.startDate = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndDate()
/*     */   {
/* 129 */     return this.endDate;
/*     */   }
/*     */ 
/*     */   public void setEndDate(Date aValue)
/*     */   {
/* 137 */     this.endDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 145 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 153 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 161 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 168 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public String getCreator()
/*     */   {
/* 173 */     return this.creator;
/*     */   }
/*     */ 
/*     */   public void setCreator(String creator) {
/* 177 */     this.creator = creator;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 184 */     if (!(object instanceof CusConnection)) {
/* 185 */       return false;
/*     */     }
/* 187 */     CusConnection rhs = (CusConnection)object;
/* 188 */     return new EqualsBuilder()
/* 189 */       .append(this.connId, rhs.connId)
/* 190 */       .append(this.contactor, rhs.contactor)
/* 191 */       .append(this.startDate, rhs.startDate)
/* 192 */       .append(this.endDate, rhs.endDate)
/* 193 */       .append(this.content, rhs.content)
/* 194 */       .append(this.notes, rhs.notes)
/* 195 */       .append(this.creator, rhs.creator)
/* 196 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 203 */     return new HashCodeBuilder(-82280557, -700257973)
/* 204 */       .append(this.connId)
/* 205 */       .append(this.contactor)
/* 206 */       .append(this.startDate)
/* 207 */       .append(this.endDate)
/* 208 */       .append(this.content)
/* 209 */       .append(this.notes)
/* 210 */       .append(this.creator)
/* 211 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 218 */     return new ToStringBuilder(this)
/* 219 */       .append("connId", this.connId)
/* 220 */       .append("contactor", this.contactor)
/* 221 */       .append("startDate", this.startDate)
/* 222 */       .append("endDate", this.endDate)
/* 223 */       .append("content", this.content)
/* 224 */       .append("notes", this.notes)
/* 225 */       .append("creator", this.creator)
/* 226 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.customer.CusConnection
 * JD-Core Version:    0.6.0
 */