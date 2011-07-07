/*     */ package com.htsoft.oa.action.flow;
/*     */ 
/*     */ import com.htsoft.core.jbpm.pv.ParamField;
/*     */ import com.htsoft.core.util.AppUtil;
/*     */ import com.htsoft.core.util.XmlUtil;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class ProcessActivityAssistant
/*     */ {
/*  30 */   private static final Log logger = LogFactory.getLog(ProcessActivityAssistant.class);
/*     */ 
/*     */   public static Map<String, ParamField> constructFieldMap(String processName, String activityName)
/*     */   {
/*  41 */     String fieldsXmlLoaction = getFieldAbsPath(processName, activityName);
/*     */ 
/*  43 */     InputStream is = null;
/*  44 */     Map map = new LinkedHashMap();
/*     */     try {
/*  46 */       is = new FileInputStream(fieldsXmlLoaction);
/*     */     } catch (Exception ex) {
/*  48 */       logger.warn("error when read the file from " + activityName + "-fields.xml, the reason is not upload the ");
/*     */     }
/*     */ 
/*  51 */     if (is == null) {
/*     */       try {
/*  53 */         is = new FileInputStream(getCommonFieldsAbsPath(activityName));
/*     */       } catch (Exception ex) {
/*  55 */         logger.warn("error when read the file from 通用、表单-fields.xml, the reason is not upload the ");
/*     */       }
/*     */     }
/*     */ 
/*  59 */     Document doc = XmlUtil.load(is);
/*  60 */     Element fields = doc.getRootElement();
/*  61 */     List<Element> els = fields.elements();
/*  62 */     for (Element el : els) {
/*  63 */       String name = el.attribute("name").getValue();
/*     */ 
/*  65 */       Attribute attLabel = el.attribute("label");
/*  66 */       Attribute attType = el.attribute("type");
/*  67 */       Attribute attLength = el.attribute("length");
/*  68 */       Attribute attIsShowed = el.attribute("isShowed");
/*     */ 
/*  70 */       String label = attLabel == null ? name : attLabel.getValue();
/*  71 */       String type = attType == null ? "varchar" : attType.getValue();
/*  72 */       Integer length = Integer.valueOf(attLength == null ? 0 : new Integer(attLength.getValue()).intValue());
/*  73 */       Short isShowed = Short.valueOf((attIsShowed == null) || ("true".equals(attIsShowed.getValue())) ? (short)1 : (short)0);
/*     */ 
/*  75 */       ParamField pf = new ParamField(name, type, label, length, isShowed);
/*  76 */       map.put(name, pf);
/*     */     }
/*     */ 
/*  79 */     return map;
/*     */   }
/*     */ 
/*     */   public static String getStartFormPath(String processName) {
/*  83 */     return "/" + processName + "/开始.vm";
/*     */   }
/*     */ 
/*     */   public static String getFormPath(String processName, String activityName)
/*     */   {
/*  91 */     return "/" + processName + "/" + activityName + ".vm";
/*     */   }
/*     */ 
/*     */   public static String getFieldAbsPath(String processName, String activityName)
/*     */   {
/* 101 */     return AppUtil.getFlowFormAbsolutePath() + processName + "/" + activityName + "-fields.xml";
/*     */   }
/*     */ 
/*     */   public static String getFieldStartAbsPath(String processName)
/*     */   {
/* 109 */     return AppUtil.getFlowFormAbsolutePath() + processName + "/开始-fields.xml";
/*     */   }
/*     */ 
/*     */   public static String getCommonFormPath(String activityName)
/*     */   {
/* 118 */     if ("开始".equals(activityName)) {
/* 119 */       return "/通用/开始.vm";
/*     */     }
/* 121 */     return "/通用/表单.vm";
/*     */   }
/*     */ 
/*     */   public static String getCommonFieldsAbsPath(String activityName)
/*     */   {
/* 131 */     String absPath = AppUtil.getFlowFormAbsolutePath();
/*     */ 
/* 133 */     if ("开始".equals(activityName)) {
/* 134 */       return absPath + "通用/开始-fields.xml";
/*     */     }
/* 136 */     return absPath + "通用/表单-fields.xml";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.flow.ProcessActivityAssistant
 * JD-Core Version:    0.6.0
 */