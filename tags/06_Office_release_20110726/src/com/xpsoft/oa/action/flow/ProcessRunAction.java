 package com.xpsoft.oa.action.flow;
 
 import com.google.gson.Gson;
 import com.xpsoft.core.command.QueryFilter;
 import com.xpsoft.core.util.ContextUtil;
 import com.xpsoft.core.web.action.BaseAction;
 import com.xpsoft.core.web.paging.PagingBean;
 import com.xpsoft.oa.model.flow.ProDefinition;
 import com.xpsoft.oa.model.flow.ProcessRun;
 import com.xpsoft.oa.service.flow.ProcessRunService;
 import java.text.SimpleDateFormat;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 
 public class ProcessRunAction extends BaseAction
 {
 
   @Resource
   private ProcessRunService processRunService;
   private ProcessRun processRun;
   private Long runId;
 
   public Long getRunId()
   {
/*  32 */     return this.runId;
   }
 
   public void setRunId(Long runId) {
/*  36 */     this.runId = runId;
   }
 
   public ProcessRun getProcessRun() {
/*  40 */     return this.processRun;
   }
 
   public void setProcessRun(ProcessRun processRun) {
/*  44 */     this.processRun = processRun;
   }
 
   public String list()
   {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
 
/*  55 */     filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
 
/*  57 */     List<ProcessRun> list = this.processRunService.getAll(filter);
 
/*  59 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  60 */       .append(filter.getPagingBean().getTotalItems()).append(",result:[");
/*  61 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
/*  63 */     for (ProcessRun run : list) {
/*  64 */       buff.append("{runId:'").append(run.getRunId()).append("',subject:'")
/*  65 */         .append(run.getSubject()).append("',createtime:'").append(sdf.format(run.getCreatetime()))
/*  66 */         .append("',piId:'").append(run.getPiId()).append("',defId:'").append(run.getProDefinition().getDefId())
/*  67 */         .append("',runStatus:'").append(run.getRunStatus()).append("'},");
     }
 
/*  70 */     if (list.size() > 0) {
/*  71 */       buff.deleteCharAt(buff.length() - 1);
     }
/*  73 */     buff.append("]");
/*  74 */     buff.append("}");
 
/*  76 */     this.jsonString = buff.toString();
 
/*  78 */     return "success";
   }
 
   public String my()
   {
/*  86 */     QueryFilter filter = new QueryFilter(getRequest());
 
/*  89 */     filter.setFilterName("MyAttendProcessRun");
 
/*  91 */     filter.addParamValue(ContextUtil.getCurrentUserId());
 
/*  93 */     List<ProcessRun> processRunList = this.processRunService.getAll(filter);
 
/*  95 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  96 */       .append(filter.getPagingBean().getTotalItems()).append(",result:[");
/*  97 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
/*  99 */     for (ProcessRun run : processRunList) {
/* 100 */       buff.append("{runId:'").append(run.getRunId()).append("',subject:'")
/* 101 */         .append(run.getSubject()).append("',createtime:'").append(sdf.format(run.getCreatetime()))
/* 102 */         .append("',piId:'").append(run.getPiId()).append("',defId:'").append(run.getProDefinition().getDefId())
/* 103 */         .append("',runStatus:'").append(run.getRunStatus()).append("'},");
     }
 
/* 106 */     if (processRunList.size() > 0) {
/* 107 */       buff.deleteCharAt(buff.length() - 1);
     }
/* 109 */     buff.append("]");
/* 110 */     buff.append("}");
 
/* 112 */     this.jsonString = buff.toString();
/* 113 */     return "success";
   }
 
   public String multiDel()
   {
/* 122 */     String[] ids = getRequest().getParameterValues("ids");
/* 123 */     if (ids != null) {
/* 124 */       for (String id : ids) {
/* 125 */         this.processRunService.remove(new Long(id));
       }
     }
/* 128 */     this.jsonString = "{success:true}";
/* 129 */     return "success";
   }
 
   public String get()
   {
/* 137 */     ProcessRun processRun = (ProcessRun)this.processRunService.get(this.runId);
 
/* 139 */     Gson gson = new Gson();
 
/* 141 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 142 */     sb.append(gson.toJson(processRun));
/* 143 */     sb.append("}");
/* 144 */     setJsonString(sb.toString());
 
/* 146 */     return "success";
   }
 
   public String save()
   {
/* 152 */     this.processRunService.save(this.processRun);
/* 153 */     setJsonString("{success:true}");
/* 154 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.flow.ProcessRunAction
 * JD-Core Version:    0.6.0
 */