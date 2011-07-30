 package com.xpsoft.oa.action.archive;
 
 import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchRecType;
import com.xpsoft.oa.model.archive.ArchivesType;
import com.xpsoft.oa.service.archive.ArchRecTypeService;

import flexjson.JSONSerializer;
 
 public class ArchRecTypeAction extends BaseAction
 {
 
   @Resource
   private ArchRecTypeService archRecTypeService;
   private ArchRecType archRecType;
   private Long recTypeId;
 
   public Long getRecTypeId()
   {
/*  35 */     return this.recTypeId;
   }
 
   public void setRecTypeId(Long recTypeId) {
/*  39 */     this.recTypeId = recTypeId;
   }
 
   public ArchRecType getArchRecType() {
/*  43 */     return this.archRecType;
   }
 
   public void setArchRecType(ArchRecType archRecType) {
/*  47 */     this.archRecType = archRecType;
   }
 
   public String list()
   {
/*  55 */     QueryFilter filter = new QueryFilter(getRequest());
/*  56 */     List list = this.archRecTypeService.getAll(filter);
 
/*  59 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  60 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
 
/*  64 */     JSONSerializer serializer = new JSONSerializer();
/*  65 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  66 */     buff.append("}");
 
/*  68 */     this.jsonString = buff.toString();
 
/*  70 */     return "success";
   }
 
   public String combo()
   {
/*  76 */     QueryFilter filter = new QueryFilter(getRequest());
/*  77 */     List<ArchRecType> list = this.archRecTypeService.getAll(filter);
/*  78 */     StringBuffer sb = new StringBuffer("[");
/*  79 */     for (ArchRecType type : list) {
/*  80 */       sb.append("['").append(type.getRecTypeId()).append("','")
					.append(type.getTypeName()).append("','")
					.append(type.getProcessDefId()!=null?type.getProcessDefId():0).append("'],");
     }
/*  82 */     if (list.size() > 0) {
/*  83 */       sb.deleteCharAt(sb.length() - 1);
     }
/*  85 */     sb.append("]");
/*  86 */     setJsonString(sb.toString());
/*  87 */     return "success";
   }
 
   public String multiDel()
   {
/*  96 */     String[] ids = getRequest().getParameterValues("ids");
/*  97 */     if (ids != null) {
/*  98 */       for (String id : ids) {
/*  99 */         this.archRecTypeService.remove(new Long(id));
       }
     }
 
/* 103 */     this.jsonString = "{success:true}";
 
/* 105 */     return "success";
   }
 
   public String get()
   {
/* 113 */     ArchRecType archRecType = (ArchRecType)this.archRecTypeService.get(this.recTypeId);
 
/* 117 */     StringBuffer sb = new StringBuffer("{success:true,data:");
 
/* 119 */     JSONSerializer serializer = new JSONSerializer();
/* 120 */     sb.append(serializer.exclude(new String[] { "class", "department.class" }).serialize(archRecType));
/* 121 */     sb.append("}");
/* 122 */     setJsonString(sb.toString());
 
/* 124 */     return "success";
   }
 
   public String save()
   {
/* 130 */     this.archRecTypeService.save(this.archRecType);
/* 131 */     setJsonString("{success:true}");
/* 132 */     return "success";
   }
   
   public String saveList() {
		String data = getRequest().getParameter("data");
		if (StringUtils.isNotEmpty(data)) {
			Gson gson = new Gson();
			ArchRecType[] artl = gson.fromJson(data,
					new com.google.gson.reflect.TypeToken<ArchRecType[]>() {
					}.getType());
			for (ArchRecType archRecType : artl) {
				if (archRecType.getRecTypeId().longValue() == -1L) {
					archRecType.setRecTypeId(null);
				}
				if (archRecType.getProcessDefId() != null) {
					this.archRecTypeService.save(archRecType);
				} else {
					setJsonString("{success:false}");
				}
			}
		}
		setJsonString("{success:true}");
		return "success";
	}
 }
