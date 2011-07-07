/*     */ package com.htsoft.core.web.action;
/*     */ 
/*     */ import com.htsoft.core.engine.MailEngine;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ import org.springframework.mail.MailSender;
/*     */ 
/*     */ public class BaseAction
/*     */ {
/*     */   public static final String SUCCESS = "success";
/*     */   public static final String INPUT = "input";
/*     */   private String successResultValue;
/*     */   public static final String JSON_SUCCESS = "{success:true}";
/*     */   protected String dir;
/*     */   protected String sort;
/*  57 */   protected Integer limit = Integer.valueOf(25);
/*     */ 
/*  61 */   protected Integer start = Integer.valueOf(0);
/*     */   protected String jsonString;
/*     */   private static final long serialVersionUID = 1L;
/*  82 */   protected final transient Log logger = LogFactory.getLog(getClass());
/*     */   protected MailEngine mailEngine;
/*     */   protected MailSender mailSender;
/*  89 */   public final String CANCEL = "cancel";
/*     */ 
/*  91 */   public final String VIEW = "view";
/*     */ 
/*     */   public String getSuccessResultValue()
/*     */   {
/*  34 */     return this.successResultValue;
/*     */   }
/*     */ 
/*     */   public void setSuccessResultValue(String successResultValue) {
/*  38 */     this.successResultValue = successResultValue;
/*     */   }
/*     */ 
/*     */   public void setJsonString(String jsonString)
/*     */   {
/*  66 */     this.jsonString = jsonString;
/*     */   }
/*     */ 
/*     */   public String getJsonString() {
/*  70 */     return this.jsonString;
/*     */   }
/*     */ 
/*     */   public BaseAction() {
/*  74 */     setSuccessResultValue("/jsonString.jsp");
/*     */   }
/*     */ 
/*     */   protected HttpServletRequest getRequest()
/*     */   {
/*  99 */     return ServletActionContext.getRequest();
/*     */   }
/*     */ 
/*     */   protected HttpServletResponse getResponse()
/*     */   {
/* 108 */     return ServletActionContext.getResponse();
/*     */   }
/*     */ 
/*     */   protected HttpSession getSession()
/*     */   {
/* 118 */     return getRequest().getSession();
/*     */   }
/*     */ 
/*     */   protected PagingBean getInitPagingBean()
/*     */   {
/* 124 */     PagingBean pb = new PagingBean(this.start.intValue(), this.limit.intValue());
/* 125 */     return pb;
/*     */   }
/*     */ 
/*     */   public void setMailEngine(MailEngine mailEngine) {
/* 129 */     this.mailEngine = mailEngine;
/*     */   }
/*     */ 
/*     */   public MailEngine getMailEngine() {
/* 133 */     return this.mailEngine;
/*     */   }
/*     */ 
/*     */   public String list() {
/* 137 */     return "success";
/*     */   }
/*     */ 
/*     */   public String edit() {
/* 141 */     return "input";
/*     */   }
/*     */ 
/*     */   public String save() {
/* 145 */     return "input";
/*     */   }
/*     */ 
/*     */   public String delete() {
/* 149 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDelete() {
/* 153 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiSave() {
/* 157 */     return "success";
/*     */   }
/*     */ 
/*     */   public String getDir() {
/* 161 */     return this.dir;
/*     */   }
/*     */ 
/*     */   public void setDir(String dir) {
/* 165 */     this.dir = dir;
/*     */   }
/*     */ 
/*     */   public String getSort() {
/* 169 */     return this.sort;
/*     */   }
/*     */ 
/*     */   public void setSort(String sort) {
/* 173 */     this.sort = sort;
/*     */   }
/*     */ 
/*     */   public Integer getLimit() {
/* 177 */     return this.limit;
/*     */   }
/*     */ 
/*     */   public void setLimit(Integer limit) {
/* 181 */     this.limit = limit;
/*     */   }
/*     */ 
/*     */   public Integer getStart() {
/* 185 */     return this.start;
/*     */   }
/*     */ 
/*     */   public void setStart(Integer start) {
/* 189 */     this.start = start;
/*     */   }
/*     */ 
/*     */   public String execute() throws Exception {
/* 193 */     HttpServletRequest request = getRequest();
/* 194 */     String uri = request.getRequestURI();
/* 195 */     String url = uri.substring(request.getContextPath().length());
/* 196 */     url = url.replace(".do", ".jsp");
/* 197 */     url = "/pages" + url;
/*     */ 
/* 199 */     if (this.logger.isInfoEnabled()) {
/* 200 */       this.logger.info("forward url:" + url);
/*     */     }
/* 202 */     setSuccessResultValue(url);
/* 203 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.web.action.BaseAction
 * JD-Core Version:    0.6.0
 */