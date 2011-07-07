 package com.htsoft.oa.action.archive;
 
 import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.oa.model.archive.ArchivesType;
import com.htsoft.oa.service.archive.ArchivesTypeService;
 
 public class ArchivesTypeAction extends BaseAction
 {
 
   @Resource
   private ArchivesTypeService archivesTypeService;
   private ArchivesType archivesType;
   private Long typeId;
 
   public Long getTypeId()
   {
/*  33 */     return this.typeId;
   }
 
   public void setTypeId(Long typeId) {
/*  37 */     this.typeId = typeId;
   }
 
   public ArchivesType getArchivesType() {
/*  41 */     return this.archivesType;
   }
 
   public void setArchivesType(ArchivesType archivesType) {
/*  45 */     this.archivesType = archivesType;
   }
 
   public String combo()
   {
/*  53 */     StringBuffer sb = new StringBuffer();
 
/*  55 */     List<ArchivesType> dutySectionList = this.archivesTypeService.getAll();
/*  56 */     sb.append("[");
/*  57 */     for (ArchivesType dutySection : dutySectionList) {
/*  58 */       sb.append("['").append(dutySection.getTypeId()).append("','").append(dutySection.getTypeName()).append("'],");
     }
/*  60 */     if (dutySectionList.size() > 0) {
/*  61 */       sb.deleteCharAt(sb.length() - 1);
     }
/*  63 */     sb.append("]");
/*  64 */     setJsonString(sb.toString());
/*  65 */     return "success";
   }
 
   public String tree()
   {
/*  74 */     List<ArchivesType> typeList = this.archivesTypeService.getAll();
 
/*  76 */     StringBuffer sb = new StringBuffer();
/*  77 */     sb.append("[{id:'0',text:'所有公文分类',expanded:true,children:[");
/*  78 */     for (ArchivesType type : typeList) {
/*  79 */       sb.append("{id:'" + type.getTypeId()).append("',text:'" + type.getTypeName()).append("',leaf:true,expanded:true},");
     }
/*  81 */     if (typeList.size() > 0) {
/*  82 */       sb.deleteCharAt(sb.length() - 1);
     }
/*  84 */     sb.append("]}]");
/*  85 */     setJsonString(sb.toString());
/*  86 */     return "success";
   }
 
   public String list()
   {
/*  94 */     QueryFilter filter = new QueryFilter(getRequest());
/*  95 */     List<ArchivesType> list = this.archivesTypeService.getAll(filter);
 
/*  97 */     Type type = new TypeToken<List<ArchivesType>>() {  }
/*  97 */     .getType();
/*  98 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  99 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/* 101 */     Gson gson = new Gson();
/* 102 */     buff.append(gson.toJson(list, type));
/* 103 */     buff.append("}");
 
/* 105 */     this.jsonString = buff.toString();
 
/* 107 */     return "success";
   }
 
   public String multiDel()
   {
/* 115 */     String[] ids = getRequest().getParameterValues("ids");
/* 116 */     if (ids != null) {
/* 117 */       for (String id : ids) {
/* 118 */         this.archivesTypeService.remove(new Long(id));
       }
     }
 
/* 122 */     this.jsonString = "{success:true}";
 
/* 124 */     return "success";
   }
 
   public String get()
   {
/* 132 */     ArchivesType archivesType = (ArchivesType)this.archivesTypeService.get(this.typeId);
 
/* 134 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
 
/* 136 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 137 */     sb.append(gson.toJson(archivesType));
/* 138 */     sb.append("}");
/* 139 */     setJsonString(sb.toString());
 
/* 141 */     return "success";
   }
 
   public String save()
   {
/* 147 */     this.archivesTypeService.save(this.archivesType);
/* 148 */     setJsonString("{success:true}");
/* 149 */     return "success";
   }
 }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.archive.ArchivesTypeAction
 * JD-Core Version:    0.6.0
 */