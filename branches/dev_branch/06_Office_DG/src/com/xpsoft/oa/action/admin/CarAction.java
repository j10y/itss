/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.Car;
/*     */ import com.xpsoft.oa.service.admin.CarService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class CarAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private CarService carService;
/*     */   private Car car;
/*     */   private Long carId;
/*     */ 
/*     */   public Long getCarId()
/*     */   {
/*  35 */     return this.carId;
/*     */   }
/*     */ 
/*     */   public void setCarId(Long carId) {
/*  39 */     this.carId = carId;
/*     */   }
/*     */ 
/*     */   public Car getCar() {
/*  43 */     return this.car;
/*     */   }
/*     */ 
/*     */   public void setCar(Car car) {
/*  47 */     this.car = car;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  54 */     QueryFilter filter = new QueryFilter(getRequest());
/*  55 */     List<Car> list = this.carService.getAll(filter);
/*  56 */     Type type = new TypeToken<List<Car>>() {  }
/*  56 */     .getType();
/*  57 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  58 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  59 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
/*  60 */     buff.append(gson.toJson(list, type));
/*  61 */     buff.append("}");
/*  62 */     this.jsonString = buff.toString();
/*  63 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  70 */     String[] ids = getRequest().getParameterValues("ids");
/*  71 */     if (ids != null) {
/*  72 */       for (String id : ids) {
/*  73 */         this.carService.remove(new Long(id));
/*     */       }
/*     */     }
/*  76 */     this.jsonString = "{success:true}";
/*  77 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  85 */     Car car = (Car)this.carService.get(this.carId);
/*  86 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/*  88 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  89 */     sb.append(gson.toJson(car));
/*  90 */     sb.append("}");
/*  91 */     setJsonString(sb.toString());
/*  92 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*  98 */     this.carService.save(this.car);
/*  99 */     setJsonString("{success:true}");
/* 100 */     return "success";
/*     */   }
/*     */ 
/*     */   public String delphoto()
/*     */   {
/* 106 */     String strCarId = getRequest().getParameter("carId");
/* 107 */     if (StringUtils.isNotEmpty(strCarId)) {
/* 108 */       this.car = ((Car)this.carService.get(new Long(strCarId)));
/* 109 */       this.car.setCartImage("");
/* 110 */       this.carService.save(this.car);
/* 111 */       setJsonString("{success:true}");
/*     */     }
/* 113 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.CarAction
 * JD-Core Version:    0.6.0
 */