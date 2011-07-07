/*     */ package com.htsoft.oa.action.admin;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.admin.OfficeGoodsType;
/*     */ import com.htsoft.oa.service.admin.OfficeGoodsService;
/*     */ import com.htsoft.oa.service.admin.OfficeGoodsTypeService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class OfficeGoodsTypeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private OfficeGoodsTypeService officeGoodsTypeService;
/*     */   private OfficeGoodsType officeGoodsType;
/*     */ 
/*     */   @Resource
/*     */   private OfficeGoodsService officeGoodsService;
/*     */   private Long typeId;
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  37 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long typeId) {
/*  41 */     this.typeId = typeId;
/*     */   }
/*     */ 
/*     */   public OfficeGoodsType getOfficeGoodsType() {
/*  45 */     return this.officeGoodsType;
/*     */   }
/*     */ 
/*     */   public void setOfficeGoodsType(OfficeGoodsType officeGoodsType) {
/*  49 */     this.officeGoodsType = officeGoodsType;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  57 */     QueryFilter filter = new QueryFilter(getRequest());
/*  58 */     List<OfficeGoodsType> list = this.officeGoodsTypeService.getAll(filter);
/*  59 */     Type type = new TypeToken<List<OfficeGoodsType>>() {  }
/*  59 */     .getType();
/*  60 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  61 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  62 */     Gson gson = new Gson();
/*  63 */     buff.append(gson.toJson(list, type));
/*  64 */     buff.append("}");
/*  65 */     this.jsonString = buff.toString();
/*  66 */     return "success";
/*     */   }
/*     */ 
/*     */   public String tree()
/*     */   {
/*  75 */     String method = getRequest().getParameter("method");
/*  76 */     List<OfficeGoodsType> list = this.officeGoodsTypeService.getAll();
/*  77 */     StringBuffer sb = new StringBuffer();
/*  78 */     int i = 0;
/*  79 */     if (StringUtils.isNotEmpty(method)) {
/*  80 */       sb.append("[");
/*     */     } else {
/*  82 */       i++;
/*  83 */       sb.append("[{id:'0',text:'办公用品分类',expanded:true,children:[");
/*     */     }
/*  85 */     for (OfficeGoodsType officeGoodsType : list) {
/*  86 */       sb.append("{id:'" + officeGoodsType.getTypeId() + "',text:'" + officeGoodsType.getTypeName() + "',leaf:true},");
/*     */     }
/*  88 */     if (list.size() > 0) {
/*  89 */       sb.deleteCharAt(sb.length() - 1);
/*     */     }
/*  91 */     if (i == 0)
/*  92 */       sb.append("]");
/*     */     else {
/*  94 */       sb.append("]}]");
/*     */     }
/*  96 */     setJsonString(sb.toString());
/*  97 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 105 */     String[] ids = getRequest().getParameterValues("ids");
/* 106 */     if (ids != null) {
/* 107 */       for (String id : ids) {
/* 108 */         QueryFilter filter = new QueryFilter(getRequest());
/* 109 */         filter.addFilter("Q_officeGoodsType.typeId_L_EQ", id);
/* 110 */         List list = this.officeGoodsService.getAll(filter);
/* 111 */         if (list.size() > 0) {
/* 112 */           this.jsonString = "{success:false,message:'该类型下还有用品，请转移后再删除！'}";
/* 113 */           return "success";
/*     */         }
/* 115 */         this.officeGoodsTypeService.remove(new Long(id));
/*     */       }
/*     */     }
/* 118 */     this.jsonString = "{success:true}";
/* 119 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 127 */     OfficeGoodsType officeGoodsType = (OfficeGoodsType)this.officeGoodsTypeService.get(this.typeId);
/* 128 */     Gson gson = new Gson();
/*     */ 
/* 130 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 131 */     sb.append(gson.toJson(officeGoodsType));
/* 132 */     sb.append("}");
/* 133 */     setJsonString(sb.toString());
/* 134 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 140 */     this.officeGoodsTypeService.save(this.officeGoodsType);
/* 141 */     setJsonString("{success:true}");
/* 142 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.admin.OfficeGoodsTypeAction
 * JD-Core Version:    0.6.0
 */