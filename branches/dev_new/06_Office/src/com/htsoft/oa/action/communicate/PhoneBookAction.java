/*     */ package com.htsoft.oa.action.communicate;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.communicate.PhoneBook;
/*     */ import com.htsoft.oa.model.communicate.PhoneGroup;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.service.communicate.PhoneBookService;
/*     */ import flexjson.DateTransformer;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class PhoneBookAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private PhoneBookService phoneBookService;
/*     */   private PhoneBook phoneBook;
/*     */   private PhoneGroup phoneGroup;
/*     */   private Long phoneId;
/*     */ 
/*     */   public Long getPhoneId()
/*     */   {
/*  42 */     return this.phoneId;
/*     */   }
/*     */ 
/*     */   public void setPhoneId(Long phoneId) {
/*  46 */     this.phoneId = phoneId;
/*     */   }
/*     */ 
/*     */   public PhoneGroup getPhoneGroup() {
/*  50 */     return this.phoneGroup;
/*     */   }
/*     */ 
/*     */   public void setPhoneGroup(PhoneGroup phoneGroup) {
/*  54 */     this.phoneGroup = phoneGroup;
/*     */   }
/*     */ 
/*     */   public PhoneBook getPhoneBook() {
/*  58 */     return this.phoneBook;
/*     */   }
/*     */ 
/*     */   public void setPhoneBook(PhoneBook phoneBook) {
/*  62 */     this.phoneBook = phoneBook;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  69 */     QueryFilter filter = new QueryFilter(getRequest());
/*  70 */     filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/*  71 */     String strGroupId = getRequest().getParameter("groupId");
/*  72 */     if (StringUtils.isNotEmpty(strGroupId)) {
/*  73 */       Long groupId = Long.valueOf(Long.parseLong(strGroupId));
/*  74 */       if (groupId.longValue() > 0L) {
/*  75 */         filter.addFilter("Q_phoneGroup.groupId_L_EQ", strGroupId);
/*     */       }
/*     */     }
/*  78 */     List<PhoneBook> list = this.phoneBookService.getAll(filter);
/*  79 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  80 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  81 */     Type type = new TypeToken< List<PhoneBook>>() {  }.getType();
/*  82 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*  83 */     buff.append(gson.toJson(list, type));
/*  84 */     buff.append("}");
/*  85 */     this.jsonString = buff.toString();
/*  86 */     return "success";
/*     */   }
/*     */ 
/*     */   public String share() {
/*  90 */     String fullname = getRequest().getParameter("fullname");
/*  91 */     String sharedUser = getRequest().getParameter("sharedUser");
/*  92 */     PagingBean pb = getInitPagingBean();
/*  93 */     List list = this.phoneBookService.sharedPhoneBooks(fullname, sharedUser, pb);
/*  94 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  95 */       .append(pb.getTotalItems()).append(",result:");
/*  96 */     JSONSerializer serializer = new JSONSerializer();
/*  97 */     buff.append(serializer.exclude(new String[] { "class", "phoneGroup", "appUser.department", "appUser.password" }).serialize(list));
/*  98 */     buff.append("}");
/*  99 */     this.jsonString = buff.toString();
/* 100 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 109 */     String[] ids = getRequest().getParameterValues("ids");
/* 110 */     if (ids != null) {
/* 111 */       for (String id : ids) {
/* 112 */         this.phoneBookService.remove(new Long(id));
/*     */       }
/*     */     }
/* 115 */     this.jsonString = "{success:true}";
/* 116 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 124 */     PhoneBook phoneBook = (PhoneBook)this.phoneBookService.get(this.phoneId);
/* 125 */     JSONSerializer serializer = new JSONSerializer();
/* 126 */     serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), new String[] { "birthday" });
/* 127 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 128 */     sb.append(serializer.exclude(new String[] { "class", "appUser", "phoneGroup.appUser" }).serialize(phoneBook));
/* 129 */     sb.append("}");
/* 130 */     setJsonString(sb.toString());
/* 131 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 137 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 138 */     this.phoneBook.setAppUser(appUser);
/* 139 */     this.phoneBookService.save(this.phoneBook);
/* 140 */     setJsonString("{success:true}");
/* 141 */     return "success";
/*     */   }
/*     */ 
/*     */   public String detail()
/*     */   {
/* 150 */     String strPhoneId = getRequest().getParameter("phoneId");
/* 151 */     if (StringUtils.isNotEmpty(strPhoneId)) {
/* 152 */       Long phoneId = new Long(strPhoneId);
/* 153 */       this.phoneBook = ((PhoneBook)this.phoneBookService.get(phoneId));
/*     */     }
/* 155 */     return "detail";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.communicate.PhoneBookAction
 * JD-Core Version:    0.6.0
 */