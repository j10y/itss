/*     */ package com.xpsoft.oa.action.customer;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.customer.Customer;
/*     */ import com.xpsoft.oa.service.customer.CustomerService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CustomerAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private CustomerService customerService;
/*     */   private Customer customer;
/*     */   private Long customerId;
/*     */   private String customerNo;
/*     */ 
/*     */   public Long getCustomerId()
/*     */   {
/*  34 */     return this.customerId;
/*     */   }
/*     */ 
/*     */   public void setCustomerId(Long customerId) {
/*  38 */     this.customerId = customerId;
/*     */   }
/*     */ 
/*     */   public Customer getCustomer() {
/*  42 */     return this.customer;
/*     */   }
/*     */ 
/*     */   public void setCustomer(Customer customer) {
/*  46 */     this.customer = customer;
/*     */   }
/*     */ 
/*     */   public String getCustomerNo() {
/*  50 */     return this.customerNo;
/*     */   }
/*     */ 
/*     */   public void setCustomerNo(String customerNo) {
/*  54 */     this.customerNo = customerNo;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  62 */     QueryFilter filter = new QueryFilter(getRequest());
/*  63 */     List<Customer> list = this.customerService.getAll(filter);
/*     */ 
/*  65 */     Type type = new TypeToken<List<Customer>>() {  }.getType();
/*  66 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  67 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  69 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*  70 */     buff.append(gson.toJson(list, type));
/*  71 */     buff.append("}");
/*     */ 
/*  73 */     this.jsonString = buff.toString();
/*     */ 
/*  75 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  83 */     String[] ids = getRequest().getParameterValues("ids");
/*  84 */     if (ids != null) {
/*  85 */       for (String id : ids) {
/*  86 */         this.customerService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  90 */     this.jsonString = "{success:true}";
/*     */ 
/*  92 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 100 */     Customer customer = (Customer)this.customerService.get(this.customerId);
/*     */ 
/* 102 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*     */ 
/* 104 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 105 */     sb.append(gson.toJson(customer));
/* 106 */     sb.append("}");
/* 107 */     setJsonString(sb.toString());
/*     */ 
/* 109 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 115 */     boolean pass = false;
/* 116 */     StringBuffer buff = new StringBuffer("{");
/* 117 */     if (this.customer.getCustomerId() == null) {
/* 118 */       if (this.customerService.checkCustomerNo(this.customer.getCustomerNo()))
/* 119 */         pass = true;
/* 120 */       else buff.append("msg:'客户已存在,请重新填写.',rewrite:true,"); 
/*     */     }
/*     */     else {
/* 122 */       pass = true;
/*     */     }
/* 124 */     if (pass) {
/* 125 */       this.customerService.save(this.customer);
/* 126 */       buff.append("success:true,customerId:" + this.customer.getCustomerId() + "}");
/*     */     } else {
/* 128 */       buff.append("failure:true}");
/*     */     }
/* 130 */     setJsonString(buff.toString());
/* 131 */     return "success";
/*     */   }
/*     */ 
/*     */   public String number()
/*     */   {
/* 139 */     SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
/* 140 */     String customerNo = date.format(new Date());
/* 141 */     setJsonString("{success:true,customerNo:'" + customerNo + "'}");
/* 142 */     return "success";
/*     */   }
/*     */ 
/*     */   public String check()
/*     */   {
/* 150 */     boolean pass = false;
/* 151 */     pass = this.customerService.checkCustomerNo(this.customerNo);
/* 152 */     if (pass)
/* 153 */       setJsonString("{success:true,pass:true}");
/*     */     else {
/* 155 */       setJsonString("{success:true,pass:false}");
/*     */     }
/* 157 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.customer.CustomerAction
 * JD-Core Version:    0.6.0
 */