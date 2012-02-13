/*    */ package com.xpsoft.core.jbpm.servlet;
/*    */ 
/*    */ import com.xpsoft.core.jbpm.jpdl.JpdlModel;
/*    */ import com.xpsoft.core.jbpm.jpdl.JpdlModelDrawer;
/*    */ import com.xpsoft.core.util.AppUtil;
/*    */ import com.xpsoft.oa.service.flow.JbpmService;
/*    */ import java.io.IOException;
/*    */ import java.util.Set;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.jbpm.api.ProcessInstance;
/*    */ 
/*    */ public class JpdlImageServlet extends HttpServlet
/*    */ {
/* 32 */   private Log logger = LogFactory.getLog(JpdlImageServlet.class);
/*    */ 
/* 37 */   private JbpmService jbpmService = (JbpmService)AppUtil.getBean("jbpmService");
/*    */ 
/*    */   public String getProcessDefintionXml(HttpServletRequest request)
/*    */   {
/* 46 */     String taskId = request.getParameter("taskId");
/*    */ 
/* 48 */     if (StringUtils.isNotEmpty(taskId)) {
/* 49 */       ProcessInstance pi = this.jbpmService.getProcessInstanceByTaskId(taskId);
/* 50 */       return this.jbpmService.getDefinitionXmlByPiId(pi.getId());
/*    */     }
/*    */ 
/* 53 */     String deployId = request.getParameter("deployId");
/* 54 */     if (StringUtils.isNotEmpty(deployId)) {
/* 55 */       return this.jbpmService.getDefinitionXmlByDpId(deployId);
/*    */     }
/*    */ 
/* 58 */     String piId = request.getParameter("piId");
/* 59 */     if ((StringUtils.isNotEmpty(piId)) && (!"null".equals(piId))) {
/* 60 */       return this.jbpmService.getDefinitionXmlByPiId(piId);
/*    */     }
/*    */ 
/* 63 */     String defId = request.getParameter("defId");
/* 64 */     return this.jbpmService.getDefinitionXmlByDefId(new Long(defId));
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 78 */     response.setCharacterEncoding("UTF-8");
/*    */ 
/* 80 */     String defXml = getProcessDefintionXml(request);
/*    */     try {
/* 82 */       JpdlModel jpdlModel = new JpdlModel(defXml);
/* 83 */       String taskId = request.getParameter("taskId");
/* 84 */       ProcessInstance pi = null;
/*    */ 
/* 86 */       if (StringUtils.isNotEmpty(taskId)) {
/* 87 */         pi = this.jbpmService.getProcessInstanceByTaskId(taskId);
/*    */       } else {
/* 89 */         String piId = request.getParameter("piId");
/* 90 */         if (StringUtils.isNotEmpty(piId)) {
/* 91 */           pi = this.jbpmService.getProcessInstance(piId);
/*    */         }
/*    */       }
/* 94 */       if (pi != null) {
/* 95 */         Set activeActivityNames = pi.findActiveActivityNames();
/* 96 */         if (activeActivityNames != null) {
/* 97 */           jpdlModel.setActivityNames(activeActivityNames);
/*    */         }
/*    */       }
/* 100 */       response.setContentType("image/png");
/* 101 */       ImageIO.write(new JpdlModelDrawer().draw(jpdlModel), "png", response.getOutputStream());
/*    */     } catch (Exception ex) {
/* 103 */       ex.printStackTrace();
/*    */     }
/*    */   }
/*    */ }