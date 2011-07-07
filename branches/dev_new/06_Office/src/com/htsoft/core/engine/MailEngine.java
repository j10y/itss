/*     */ package com.htsoft.core.engine;
/*     */ 
/*     */ import com.htsoft.core.util.AppUtil;
/*     */ import java.io.File;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.velocity.app.VelocityEngine;
/*     */ import org.apache.velocity.exception.VelocityException;
/*     */ import org.htmlparser.Parser;
/*     */ import org.htmlparser.visitors.HtmlPage;
/*     */ import org.springframework.core.io.ClassPathResource;
/*     */ import org.springframework.mail.MailException;
/*     */ import org.springframework.mail.SimpleMailMessage;
/*     */ import org.springframework.mail.javamail.JavaMailSender;
/*     */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*     */ import org.springframework.mail.javamail.MimeMessageHelper;
/*     */ import org.springframework.ui.velocity.VelocityEngineUtils;
/*     */ 
/*     */ public class MailEngine
/*     */ {
/*  34 */   private final Log logger = LogFactory.getLog(MailEngine.class);
/*     */   private JavaMailSender mailSender;
/*     */   private VelocityEngine velocityEngine;
/*     */   private String defaultFrom;
/*     */ 
/*     */   public void setMailSender(JavaMailSender mailSender)
/*     */   {
/*  40 */     this.mailSender = mailSender;
/*     */   }
/*     */ 
/*     */   public void setVelocityEngine(VelocityEngine velocityEngine) {
/*  44 */     this.velocityEngine = velocityEngine;
/*     */   }
/*     */ 
/*     */   public void setFrom(String from) {
/*  48 */     this.defaultFrom = from;
/*     */   }
/*     */ 
/*     */   public void sendMessage(SimpleMailMessage msg, String templateName, Map model)
/*     */   {
/*  58 */     String result = null;
/*     */     try
/*     */     {
/*  61 */       result = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, templateName, model);
/*     */     } catch (VelocityException e) {
/*  63 */       e.printStackTrace();
/*  64 */       this.logger.error(e.getMessage());
/*     */     }
/*     */ 
/*  67 */     msg.setText(result);
/*  68 */     send(msg);
/*     */   }
/*     */ 
/*     */   public void send(SimpleMailMessage msg)
/*     */     throws MailException
/*     */   {
/*     */     try
/*     */     {
/*  78 */       this.mailSender.send(msg);
/*     */     } catch (MailException ex) {
/*  80 */       this.logger.error(ex.getMessage());
/*  81 */       throw ex;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendMessage(String[] recipients, String sender, ClassPathResource resource, String bodyText, String subject, String attachmentName)
/*     */     throws MessagingException
/*     */   {
/* 100 */     MimeMessage message = ((JavaMailSenderImpl)this.mailSender).createMimeMessage();
/*     */ 
/* 103 */     MimeMessageHelper helper = new MimeMessageHelper(message, true);
/*     */ 
/* 105 */     helper.setTo(recipients);
/*     */ 
/* 108 */     if (sender == null)
/* 109 */       helper.setFrom(this.defaultFrom);
/*     */     else {
/* 111 */       helper.setFrom(sender);
/*     */     }
/*     */ 
/* 114 */     helper.setText(bodyText);
/* 115 */     helper.setSubject(subject);
/*     */ 
/* 117 */     helper.addAttachment(attachmentName, resource);
/*     */ 
/* 119 */     ((JavaMailSenderImpl)this.mailSender).send(message);
/*     */   }
/*     */ 
/*     */   public String sendMimeMessage(String from, String[] tos, String cc, String replyTo, String subject, String htmlMsgContent, String[] attachedFileNames, File[] attachedFiles, boolean inline)
/*     */   {
/* 142 */     if ((tos == null) || (tos.length == 0) || (tos[0] == null) || 
/* 143 */       ("".equals(tos[0]))) {
/* 144 */       if (this.logger.isErrorEnabled()) {
/* 145 */         this.logger
/* 146 */           .error("Recipient found empty while sending a email, no further processing. Mail subject is:" + 
/* 147 */           subject);
/*     */       }
/* 149 */       return "Recipient is empty";
/*     */     }
/*     */ 
/* 152 */     JavaMailSenderImpl mailSender = (JavaMailSenderImpl)AppUtil.getBean("mailSender");
/* 153 */     Map configs = AppUtil.getSysConfig();
/* 154 */     mailSender.setHost((String)configs.get("host"));
/* 155 */     mailSender.setUsername((String)configs.get("username"));
/* 156 */     mailSender.setPassword((String)configs.get("password"));
/* 157 */     mailSender.setDefaultEncoding("UTF-8");
/* 158 */     mailSender.setProtocol("smtp");
/* 159 */     Properties props = new Properties();
/* 160 */     props.put("mail.smtp.auth", "true");
/* 161 */     setFrom((String)configs.get("from"));
/* 162 */     mailSender.setJavaMailProperties(props);
/* 163 */     MimeMessage message = mailSender.createMimeMessage();
/*     */     try
/*     */     {
/* 166 */       MimeMessageHelper helper = new MimeMessageHelper(message, attachedFiles != null);
/*     */ 
/* 168 */       helper.setFrom(from == null ? this.defaultFrom : from);
/* 169 */       helper.setTo(tos);
/* 170 */       if ((cc != null) && (!"".equals(cc))) {
/* 171 */         helper.setCc(cc);
/*     */       }
/* 173 */       if ((replyTo != null) && (!"".equals(replyTo))) {
/* 174 */         helper.setReplyTo(replyTo);
/*     */       }
/*     */ 
/* 177 */       helper.setSubject(subject);
/*     */ 
/* 179 */       helper.setText(htmlMsgContent, true);
/*     */ 
/* 182 */       if (attachedFiles != null) {
/* 183 */         if (inline) {
/* 184 */           for (int i = 0; i < attachedFiles.length; i++)
/* 185 */             helper.addInline(attachedFileNames[i], attachedFiles[i]);
/*     */         }
/*     */         else {
/* 188 */           for (int i = 0; i < attachedFiles.length; i++) {
/* 189 */             helper.addAttachment(attachedFileNames[i], attachedFiles[i]);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 195 */       mailSender.send(message);
/* 196 */       if (this.logger.isDebugEnabled())
/* 197 */         this.logger.debug("A email has been sent successfully to: " + StringUtils.join(tos, ','));
/*     */     }
/*     */     catch (Throwable e) {
/* 200 */       this.logger.error("Error occured when sending email.", e);
/* 201 */       return "Error occured when sending email." + e.getMessage();
/*     */     }
/*     */ 
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */   public String sendTemplateMail(String templateName, Map<String, Object> model, String subject, String from, String[] tos, String cc, String replyTo, String[] attachedFileNames, File[] attachedFiles, boolean inline)
/*     */   {
/* 227 */     String mailContent = null;
/* 228 */     String mailSubject = subject;
/*     */     try {
/* 230 */       mailContent = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, templateName, model);
/* 231 */       if (subject == null) {
/* 232 */         Parser myParser = Parser.createParser(mailContent, "UTF-8");
/* 233 */         HtmlPage visitor = new HtmlPage(myParser);
/* 234 */         myParser.visitAllNodesWith(visitor);
/* 235 */         mailSubject = visitor.getTitle();
/*     */       }
/*     */     } catch (Throwable e) {
/* 238 */       throw new RuntimeException("Email template processing error, Check log for detail infomation. Template path: " + 
/* 238 */         templateName, e);
/*     */     }
/*     */ 
/* 241 */     return sendMimeMessage(from, tos, cc, replyTo, mailSubject, 
/* 242 */       mailContent, attachedFileNames, attachedFiles, inline);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.engine.MailEngine
 * JD-Core Version:    0.6.0
 */