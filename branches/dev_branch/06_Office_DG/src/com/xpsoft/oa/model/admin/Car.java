/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Car extends BaseModel
/*     */ {
/*  26 */   public static short PASS_APPLY = 1;
/*  27 */   public static short NOTPASS_APPLY = 0;
/*     */ 
/*     */   @Expose
/*     */   protected Long carId;
/*     */ 
/*     */   @Expose
/*     */   protected String carNo;
/*     */ 
/*     */   @Expose
/*     */   protected String carType;
/*     */ 
/*     */   @Expose
/*     */   protected String engineNo;
/*     */ 
/*     */   @Expose
/*     */   protected Date buyInsureTime;
/*     */ 
/*     */   @Expose
/*     */   protected Date auditTime;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */ 
/*     */   @Expose
/*     */   protected String factoryModel;
/*     */ 
/*     */   @Expose
/*     */   protected String driver;
/*     */ 
/*     */   @Expose
/*     */   protected Date buyDate;
/*     */ 
/*     */   @Expose
/*     */   protected Short status;
/*     */ 
/*     */   @Expose
/*     */   protected String cartImage;
/*  52 */   protected Set<CarApply> carApplys = new HashSet();
/*  53 */   protected Set<CartRepair> cartRepairs = new HashSet();
/*     */ 
/*     */   public Set<CarApply> getCarApplys()
/*     */   {
/*  57 */     return this.carApplys;
/*     */   }
/*     */ 
/*     */   public void setCarApplys(Set<CarApply> carApplys) {
/*  61 */     this.carApplys = carApplys;
/*     */   }
/*     */ 
/*     */   public Set<CartRepair> getCartRepairs() {
/*  65 */     return this.cartRepairs;
/*     */   }
/*     */ 
/*     */   public void setCartRepairs(Set<CartRepair> cartRepairs) {
/*  69 */     this.cartRepairs = cartRepairs;
/*     */   }
/*     */ 
/*     */   public Car()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Car(Long in_carId)
/*     */   {
/*  85 */     setCarId(in_carId);
/*     */   }
/*     */ 
/*     */   public Long getCarId()
/*     */   {
/*  93 */     return this.carId;
/*     */   }
/*     */ 
/*     */   public void setCarId(Long aValue)
/*     */   {
/* 100 */     this.carId = aValue;
/*     */   }
/*     */ 
/*     */   public String getCarNo()
/*     */   {
/* 108 */     return this.carNo;
/*     */   }
/*     */ 
/*     */   public void setCarNo(String aValue)
/*     */   {
/* 116 */     this.carNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getCarType()
/*     */   {
/* 127 */     return this.carType;
/*     */   }
/*     */ 
/*     */   public void setCarType(String aValue)
/*     */   {
/* 135 */     this.carType = aValue;
/*     */   }
/*     */ 
/*     */   public String getEngineNo()
/*     */   {
/* 143 */     return this.engineNo;
/*     */   }
/*     */ 
/*     */   public void setEngineNo(String aValue)
/*     */   {
/* 150 */     this.engineNo = aValue;
/*     */   }
/*     */ 
/*     */   public Date getBuyInsureTime()
/*     */   {
/* 158 */     return this.buyInsureTime;
/*     */   }
/*     */ 
/*     */   public void setBuyInsureTime(Date aValue)
/*     */   {
/* 165 */     this.buyInsureTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getAuditTime()
/*     */   {
/* 173 */     return this.auditTime;
/*     */   }
/*     */ 
/*     */   public void setAuditTime(Date aValue)
/*     */   {
/* 180 */     this.auditTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 188 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 195 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public String getFactoryModel()
/*     */   {
/* 203 */     return this.factoryModel;
/*     */   }
/*     */ 
/*     */   public void setFactoryModel(String aValue)
/*     */   {
/* 211 */     this.factoryModel = aValue;
/*     */   }
/*     */ 
/*     */   public String getDriver()
/*     */   {
/* 219 */     return this.driver;
/*     */   }
/*     */ 
/*     */   public void setDriver(String aValue)
/*     */   {
/* 227 */     this.driver = aValue;
/*     */   }
/*     */ 
/*     */   public Date getBuyDate()
/*     */   {
/* 235 */     return this.buyDate;
/*     */   }
/*     */ 
/*     */   public void setBuyDate(Date aValue)
/*     */   {
/* 243 */     this.buyDate = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 254 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 262 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public String getCartImage()
/*     */   {
/* 270 */     return this.cartImage;
/*     */   }
/*     */ 
/*     */   public void setCartImage(String aValue)
/*     */   {
/* 277 */     this.cartImage = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 284 */     if (!(object instanceof Car)) {
/* 285 */       return false;
/*     */     }
/* 287 */     Car rhs = (Car)object;
/* 288 */     return new EqualsBuilder()
/* 289 */       .append(this.carId, rhs.carId)
/* 290 */       .append(this.carNo, rhs.carNo)
/* 291 */       .append(this.carType, rhs.carType)
/* 292 */       .append(this.engineNo, rhs.engineNo)
/* 293 */       .append(this.buyInsureTime, rhs.buyInsureTime)
/* 294 */       .append(this.auditTime, rhs.auditTime)
/* 295 */       .append(this.notes, rhs.notes)
/* 296 */       .append(this.factoryModel, rhs.factoryModel)
/* 297 */       .append(this.driver, rhs.driver)
/* 298 */       .append(this.buyDate, rhs.buyDate)
/* 299 */       .append(this.status, rhs.status)
/* 300 */       .append(this.cartImage, rhs.cartImage)
/* 301 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 308 */     return new HashCodeBuilder(-82280557, -700257973)
/* 309 */       .append(this.carId)
/* 310 */       .append(this.carNo)
/* 311 */       .append(this.carType)
/* 312 */       .append(this.engineNo)
/* 313 */       .append(this.buyInsureTime)
/* 314 */       .append(this.auditTime)
/* 315 */       .append(this.notes)
/* 316 */       .append(this.factoryModel)
/* 317 */       .append(this.driver)
/* 318 */       .append(this.buyDate)
/* 319 */       .append(this.status)
/* 320 */       .append(this.cartImage)
/* 321 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 328 */     return new ToStringBuilder(this)
/* 329 */       .append("carId", this.carId)
/* 330 */       .append("carNo", this.carNo)
/* 331 */       .append("carType", this.carType)
/* 332 */       .append("engineNo", this.engineNo)
/* 333 */       .append("buyInsureTime", this.buyInsureTime)
/* 334 */       .append("auditTime", this.auditTime)
/* 335 */       .append("notes", this.notes)
/* 336 */       .append("factoryModel", this.factoryModel)
/* 337 */       .append("driver", this.driver)
/* 338 */       .append("buyDate", this.buyDate)
/* 339 */       .append("status", this.status)
/* 340 */       .append("cartImage", this.cartImage)
/* 341 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.Car
 * JD-Core Version:    0.6.0
 */