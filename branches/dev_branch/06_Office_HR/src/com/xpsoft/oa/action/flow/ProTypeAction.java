/*     */ package com.xpsoft.oa.action.flow;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.oa.model.flow.ProType;
/*     */ import com.xpsoft.oa.service.flow.ProTypeService;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ProTypeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ProTypeService proTypeService;
/*     */   private ProType proType;
/*     */   private Long typeId;
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  33 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long typeId) {
/*  37 */     this.typeId = typeId;
/*     */   }
/*     */ 
/*     */   public ProType getProType() {
/*  41 */     return this.proType;
/*     */   }
/*     */ 
/*     */   public void setProType(ProType proType) {
/*  45 */     this.proType = proType;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  52 */     List<ProType> processTypeList = this.proTypeService.getAll();
/*  53 */     StringBuffer sb = new StringBuffer("[");
/*  54 */     for (ProType proType : processTypeList) {
/*  55 */       sb.append("{id:'").append(proType.getTypeId()).append("',text:'").append(proType.getTypeName()).append("',leaf:true},");
/*     */     }
/*  57 */     if (!processTypeList.isEmpty()) {
/*  58 */       sb.deleteCharAt(sb.length() - 1);
/*     */     }
/*  60 */     sb.append("]");
/*  61 */     this.jsonString = sb.toString();
/*     */ 
/*  63 */     return "success";
/*     */   }
/*     */ 
/*     */   public String root() {
/*  67 */     List<ProType> processTypeList = this.proTypeService.getAll();
/*  68 */     StringBuffer sb = new StringBuffer("[{id:'0',text:'流程分类',leaf:false,expanded:true,children:[");
/*  69 */     for (ProType proType : processTypeList) {
/*  70 */       sb.append("{id:'").append(proType.getTypeId()).append("',text:'").append(proType.getTypeName()).append("',leaf:true},");
/*     */     }
/*  72 */     if (!processTypeList.isEmpty()) {
/*  73 */       sb.deleteCharAt(sb.length() - 1);
/*     */     }
/*  75 */     sb.append("]}]");
/*  76 */     this.jsonString = sb.toString();
/*     */ 
/*  78 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  87 */     String[] ids = getRequest().getParameterValues("ids");
/*  88 */     if (ids != null) {
/*  89 */       for (String id : ids) {
/*  90 */         this.proTypeService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  94 */     this.jsonString = "{success:true}";
/*     */ 
/*  96 */     return "success";
/*     */   }
/*     */ 
/*     */   public String remove()
/*     */   {
/* 104 */     this.proTypeService.remove(this.typeId);
/* 105 */     this.jsonString = "{success:true}";
/* 106 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 114 */     ProType proType = (ProType)this.proTypeService.get(this.typeId);
/*     */ 
/* 116 */     Gson gson = new Gson();
/*     */ 
/* 118 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 119 */     sb.append(gson.toJson(proType));
/* 120 */     sb.append("}");
/* 121 */     setJsonString(sb.toString());
/*     */ 
/* 123 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 129 */     this.proTypeService.save(this.proType);
/* 130 */     setJsonString("{success:true}");
/* 131 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.flow.ProTypeAction
 * JD-Core Version:    0.6.0
 */