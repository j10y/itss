/*     */ package com.htsoft.core.jbpm;
/*     */ 
/*     */ import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.ui.velocity.CommonsLoggingLogSystem;

import com.htsoft.core.util.AppUtil;
/*     */ 
/*     */ public class FlowVelocityEngine
/*     */   implements FactoryBean
/*     */ {
/*  44 */   private Log logger = LogFactory.getLog(FlowVelocityEngine.class);
/*     */   private Properties velocityProperties;
/*     */   private String templatePath;
/*     */ 
/*     */   public Properties getVelocityProperties()
/*     */   {
/*  57 */     return this.velocityProperties;
/*     */   }
/*     */ 
/*     */   public void setVelocityProperties(Properties velocityProperties) {
/*  61 */     this.velocityProperties = velocityProperties;
/*     */   }
/*     */ 
/*     */   public Object getObject() throws Exception
/*     */   {
/*  66 */     return createVelocityEngine();
/*     */   }
/*     */ 
/*     */   public Class getObjectType()
/*     */   {
/*  71 */     return VelocityEngine.class;
/*     */   }
/*     */ 
/*     */   public boolean isSingleton()
/*     */   {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   public VelocityEngine createVelocityEngine()
/*     */     throws IOException, VelocityException
/*     */   {
/*  87 */     if (this.logger.isDebugEnabled()) {
/*  88 */       this.logger.debug("create flowVelocityEngine... and the path is " + AppUtil.getAppAbsolutePath() + this.templatePath);
/*     */     }
/*     */ 
/*  91 */     VelocityEngine velocityEngine = new VelocityEngine();
/*     */ 
/*  93 */     velocityEngine.setProperty("runtime.log.logsystem", new CommonsLoggingLogSystem());
/*     */ 
/*  96 */     for (Iterator it = this.velocityProperties.entrySet().iterator(); it.hasNext(); ) {
/*  97 */       Map.Entry entry = (Map.Entry)it.next();
/*  98 */       if (!(entry.getKey() instanceof String)) {
/*  99 */         throw new IllegalArgumentException("Illegal property key [" + entry.getKey() + "]: only Strings allowed");
/*     */       }
/* 101 */       velocityEngine.setProperty((String)entry.getKey(), entry.getValue());
/*     */     }
/*     */ 
/* 104 */     velocityEngine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
/*     */ 
/* 106 */     velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
/*     */ 
/* 110 */     velocityEngine.setProperty("file.resource.loader.path", AppUtil.getAppAbsolutePath() + this.templatePath);
/*     */     try
/*     */     {
/* 113 */       velocityEngine.init();
/*     */     }
/*     */     catch (IOException ex) {
/* 116 */       throw ex;
/*     */     }
/*     */     catch (VelocityException ex) {
/* 119 */       throw ex;
/*     */     }
/*     */     catch (RuntimeException ex) {
/* 122 */       throw ex;
/*     */     }
/*     */     catch (Exception ex) {
/* 125 */       this.logger.error("Why does VelocityEngine throw a generic checked exception, after all?", ex);
/* 126 */       throw new VelocityException(ex.toString());
/*     */     }
/*     */ 
/* 129 */     return velocityEngine;
/*     */   }
/*     */ 
/*     */   public String getTemplatePath() {
/* 133 */     return this.templatePath;
/*     */   }
/*     */ 
/*     */   public void setTemplatePath(String templatePath) {
/* 137 */     this.templatePath = templatePath;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.FlowVelocityEngine
 * JD-Core Version:    0.6.0
 */