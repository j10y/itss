/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class GoodsApply extends BaseModel
/*     */ {
/*     */   protected Long applyId;
/*     */   protected Date applyDate;
/*     */   protected String applyNo;
/*     */   protected Integer useCounts;
/*     */   protected String proposer;
/*     */   protected Long userId;
/*     */   protected String notes;
/*     */   protected Short approvalStatus;
/*     */   protected OfficeGoods officeGoods;
/*     */ 
/*     */   public GoodsApply()
/*     */   {
/*     */   }
/*     */ 
/*     */   public GoodsApply(Long in_applyId)
/*     */   {
/*  44 */     setApplyId(in_applyId);
/*     */   }
/*     */ 
/*     */   public OfficeGoods getOfficeGoods()
/*     */   {
/*  49 */     return this.officeGoods;
/*     */   }
/*     */ 
/*     */   public void setOfficeGoods(OfficeGoods in_officeGoods) {
/*  53 */     this.officeGoods = in_officeGoods;
/*     */   }
/*     */ 
/*     */   public Long getUserId() {
/*  57 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long userId) {
/*  61 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */   public Long getApplyId()
/*     */   {
/*  68 */     return this.applyId;
/*     */   }
/*     */ 
/*     */   public void setApplyId(Long aValue)
/*     */   {
/*  75 */     this.applyId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getGoodsId()
/*     */   {
/*  82 */     return getOfficeGoods() == null ? null : getOfficeGoods().getGoodsId();
/*     */   }
/*     */ 
/*     */   public void setGoodsId(Long aValue)
/*     */   {
/*  89 */     if (aValue == null) {
/*  90 */       this.officeGoods = null;
/*  91 */     } else if (this.officeGoods == null) {
/*  92 */       this.officeGoods = new OfficeGoods(aValue);
/*  93 */       this.officeGoods.setVersion(new Integer(0));
/*     */     } else {
/*  95 */       this.officeGoods.setGoodsId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getApplyDate()
/*     */   {
/* 104 */     return this.applyDate;
/*     */   }
/*     */ 
/*     */   public void setApplyDate(Date aValue)
/*     */   {
/* 112 */     this.applyDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getApplyNo()
/*     */   {
/* 120 */     return this.applyNo;
/*     */   }
/*     */ 
/*     */   public void setApplyNo(String aValue)
/*     */   {
/* 128 */     this.applyNo = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getUseCounts()
/*     */   {
/* 136 */     return this.useCounts;
/*     */   }
/*     */ 
/*     */   public void setUseCounts(Integer aValue)
/*     */   {
/* 144 */     this.useCounts = aValue;
/*     */   }
/*     */ 
/*     */   public String getProposer()
/*     */   {
/* 152 */     return this.proposer;
/*     */   }
/*     */ 
/*     */   public void setProposer(String aValue)
/*     */   {
/* 160 */     this.proposer = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 168 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 175 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public Short getApprovalStatus()
/*     */   {
/* 186 */     return this.approvalStatus;
/*     */   }
/*     */ 
/*     */   public void setApprovalStatus(Short aValue)
/*     */   {
/* 194 */     this.approvalStatus = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 201 */     if (!(object instanceof GoodsApply)) {
/* 202 */       return false;
/*     */     }
/* 204 */     GoodsApply rhs = (GoodsApply)object;
/* 205 */     return new EqualsBuilder()
/* 206 */       .append(this.applyId, rhs.applyId)
/* 207 */       .append(this.applyDate, rhs.applyDate)
/* 208 */       .append(this.applyNo, rhs.applyNo)
/* 209 */       .append(this.useCounts, rhs.useCounts)
/* 210 */       .append(this.userId, rhs.userId)
/* 211 */       .append(this.proposer, rhs.proposer)
/* 212 */       .append(this.notes, rhs.notes)
/* 213 */       .append(this.approvalStatus, rhs.approvalStatus)
/* 214 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 221 */     return new HashCodeBuilder(-82280557, -700257973)
/* 222 */       .append(this.applyId)
/* 223 */       .append(this.applyDate)
/* 224 */       .append(this.applyNo)
/* 225 */       .append(this.useCounts)
/* 226 */       .append(this.proposer)
/* 227 */       .append(this.userId)
/* 228 */       .append(this.notes)
/* 229 */       .append(this.approvalStatus)
/* 230 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 237 */     return new ToStringBuilder(this)
/* 238 */       .append("applyId", this.applyId)
/* 239 */       .append("applyDate", this.applyDate)
/* 240 */       .append("applyNo", this.applyNo)
/* 241 */       .append("useCounts", this.useCounts)
/* 242 */       .append("proposer", this.proposer)
/* 243 */       .append("userId", this.userId)
/* 244 */       .append("notes", this.notes)
/* 245 */       .append("approvalStatus", this.approvalStatus)
/* 246 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.GoodsApply
 * JD-Core Version:    0.6.0
 */