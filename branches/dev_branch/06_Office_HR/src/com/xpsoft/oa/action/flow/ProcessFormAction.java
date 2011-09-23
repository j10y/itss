package com.xpsoft.oa.action.flow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.service.flow.ProcessFormService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class ProcessFormAction extends BaseAction
{

  @Resource
  private ProcessFormService processFormService;
  private ProcessForm processForm;
  private Long formId;

  public Long getFormId()
  {
/*  32 */     return this.formId;
  }

  public void setFormId(Long formId) {
/*  36 */     this.formId = formId;
  }

  public ProcessForm getProcessForm() {
/*  40 */     return this.processForm;
  }

  public void setProcessForm(ProcessForm processForm) {
/*  44 */     this.processForm = processForm;
  }

  public String list()
  {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
/*  53 */     List<ProcessForm> list = this.processFormService.getAll(filter);

/*  55 */     Type type = new TypeToken<List<ProcessForm>>() {  }
/*  55 */     .getType();
/*  56 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  57 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");

/*  59 */     Gson gson = new Gson();
/*  60 */     buff.append(gson.toJson(list, type));
/*  61 */     buff.append("}");

/*  63 */     this.jsonString = buff.toString();

/*  65 */     return "success";
  }

  public String multiDel()
  {
/*  73 */     String[] ids = getRequest().getParameterValues("ids");
/*  74 */     if (ids != null) {
/*  75 */       for (String id : ids) {
/*  76 */         this.processFormService.remove(new Long(id));
      }
    }

/*  80 */     this.jsonString = "{success:true}";

/*  82 */     return "success";
  }

  public String get()
  {
/*  90 */     ProcessForm processForm = (ProcessForm)this.processFormService.get(this.formId);

/*  92 */     Gson gson = new Gson();

/*  94 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  95 */     sb.append(gson.toJson(processForm));
/*  96 */     sb.append("}");
/*  97 */     setJsonString(sb.toString());

/*  99 */     return "success";
  }

  public String save()
  {
/* 105 */     this.processFormService.save(this.processForm);
/* 106 */     setJsonString("{success:true}");
/* 107 */     return "success";
  }
}