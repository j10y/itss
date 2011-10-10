 package com.xpsoft.oa.action.hrm;
 
 import com.xpsoft.core.command.QueryFilter;
 import com.xpsoft.core.web.action.BaseAction;
 import com.xpsoft.core.web.paging.PagingBean;
 import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
 import com.xpsoft.oa.service.hrm.JobService;
 import flexjson.JSONSerializer;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
 
 public class JobAction extends BaseAction
 {
 
   @Resource
   private JobService jobService;
   private Job job;
   private Long jobId;
 
   public Long getJobId()
   {
/*  38 */     return this.jobId;
   }
 
   public void setJobId(Long jobId) {
/*  42 */     this.jobId = jobId;
   }
 
   public Job getJob() {
/*  46 */     return this.job;
   }
 
   public void setJob(Job job) {
/*  50 */     this.job = job;
   }
 
   public String list()
   {
/*  58 */     QueryFilter filter = new QueryFilter(getRequest());
/*  59 */     List list = this.jobService.getAll(filter);
 
/*  62 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  63 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/*  66 */     JSONSerializer serializer = new JSONSerializer();
/*  67 */     buff.append(serializer.exclude(new String[] { "class", "department.appUser" }).serialize(list));
 
/*  69 */     buff.append("}");
 
/*  71 */     this.jsonString = buff.toString();
 
/*  73 */     return "success";
   }
 
   public String multiDel()
   {
/*  81 */     String[] ids = getRequest().getParameterValues("ids");
/*  82 */     if (ids != null) {
/*  83 */       for (String id : ids) {
/*  84 */         Job removeJob = (Job)this.jobService.get(new Long(id));
/*  85 */         removeJob.setDelFlag(Short.valueOf(Job.DELFLAG_HAD));
/*  86 */         this.jobService.save(removeJob);
       }
     }
 
/*  90 */     this.jsonString = "{success:true}";
 
/*  92 */     return "success";
   }
 
   public String get()
   {
/* 100 */     Job job = (Job)this.jobService.get(this.jobId);
 
/* 103 */     JSONSerializer json = new JSONSerializer();
 
/* 105 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 106 */     sb.append(json.exclude(new String[] { "class" }).serialize(job));
/* 107 */     sb.append("}");
/* 108 */     setJsonString(sb.toString());
 
/* 110 */     return "success";
   }
 
   public String save()
   {
		/* 116 */this.job.setDelFlag(Short.valueOf(Job.DELFLAG_NOT));
		HrPaDatadictionary band = null;
		if(StringUtils.isNotEmpty(getRequest().getParameter("job.band.id"))){
			band = new HrPaDatadictionary();
			band.setId(Long.valueOf(getRequest().getParameter("job.band.id")));
		}
		this.job.setBand(band);
/* 117 */     this.jobService.save(this.job);
/* 118 */     setJsonString("{success:true}");
/* 119 */     return "success";
   }
 
   public String combo()
   {
/* 127 */     String strDepId = getRequest().getParameter("depId");
/* 128 */     if (StringUtils.isNotEmpty(strDepId)) {
/* 129 */       List<Job> list = this.jobService.findByDep(new Long(strDepId));
/* 130 */       StringBuffer sb = new StringBuffer("[");
/* 131 */       for (Job job : list) {
/* 132 */         sb.append("['").append(job.getJobId()).append("','").append(job.getJobName()).append("'],");
       }
/* 134 */       if (list.size() > 0) {
/* 135 */         sb.deleteCharAt(sb.length() - 1);
       }
/* 137 */       sb.append("]");
/* 138 */       setJsonString(sb.toString());
     } else {
/* 140 */       setJsonString("{success:false}");
     }
/* 142 */     return "success";
   }
 
   public String recovery()
   {
/* 150 */     String[] ids = getRequest().getParameterValues("ids");
/* 151 */     if (ids != null) {
/* 152 */       for (String id : ids) {
/* 153 */         Job deleteJob = (Job)this.jobService.get(new Long(id));
/* 154 */         deleteJob.setDelFlag(Short.valueOf(Job.DELFLAG_NOT));
/* 155 */         this.jobService.save(deleteJob);
       }
     }
/* 158 */     this.jsonString = "{success:true}";
/* 159 */     return "success";
   }
 }
