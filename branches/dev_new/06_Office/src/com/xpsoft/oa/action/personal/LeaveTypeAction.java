 package com.xpsoft.oa.action.personal;
 
 import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.personal.LeaveType;
import com.xpsoft.oa.service.personal.LeaveTypeService;

import flexjson.JSONSerializer;
 
 public class LeaveTypeAction extends BaseAction
 {
 
   @Resource
   private LeaveTypeService leaveTypeService;
   private LeaveType leaveType;
   private Long typeId;
 
   public Long getTypeId()
   {
/*  35 */     return this.typeId;
   }
 
   public void setTypeId(Long typeId) {
/*  39 */     this.typeId = typeId;
   }
 
   public LeaveType getLeaveType() {
/*  43 */     return this.leaveType;
   }
 
   public void setLeaveType(LeaveType leaveType) {
/*  47 */     this.leaveType = leaveType;
   }
 
   public String list()
   {
/*  55 */     QueryFilter filter = new QueryFilter(getRequest());
/*  56 */     List list = this.leaveTypeService.getAll(filter);
 
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
/*  77 */     List<LeaveType> list = this.leaveTypeService.getAll(filter);
/*  78 */     StringBuffer sb = new StringBuffer("[");
/*  79 */     for (LeaveType type : list) {
/*  80 */       sb.append("['").append(type.getTypeId()).append("','")
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
/*  99 */         this.leaveTypeService.remove(new Long(id));
       }
     }
 
/* 103 */     this.jsonString = "{success:true}";
 
/* 105 */     return "success";
   }
 
   public String get()
   {
/* 113 */     LeaveType leaveType = (LeaveType)this.leaveTypeService.get(this.typeId);
 
/* 117 */     StringBuffer sb = new StringBuffer("{success:true,data:");
 
/* 119 */     JSONSerializer serializer = new JSONSerializer();
/* 120 */     sb.append(serializer.exclude(new String[] { "class", "department.class" }).serialize(leaveType));
/* 121 */     sb.append("}");
/* 122 */     setJsonString(sb.toString());
 
/* 124 */     return "success";
   }
 
   public String save()
   {
/* 130 */     this.leaveTypeService.save(this.leaveType);
/* 131 */     setJsonString("{success:true}");
/* 132 */     return "success";
   }
   
   public String saveList() {
		String data = getRequest().getParameter("data");
		if (StringUtils.isNotEmpty(data)) {
			Gson gson = new Gson();
			LeaveType[] artl = gson.fromJson(data,
					new com.google.gson.reflect.TypeToken<LeaveType[]>() {
					}.getType());
			for (LeaveType leaveType : artl) {
				if (leaveType.getTypeId().longValue() == -1L) {
					leaveType.setTypeId(null);
				}
				if (leaveType.getProcessDefId() != null) {
					this.leaveTypeService.save(leaveType);
				} else {
					setJsonString("{success:false}");
				}
			}
		}
		setJsonString("{success:true}");
		return "success";
	}
 }
