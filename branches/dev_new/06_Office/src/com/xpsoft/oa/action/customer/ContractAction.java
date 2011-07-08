 package com.xpsoft.oa.action.customer;
 
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.xpsoft.core.command.QueryFilter;
 import com.xpsoft.core.util.ContextUtil;
 import com.xpsoft.core.util.JsonUtil;
 import com.xpsoft.core.web.action.BaseAction;
 import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.ArchivesDoc;
 import com.xpsoft.oa.model.customer.Contract;
 import com.xpsoft.oa.model.customer.ContractConfig;
 import com.xpsoft.oa.model.system.AppUser;
 import com.xpsoft.oa.model.system.FileAttach;
 import com.xpsoft.oa.service.customer.ContractConfigService;
 import com.xpsoft.oa.service.customer.ContractService;
 import com.xpsoft.oa.service.system.FileAttachService;
 import flexjson.JSONSerializer;
 import java.util.Date;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
 
 public class ContractAction extends BaseAction
 {
 
   @Resource
   private ContractService contractService;
 
   @Resource
   private ContractConfigService contractConfigService;
 
   @Resource
   private FileAttachService fileAttachService;
   private Contract contract;
   private Long contractId;
   private String contractAttachIDs;
   private String data;
 
   public Long getContractId()
   {
/*  48 */     return this.contractId;
   }
 
   public void setContractId(Long contactId) {
/*  52 */     this.contractId = contactId;
   }
 
   public Contract getContract() {
/*  56 */     return this.contract;
   }
 
   public void setContract(Contract contract) {
/*  60 */     this.contract = contract;
   }
 
   public String getData() {
/*  64 */     return this.data;
   }
 
   public void setData(String data) {
/*  68 */     this.data = data;
   }
 
   public String getContractAttachIDs() {
/*  72 */     return this.contractAttachIDs;
   }
 
   public void setContractAttachIDs(String contractAttachIDs) {
/*  76 */     this.contractAttachIDs = contractAttachIDs;
   }
 
   public String list()
   {
/*  84 */     QueryFilter filter = new QueryFilter(getRequest());
/*  85 */     List list = this.contractService.getAll(filter);
 
/*  87 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  88 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/*  90 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "signupTime" });
/*  91 */     buff.append(json.exclude(new String[] { "class", "contractConfigs" }).serialize(list));
/*  92 */     buff.append("}");
 
/*  94 */     this.jsonString = buff.toString();
 
/*  96 */     return "success";
   }
 
   public String multiDel()
   {
/* 104 */     String[] ids = getRequest().getParameterValues("ids");
/* 105 */     if (ids != null) {
/* 106 */       for (String id : ids) {
/* 107 */         this.contractService.remove(new Long(id));
       }
     }
 
/* 111 */     this.jsonString = "{success:true}";
 
/* 113 */     return "success";
   }
 
   public String get()
   {
/* 121 */     Contract contract = (Contract)this.contractService.get(this.contractId);
/* 122 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
 
/* 125 */     StringBuffer sb = new StringBuffer("{success:true,data:");
 
/* 127 */     sb.append(gson.toJson(contract));
/* 128 */     sb.append(",projectId:" + contract.getProjectId());
/* 129 */     sb.append("}");
/* 130 */     setJsonString(sb.toString());
 
/* 132 */     return "success";
   }
 
   public String save()
   {
/* 140 */     boolean pass = false;
/* 141 */     StringBuffer buff = new StringBuffer("{");
/* 142 */     if (this.contract.getValidDate().getTime() <= this.contract.getExpireDate().getTime())
/* 143 */       pass = true;
     else {
/* 145 */       buff.append("msg:'合同失效日期不能早于生效日期,请重新填写!',");
     }
 
/* 149 */     if (pass)
     {
/* 151 */       this.contract.setCreator(ContextUtil.getCurrentUser().getFullname());
/* 152 */       this.contract.setCreatetime(new Date());
 
/* 154 */       String[] fileIDs = getContractAttachIDs().split(",");
/* 155 */       Set contractAttachs = new HashSet();
/* 156 */       for (String id : fileIDs) {
/* 157 */         if (!id.equals("")) {
/* 158 */           contractAttachs.add((FileAttach)this.fileAttachService.get(new Long(id)));
         }
       }
/* 161 */       this.contract.setContractFiles(contractAttachs);
/* 162 */       this.contractService.save(this.contract);
/* 163 */       if (StringUtils.isNotEmpty(this.data)) {
/* 164 */         Gson gson = new Gson();
/* 165 */         ContractConfig[] contractConfigs = gson.fromJson(this.data, new com.google.gson.reflect.TypeToken<ContractConfig[]>(){}.getType());
/* 166 */         for (ContractConfig contractConfig : contractConfigs) {
/* 167 */           if (contractConfig.getConfigId().longValue() == -1L) {
/* 168 */             contractConfig.setConfigId(null);
/* 169 */             contractConfig.setContractId(this.contract.getContractId());
           }
/* 171 */           this.contractConfigService.save(contractConfig);
         }
       }
/* 174 */       buff.append("success:true}");
     } else {
/* 176 */       buff.append("failure:true}");
     }
/* 178 */     setJsonString(buff.toString());
/* 179 */     return "success";
   }
 
   public String removeFile()
   {
/* 187 */     setContract((Contract)this.contractService.get(this.contractId));
/* 188 */     Set contractFiles = this.contract.getContractFiles();
/* 189 */     FileAttach removeFile = (FileAttach)this.fileAttachService.get(new Long(this.contractAttachIDs));
/* 190 */     contractFiles.remove(removeFile);
/* 191 */     this.contract.setContractFiles(contractFiles);
/* 192 */     this.contractService.save(this.contract);
/* 193 */     setJsonString("{success:true}");
/* 194 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.customer.ContractAction
 * JD-Core Version:    0.6.0
 */