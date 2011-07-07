/*     */ package com.htsoft.core.web.servlet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import net.sf.jasperreports.engine.JRAbstractExporter;
/*     */ import net.sf.jasperreports.engine.JRDataSource;
/*     */ import net.sf.jasperreports.engine.JRException;
/*     */ import net.sf.jasperreports.engine.JRExporterParameter;
/*     */ import net.sf.jasperreports.engine.JasperCompileManager;
/*     */ import net.sf.jasperreports.engine.JasperFillManager;
/*     */ import net.sf.jasperreports.engine.JasperPrint;
/*     */ import net.sf.jasperreports.engine.JasperReport;
/*     */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*     */ import net.sf.jasperreports.engine.export.JExcelApiExporter;
/*     */ import net.sf.jasperreports.engine.export.JRHtmlExporter;
/*     */ import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
/*     */ import net.sf.jasperreports.engine.export.JRPdfExporter;
/*     */ import net.sf.jasperreports.engine.export.JRRtfExporter;
/*     */ import net.sf.jasperreports.engine.export.JRXmlExporter;
/*     */ import net.sf.jasperreports.engine.util.JRLoader;
/*     */ 
/*     */ public class ReportUtils
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private HttpServletResponse response;
/*     */   private HttpSession session;
/*     */ 
/*     */   public ReportUtils(HttpServletRequest request)
/*     */   {
/*  51 */     this.request = request;
/*  52 */     this.session = request.getSession();
/*     */   }
/*     */ 
/*     */   public ReportUtils(HttpServletResponse response) {
/*  56 */     this.response = response;
/*     */   }
/*     */ 
/*     */   public ReportUtils(HttpServletRequest request, HttpServletResponse response) {
/*  60 */     this(request);
/*  61 */     this.response = response;
/*     */   }
/*     */ 
/*     */   public JasperPrint getJasperPrint(String filePath, Map parameter, JRDataSource dataSource)
/*     */     throws JRException
/*     */   {
/*  75 */     JasperReport jasperReport = null;
/*     */     try {
/*  77 */       jasperReport = (JasperReport)JRLoader.loadObject(filePath);
/*  78 */       return JasperFillManager.fillReport(jasperReport, parameter, 
/*  79 */         dataSource);
/*     */     } catch (JRException e) {
/*  81 */       e.printStackTrace();
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   public JasperPrint getPrintWithBeanList(String filePath, Map parameter, List list)
/*     */     throws JRException
/*     */   {
/*  98 */     JRDataSource dataSource = new JRBeanCollectionDataSource(list);
/*  99 */     return getJasperPrint(filePath, parameter, dataSource);
/*     */   }
/*     */ 
/*     */   public JRAbstractExporter getJRExporter(DocType docType)
/*     */   {
/* 109 */     JRAbstractExporter exporter = null;
/* 110 */     switch (docType) {
/*     */     case HTML:
/* 112 */       exporter = new JRPdfExporter();
/* 113 */       break;
/*     */     case PDF:
/* 115 */       exporter = new JRHtmlExporter();
/* 116 */       break;
/*     */     case RTF:
/* 118 */       exporter = new JExcelApiExporter();
/* 119 */       break;
/*     */     case XLS:
/* 121 */       exporter = new JRXmlExporter();
/* 122 */       break;
/*     */     case XML:
/* 124 */       exporter = new JRRtfExporter();
/*     */     }
/*     */ 
/* 127 */     return exporter;
/*     */   }
/*     */ 
/*     */   public void setAttrToPage(JasperPrint jasperPrint, String report_fileName, String report_type)
/*     */   {
/* 132 */     this.session.setAttribute("REPORT_JASPERPRINT", jasperPrint);
/* 133 */     this.session.setAttribute("REPORT_FILENAME", report_fileName);
/* 134 */     this.session.setAttribute("REPORT_TYPE", report_type);
/*     */   }
/*     */ 
/*     */   public void complieJaxml(String jaxmlPath, String jasperPath)
/*     */     throws JRException
/*     */   {
/* 156 */     JasperCompileManager.compileReportToFile(jaxmlPath, jasperPath);
/*     */   }
/*     */ 
/*     */   public void servletExportPDF(String jasperPath, Map params, List sourceList, String fileName)
/*     */     throws JRException, IOException, ServletException
/*     */   {
/* 173 */     servletExportDocument(DocType.PDF, jasperPath, params, sourceList, 
/* 174 */       fileName);
/*     */   }
/*     */ 
/*     */   public void servletExportHTML(String jasperPath, Map params, List sourceList, String imageUrl)
/*     */     throws JRException, IOException, ServletException
/*     */   {
/* 192 */     this.response.setContentType("text/html");
/* 193 */     this.response.setCharacterEncoding("UTF-8");
/* 194 */     JRAbstractExporter exporter = getJRExporter(DocType.HTML);
/*     */ 
/* 196 */     JasperPrint jasperPrint = getPrintWithBeanList(jasperPath, params, 
/* 197 */       sourceList);
/*     */ 
/* 199 */     this.session.setAttribute(
/* 200 */       "net.sf.jasperreports.j2ee.jasper_print", 
/* 201 */       jasperPrint);
/*     */ 
/* 203 */     PrintWriter out = this.response.getWriter();
/*     */ 
/* 205 */     exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
/* 206 */     exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
/* 207 */     exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imageUrl);
/* 208 */     exporter.exportReport();
/*     */   }
/*     */ 
/*     */   public void servletExportExcel(String jasperPath, Map params, List sourceList, String fileName)
/*     */     throws JRException, IOException, ServletException
/*     */   {
/* 225 */     servletExportDocument(DocType.XLS, jasperPath, params, sourceList, 
/* 226 */       fileName);
/*     */   }
/*     */ 
/*     */   public void servletExportDocument(DocType docType, String jasperPath, Map params, List sourceList, String fileName)
/*     */     throws JRException, IOException, ServletException
/*     */   {
/* 257 */     if (docType == DocType.HTML) {
/* 258 */       servletExportHTML(jasperPath, params, sourceList, fileName);
/* 259 */       return;
/*     */     }
/*     */ 
/* 262 */     JRAbstractExporter exporter = getJRExporter(docType);
/*     */ 
/* 264 */     String ext = docType.toString().toLowerCase();
/*     */ 
/* 266 */     if (!fileName.toLowerCase().endsWith(ext)) {
/* 267 */       fileName = fileName + "." + ext;
/*     */     }
/*     */ 
/* 270 */     String contentType = "application/";
/* 271 */     if (ext.equals("xls"))
/* 272 */       ext = "excel";
/* 273 */     else if (ext.equals("xml")) {
/* 274 */       contentType = "text/";
/*     */     }
/* 276 */     contentType = contentType + ext;
/*     */ 
/* 278 */     this.response.setContentType(contentType);
/* 279 */     this.response.setHeader("Content-Disposition", "attachment; filename=\"" + 
/* 280 */       URLEncoder.encode(fileName, "UTF-8") + "\"");
/*     */ 
/* 282 */     exporter.setParameter(JRExporterParameter.JASPER_PRINT, 
/* 283 */       getPrintWithBeanList(jasperPath, params, sourceList));
/*     */ 
/* 285 */     OutputStream ouputStream = this.response.getOutputStream();
/*     */ 
/* 287 */     exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
/*     */     try {
/* 289 */       exporter.exportReport();
/*     */     } catch (JRException e) {
/* 291 */       throw new ServletException(e);
/*     */     } finally {
/* 293 */       if (ouputStream != null)
/*     */         try {
/* 295 */           ouputStream.close();
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum DocType
/*     */   {
/* 144 */     PDF, HTML, XLS, XML, RTF;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.web.servlet.ReportUtils
 * JD-Core Version:    0.6.0
 */