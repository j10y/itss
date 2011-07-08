 package com.xpsoft.oa.action.hrm;
 
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.google.gson.reflect.TypeToken;
 import com.xpsoft.core.command.QueryFilter;
 import com.xpsoft.core.util.ContextUtil;
 import com.xpsoft.core.web.action.BaseAction;
 import com.xpsoft.core.web.paging.PagingBean;
 import com.xpsoft.oa.model.hrm.SalaryPayoff;
 import com.xpsoft.oa.model.hrm.StandSalaryItem;
 import com.xpsoft.oa.model.system.AppUser;
 import com.xpsoft.oa.service.hrm.SalaryPayoffService;
 import com.xpsoft.oa.service.hrm.StandSalaryItemService;
 import java.lang.reflect.Type;
 import java.math.BigDecimal;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 
 public class SalaryPayoffAction extends BaseAction
 {
 
   @Resource
   private SalaryPayoffService salaryPayoffService;
 
   @Resource
   private StandSalaryItemService standSalaryItemService;
   private SalaryPayoff salaryPayoff;
   private Long recordId;
 
   public Long getRecordId()
   {
/*  40 */     return this.recordId;
   }
 
   public void setRecordId(Long recordId) {
/*  44 */     this.recordId = recordId;
   }
 
   public SalaryPayoff getSalaryPayoff() {
/*  48 */     return this.salaryPayoff;
   }
 
   public void setSalaryPayoff(SalaryPayoff salaryPayoff) {
/*  52 */     this.salaryPayoff = salaryPayoff;
   }
 
   public String list()
   {
/*  60 */     QueryFilter filter = new QueryFilter(getRequest());
/*  61 */     List<SalaryPayoff> list = this.salaryPayoffService.getAll(filter);
 
/*  63 */     Type type = new TypeToken<List<SalaryPayoff>>(){}.getType();
/*  64 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  65 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/*  67 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*  68 */     buff.append(gson.toJson(list, type));
/*  69 */     buff.append("}");
 
/*  71 */     this.jsonString = buff.toString();
 
/*  73 */     return "success";
   }
 
   public String multiDel()
   {
/*  81 */     String[] ids = getRequest().getParameterValues("ids");
/*  82 */     if (ids != null) {
/*  83 */       for (String id : ids) {
/*  84 */         this.salaryPayoffService.remove(new Long(id));
       }
     }
 
/*  88 */     this.jsonString = "{success:true}";
 
/*  90 */     return "success";
   }
 
   public String get()
   {
/*  98 */     SalaryPayoff salaryPayoff = (SalaryPayoff)this.salaryPayoffService.get(this.recordId);
 
/* 100 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
 
/* 102 */     StringBuffer sb = new StringBuffer("{success:true,data:[");
/* 103 */     sb.append(gson.toJson(salaryPayoff));
/* 104 */     sb.append("]}");
/* 105 */     setJsonString(sb.toString());
 
/* 107 */     return "success";
   }
 
   public String save()
   {
/* 113 */     if (this.salaryPayoff.getRecordId() == null) {
/* 114 */       this.salaryPayoff.setCheckStatus(Short.valueOf(SalaryPayoff.CHECK_FLAG_NONE));
/* 115 */       this.salaryPayoff.setRegTime(new Date());
/* 116 */       this.salaryPayoff.setRegister(ContextUtil.getCurrentUser().getFullname());
     }
/* 118 */     BigDecimal acutalAmount = this.salaryPayoff.getStandAmount().add(this.salaryPayoff.getEncourageAmount()).subtract(this.salaryPayoff.getDeductAmount());
/* 119 */     if (this.salaryPayoff.getAchieveAmount().compareTo(new BigDecimal(0)) == 1) {
/* 120 */       acutalAmount = acutalAmount.add(this.salaryPayoff.getAchieveAmount());
     }
/* 122 */     this.salaryPayoff.setAcutalAmount(acutalAmount);
/* 123 */     this.salaryPayoffService.save(this.salaryPayoff);
/* 124 */     setJsonString("{success:true}");
/* 125 */     return "success";
   }
 
   public String check()
   {
/* 133 */     SalaryPayoff checkSalaryPayoff = (SalaryPayoff)this.salaryPayoffService.get(new Long(this.recordId.longValue()));
/* 134 */     checkSalaryPayoff.setCheckTime(new Date());
/* 135 */     checkSalaryPayoff.setCheckName(ContextUtil.getCurrentUser().getFullname());
/* 136 */     checkSalaryPayoff.setCheckStatus(this.salaryPayoff.getCheckStatus());
/* 137 */     checkSalaryPayoff.setCheckOpinion(this.salaryPayoff.getCheckOpinion());
/* 138 */     this.salaryPayoffService.save(checkSalaryPayoff);
/* 139 */     return "success";
   }
 
   public String personal()
   {
/* 146 */     QueryFilter filter = new QueryFilter(getRequest());
/* 147 */     List<SalaryPayoff> list = this.salaryPayoffService.getAll(filter);
 
/* 151 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 152 */       .append(filter.getPagingBean().getTotalItems()).append(",result:[");
/* 153 */     for (SalaryPayoff salaryDetail : list) {
/* 154 */       buff.append("{recordId:'")
/* 155 */         .append(salaryDetail.getRecordId())
/* 156 */         .append("',fullname:'")
/* 157 */         .append(salaryDetail.getFullname())
/* 158 */         .append("',profileNo:'")
/* 159 */         .append(salaryDetail.getProfileNo())
/* 160 */         .append("',idNo:'")
/* 161 */         .append(salaryDetail.getIdNo())
/* 162 */         .append("',standAmount:'")
/* 163 */         .append(salaryDetail.getStandAmount())
/* 164 */         .append("',acutalAmount:'")
/* 165 */         .append(salaryDetail.getAcutalAmount())
/* 166 */         .append("',startTime:'")
/* 167 */         .append(salaryDetail.getStartTime())
/* 168 */         .append("',endTime:'")
/* 169 */         .append(salaryDetail.getEndTime())
/* 170 */         .append("',checkStatus:'")
/* 171 */         .append(salaryDetail.getCheckStatus());
/* 172 */       List<StandSalaryItem> items = this.standSalaryItemService.getAllByStandardId(salaryDetail.getStandardId());
/* 173 */       StringBuffer content = new StringBuffer("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
 
/* 175 */       if ((salaryDetail.getEncourageAmount() != new BigDecimal(0)) && 
/* 176 */         (salaryDetail.getEncourageAmount() != null)) {
/* 177 */         content.append("<th>")
/* 178 */           .append("奖励金额</th><td>")
/* 179 */           .append(salaryDetail.getEncourageAmount())
/* 180 */           .append("</td>");
       }
 
/* 183 */       if ((salaryDetail.getEncourageAmount() != new BigDecimal(0)) && 
/* 184 */         (salaryDetail.getEncourageAmount() != null)) {
/* 185 */         content.append("<th>")
/* 186 */           .append("扣除金额</th><td>")
/* 187 */           .append(salaryDetail.getDeductAmount())
/* 188 */           .append("</td>");
       }
 
/* 191 */       if ((salaryDetail.getEncourageAmount() != new BigDecimal(0)) && 
/* 192 */         (salaryDetail.getEncourageAmount() != null)) {
/* 193 */         content.append("<th>")
/* 194 */           .append("效绩金额</th><td>")
/* 195 */           .append(salaryDetail.getAchieveAmount())
/* 196 */           .append("</td>");
       }
/* 198 */       content.append("</tr></table><table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
/* 199 */       for (StandSalaryItem item : items) {
/* 200 */         content.append("<th>")
/* 201 */           .append(item.getItemName())
/* 202 */           .append("</th>");
       }
/* 204 */       content.append("</tr><tr>");
/* 205 */       for (StandSalaryItem item2 : items) {
/* 206 */         content.append("<td>")
/* 207 */           .append(item2.getAmount())
/* 208 */           .append("</td>");
       }
/* 210 */       content.append("</tr></table>");
/* 211 */       buff.append("',content:'")
/* 212 */         .append(content.toString())
/* 213 */         .append("'},");
     }
/* 215 */     if (list.size() > 0) {
/* 216 */       buff.deleteCharAt(buff.length() - 1);
     }
/* 218 */     buff.append("]}");
 
/* 220 */     this.jsonString = buff.toString();
/* 221 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.hrm.SalaryPayoffAction
 * JD-Core Version:    0.6.0
 */