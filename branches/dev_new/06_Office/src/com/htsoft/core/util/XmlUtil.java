/*     */ package com.htsoft.core.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.DocumentResult;
/*     */ import org.dom4j.io.DocumentSource;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class XmlUtil
/*     */ {
/*  38 */   private static final Log logger = LogFactory.getLog(XmlUtil.class);
/*     */ 
/*     */   public static String docToString(Document document)
/*     */   {
/*  46 */     String s = "";
/*     */     try {
/*  48 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */ 
/*  50 */       OutputFormat format = new OutputFormat("  ", true, "UTF-8");
/*  51 */       XMLWriter writer = new XMLWriter(out, format);
/*  52 */       writer.write(document);
/*  53 */       s = out.toString("UTF-8");
/*     */     } catch (Exception ex) {
/*  55 */       logger.error("docToString error:" + ex.getMessage());
/*     */     }
/*  57 */     return s;
/*     */   }
/*     */ 
/*     */   public static Document stringToDocument(String s)
/*     */   {
/*  65 */     Document doc = null;
/*     */     try {
/*  67 */       doc = DocumentHelper.parseText(s);
/*     */     } catch (Exception ex) {
/*  69 */       logger.error("stringToDocument error:" + ex.getMessage());
/*     */     }
/*  71 */     return doc;
/*     */   }
/*     */ 
/*     */   public static boolean docToXmlFile(Document document, String filename)
/*     */   {
/*  81 */     boolean flag = true;
/*     */     try {
/*  83 */       OutputFormat format = OutputFormat.createPrettyPrint();
/*  84 */       format.setEncoding("UTF-8");
/*  85 */       XMLWriter writer = new XMLWriter(
/*  86 */         new FileWriter(new File(filename)), format);
/*  87 */       writer.write(document);
/*  88 */       writer.close();
/*     */     } catch (Exception ex) {
/*  90 */       flag = false;
/*  91 */       logger.error("docToXmlFile error:" + ex.getMessage());
/*     */     }
/*  93 */     return flag;
/*     */   }
/*     */ 
/*     */   public static boolean stringToXmlFile(String str, String filename)
/*     */   {
/* 102 */     boolean flag = true;
/*     */     try {
/* 104 */       Document doc = DocumentHelper.parseText(str);
/* 105 */       flag = docToXmlFile(doc, filename);
/*     */     } catch (Exception ex) {
/* 107 */       flag = false;
/* 108 */       logger.error("stringToXmlFile error:" + ex.getMessage());
/*     */     }
/* 110 */     return flag;
/*     */   }
/*     */ 
/*     */   public static Document load(String filename)
/*     */   {
/* 118 */     Document document = null;
/*     */     try {
/* 120 */       SAXReader saxReader = new SAXReader();
/* 121 */       saxReader.setEncoding("UTF-8");
/* 122 */       document = saxReader.read(new File(filename));
/*     */     } catch (Exception ex) {
/* 124 */       logger.error("load XML File error:" + ex.getMessage());
/*     */     }
/* 126 */     return document;
/*     */   }
/*     */ 
/*     */   public static Document load(InputStream is)
/*     */   {
/* 134 */     Document document = null;
/*     */     try {
/* 136 */       SAXReader saxReader = new SAXReader();
/*     */ 
/* 138 */       saxReader.setEncoding("UTF-8");
/* 139 */       document = saxReader.read(is);
/*     */     } catch (Exception ex) {
/* 141 */       logger.error("load XML File error:" + ex.getMessage());
/*     */     }
/* 143 */     return document;
/*     */   }
/*     */ 
/*     */   public static Document load(InputStream is, String encode)
/*     */   {
/* 153 */     Document document = null;
/*     */     try {
/* 155 */       SAXReader saxReader = new SAXReader();
/* 156 */       saxReader.setEncoding(encode);
/* 157 */       document = saxReader.read(is);
/*     */     } catch (Exception ex) {
/* 159 */       logger.error("load XML File error:" + ex.getMessage());
/*     */     }
/* 161 */     return document;
/*     */   }
/*     */ 
/*     */   public static Document styleDocument(Document document, String stylesheet)
/*     */     throws Exception
/*     */   {
/* 170 */     TransformerFactory factory = TransformerFactory.newInstance();
/* 171 */     Transformer transformer = factory.newTransformer(
/* 172 */       new StreamSource(stylesheet));
/*     */ 
/* 176 */     DocumentSource source = new DocumentSource(document);
/* 177 */     DocumentResult result = new DocumentResult();
/* 178 */     transformer.transform(source, result);
/*     */ 
/* 181 */     Document transformedDoc = result.getDocument();
/* 182 */     return transformedDoc;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 187 */     String filePath = "L:/devtools/workspace/eoffice/web/js/menu.xml";
/* 188 */     String style = "L:/devtools/workspace/eoffice/web/js/menu-public.xsl";
/* 189 */     Document doc = load(filePath);
/*     */     try {
/* 191 */       Document another = styleDocument(doc, style);
/* 192 */       System.out.println("xml:" + another.asXML());
/*     */ 
/* 196 */       Document publicDoc = another;
/* 197 */       Element rootEl = publicDoc.getRootElement();
/* 198 */       List idNodes = rootEl.selectNodes("/Menus//*");
/*     */ 
/* 200 */       System.out.println("size:" + idNodes.size());
/*     */ 
/* 202 */       for (int i = 0; i < idNodes.size(); i++) {
/* 203 */         Element el = (Element)idNodes.get(i);
/* 204 */         Attribute attr = el.attribute("id");
/* 205 */         if (attr != null) {
/* 206 */           System.out.println("attr:" + attr.getValue());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 214 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.util.XmlUtil
 * JD-Core Version:    0.6.0
 */