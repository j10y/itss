/*    */ package com.xpsoft.oa.action.system;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.xpsoft.core.util.AppUtil;
/*    */ import com.xpsoft.core.web.action.BaseAction;
/*    */ import com.xpsoft.oa.model.system.Company;
/*    */ import com.xpsoft.oa.service.system.CompanyService;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Resource;
/*    */ 
/*    */ public class CompanyAction extends BaseAction
/*    */ {
/*    */   private Company company;
/*    */ 
/*    */   @Resource
/*    */   private CompanyService companyService;
/*    */ 
/*    */   public Company getCompany()
/*    */   {
/* 28 */     return this.company;
/*    */   }
/*    */ 
/*    */   public void setCompany(Company company) {
/* 32 */     this.company = company;
/*    */   }
/*    */ 
/*    */   public String check()
/*    */   {
/* 40 */     List list = this.companyService.findCompany();
/* 41 */     if (list.size() > 0)
/* 42 */       setJsonString("{success:true}");
/*    */     else {
/* 44 */       setJsonString("{success:false}");
/*    */     }
/* 46 */     return "success";
/*    */   }
/*    */ 
/*    */   public String list() {
/* 50 */     List list = this.companyService.findCompany();
/* 51 */     if (list.size() > 0) {
/* 52 */       this.company = ((Company)list.get(0));
/* 53 */       Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
/* 54 */       StringBuffer cf = new StringBuffer("{success:true,result:[");
/* 55 */       cf.append(gson.toJson(this.company));
/* 56 */       cf.append("]}");
/* 57 */       setJsonString(cf.toString());
/*    */     } else {
/* 59 */       setJsonString("{success:false,message:'还没有填写公司信息'}");
/* 60 */       return "success";
/*    */     }
/* 62 */     return "success";
/*    */   }
/*    */ 
/*    */   public String add()
/*    */   {
/* 67 */     this.companyService.save(this.company);
/* 68 */     Map map = AppUtil.getSysConfig();
/* 69 */     map.remove("app.logoPath");
/* 70 */     map.remove("app.companyName");
/* 71 */     map.put("app.logoPath", this.company.getLogo());
/* 72 */     map.put("app.companyName", this.company.getCompanyName());
/* 73 */     setJsonString("{success:true}");
/* 74 */     return "success";
/*    */   }
/*    */ 
/*    */   public String delphoto() {
/* 78 */     List list = this.companyService.findCompany();
/* 79 */     if (list.size() > 0) {
/* 80 */       this.company = ((Company)list.get(0));
/* 81 */       this.company.setLogo("");
/* 82 */       this.companyService.save(this.company);
/*    */     }
/* 84 */     return "success";
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.system.CompanyAction
 * JD-Core Version:    0.6.0
 */