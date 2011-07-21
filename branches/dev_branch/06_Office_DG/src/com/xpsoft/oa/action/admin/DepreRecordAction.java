/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.DepreRecord;
/*     */ import com.xpsoft.oa.model.admin.DepreType;
/*     */ import com.xpsoft.oa.model.admin.FixedAssets;
/*     */ import com.xpsoft.oa.service.admin.DepreRecordService;
/*     */ import com.xpsoft.oa.service.admin.FixedAssetsService;
/*     */ import flexjson.DateTransformer;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class DepreRecordAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DepreRecordService depreRecordService;
/*     */   private DepreRecord depreRecord;
/*     */ 
/*     */   @Resource
/*     */   private FixedAssetsService fixedAssetsService;
/*     */   private Long recordId;
/*  47 */   static int YEARS = 11;
/*     */ 
/*     */   public Long getRecordId() {
/*  50 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long recordId) {
/*  54 */     this.recordId = recordId;
/*     */   }
/*     */ 
/*     */   public DepreRecord getDepreRecord() {
/*  58 */     return this.depreRecord;
/*     */   }
/*     */ 
/*     */   public void setDepreRecord(DepreRecord depreRecord) {
/*  62 */     this.depreRecord = depreRecord;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  70 */     QueryFilter filter = new QueryFilter(getRequest());
/*  71 */     List list = this.depreRecordService.getAll(filter);
/*  72 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  73 */       .append(filter.getPagingBean().getTotalItems()).append(
/*  74 */       ",result:");
/*  75 */     JSONSerializer serializer = new JSONSerializer();
/*  76 */     serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), new String[] { 
/*  77 */       "calTime" });
/*  78 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(
/*  79 */       list));
/*  80 */     buff.append("}");
/*  81 */     this.jsonString = buff.toString();
/*  82 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  92 */     String[] ids = getRequest().getParameterValues("ids");
/*  93 */     if (ids != null) {
/*  94 */       for (String id : ids) {
/*  95 */         this.depreRecordService.remove(new Long(id));
/*     */       }
/*     */     }
/*  98 */     this.jsonString = "{success:true}";
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 108 */     DepreRecord depreRecord = (DepreRecord)this.depreRecordService.get(this.recordId);
/* 109 */     Gson gson = new Gson();
/*     */ 
/* 111 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 112 */     sb.append(gson.toJson(depreRecord));
/* 113 */     sb.append("}");
/* 114 */     setJsonString(sb.toString());
/* 115 */     return "success";
/*     */   }
/*     */ 
/*     */   public String depreciate()
/*     */   {
/* 156 */     String strAssetsId = getRequest().getParameter("ids");
/* 157 */     if (StringUtils.isNotEmpty(strAssetsId)) {
/* 158 */       FixedAssets fixedAssets = (FixedAssets)this.fixedAssetsService.get(
/* 159 */         new Long(strAssetsId));
/* 160 */       BigDecimal cruValue = fixedAssets.getAssetCurValue();
/* 161 */       BigDecimal assetValue = fixedAssets.getAssetValue();
/* 162 */       short method = fixedAssets.getDepreType().getCalMethod().shortValue();
/* 163 */       Integer i = Integer.valueOf(0);
/* 164 */       if (method == 1) {
/* 165 */         BigDecimal yearRate = new BigDecimal(0);
/* 166 */         yearRate = fixedAssets.getDepreRate();
/* 167 */         BigDecimal monthRate = yearRate
/* 168 */           .divide(new BigDecimal(12), 2, 2);
/* 169 */         Date lastCalTime = this.depreRecordService.findMaxDate(
/* 170 */           new Long(strAssetsId));
/* 171 */         if (lastCalTime == null) {
/* 172 */           lastCalTime = fixedAssets.getStartDepre();
/*     */         }
/* 174 */         Integer deprePeriod = fixedAssets.getDepreType()
/* 175 */           .getDeprePeriod();
/* 176 */         BigDecimal value = assetValue.multiply(
/* 177 */           new BigDecimal(deprePeriod.toString())).multiply(
/* 178 */           monthRate);
/* 179 */         GregorianCalendar gc1 = new GregorianCalendar();
/* 180 */         gc1.setTime(lastCalTime);
/* 181 */         GregorianCalendar gcYears = new GregorianCalendar();
/* 182 */         gcYears.setTime(fixedAssets.getStartDepre());
/* 183 */         Integer years = Integer.valueOf(fixedAssets.getIntendTerm().intValue());
/* 184 */         gcYears.add(1, years.intValue());
/* 185 */         while (deprePeriod.intValue() >= 1) {
/* 186 */           gc1.add(2, deprePeriod.intValue());
/* 187 */           Date curDate = gc1.getTime();
/* 188 */           if ((curDate.after(new Date())) || 
/* 189 */             (curDate.after(gcYears.getTime()))) {
/*     */             break;
/*     */           }
/* 192 */           i = Integer.valueOf(i.intValue() + 1);
/* 193 */           DepreRecord depreR = new DepreRecord();
/* 194 */           depreR.setFixedAssets(fixedAssets);
/* 195 */           depreR.setCalTime(curDate);
/* 196 */           cruValue = cruValue.subtract(value);
/* 197 */           if (cruValue.compareTo(new BigDecimal("0.001")) == -1) {
/*     */             break;
/*     */           }
/* 200 */           depreR.setDepreAmount(value);
/* 201 */           this.depreRecordService.save(depreR);
/*     */         }
/*     */       }
/* 204 */       else if (method == 2) {
/* 205 */         i = Integer.valueOf(i.intValue() + 1);
/* 206 */         String cruCalDate = getRequest().getParameter("cruCalDate");
/* 207 */         if (StringUtils.isNotEmpty(cruCalDate)) {
/* 208 */           SimpleDateFormat sdf = new SimpleDateFormat(
/* 209 */             "yyyy-MM-dd HH:mm:ss");
/* 210 */           Date calDate = new Date();
/*     */           try {
/* 212 */             calDate = sdf.parse(cruCalDate);
/*     */           } catch (ParseException e) {
/* 214 */             e.printStackTrace();
/* 215 */             setJsonString("{success:false}");
/* 216 */             return "success";
/*     */           }
/* 218 */           BigDecimal total = new BigDecimal(1).subtract(
/* 219 */             fixedAssets.getRemainValRate().divide(
/* 220 */             new BigDecimal(100))).multiply(
/* 221 */             fixedAssets.getAssetValue());
/* 222 */           BigDecimal per = total.divide(fixedAssets
/* 223 */             .getIntendWorkGross(), 2, 2);
/* 224 */           cruValue = cruValue.subtract(per.multiply(this.depreRecord
/* 225 */             .getWorkCapacity()));
/*     */ 
/* 227 */           this.depreRecord.setCalTime(calDate);
/* 228 */           this.depreRecord.setFixedAssets(fixedAssets);
/* 229 */           this.depreRecord.setDepreAmount(per.multiply(this.depreRecord
/* 230 */             .getWorkCapacity()));
/* 231 */           this.depreRecordService.save(this.depreRecord);
/*     */         } else {
/* 233 */           setJsonString("{success:false}");
/* 234 */           return "success";
/*     */         }
/* 236 */       } else if (method == 3) {
/* 237 */         Integer deprePeriod = fixedAssets.getDepreType()
/* 238 */           .getDeprePeriod();
/* 239 */         Date lastCalTime = this.depreRecordService.findMaxDate(
/* 240 */           new Long(strAssetsId));
/* 241 */         if (lastCalTime == null) {
/* 242 */           lastCalTime = fixedAssets.getStartDepre();
/*     */         }
/* 244 */         Date startDepre = fixedAssets.getStartDepre();
/* 245 */         GregorianCalendar gc1 = new GregorianCalendar();
/* 246 */         GregorianCalendar gcYear = new GregorianCalendar();
/* 247 */         GregorianCalendar gcYears = new GregorianCalendar();
/* 248 */         BigDecimal yearRate = new BigDecimal(2).divide(fixedAssets
/* 249 */           .getIntendTerm(), 2, 3);
/* 250 */         BigDecimal monthRate = yearRate
/* 251 */           .divide(new BigDecimal(12), 2, 3);
/* 252 */         gcYear.setTime(startDepre);
/* 253 */         Integer years = Integer.valueOf(fixedAssets.getIntendTerm().intValue());
/* 254 */         if (years.intValue() > 2) {
/* 255 */           gcYear.add(1, years.intValue() - 2);
/*     */         }
/* 257 */         gcYears.setTime(startDepre);
/* 258 */         gcYears.add(1, years.intValue());
/* 259 */         gc1.setTime(lastCalTime);
/* 260 */         Integer flag = Integer.valueOf(0);
/* 261 */         BigDecimal twoYearValue = new BigDecimal(0);
/* 262 */         while (deprePeriod.intValue() > 0) {
/* 263 */           DepreRecord depreR = new DepreRecord();
/* 264 */           BigDecimal bd = new BigDecimal(0);
/* 265 */           Date last = gc1.getTime();
/* 266 */           gc1.add(2, deprePeriod.intValue());
/* 267 */           if ((gc1.getTime().after(new Date())) || 
/* 268 */             (gc1.getTime().after(gcYears.getTime()))) {
/*     */             break;
/*     */           }
/* 271 */           i = Integer.valueOf(i.intValue() + 1);
/* 272 */           if (!gc1.getTime().after(gcYear.getTime())) {
/* 273 */             bd = cruValue.multiply(monthRate).multiply(
/* 274 */               new BigDecimal(deprePeriod.toString()));
/* 275 */             cruValue = cruValue.subtract(bd);
/*     */           } else {
/* 277 */             GregorianCalendar lastGc = new GregorianCalendar();
/* 278 */             lastGc.setTime(last);
/* 279 */             Integer j = Integer.valueOf(0);
/* 280 */             Date lastDate = lastGc.getTime();
/* 281 */             Date gcDate = gcYear.getTime();
/*     */ 
/* 283 */             while (lastDate.before(gcDate)) {
/* 284 */               lastGc.add(2, 1);
/* 285 */               cruValue = cruValue.subtract(cruValue
/* 286 */                 .multiply(monthRate));
/* 287 */               bd = bd.add(cruValue.multiply(monthRate));
/* 288 */               j = Integer.valueOf(j.intValue() + 1);
/*     */             }
/* 290 */             if (deprePeriod.intValue() - j.intValue() > 0)
/*     */             {
/* 292 */               if (flag.intValue() == 0) {
/* 293 */                 twoYearValue = cruValue.subtract(cruValue
/* 294 */                   .multiply(fixedAssets
/* 295 */                   .getRemainValRate().divide(
/* 296 */                   new BigDecimal(100), 2, 
/* 297 */                   2)));
/*     */               }
/* 299 */               flag = Integer.valueOf(flag.intValue() + 1);
/* 300 */               Integer w = Integer.valueOf(deprePeriod.intValue() - j.intValue());
/* 301 */               if (fixedAssets.getIntendTerm().intValue() > 1) {
/* 302 */                 bd = bd.add(twoYearValue.divide(
/* 303 */                   new BigDecimal(24), 2, 3).multiply(
/* 304 */                   new BigDecimal(w.toString())));
/* 305 */                 cruValue = cruValue
/* 306 */                   .subtract(twoYearValue.divide(
/* 307 */                   new BigDecimal(24), 2, 3)
/* 308 */                   .multiply(
/* 309 */                   new BigDecimal(w
/* 310 */                   .toString())));
/*     */               } else {
/* 312 */                 bd = bd.add(twoYearValue.divide(
/* 313 */                   new BigDecimal(12), 2, 3).multiply(
/* 314 */                   new BigDecimal(w.toString())));
/* 315 */                 cruValue = cruValue
/* 316 */                   .subtract(twoYearValue.divide(
/* 317 */                   new BigDecimal(12), 2, 3)
/* 318 */                   .multiply(
/* 319 */                   new BigDecimal(w
/* 320 */                   .toString())));
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 325 */           Date calTime = gc1.getTime();
/* 326 */           depreR.setCalTime(calTime);
/* 327 */           depreR.setFixedAssets(fixedAssets);
/* 328 */           depreR.setDepreAmount(bd);
/* 329 */           this.depreRecordService.save(depreR);
/*     */         }
/* 331 */       } else if (method == 4) {
/* 332 */         Integer deprePeriod = fixedAssets.getDepreType()
/* 333 */           .getDeprePeriod();
/* 334 */         Date lastCalTime = this.depreRecordService.findMaxDate(
/* 335 */           new Long(strAssetsId));
/* 336 */         if (lastCalTime == null) {
/* 337 */           lastCalTime = fixedAssets.getStartDepre();
/*     */         }
/* 339 */         Date startDepre = fixedAssets.getStartDepre();
/* 340 */         BigDecimal intendTerm = fixedAssets.getIntendTerm();
/* 341 */         BigDecimal total = intendTerm.multiply(
/* 342 */           intendTerm.add(new BigDecimal(1))).divide(
/* 343 */           new BigDecimal(2));
/* 344 */         GregorianCalendar gc1 = new GregorianCalendar();
/* 345 */         GregorianCalendar gcStart = new GregorianCalendar();
/* 346 */         gcStart.setTime(startDepre);
/* 347 */         gc1.setTime(lastCalTime);
/* 348 */         GregorianCalendar gcYears = new GregorianCalendar();
/* 349 */         gcYears.setTime(fixedAssets.getStartDepre());
/* 350 */         Integer years = Integer.valueOf(fixedAssets.getIntendTerm().intValue());
/* 351 */         gcYears.add(1, years.intValue());
/* 352 */         BigDecimal stValue = fixedAssets.getAssetValue().multiply(
/* 353 */           new BigDecimal(1).subtract(fixedAssets
/* 354 */           .getRemainValRate().divide(new BigDecimal(100), 
/* 355 */           2, 2)));
/* 356 */         while (deprePeriod.intValue() > 0) {
/* 357 */           Date last = gc1.getTime();
/* 358 */           GregorianCalendar gcLast = new GregorianCalendar();
/* 359 */           gcLast.setTime(last);
/* 360 */           gc1.add(2, deprePeriod.intValue());
/*     */ 
/* 362 */           BigDecimal depValue = new BigDecimal(0);
/* 363 */           Integer jian = Integer.valueOf(gc1.get(1) - 
/* 364 */             gcLast.get(1));
/* 365 */           if ((gc1.getTime().after(new Date())) || 
/* 366 */             (gc1.getTime().after(gcYears.getTime()))) {
/*     */             break;
/*     */           }
/* 369 */           i = Integer.valueOf(i.intValue() + 1);
/* 370 */           Integer be = Integer.valueOf(gc1.get(1) - 
/* 371 */             gcStart.get(1));
/* 372 */           BigDecimal rate = intendTerm.subtract(new BigDecimal(be.intValue()))
/* 373 */             .divide(total, 2, 2);
/* 374 */           if (jian.intValue() == 0) {
/* 375 */             depValue = stValue.multiply(rate).multiply(
/* 376 */               new BigDecimal(deprePeriod.intValue()).divide(
/* 377 */               new BigDecimal(12), 2, 2));
/* 378 */             cruValue = cruValue.subtract(depValue);
/*     */           } else {
/* 380 */             Integer beLast = Integer.valueOf(gcLast.get(1) - 
/* 381 */               gcStart.get(1));
/* 382 */             BigDecimal rateLast = intendTerm.subtract(
/* 383 */               new BigDecimal(beLast.intValue())).divide(total, 2, 2);
/* 384 */             Integer months = Integer.valueOf(YEARS - gcLast.get(2));
/* 385 */             depValue = stValue.multiply(rateLast).multiply(
/* 386 */               new BigDecimal(months.intValue()).divide(
/* 387 */               new BigDecimal(12), 2, 2));
/*     */ 
/* 389 */             Integer monthsNextYear = Integer.valueOf(gc1.get(2) + 1);
/* 390 */             depValue = depValue.add(stValue.multiply(rate)
/* 391 */               .multiply(
/* 392 */               new BigDecimal(monthsNextYear.intValue()).divide(
/* 393 */               new BigDecimal(12), 2, 2)));
/* 394 */             cruValue = cruValue.subtract(depValue);
/*     */           }
/* 396 */           DepreRecord depreR = new DepreRecord();
/* 397 */           Date cruDate = gc1.getTime();
/* 398 */           depreR.setCalTime(cruDate);
/* 399 */           depreR.setFixedAssets(fixedAssets);
/* 400 */           depreR.setDepreAmount(depValue);
/* 401 */           this.depreRecordService.save(depreR);
/*     */         }
/*     */       }
/* 404 */       fixedAssets.setAssetCurValue(cruValue);
/* 405 */       this.fixedAssetsService.save(fixedAssets);
/* 406 */       if (i.intValue() == 0)
/* 407 */         setJsonString("{success:false,message:'还没到折算时间!'}");
/*     */       else
/* 409 */         setJsonString("{success:true}");
/*     */     }
/*     */     else {
/* 412 */       setJsonString("{success:false}");
/*     */     }
/*     */ 
/* 415 */     return "success";
/*     */   }
/*     */ 
/*     */   public String work()
/*     */   {
/* 424 */     String id = getRequest().getParameter("ids");
/* 425 */     if (StringUtils.isNotEmpty(id)) {
/* 426 */       Long assetsId = new Long(id);
/* 427 */       FixedAssets fixedAssets = (FixedAssets)this.fixedAssetsService.get(assetsId);
/* 428 */       Date lastCalTime = this.depreRecordService.findMaxDate(assetsId);
/* 429 */       if (lastCalTime == null) {
/* 430 */         lastCalTime = fixedAssets.getStartDepre();
/*     */       }
/* 432 */       if (lastCalTime != null) {
/* 433 */         Integer deprePeriod = fixedAssets.getDepreType()
/* 434 */           .getDeprePeriod();
/* 435 */         GregorianCalendar gc1 = new GregorianCalendar();
/* 436 */         gc1.setTime(lastCalTime);
/* 437 */         gc1.add(2, deprePeriod.intValue());
/* 438 */         Date cruCalTime = gc1.getTime();
/* 439 */         if (cruCalTime.before(new Date())) {
/* 440 */           SimpleDateFormat sdf = new SimpleDateFormat(
/* 441 */             "yyyy-MM-dd HH:mm:ss");
/* 442 */           String strLastDate = sdf.format(lastCalTime);
/* 443 */           String strCruDate = sdf.format(cruCalTime);
/* 444 */           String unit = fixedAssets.getWorkGrossUnit();
/* 445 */           BigDecimal defPerWorkGross = fixedAssets
/* 446 */             .getDefPerWorkGross();
/* 447 */           setJsonString("{success:true,lastCalTime:'" + strLastDate + 
/* 448 */             "',cruCalTime:'" + strCruDate + 
/* 449 */             "',workGrossUnit:'" + unit + 
/* 450 */             "',defPerWorkGross:'" + 
/* 451 */             defPerWorkGross.toString() + "'}");
/*     */         } else {
/* 453 */           setJsonString("{success:false,message:'还没到折算时间!'}");
/*     */         }
/*     */       } else {
/* 456 */         setJsonString("{success:false,message:'未设定开始执行折算时间!'}");
/*     */       }
/*     */     } else {
/* 459 */       setJsonString("{success:false,message:'请联系管理员!'}");
/*     */     }
/*     */ 
/* 462 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.DepreRecordAction
 * JD-Core Version:    0.6.0
 */