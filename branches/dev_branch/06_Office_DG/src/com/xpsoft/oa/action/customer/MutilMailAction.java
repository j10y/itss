/*     */ package com.xpsoft.oa.action.customer;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.engine.MailEngine;
/*     */ import com.xpsoft.core.util.AppUtil;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.oa.model.customer.Customer;
/*     */ import com.xpsoft.oa.model.customer.CustomerMail;
/*     */ import com.xpsoft.oa.model.customer.Provider;
/*     */ import com.xpsoft.oa.model.system.Company;
/*     */ import com.xpsoft.oa.model.system.FileAttach;
/*     */ import com.xpsoft.oa.service.customer.CustomerService;
/*     */ import com.xpsoft.oa.service.customer.ProviderService;
/*     */ import com.xpsoft.oa.service.system.CompanyService;
/*     */ import com.xpsoft.oa.service.system.FileAttachService;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.velocity.app.VelocityEngine;
/*     */ import org.springframework.ui.velocity.VelocityEngineUtils;
/*     */ 
/*     */ public class MutilMailAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private MailEngine mailEngine;
/*     */ 
/*     */   @Resource
/*     */   private ProviderService providerService;
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */ 
/*     */   @Resource
/*     */   private CustomerService customerService;
/*     */ 
/*     */   @Resource
/*     */   private CompanyService companyService;
/*     */   private CustomerMail customerMail;
/*     */ 
/*     */   public CustomerMail getCustomerMail()
/*     */   {
/*  48 */     return this.customerMail;
/*     */   }
/*     */ 
/*     */   public void setCustomerMail(CustomerMail customerMail) {
/*  52 */     this.customerMail = customerMail;
/*     */   }
/*     */ 
/*     */   public String send() {
/*  56 */     Short type = this.customerMail.getType();
/*  57 */     String ids = this.customerMail.getIds();
/*  58 */     String files = this.customerMail.getFiles();
/*  59 */     List atFiles = new ArrayList();
/*  60 */     List fileName = new ArrayList();
/*  61 */     if (StringUtils.isNotEmpty(files)) {
/*  62 */       String[] fIds = files.split(",");
/*  63 */       for (int i = 0; i < fIds.length; i++) {
/*  64 */         FileAttach fileAttach = (FileAttach)this.fileAttachService.get(new Long(fIds[i]));
/*  65 */         File file = new File(getSession().getServletContext().getRealPath("/attachFiles/") + "/" + fileAttach.getFilePath());
/*  66 */         fileName.add(fileAttach.getFileName());
/*  67 */         atFiles.add(file);
/*     */       }
/*     */     }
/*  70 */     String[] id = ids.split(",");
/*  71 */     List toss = new ArrayList();
/*  72 */     if (type.shortValue() == 0) {
/*  73 */       for (int i = 0; i < id.length; i++) {
/*  74 */         Customer customer = (Customer)this.customerService.get(new Long(id[i]));
/*  75 */         toss.add(customer.getEmail());
/*     */       }
/*     */     }
/*  78 */     if (type.shortValue() == 1) {
/*  79 */       for (int i = 0; i < id.length; i++) {
/*  80 */         Provider provider = (Provider)this.providerService.get(new Long(id[i]));
/*  81 */         toss.add(provider.getEmail());
/*     */       }
/*     */     }
/*  84 */     String from = null;
/*  85 */     String cc = null;
/*  86 */     String htmlMsgContent = this.customerMail.getMailContent();
/*  87 */     String subject = this.customerMail.getSubject();
/*  88 */     String[] st = new String[0];
/*  89 */     String[] attachedFileNames = (String[])fileName.toArray(st);
/*  90 */     File[] f = new File[0];
/*  91 */     File[] attachedFiles = (File[])atFiles.toArray(f);
/*  92 */     String replyTo = null;
/*  93 */     String[] tos = (String[])toss.toArray(st);
/*  94 */     if (tos.length > 0) {
/*  95 */       Map configs = AppUtil.getSysConfig();
/*  96 */       if ((StringUtils.isNotEmpty((String)configs.get("host"))) && (StringUtils.isNotEmpty((String)configs.get("username"))) && (StringUtils.isNotEmpty((String)configs.get("password"))) && (StringUtils.isNotEmpty((String)configs.get("from")))) {
/*  97 */         this.mailEngine.setFrom((String)configs.get("from"));
/*  98 */         this.mailEngine.sendMimeMessage(from, tos, cc, replyTo, subject, htmlMsgContent, attachedFileNames, attachedFiles, false);
/*  99 */         setJsonString("{success:true}");
/*     */       } else {
/* 101 */         setJsonString("{success:false,message:'未配置好邮箱配置!'}");
/*     */       }
/*     */     } else {
/* 104 */       setJsonString("{success:false}");
/*     */     }
/* 106 */     return "success";
/*     */   }
/*     */ 
/*     */   public String loadVm() {
/* 110 */     VelocityEngine velocityEngine = (VelocityEngine)AppUtil.getBean("velocityEngine");
/* 111 */     String templateLocation = "mail/sendMsg.vm";
/* 112 */     Map model = new HashMap();
/* 113 */     model.put("appUser", ContextUtil.getCurrentUser());
/* 114 */     List list = this.companyService.findCompany();
/* 115 */     if (list.size() > 0) {
/* 116 */       Company company = (Company)list.get(0);
/* 117 */       if (company != null) {
/* 118 */         model.put("company", company);
/*     */       }
/* 120 */       String pageHtml = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
/* 121 */       Gson gson = new Gson();
/* 122 */       setJsonString("{success:true,data:" + gson.toJson(pageHtml) + "}");
/*     */     } else {
/* 124 */       setJsonString("{success:false,message:'你的公司信息还不完整！请填写好公司信息!'}");
/*     */     }
/* 126 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.customer.MutilMailAction
 * JD-Core Version:    0.6.0
 */