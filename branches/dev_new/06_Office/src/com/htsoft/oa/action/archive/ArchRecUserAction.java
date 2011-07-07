 package com.htsoft.oa.action.archive;
 
 import com.google.gson.Gson;
 import com.google.gson.reflect.TypeToken;
 import com.htsoft.core.command.QueryFilter;
 import com.htsoft.core.web.action.BaseAction;
 import com.htsoft.core.web.paging.PagingBean;
 import com.htsoft.oa.model.archive.ArchRecUser;
import com.htsoft.oa.model.archive.ArchivesDoc;
 import com.htsoft.oa.model.system.AppUser;
 import com.htsoft.oa.model.system.Department;
 import com.htsoft.oa.service.archive.ArchRecUserService;
 import com.htsoft.oa.service.system.AppUserService;
 import com.htsoft.oa.service.system.DepartmentService;
 import java.lang.reflect.Type;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
 
 public class ArchRecUserAction extends BaseAction
 {
 
   @Resource
   private ArchRecUserService archRecUserService;
   private ArchRecUser archRecUser;
 
   @Resource
   private DepartmentService departmentService;
 
   @Resource
   private AppUserService appUserService;
   private Long archRecId;
 
   public Long getArchRecId()
   {
/*  42 */     return this.archRecId;
   }
 
   public void setArchRecId(Long archRecId) {
/*  46 */     this.archRecId = archRecId;
   }
 
   public ArchRecUser getArchRecUser() {
/*  50 */     return this.archRecUser;
   }
 
   public void setArchRecUser(ArchRecUser archRecUser) {
/*  54 */     this.archRecUser = archRecUser;
   }
 
   public String list()
   {
/*  62 */     QueryFilter filter = new QueryFilter(getRequest());
/*  63 */     List<ArchRecUser> list = this.archRecUserService.getAll(filter);
 
/*  65 */     Type type = new TypeToken<List<ArchRecUser>>() {  }
/*  65 */     .getType();
/*  66 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  67 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/*  69 */     Gson gson = new Gson();
/*  70 */     buff.append(gson.toJson(list, type));
/*  71 */     buff.append("}");
 
/*  73 */     this.jsonString = buff.toString();
 
/*  75 */     return "success";
   }
 
   public String depList() {
/*  79 */     List list = this.archRecUserService.findDepAll();
/*  80 */     StringBuffer sb = new StringBuffer("{success:true,'totalCounts':");
/*  81 */     sb.append(list.size()).append(",result:[");
/*  82 */     for (int i = 0; i < list.size(); i++) {
/*  83 */       if (i > 0) {
/*  84 */         sb.append(",");
       }
/*  86 */       ArchRecUser ar = (ArchRecUser)((Object[])list.get(i))[0];
/*  87 */       Department dep = (Department)((Object[])list.get(i))[1];
/*  88 */       sb.append("{'depId':'" + dep.getDepId() + "','depName':'" + dep.getDepName() + "','depLevel':" + dep.getDepLevel() + ",");
/*  89 */       if (ar != null)
/*  90 */         sb.append("'archRecId':'" + ar.getArchRecId() + "','userId':'" + ar.getUserId() + "','fullname':'" + ar.getFullname() + "'}");
       else {
/*  92 */         sb.append("'archRecId':'','userId':'','fullname':''}");
       }
     }
/*  95 */     sb.append("]}");
/*  96 */     this.jsonString = sb.toString();
/*  97 */     return "success";
   }
 
   public String multiDel()
   {
/* 106 */     String[] ids = getRequest().getParameterValues("ids");
/* 107 */     if (ids != null) {
/* 108 */       for (String id : ids) {
/* 109 */         this.archRecUserService.remove(new Long(id));
       }
     }
 
/* 113 */     this.jsonString = "{success:true}";
 
/* 115 */     return "success";
   }
 
   public String get()
   {
/* 123 */     ArchRecUser archRecUser = (ArchRecUser)this.archRecUserService.get(this.archRecId);
 
/* 125 */     Gson gson = new Gson();
 
/* 127 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 128 */     sb.append(gson.toJson(archRecUser));
/* 129 */     sb.append("}");
/* 130 */     setJsonString(sb.toString());
 
/* 132 */     return "success";
   }
 
   public String save()
   {
/* 138 */     String data = getRequest().getParameter("data");
/* 139 */     if (StringUtils.isNotEmpty(data)) {
/* 140 */       Gson gson = new Gson();
/* 141 */       ArchRecUser[] aru = gson.fromJson(data, new com.google.gson.reflect.TypeToken<ArchRecUser[]>(){}.getType());
/* 142 */       for (ArchRecUser archRecUser : aru) {
/* 143 */         if (archRecUser.getArchRecId().longValue() == -1L) {
/* 144 */           archRecUser.setArchRecId(null);
         }
/* 146 */         if (archRecUser.getDepId() != null) {
/* 147 */           Department department = (Department)this.departmentService.get(archRecUser.getDepId());
/* 148 */           archRecUser.setDepartment(department);
/* 149 */           this.archRecUserService.save(archRecUser);
         } else {
/* 151 */           setJsonString("{success:false}");
         }
       }
     }
/* 155 */     setJsonString("{success:true}");
/* 156 */     return "success";
   }
 
   public String select() {
/* 160 */     String strDepId = getRequest().getParameter("depId");
/* 161 */     StringBuffer sb = new StringBuffer("[");
/* 162 */     if (StringUtils.isNotEmpty(strDepId)) {
/* 163 */       List<AppUser> list = this.appUserService.findByDepId(new Long(strDepId));
/* 164 */       for (AppUser appUser : list) {
/* 165 */         sb.append("['").append(appUser.getUserId()).append("','").append(appUser.getFullname()).append("'],");
       }
/* 167 */       if (list.size() > 0) {
/* 168 */         sb.deleteCharAt(sb.length() - 1);
       }
     }
/* 171 */     sb.append("]");
/* 172 */     setJsonString(sb.toString());
/* 173 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.archive.ArchRecUserAction
 * JD-Core Version:    0.6.0
 */