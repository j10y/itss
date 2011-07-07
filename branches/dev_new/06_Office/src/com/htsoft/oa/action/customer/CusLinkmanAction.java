 package com.htsoft.oa.action.customer;
 
 import com.htsoft.core.command.QueryFilter;
 import com.htsoft.core.web.action.BaseAction;
 import com.htsoft.core.web.paging.PagingBean;
 import com.htsoft.oa.model.customer.CusLinkman;
 import com.htsoft.oa.model.customer.Customer;
 import com.htsoft.oa.service.customer.CusLinkmanService;
 import com.htsoft.oa.service.customer.CustomerService;
 import flexjson.JSONSerializer;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 
 public class CusLinkmanAction extends BaseAction
 {
/*  23 */   private Short isPrimary = Short.valueOf((short) 1);
 
   @Resource
   private CusLinkmanService cusLinkmanService;
 
   @Resource
   private CustomerService customerService;
   private CusLinkman cusLinkman;
   private Long linkmanId;
 
/*  33 */   public Long getLinkmanId() { return this.linkmanId; }
 
   public void setLinkmanId(Long linkmanId)
   {
/*  37 */     this.linkmanId = linkmanId;
   }
 
   public CusLinkman getCusLinkman() {
/*  41 */     return this.cusLinkman;
   }
 
   public void setCusLinkman(CusLinkman cusLinkman) {
/*  45 */     this.cusLinkman = cusLinkman;
   }
 
   public String list()
   {
/*  53 */     QueryFilter filter = new QueryFilter(getRequest());
/*  54 */     List list = this.cusLinkmanService.getAll(filter);
 
/*  57 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  58 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/*  61 */     JSONSerializer json = new JSONSerializer();
/*  62 */     buff.append(json.exclude(new String[] { "class" }).serialize(list));
/*  63 */     buff.append("}");
 
/*  65 */     this.jsonString = buff.toString();
 
/*  67 */     return "success";
   }
 
   public String multiDel()
   {
/*  75 */     String[] ids = getRequest().getParameterValues("ids");
/*  76 */     if (ids != null) {
/*  77 */       for (String id : ids) {
/*  78 */         this.cusLinkmanService.remove(new Long(id));
       }
     }
 
/*  82 */     this.jsonString = "{success:true}";
 
/*  84 */     return "success";
   }
 
   public String get()
   {
/*  92 */     CusLinkman cusLinkman = (CusLinkman)this.cusLinkmanService.get(this.linkmanId);
 
/*  95 */     JSONSerializer json = new JSONSerializer();
 
/*  97 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  98 */     sb.append(json.exclude(new String[] { "class", "custoemr.class" }).serialize(cusLinkman));
/*  99 */     sb.append("}");
/* 100 */     setJsonString(sb.toString());
/* 101 */     return "success";
   }
 
   public String save()
   {
/* 107 */     boolean pass = false;
/* 108 */     StringBuffer buff = new StringBuffer("{");
/* 109 */     if (this.cusLinkman.getCustomerId() != null) {
/* 110 */       if (this.cusLinkman.getIsPrimary().shortValue() != 1) {
/* 111 */         pass = true;
       }
/* 113 */       else if (this.cusLinkmanService.checkMainCusLinkman(this.cusLinkman.getCustomerId(), this.cusLinkman.getLinkmanId()))
/* 114 */         pass = true;
/* 115 */       else buff.append("msg:'该客户的主要联系人已存在,请保存为普通联系人!',"); 
     }
     else {
/* 117 */       buff.append("msg:'所属客户不能为空.',");
     }
/* 119 */     if (pass) {
/* 120 */       this.cusLinkman.setCustomer((Customer)this.customerService.get(this.cusLinkman.getCustomerId()));
/* 121 */       this.cusLinkmanService.save(this.cusLinkman);
/* 122 */       buff.append("success:true}");
     } else {
/* 124 */       buff.append("failure:true}");
     }
/* 126 */     setJsonString(buff.toString());
/* 127 */     return "success";
   }
   public String find() {
/* 130 */     QueryFilter filter = new QueryFilter(getRequest());
/* 131 */     filter.addSorted("isPrimary", "desc");
/* 132 */     List<CusLinkman> list = this.cusLinkmanService.getAll(filter);
 
/* 134 */     StringBuffer buff = new StringBuffer("[");
/* 135 */     for (CusLinkman cusLinkman : list) {
/* 136 */       buff.append("['" + cusLinkman.getLinkmanId() + "','" + cusLinkman.getFullname() + "'],");
     }
/* 138 */     if (list.size() != 0) {
/* 139 */       buff.deleteCharAt(buff.length() - 1);
     }
/* 141 */     buff.append("]");
/* 142 */     setJsonString(buff.toString());
/* 143 */     return "success";
   }
 }
